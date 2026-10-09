package bonus.task15;


public final class SummerBackgroundTrack implements BackgroundTrack {

    @Override
    public String family() {
        return "summer";
    }

    @Override
    public String play(int hour) {
        return "Sunny beach music at " + hour + ":00";
    }
}
