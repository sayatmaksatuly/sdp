package bonus.task15;


public final class HorrorBackgroundTrack implements BackgroundTrack {

    @Override
    public String family() {
        return "horror";
    }

    @Override
    public String play(int hour) {
        return "Spooky organ music at " + hour + ":00";
    }
}
