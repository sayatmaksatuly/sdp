package bonus.task16;

import org.junit.jupiter.api.Test;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

class UniverseTest {

    @Test
    void allFourFamiliesProduceConsistentTriosWithDistinctBehavior() {
        var config = new UniverseConfiguration();
        var descriptions = new java.util.HashSet<String>();
        for (String flag : new String[]{"steampunk", "cyberpunk", "medieval", "space"}) {
            var factory = config.select(flag);
            assertEquals(flag, factory.createHero().family());
            assertEquals(flag, factory.createVillain().family());
            assertEquals(flag, factory.createVehicle().family());
            String description = new Universe(factory).explore();
            assertTrue(description.contains(factory.createHero().act()));
            assertTrue(description.contains(factory.createVillain().act()));
            assertTrue(description.contains(factory.createVehicle().travel()));
            descriptions.add(description);
        }
        assertEquals(4, descriptions.size());
    }

    @Test
    void fourthUniverseLeavesOriginalThreeUnchanged() {
        var original = new UniverseConfiguration(Map.of("steampunk", SteampunkUniverseFactory::new,
        "cyberpunk", CyberpunkUniverseFactory::new, "medieval", MedievalUniverseFactory::new));
        var extended = new UniverseConfiguration();
        for (String flag : new String[]{"steampunk", "cyberpunk", "medieval"}) {
            assertEquals(new Universe(original.select(flag)).explore(), new Universe(extended.select(flag)).explore());
        }
        assertEquals("space", new Universe(extended.select(" SPACE ")).family());
    }

    @Test
    void invalidFlagsAndCrossUniverseProductsFailClearly() {
        assertThrows(IllegalArgumentException.class, () -> new UniverseConfiguration().select(""));
        assertThrows(IllegalArgumentException.class, () -> new UniverseConfiguration().select("missing"));
        assertThrows(NullPointerException.class, () -> new UniverseConfiguration().select(null));
        assertThrows(NullPointerException.class, () -> new Universe(null));
        UniverseFactory mixed = new UniverseFactory() {
            public Hero createHero() {
                return new MedievalHero();
            }
            public Villain createVillain() {
                return new CyberpunkVillain();
            }
            public Vehicle createVehicle() {
                return new MedievalVehicle();
            }
        };
        assertThrows(IllegalArgumentException.class, () -> new Universe(mixed));
    }

    @Test
    void clientCreatesEachProductOnceAndDelegatesThroughInterfaces() {
        int[] creations = new int[3];
        int[] actions = new int[3];
        UniverseFactory recording = new UniverseFactory() {
            public Hero createHero() {
                creations[0]++;
                return new Hero() {
                    public String family() {
                        return "custom";
                    }
                    public String act() {
                        actions[0]++;
                        return "hero";
                    }
                };
            }
            public Villain createVillain() {
                creations[1]++;
                return new Villain() {
                    public String family() {
                        return "custom";
                    }
                    public String act() {
                        actions[1]++;
                        return "villain";
                    }
                };
            }
            public Vehicle createVehicle() {
                creations[2]++;
                return new Vehicle() {
                    public String family() {
                        return "custom";
                    }
                    public String travel() {
                        actions[2]++;
                        return "vehicle";
                    }
                };
            }
        };
        var universe = new Universe(recording);
        assertEquals("custom: hero | villain | vehicle", universe.explore());
        assertArrayEquals(new int[]{1, 1, 1}
        , creations);
        assertArrayEquals(new int[]{1, 1, 1}
        , actions);
    }
}
