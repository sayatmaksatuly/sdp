package bonus.task06;

import java.time.DayOfWeek;
import java.util.Objects;

public record DailyServing(String truckName, DayOfWeek day, String special) {
    public DailyServing {
        Objects.requireNonNull(truckName, "truckName");
        Objects.requireNonNull(day, "day");
        Objects.requireNonNull(special, "special");
    }
}
