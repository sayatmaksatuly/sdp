package bonus.task15;


public final class WinterBackgroundTrack implements BackgroundTrack {

    @Override
    public String family() {
        return "winter";
    }

    @Override
    public String play(int hour) {
        return "Winter bell music at " + hour + ":00";
    }
}
