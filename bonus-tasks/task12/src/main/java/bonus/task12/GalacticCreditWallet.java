package bonus.task12;

public record GalacticCreditWallet(long credits, long fragments) {
    public GalacticCreditWallet {
        if (credits < 0 || fragments < 0) {
            throw new IllegalArgumentException("Currency counts must be nonnegative");
        }
    }

    public long scanCredits() { return credits; }

    public long scanFragments() { return fragments; }
}
