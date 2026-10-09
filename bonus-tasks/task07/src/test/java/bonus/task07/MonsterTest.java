package bonus.task07;

import java.util.List;
import java.util.Random;
import java.util.random.RandomGenerator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MonsterTest {
    private record EndpointRandom(boolean maximum) implements RandomGenerator {
        @Override
        public long nextLong() {
            return 0;
        }

        @Override
        public int nextInt(int origin, int bound) {
            return maximum ? bound - 1 : origin;
        }
    }

    @Test
    void deterministicMinimumAndMaximumForEveryType() {
        assertStats(new SlimeCreator(new EndpointRandom(false)), 15, 0, 1);
        assertStats(new SlimeCreator(new EndpointRandom(true)), 25, 5, 4);
        assertStats(new DragonCreator(new EndpointRandom(false)), 120, 70, 20);
        assertStats(new DragonCreator(new EndpointRandom(true)), 160, 100, 35);
        assertStats(new GolemCreator(new EndpointRandom(false)), 80, 10, 45);
        assertStats(new GolemCreator(new EndpointRandom(true)), 110, 20, 65);
        assertTrue(DragonCreator.FIRE.minimum() > SlimeCreator.FIRE.maximum());
    }

    @Test
    void seededRollsAreReproducibleAndVaryWithinBounds() {
        MonsterCreator first = new DragonCreator(new Random(42));
        MonsterCreator second = new DragonCreator(new Random(42));
        var rolls = java.util.stream.IntStream.range(0, 100).mapToObj(i -> first.hatch().stats()).toList();
        assertEquals(rolls, java.util.stream.IntStream.range(0, 100).mapToObj(i -> second.hatch().stats()).toList());
        assertTrue(rolls.stream().distinct().count() > 1);
        assertTrue(rolls.stream().allMatch(s -> s.health() >= 120 && s.health() <= 160 && s.fire() >= 70
                && s.fire() <= 100 && s.defense() >= 20 && s.defense() <= 35));
    }

    @Test
    void thirdMonsterAndExtensionUseSameUnawareClient() {
        List<MonsterCreator> creators = List.of(new SlimeCreator(new EndpointRandom(false)),
            new DragonCreator(new EndpointRandom(false)), new GolemCreator(new EndpointRandom(false)));
        assertEquals(List.of("Slime", "Dragon", "Golem"), creators.stream().map(c -> c.hatch().species()).toList());
        MonsterCreator extension = new MonsterCreator(new EndpointRandom(false)) {
            @Override
            protected Monster createMonster() {
                return new Monster() {
                    @Override
                    public String species() {
                        return "Phoenix";
                    }

                    @Override
                    public MonsterStats stats() {
                        return new MonsterStats(50, 55, 5);
                    }
                };
            }
        };
        assertEquals("Phoenix", extension.hatch().species());
    }

    @Test
    void invalidRangesStatsRandomAndProductsFailFast() {
        assertThrows(IllegalArgumentException.class, () -> new StatRange(10, 9));
        assertThrows(IllegalArgumentException.class, () -> new StatRange(-1, 9));
        assertThrows(IllegalArgumentException.class, () -> new StatRange(0, Integer.MAX_VALUE));
        assertEquals(4, new StatRange(4, 4).roll(new Random(1)));
        assertThrows(IllegalArgumentException.class, () -> new MonsterStats(0, 1, 1));
        assertThrows(NullPointerException.class, () -> new DragonCreator(null));
        assertThrows(NullPointerException.class, () -> new Slime(null));
        assertThrows(NullPointerException.class, () -> new MonsterCreator(new Random(1)) {
            @Override
            protected Monster createMonster() {
                return null;
            }
        }.hatch());
    }

    private void assertStats(MonsterCreator creator, int health, int fire, int defense) {
        assertEquals(new MonsterStats(health, fire, defense), creator.hatch().stats());
    }
}
