package bonus.task05;

import java.util.List;
import java.util.Random;

public final class Demo {
    public static void main(String[] args) {
        var random = new Random();
        List<PotionCreator> creators = List.of(new HealingCreator(random), new InvisibilityCreator(random), new LevitationCreator(random));
        creators.forEach(creator -> {
            Potion potion = creator.brew();
            System.out.println(potion.name() + ": " + potion.sideEffect());
        });
    }
}
