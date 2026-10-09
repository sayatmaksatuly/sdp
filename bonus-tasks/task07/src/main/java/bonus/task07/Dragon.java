package bonus.task07;

import java.util.Objects;

public record Dragon(MonsterStats stats) implements Monster {
    public Dragon {
        Objects.requireNonNull(stats, "stats");
    }

    @Override
    public String species() {
        return "Dragon";
    }
}
