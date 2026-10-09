package bonus.task14;

import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CateringEventTest {

    @Test
    void threeMoodsHaveMatchingMenusAndDecor() {
        var config = new CateringConfiguration();
        for (String flag : new String[]{"wedding", "funeral", "birthday"}) {
            var factory = config.select(flag);
            assertEquals(flag, factory.createMenu().family());
            assertEquals(flag, factory.createDecor().family());
            assertTrue(new CateringEvent(factory).host().contains(factory.createMenu().describe()));
            assertTrue(new CateringEvent(factory).host().contains(factory.createDecor().describe()));
        }
    }

    @Test
    void thirdMoodDoesNotRegressOriginalFactories() {
        var original = new CateringConfiguration(Map.of("wedding", WeddingCateringFactory::new, "funeral", FuneralCateringFactory::new));
        var extended = new CateringConfiguration();
        for (String flag : new String[]{"wedding", "funeral"}) {
            assertEquals(new CateringEvent(original.select(flag)).host(), new CateringEvent(extended.select(flag)).host());
        }
        assertEquals("birthday", new CateringEvent(extended.select(" BIRTHDAY ")).mood());
    }

    @Test
    void invalidFlagsAndMixedMoodsAreRejected() {
        assertThrows(IllegalArgumentException.class, () -> new CateringConfiguration().select(""));
        assertThrows(IllegalArgumentException.class, () -> new CateringConfiguration().select("unknown"));
        assertThrows(NullPointerException.class, () -> new CateringConfiguration().select(null));
        assertThrows(NullPointerException.class, () -> new CateringEvent(null));
        CateringFactory mixed = new CateringFactory() {
            public Menu createMenu() {
                return new WeddingMenu();
            }
            public Decor createDecor() {
                return new FuneralDecor();
            }
        };
        assertThrows(IllegalArgumentException.class, () -> new CateringEvent(mixed));
    }

    @Test
    void clientCreatesEachProductOnceAndDelegatesThroughInterfaces() {
        int[] creations = new int[2];
        int[] actions = new int[2];
        CateringFactory recording = new CateringFactory() {
            public Menu createMenu() {
                creations[0]++;
                return new Menu() {
                    public String family() {
                        return "custom";
                    }
                    public String describe() {
                        actions[0]++;
                        return "custom menu";
                    }
                };
            }
            public Decor createDecor() {
                creations[1]++;
                return new Decor() {
                    public String family() {
                        return "custom";
                    }
                    public String describe() {
                        actions[1]++;
                        return "custom decor";
                    }
                };
            }
        };
        var event = new CateringEvent(recording);
        assertEquals("custom: custom menu | custom decor", event.host());
        assertArrayEquals(new int[]{1, 1}
        , creations);
        assertArrayEquals(new int[]{1, 1}
        , actions);
    }
}
