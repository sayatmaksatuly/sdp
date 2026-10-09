package bonus.task06;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Objects;

public final class TacoTruck implements FoodTruck {
    private static final List<String> SPECIALS = List.of("Monday mole taco",
        "Tuesday cactus taco",
        "Wednesday fish taco",
        "Thursday mushroom taco",
        "Friday fiesta taco",
        "Saturday smoky bean taco",
        "Sunday breakfast taco");

    @Override
    public String name() {
        return "TacoTruck";
    }

    @Override
    public String special(DayOfWeek day) {
        return SPECIALS.get(Objects.requireNonNull(day, "day").getValue() - 1);
    }
}
