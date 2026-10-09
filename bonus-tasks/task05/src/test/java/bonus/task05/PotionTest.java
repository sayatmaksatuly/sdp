package bonus.task05;

import java.util.List;
import java.util.random.RandomGenerator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PotionTest {
    private static final class SequenceRandom implements RandomGenerator {
        private int next;

        @Override
        public long nextLong() {
            return 0;
        }

        @Override
        public int nextInt(int bound) {
            return Math.floorMod(next++, bound);
        }
    }

    @Test
    void allThreePotionsSelectAtLeastThreeDistinctEffects() {
        for (PotionCreator creator : creators()) {
            Potion potion = creator.brew();
            var effects = List.of(potion.sideEffect(), potion.sideEffect(), potion.sideEffect());
            assertEquals(3, effects.stream().distinct().count());
            assertTrue(effects.stream().noneMatch(s -> s.isBlank() || s.contains("\n")));
            assertEquals(effects.getFirst(), potion.sideEffect());
        }
    }

    @Test
    void thirdPotionUsesUnchangedCreatorContractAndOriginalProductsStillWork() {
        assertEquals(List.of("Healing", "Invisibility", "Levitation"), creators().stream().map(c -> c.brew().name()).toList());
        PotionCreator extension = new PotionCreator() {
            @Override
            protected Potion createPotion() {
                return new RandomPotion(new SequenceRandom(), List.of("a", "b", "c")) {
                    @Override
                    public String name() {
                        return "Extension";
                    }
                };
            }
        };
        assertEquals("a", extension.brew().sideEffect());
    }

    @Test
    void invalidRandomAndMalformedProductsAreRejected() {
        assertThrows(NullPointerException.class, () -> new HealingCreator(null));
        assertThrows(NullPointerException.class, () -> new PotionCreator() {
            @Override
            protected Potion createPotion() {
                return null;
            }
        }.brew());
        assertThrows(IllegalArgumentException.class, () -> new RandomPotion(new SequenceRandom(), List.of("a", "b")) {
            @Override
            public String name() {
                return "Bad";
            }
        });
    }

    private List<PotionCreator> creators() {
        return List.of(new HealingCreator(new SequenceRandom()), new InvisibilityCreator(new SequenceRandom()),
            new LevitationCreator(new SequenceRandom()));
    }
}
