package bonus.task17;


public final class Latte extends CoffeeDrink {
    public Latte(BrewMethod method) {
        super(method);
    }

    @Override
    public String serve() {
        return brewBase("latte base", 60) + "; add 180 ml steamed milk";
    }
}
