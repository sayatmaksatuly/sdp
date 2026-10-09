package bonus.task06;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Objects;

public final class WaffleTruck implements FoodTruck {
    private static final List<String> SPECIALS = List.of("Monday maple waffle",
        "Tuesday berry waffle",
        "Wednesday chocolate waffle",
        "Thursday banana waffle",
        "Friday caramel waffle",
        "Saturday savory waffle",
        "Sunday cinnamon waffle");

    @Override
    public String name() {
        return "WaffleTruck";
    }

    @Override
    public String special(DayOfWeek day) {
        return SPECIALS.get(Objects.requireNonNull(day, "day").getValue() - 1);
    }
}
