package bonus.task15;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ThemeParkTest {

    @Test
    void fullDaysCoverEveryHourFromOpeningToClosingInBothModes() {
        var config = new ParkConfiguration();
        for (String flag : new String[]{"horror", "summer"}) {
            var factory = config.select(flag);
            assertEquals(flag, factory.createRide().family());
            assertEquals(flag, factory.createBackgroundTrack().family());
            var day = new ThemePark(factory).simulateDay();
            assertEquals(13, day.size());
            assertEquals("OPEN", day.getFirst().phase());
            assertEquals("CLOSE", day.getLast().phase());
            for (int i = 0; i < day.size(); i++) {
                assertEquals(9 + i, day.get(i).hour());
                assertEquals(flag, day.get(i).family());
            }
            assertEquals("OPERATING", day.get(1).phase());
            assertThrows(UnsupportedOperationException.class, () -> day.clear());
        }
    }

    @Test
    void eachOpenHourDelegatesToBothProductsAndClosingStopsThem() {
        List<Integer> rides = new ArrayList<>();
        List<Integer> tracks = new ArrayList<>();
        ParkFactory recording = new ParkFactory() {
            public Ride createRide() {
                return new Ride() {
                    public String family() {
                        return "test";
                    }
                    public String operate(int hour) {
                        rides.add(hour);
                        return "ride";
                    }
                };
            }
            public BackgroundTrack createBackgroundTrack() {
                return new BackgroundTrack() {
                    public String family() {
                        return "test";
                    }
                    public String play(int hour) {
                        tracks.add(hour);
                        return "track";
                    }
                };
            }
        };
        var day = new ThemePark(recording).simulateDay(0, 23);
        assertEquals(24, day.size());
        assertEquals(23, rides.size());
        assertEquals(rides, tracks);
        assertEquals(0, rides.getFirst());
        assertEquals(22, rides.getLast());
        assertEquals("Music stopped", day.getLast().backgroundTrack());
    }

    @Test
    void invalidSchedulesFlagsAndMixedFamiliesAreRejected() {
        var park = new ThemePark(new HorrorParkFactory());
        assertThrows(IllegalArgumentException.class, () -> park.simulateDay(-1, 21));
        assertThrows(IllegalArgumentException.class, () -> park.simulateDay(9, 24));
        assertThrows(IllegalArgumentException.class, () -> park.simulateDay(9, 9));
        assertThrows(IllegalArgumentException.class, () -> park.simulateDay(21, 9));
        assertEquals(2, park.simulateDay(22, 23).size());
        assertThrows(IllegalArgumentException.class, () -> new ParkConfiguration().select("unknown"));
        assertThrows(NullPointerException.class, () -> new ThemePark(null));
        ParkFactory mixed = new ParkFactory() {
            public Ride createRide() {
                return new HorrorRide();
            }
            public BackgroundTrack createBackgroundTrack() {
                return new SummerBackgroundTrack();
            }
        };
        assertThrows(IllegalArgumentException.class, () -> new ThemePark(mixed));
    }

    @Test
    void winterExtensionDoesNotChangeHorrorOrSummerDays() {
        var original = new ParkConfiguration(Map.of("horror", HorrorParkFactory::new, "summer", SummerParkFactory::new));
        var extended = new ParkConfiguration();
        for (String flag : new String[]{"horror", "summer"}) {
            assertEquals(new ThemePark(original.select(flag)).simulateDay(), new ThemePark(extended.select(flag)).simulateDay());
        }
        assertTrue(new ThemePark(extended.select("winter")).simulateDay().stream().allMatch(event -> event.family().equals("winter")));
    }
}
