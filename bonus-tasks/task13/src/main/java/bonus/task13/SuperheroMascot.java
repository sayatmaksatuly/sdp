package bonus.task13;


public final class SuperheroMascot implements Mascot {

    @Override
    public String family() {
        return "superhero";
    }

    @Override
    public String introduce() {
        return "Captain Crunch-Time saves breakfast!";
    }
}
