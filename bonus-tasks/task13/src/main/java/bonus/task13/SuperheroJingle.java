package bonus.task13;


public final class SuperheroJingle implements Jingle {

    @Override
    public String family() {
        return "superhero";
    }

    @Override
    public String sing() {
        return "Crunch, soar, save the morning!";
    }
}
