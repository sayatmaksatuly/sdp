package bonus.task06;

import java.time.Clock;
import java.time.LocalDate;
import java.util.Objects;

public abstract class FoodTruckCreator {
    private final Clock clock;

    protected FoodTruckCreator(Clock clock) {
        this.clock = Objects.requireNonNull(clock, "clock");
    }

    public final DailyServing spawnToday() {
        FoodTruck truck = Objects.requireNonNull(createTruck(), "Creator returned no truck");
        var day = LocalDate.now(clock).getDayOfWeek();
        return new DailyServing(truck.name(), day, truck.special(day));
    }

    protected abstract FoodTruck createTruck();
}
