package bonus.task06;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Objects;

public final class RamenTruck implements FoodTruck {
    private static final List<String> SPECIALS = List.of("Monday miso ramen",
        "Tuesday shoyu ramen",
        "Wednesday spicy ramen",
        "Thursday sesame ramen",
        "Friday seafood ramen",
        "Saturday curry ramen",
        "Sunday vegetable ramen");

    @Override
    public String name() {
        return "RamenTruck";
    }

    @Override
    public String special(DayOfWeek day) {
        return SPECIALS.get(Objects.requireNonNull(day, "day").getValue() - 1);
    }
}
