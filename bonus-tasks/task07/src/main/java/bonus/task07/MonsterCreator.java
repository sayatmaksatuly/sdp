package bonus.task07;

import java.util.Objects;
import java.util.random.RandomGenerator;

public abstract class MonsterCreator {
    private final RandomGenerator random;

    protected MonsterCreator(RandomGenerator random) {
        this.random = Objects.requireNonNull(random, "random");
    }

    public final Monster hatch() {
        return Objects.requireNonNull(createMonster(), "Creator returned no monster");
    }

    protected final int roll(StatRange range) {
        return range.roll(random);
    }

    protected abstract Monster createMonster();
}
