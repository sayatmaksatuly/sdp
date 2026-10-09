package bonus.task16;


public final class SpaceUniverseFactory implements UniverseFactory {

    @Override
    public Hero createHero() {
        return new SpaceHero();
    }

    @Override
    public Villain createVillain() {
        return new SpaceVillain();
    }

    @Override
    public Vehicle createVehicle() {
        return new SpaceVehicle();
    }
}
