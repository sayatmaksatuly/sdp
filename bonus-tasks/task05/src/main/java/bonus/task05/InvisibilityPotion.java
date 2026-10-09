package bonus.task05;

import java.util.List;
import java.util.random.RandomGenerator;

public final class InvisibilityPotion extends RandomPotion {
    public InvisibilityPotion(RandomGenerator random) {
        super(random, List.of("Your shadow wears a hat.", "Your sneeze appears as glitter.", "Your footprints quack."));
    }

    @Override
    public String name() {
        return "Invisibility";
    }
}
