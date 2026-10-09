package bonus.task16;

import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;
/** Configuration is the composition root; clients only receive the abstract factory. */

public final class UniverseConfiguration {
    private final Map<String, Supplier<UniverseFactory>> factories;
    public UniverseConfiguration() {
        this(Map.of("steampunk", SteampunkUniverseFactory::new,
        "cyberpunk", CyberpunkUniverseFactory::new,
        "medieval", MedievalUniverseFactory::new,
        "space", SpaceUniverseFactory::new));
    }
    public UniverseConfiguration(Map<String, Supplier<UniverseFactory>> factories) {
        this.factories = Map.copyOf(Objects.requireNonNull(factories, "factories"));
        if (this.factories.isEmpty()) {
            throw new IllegalArgumentException("At least one family is required");
        }
    }
    public UniverseFactory select(String flag) {
        Objects.requireNonNull(flag, "flag");
        var supplier = factories.get(flag.trim().toLowerCase(Locale.ROOT));
        if (supplier == null) {
            throw new IllegalArgumentException("Unknown family: " + flag);
        }
        return Objects.requireNonNull(supplier.get(), "Factory supplier returned null");
    }
}
