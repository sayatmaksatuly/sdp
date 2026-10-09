package bonus.task12;

import java.math.BigDecimal;
import java.util.List;

public final class Demo {
    private Demo() { }

    public static void main(String[] args) {
        List<PaymentProcessor> processors = List.of(
                new MedievalPaymentAdapter(new MedievalCoinPurse(1, 2),
                        new ConversionRates(new BigDecimal("100.25"), new BigDecimal("10.25"))),
                new GalacticPaymentAdapter(new GalacticCreditWallet(2, 5),
                        new ConversionRates(new BigDecimal("50.25"), new BigDecimal("4.05"))));
        for (PaymentProcessor processor : processors) {
            Checkout checkout = new Checkout(processor);
            System.out.println("Charge 121 cents: " + checkout.pay(121));
            System.out.println("Charge 1 more cent: " + checkout.pay(1));
        }
    }
}
