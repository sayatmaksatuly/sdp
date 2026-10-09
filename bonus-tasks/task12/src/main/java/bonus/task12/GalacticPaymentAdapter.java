package bonus.task12;

import java.util.Objects;

public final class GalacticPaymentAdapter implements PaymentProcessor {
    private final ChargeLedger ledger;

    public GalacticPaymentAdapter(GalacticCreditWallet wallet, ConversionRates rates) {
        Objects.requireNonNull(wallet, "wallet");
        Objects.requireNonNull(rates, "rates");
        ledger = new ChargeLedger(rates.convert(wallet.scanCredits(), wallet.scanFragments()));
    }

    @Override
    public boolean charge(long amountInCents) { return ledger.charge(amountInCents); }

    public long remainingCents() { return ledger.remaining(); }
}
