package bonus.task16;


public final class SteampunkUniverseFactory implements UniverseFactory {

    @Override
    public Hero createHero() {
        return new SteampunkHero();
    }

    @Override
    public Villain createVillain() {
        return new SteampunkVillain();
    }

    @Override
    public Vehicle createVehicle() {
        return new SteampunkVehicle();
    }
}
