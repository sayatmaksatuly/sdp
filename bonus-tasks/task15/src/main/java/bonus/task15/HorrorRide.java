package bonus.task15;


public final class HorrorRide implements Ride {

    @Override
    public String family() {
        return "horror";
    }

    @Override
    public String operate(int hour) {
        return "Haunted Mansion at " + hour + ":00";
    }
}
