package bonus.task13;

import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;
/** Configuration is the composition root; clients only receive the abstract factory. */

public final class CerealConfiguration {
    private final Map<String, Supplier<CerealFactory>> factories;
    public CerealConfiguration() {
        this(Map.of("superhero", SuperheroCerealFactory::new,
        "dinosaur", DinosaurCerealFactory::new));
    }
    public CerealConfiguration(Map<String, Supplier<CerealFactory>> factories) {
        this.factories = Map.copyOf(Objects.requireNonNull(factories, "factories"));
        if (this.factories.isEmpty()) {
            throw new IllegalArgumentException("At least one family is required");
        }
    }
    public CerealFactory select(String flag) {
        Objects.requireNonNull(flag, "flag");
        var supplier = factories.get(flag.trim().toLowerCase(Locale.ROOT));
        if (supplier == null) {
            throw new IllegalArgumentException("Unknown family: " + flag);
        }
        return Objects.requireNonNull(supplier.get(), "Factory supplier returned null");
    }
}
