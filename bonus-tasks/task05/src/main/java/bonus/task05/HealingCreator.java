package bonus.task05;

import java.util.Objects;
import java.util.random.RandomGenerator;

public final class HealingCreator extends PotionCreator {
    private final RandomGenerator random;

    public HealingCreator(RandomGenerator random) {
        this.random = Objects.requireNonNull(random, "random");
    }

    @Override
    protected Potion createPotion() {
        return new HealingPotion(random);
    }
}
