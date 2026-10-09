package bonus.task17;

import java.util.Objects;

public final class PourOver implements BrewMethod {

    @Override
    public String brew(String coffee, int milliliters) {
        Objects.requireNonNull(coffee, "coffee");
        if (coffee.isBlank() || milliliters <= 0) {
            throw new IllegalArgumentException("Coffee and positive volume required");
        }
        return "Pour-over filters " + milliliters + " ml of " + coffee;
    }
}
