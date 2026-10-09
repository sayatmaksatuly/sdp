package bonus.task06;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.List;

public final class Demo {
    public static void main(String[] args) {
        for (int day = 0; day < 7; day++) {
            Clock clock = Clock.fixed(Instant.parse("2026-10-05T12:00:00Z").plusSeconds(day * 86400L), ZoneOffset.UTC);
            List<FoodTruckCreator> creators = List.of(new TacoTruckCreator(clock), new RamenTruckCreator(clock),
                new WaffleTruckCreator(clock));
            creators.forEach(creator -> System.out.println(creator.spawnToday()));
        }
    }
}
