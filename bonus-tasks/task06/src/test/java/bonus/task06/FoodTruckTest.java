package bonus.task06;

import java.time.*;
import java.util.*;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FoodTruckTest {
    @Test
    void everyTruckHasSevenDifferentSpecials() {
        for (FoodTruck truck : List.of(new TacoTruck(), new RamenTruck(), new WaffleTruck())) {
            var specials = Arrays.stream(DayOfWeek.values()).map(truck::special).toList();
            assertEquals(7, new HashSet<>(specials).size());
            assertTrue(specials.stream().noneMatch(String::isBlank));
            assertThrows(NullPointerException.class, () -> truck.special(null));
        }
    }

    @Test
    void creatorUsesInjectedClockForEachDayAndOriginalTrucksRegressions() {
        for (int day = 0; day < 7; day++) {
            Clock clock = Clock.fixed(Instant.parse("2026-10-05T12:00:00Z").plusSeconds(day * 86400L), ZoneOffset.UTC);
            for (FoodTruckCreator creator : List.of(new TacoTruckCreator(clock), new RamenTruckCreator(clock),
                new WaffleTruckCreator(clock))) {
                DailyServing serving = creator.spawnToday();
                assertEquals(DayOfWeek.of(day + 1), serving.day());
                assertTrue(serving.special().startsWith(serving.day().toString().substring(0,
                    1) + serving.day().toString().substring(1).toLowerCase()));
            }
        }
    }

    @Test
    void clockZoneDeterminesDayAcrossMidnight() {
        Instant instant = Instant.parse("2026-10-05T23:30:00Z");
        assertEquals(DayOfWeek.MONDAY, new TacoTruckCreator(Clock.fixed(instant, ZoneOffset.UTC)).spawnToday().day());
        assertEquals(DayOfWeek.TUESDAY, new TacoTruckCreator(Clock.fixed(instant, ZoneOffset.ofHours(5))).spawnToday().day());
    }

    @Test
    void extensionWorksThroughExistingClientContract() {
        FoodTruckCreator creator = new FoodTruckCreator(Clock.fixed(Instant.parse("2026-10-05T12:00:00Z"), ZoneOffset.UTC)) {
            @Override
            protected FoodTruck createTruck() {
                return new FoodTruck() {
                    @Override
                    public String name() {
                        return "Dumplings";
                    }

                    @Override
                    public String special(DayOfWeek day) {
                        return day + " dumplings";
                    }
                };
            }
        };
        assertEquals("MONDAY dumplings", creator.spawnToday().special());
    }

    @Test
    void nullClockAndNullProductAreRejected() {
        assertThrows(NullPointerException.class, () -> new TacoTruckCreator(null));
        assertThrows(NullPointerException.class, () -> new FoodTruckCreator(Clock.systemUTC()) {
            @Override
            protected FoodTruck createTruck() {
                return null;
            }
        }.spawnToday());
    }
}
