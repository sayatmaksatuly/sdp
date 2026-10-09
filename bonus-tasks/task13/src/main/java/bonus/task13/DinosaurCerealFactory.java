package bonus.task13;


public final class DinosaurCerealFactory implements CerealFactory {

    @Override
    public Mascot createMascot() {
        return new DinosaurMascot();
    }

    @Override
    public Jingle createJingle() {
        return new DinosaurJingle();
    }
}
