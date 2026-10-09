package bonus.task07;

import java.util.random.RandomGenerator;

public final class GolemCreator extends MonsterCreator {
    public static final StatRange HEALTH = new StatRange(80, 110);
    public static final StatRange FIRE = new StatRange(10, 20);
    public static final StatRange DEFENSE = new StatRange(45, 65);

    public GolemCreator(RandomGenerator random) {
        super(random);
    }

    @Override
    protected Monster createMonster() {
        return new Golem(new MonsterStats(roll(HEALTH), roll(FIRE), roll(DEFENSE)));
    }
}
