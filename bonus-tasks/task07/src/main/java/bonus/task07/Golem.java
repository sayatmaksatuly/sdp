package bonus.task07;

import java.util.Objects;

public record Golem(MonsterStats stats) implements Monster {
    public Golem {
        Objects.requireNonNull(stats, "stats");
    }

    @Override
    public String species() {
        return "Golem";
    }
}
