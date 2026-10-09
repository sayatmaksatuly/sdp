package bonus.task07;

import java.util.random.RandomGenerator;

public final class SlimeCreator extends MonsterCreator {
    public static final StatRange HEALTH = new StatRange(15, 25);
    public static final StatRange FIRE = new StatRange(0, 5);
    public static final StatRange DEFENSE = new StatRange(1, 4);

    public SlimeCreator(RandomGenerator random) {
        super(random);
    }

    @Override
    protected Monster createMonster() {
        return new Slime(new MonsterStats(roll(HEALTH), roll(FIRE), roll(DEFENSE)));
    }
}
