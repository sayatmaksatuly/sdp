package bonus.task16;


public final class CyberpunkUniverseFactory implements UniverseFactory {

    @Override
    public Hero createHero() {
        return new CyberpunkHero();
    }

    @Override
    public Villain createVillain() {
        return new CyberpunkVillain();
    }

    @Override
    public Vehicle createVehicle() {
        return new CyberpunkVehicle();
    }
}
