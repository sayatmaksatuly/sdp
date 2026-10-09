package bonus.task15;


public final class SummerRide implements Ride {

    @Override
    public String family() {
        return "summer";
    }

    @Override
    public String operate(int hour) {
        return "Splash Coaster at " + hour + ":00";
    }
}
