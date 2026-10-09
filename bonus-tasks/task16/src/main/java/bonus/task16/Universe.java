package bonus.task16;

import java.util.Objects;

public final class Universe {
    private final Hero hero;
    private final Villain villain;
    private final Vehicle vehicle;
    public Universe(UniverseFactory factory) {
        Objects.requireNonNull(factory, "factory");
        hero = Objects.requireNonNull(factory.createHero(), "hero");
        villain = Objects.requireNonNull(factory.createVillain(), "villain");
        vehicle = Objects.requireNonNull(factory.createVehicle(), "vehicle");
        if (!hero.family().equals(villain.family()) || !hero.family().equals(vehicle.family())) {
            throw new IllegalArgumentException("Mixed universes");
        }
    }
    public String family() {
        return hero.family();
    }
    public String explore() {
        return family() + ": " + hero.act() + " | " + villain.act() + " | " + vehicle.travel();
    }
}
