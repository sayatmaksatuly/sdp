package bonus.task07;

import java.util.random.RandomGenerator;

public final class DragonCreator extends MonsterCreator {
    public static final StatRange HEALTH = new StatRange(120, 160);
    public static final StatRange FIRE = new StatRange(70, 100);
    public static final StatRange DEFENSE = new StatRange(20, 35);

    public DragonCreator(RandomGenerator random) {
        super(random);
    }

    @Override
    protected Monster createMonster() {
        return new Dragon(new MonsterStats(roll(HEALTH), roll(FIRE), roll(DEFENSE)));
    }
}
