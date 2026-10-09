package bonus.task05;

import java.util.Objects;

public abstract class PotionCreator {
    public final Potion brew() {
        return Objects.requireNonNull(createPotion(), "Creator returned no potion");
    }

    protected abstract Potion createPotion();
}
