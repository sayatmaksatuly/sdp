package bonus.task05;

import java.util.List;
import java.util.random.RandomGenerator;

public final class LevitationPotion extends RandomPotion {
    public LevitationPotion(RandomGenerator random) {
        super(random, List.of("Your hair floats first.", "Your pockets attract clouds.", "Your socks orbit your ankles."));
    }

    @Override
    public String name() {
        return "Levitation";
    }
}
