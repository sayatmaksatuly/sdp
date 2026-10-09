package bonus.task15;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class ThemePark {
    private final Ride ride;
    private final BackgroundTrack track;
    public ThemePark(ParkFactory factory) {
        Objects.requireNonNull(factory, "factory");
        ride = Objects.requireNonNull(factory.createRide(), "ride");
        track = Objects.requireNonNull(factory.createBackgroundTrack(), "track");
        if (!ride.family().equals(track.family())) {
            throw new IllegalArgumentException("Mixed park modes");
        }
    }
    public List<DayEvent> simulateDay() {
        return simulateDay(9, 21);
    }
    public List<DayEvent> simulateDay(int openingHour, int closingHour) {
        if (openingHour < 0 || closingHour > 23 || openingHour >= closingHour) {
            throw new IllegalArgumentException("Require 0 <= opening < closing <= 23");
        }
        List<DayEvent> events = new ArrayList<>();
        for (int hour = openingHour; hour < closingHour; hour++) {
            String phase = hour == openingHour ? "OPEN" : "OPERATING";
            events.add(new DayEvent(hour, phase, ride.family(), ride.operate(hour), track.play(hour)));
        }
        events.add(new DayEvent(closingHour, "CLOSE", ride.family(), "All rides stopped", "Music stopped"));
        return List.copyOf(events);
    }
}
