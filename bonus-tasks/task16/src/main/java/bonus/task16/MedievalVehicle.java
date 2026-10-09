package bonus.task16;


public final class MedievalVehicle implements Vehicle {

    @Override
    public String family() {
        return "medieval";
    }

    @Override
    public String travel() {
        return "Royal horse gallops along castle roads";
    }
}
