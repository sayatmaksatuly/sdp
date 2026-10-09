package bonus.task15;


public final class WinterRide implements Ride {

    @Override
    public String family() {
        return "winter";
    }

    @Override
    public String operate(int hour) {
        return "Snowflake Carousel at " + hour + ":00";
    }
}
