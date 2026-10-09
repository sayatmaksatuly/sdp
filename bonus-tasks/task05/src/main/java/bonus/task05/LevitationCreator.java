package bonus.task05;

import java.util.Objects;
import java.util.random.RandomGenerator;

public final class LevitationCreator extends PotionCreator {
    private final RandomGenerator random;

    public LevitationCreator(RandomGenerator random) {
        this.random = Objects.requireNonNull(random, "random");
    }

    @Override
    protected Potion createPotion() {
        return new LevitationPotion(random);
    }
}
