package bonus.task17;

import java.util.Objects;

public abstract class CoffeeDrink {
    private final BrewMethod method;
    protected CoffeeDrink(BrewMethod method) {
        this.method = Objects.requireNonNull(method, "method");
    }
    protected final String brewBase(String coffee, int milliliters) {
        return method.brew(coffee, milliliters);
    }
    public abstract String serve();
}
