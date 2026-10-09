package bonus.task17;


public final class Americano extends CoffeeDrink {
    public Americano(BrewMethod method) {
        super(method);
    }

    @Override
    public String serve() {
        return brewBase("americano base", 60) + "; add 180 ml hot water";
    }
}
