package bonus.task05;

import java.util.List;
import java.util.Objects;
import java.util.random.RandomGenerator;

public abstract class RandomPotion implements Potion {
    private final RandomGenerator random;
    private final List<String> effects;

    protected RandomPotion(RandomGenerator random, List<String> effects) {
        this.random = Objects.requireNonNull(random, "random");
        this.effects = List.copyOf(effects);
        if (this.effects.size() < 3 || this.effects.stream().anyMatch(String::isBlank)) {
            throw new IllegalArgumentException("At least three nonblank side effects required");
        }
    }

    @Override
    public final String sideEffect() {
        return effects.get(random.nextInt(effects.size()));
    }
}
