package bonus.task15;


public final class SummerParkFactory implements ParkFactory {

    @Override
    public Ride createRide() {
        return new SummerRide();
    }

    @Override
    public BackgroundTrack createBackgroundTrack() {
        return new SummerBackgroundTrack();
    }
}
