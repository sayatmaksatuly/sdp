package bonus.task13;


public final class SuperheroCerealFactory implements CerealFactory {

    @Override
    public Mascot createMascot() {
        return new SuperheroMascot();
    }

    @Override
    public Jingle createJingle() {
        return new SuperheroJingle();
    }
}
