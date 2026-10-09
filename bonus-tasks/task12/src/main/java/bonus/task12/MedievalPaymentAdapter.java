package bonus.task12;

import java.util.Objects;

public final class MedievalPaymentAdapter implements PaymentProcessor {
    private final ChargeLedger ledger;

    public MedievalPaymentAdapter(MedievalCoinPurse purse, ConversionRates rates) {
        Objects.requireNonNull(purse, "purse");
        Objects.requireNonNull(rates, "rates");
        ledger = new ChargeLedger(rates.convert(purse.countGoldPieces(), purse.countSilverPieces()));
    }

    @Override
    public boolean charge(long amountInCents) { return ledger.charge(amountInCents); }

    public long remainingCents() { return ledger.remaining(); }
}
