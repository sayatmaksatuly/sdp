package bonus.task13;


public final class DinosaurMascot implements Mascot {

    @Override
    public String family() {
        return "dinosaur";
    }

    @Override
    public String introduce() {
        return "Professor T-Rex stomps into breakfast!";
    }
}
