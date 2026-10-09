package bonus.task16;


public final class MedievalUniverseFactory implements UniverseFactory {

    @Override
    public Hero createHero() {
        return new MedievalHero();
    }

    @Override
    public Villain createVillain() {
        return new MedievalVillain();
    }

    @Override
    public Vehicle createVehicle() {
        return new MedievalVehicle();
    }
}
