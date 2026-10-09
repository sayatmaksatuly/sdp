package bonus.task16;


public final class SpaceVehicle implements Vehicle {

    @Override
    public String family() {
        return "space";
    }

    @Override
    public String travel() {
        return "Ion starship flies between planets";
    }
}
