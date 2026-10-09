package bonus.task15;


public final class HorrorParkFactory implements ParkFactory {

    @Override
    public Ride createRide() {
        return new HorrorRide();
    }

    @Override
    public BackgroundTrack createBackgroundTrack() {
        return new HorrorBackgroundTrack();
    }
}
