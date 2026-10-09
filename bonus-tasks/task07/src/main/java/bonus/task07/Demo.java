package bonus.task07;

import java.util.List;
import java.util.Random;

public final class Demo {
    public static void main(String[] args) {
        var random = new Random();
        List<MonsterCreator> hatcheries = List.of(new SlimeCreator(random), new DragonCreator(random), new GolemCreator(random));
        hatcheries.forEach(creator -> {
            Monster monster = creator.hatch();
            System.out.println(monster.species() + " " + monster.stats());
        });
    }
}
