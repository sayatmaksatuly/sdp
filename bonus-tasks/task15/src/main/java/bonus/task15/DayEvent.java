package bonus.task15;


public record DayEvent(int hour, String phase, String family, String ride, String backgroundTrack) {
    public String describe() {
        return "%02d:00 %s [%s] %s | %s".formatted(hour, phase, family, ride, backgroundTrack);
    }
}
