package bonus.task05;

import java.util.Objects;
import java.util.random.RandomGenerator;

public final class InvisibilityCreator extends PotionCreator {
    private final RandomGenerator random;

    public InvisibilityCreator(RandomGenerator random) {
        this.random = Objects.requireNonNull(random, "random");
    }

    @Override
    protected Potion createPotion() {
        return new InvisibilityPotion(random);
    }
}
