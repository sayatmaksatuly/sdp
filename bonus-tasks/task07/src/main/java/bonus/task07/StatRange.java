package bonus.task07;

import java.util.Objects;
import java.util.random.RandomGenerator;

public record StatRange(int minimum, int maximum) {
    public StatRange {
        if (minimum < 0 || maximum < minimum || maximum == Integer.MAX_VALUE) {
            throw new IllegalArgumentException("Invalid inclusive stat range");
        }
    }

    public int roll(RandomGenerator random) {
        return Objects.requireNonNull(random, "random").nextInt(minimum, maximum + 1);
    }
}
