package bonus.task05;

import java.util.List;
import java.util.random.RandomGenerator;

public final class HealingPotion extends RandomPotion {
    public HealingPotion(RandomGenerator random) {
        super(random, List.of("Your hiccups glow green.", "Your eyebrows sing lullabies.", "Your shoes smell like mint."));
    }

    @Override
    public String name() {
        return "Healing";
    }
}
