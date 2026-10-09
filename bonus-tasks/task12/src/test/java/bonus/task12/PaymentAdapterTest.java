package bonus.task12;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PaymentAdapterTest {
    private ConversionRates rates(String major, String minor) {
        return new ConversionRates(new BigDecimal(major), new BigDecimal(minor));
    }

    @Test
    void combinesCurrenciesAndRoundsOnceHalfUp() {
        assertEquals(121, rates("100.25", "10.25").convert(1, 2));
        assertEquals(1, rates("0.25", "0.25").convert(1, 1));
        assertEquals(0, rates("0.24", "0.25").convert(1, 1));
        assertEquals(0, rates("0.1", "0.2").convert(0, 0));
    }

    @Test
    void exactBalanceInsufficientFundsAndZeroAreHandledAtomically() {
        MedievalPaymentAdapter adapter = new MedievalPaymentAdapter(new MedievalCoinPurse(1, 2), rates("100.25", "10.25"));
        assertFalse(adapter.charge(122));
        assertEquals(121, adapter.remainingCents());
        assertTrue(adapter.charge(0));
        assertTrue(adapter.charge(120));
        assertTrue(adapter.charge(1));
        assertFalse(adapter.charge(1));
        assertTrue(adapter.charge(0));
        assertEquals(0, adapter.remainingCents());
    }

    @Test
    void newCurrencyWorksThroughUnchangedCheckoutAndOriginalStillWorks() {
        List<PaymentProcessor> processors = List.of(
                new MedievalPaymentAdapter(new MedievalCoinPurse(1, 2), rates("100.25", "10.25")),
                new GalacticPaymentAdapter(new GalacticCreditWallet(2, 5), rates("50.25", "4.05")));
        for (PaymentProcessor processor : processors) {
            Checkout checkout = new Checkout(processor);
            assertTrue(checkout.pay(121));
            assertFalse(checkout.pay(1));
            assertTrue(checkout.pay(0));
            assertThrows(IllegalArgumentException.class, () -> checkout.pay(-1));
        }
    }

    @Test
    void negativeValuesAndInvalidDependenciesAreRejected() {
        assertThrows(IllegalArgumentException.class, () -> new MedievalCoinPurse(-1, 0));
        assertThrows(IllegalArgumentException.class, () -> new MedievalCoinPurse(0, -1));
        assertThrows(IllegalArgumentException.class, () -> new GalacticCreditWallet(-1, 0));
        assertThrows(IllegalArgumentException.class, () -> new GalacticCreditWallet(0, -1));
        assertThrows(IllegalArgumentException.class, () -> rates("0", "1"));
        assertThrows(IllegalArgumentException.class, () -> rates("1", "-1"));
        assertThrows(IllegalArgumentException.class, () -> rates("1", "1").convert(-1, 0));
        assertThrows(NullPointerException.class, () -> new ConversionRates(null, BigDecimal.ONE));
        assertThrows(NullPointerException.class, () -> new MedievalPaymentAdapter(null, rates("1", "1")));
        assertThrows(NullPointerException.class, () -> new GalacticPaymentAdapter(null, rates("1", "1")));
        assertThrows(NullPointerException.class, () -> new MedievalPaymentAdapter(new MedievalCoinPurse(0, 0), null));
        assertThrows(NullPointerException.class, () -> new GalacticPaymentAdapter(new GalacticCreditWallet(0, 0), null));
        assertThrows(NullPointerException.class, () -> new Checkout(null));
    }

    @Test
    void longBoundaryIsSupportedAndConversionOverflowIsRejected() {
        MedievalPaymentAdapter adapter = new MedievalPaymentAdapter(new MedievalCoinPurse(Long.MAX_VALUE, 0), rates("1", "1"));
        assertTrue(adapter.charge(Long.MAX_VALUE));
        assertEquals(0, adapter.remainingCents());
        assertThrows(ArithmeticException.class, () -> rates("1", "1").convert(Long.MAX_VALUE, 1));
        assertThrows(ArithmeticException.class, () -> rates("2", "1").convert(Long.MAX_VALUE, 0));
        assertFalse(new MedievalPaymentAdapter(new MedievalCoinPurse(0, 0), rates("1", "1")).charge(1));
    }

    @Test
    void concurrentChargesCannotOverspend() throws Exception {
        MedievalPaymentAdapter adapter = new MedievalPaymentAdapter(new MedievalCoinPurse(1, 0), rates("100", "1"));
        try (var executor = Executors.newFixedThreadPool(2)) {
            Callable<Boolean> charge = () -> adapter.charge(60);
            var futures = executor.invokeAll(List.of(charge, charge));
            long successes = 0;
            for (var future : futures) {
                if (future.get()) {
                    successes++;
                }
            }
            assertEquals(1, successes);
            assertEquals(40, adapter.remainingCents());
        }
    }
}
