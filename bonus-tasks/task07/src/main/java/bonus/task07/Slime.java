package bonus.task07;

import java.util.Objects;

public record Slime(MonsterStats stats) implements Monster {
    public Slime {
        Objects.requireNonNull(stats, "stats");
    }

    @Override
    public String species() {
        return "Slime";
    }
}
