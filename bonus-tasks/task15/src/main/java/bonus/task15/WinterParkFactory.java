package bonus.task15;


public final class WinterParkFactory implements ParkFactory {

    @Override
    public Ride createRide() {
        return new WinterRide();
    }

    @Override
    public BackgroundTrack createBackgroundTrack() {
        return new WinterBackgroundTrack();
    }
}
