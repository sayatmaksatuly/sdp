package bonus.task12;

public record MedievalCoinPurse(long gold, long silver) {
    public MedievalCoinPurse {
        if (gold < 0 || silver < 0) {
            throw new IllegalArgumentException("Coin counts must be nonnegative");
        }
    }

    public long countGoldPieces() { return gold; }

    public long countSilverPieces() { return silver; }
}
