package bonus.task13;

import org.junit.jupiter.api.Test;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

class CerealBoxTest {

    @Test
    void everyFlagCreatesAMatchingFamily() {
        var config = new CerealConfiguration();
        for (String flag : new String[]{"superhero", "dinosaur"}) {
            var factory = config.select(flag);
            assertEquals(flag, factory.createMascot().family());
            assertEquals(flag, factory.createJingle().family());
            var box = new CerealBox(factory);
            assertEquals(flag, box.family());
            assertTrue(box.advertise().contains(factory.createMascot().introduce()));
            assertTrue(box.advertise().contains(factory.createJingle().sing()));
        }
        assertEquals("dinosaur", new CerealBox(config.select(" DINOSAUR ")).family());
    }

    @Test
    void rejectsInvalidConfigurationAndMixedProducts() {
        assertThrows(IllegalArgumentException.class, () -> new CerealConfiguration().select("unknown"));
        assertThrows(IllegalArgumentException.class, () -> new CerealConfiguration().select(""));
        assertThrows(NullPointerException.class, () -> new CerealConfiguration().select(null));
        assertThrows(IllegalArgumentException.class, () -> new CerealConfiguration(Map.of()));
        assertThrows(NullPointerException.class, () -> new CerealBox(null));
        CerealFactory mixed = new CerealFactory() {
            public Mascot createMascot() {
                return new SuperheroMascot();
            }
            public Jingle createJingle() {
                return new DinosaurJingle();
            }
        };
        assertThrows(IllegalArgumentException.class, () -> new CerealBox(mixed));
    }

    @Test
    void registryExtensionLeavesExistingFamiliesWorking() {
        var config = new CerealConfiguration(Map.of("custom", DinosaurCerealFactory::new, "superhero", SuperheroCerealFactory::new));
        assertEquals("dinosaur", new CerealBox(config.select("custom")).family());
        assertEquals("superhero", new CerealBox(config.select("superhero")).family());
    }

    @Test
    void clientCreatesEachProductOnceAndDelegatesThroughInterfaces() {
        int[] creations = new int[2];
        int[] actions = new int[2];
        CerealFactory recording = new CerealFactory() {
            public Mascot createMascot() {
                creations[0]++;
                return new Mascot() {
                    public String family() {
                        return "custom";
                    }
                    public String introduce() {
                        actions[0]++;
                        return "custom mascot";
                    }
                };
            }
            public Jingle createJingle() {
                creations[1]++;
                return new Jingle() {
                    public String family() {
                        return "custom";
                    }
                    public String sing() {
                        actions[1]++;
                        return "custom song";
                    }
                };
            }
        };
        var box = new CerealBox(recording);
        assertEquals("custom mascot custom song", box.advertise());
        assertArrayEquals(new int[]{1, 1}
        , creations);
        assertArrayEquals(new int[]{1, 1}
        , actions);
    }
}
