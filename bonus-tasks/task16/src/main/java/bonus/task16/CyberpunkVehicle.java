package bonus.task16;


public final class CyberpunkVehicle implements Vehicle {

    @Override
    public String family() {
        return "cyberpunk";
    }

    @Override
    public String travel() {
        return "Hoverbike races through neon streets";
    }
}
