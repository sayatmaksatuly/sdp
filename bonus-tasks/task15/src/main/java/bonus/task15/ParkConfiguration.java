package bonus.task15;

import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;
/** Configuration is the composition root; clients only receive the abstract factory. */

public final class ParkConfiguration {
    private final Map<String, Supplier<ParkFactory>> factories;
    public ParkConfiguration() {
        this(Map.of("horror", HorrorParkFactory::new,
        "summer", SummerParkFactory::new,
        "winter", WinterParkFactory::new));
    }
    public ParkConfiguration(Map<String, Supplier<ParkFactory>> factories) {
        this.factories = Map.copyOf(Objects.requireNonNull(factories, "factories"));
        if (this.factories.isEmpty()) {
            throw new IllegalArgumentException("At least one family is required");
        }
    }
    public ParkFactory select(String flag) {
        Objects.requireNonNull(flag, "flag");
        var supplier = factories.get(flag.trim().toLowerCase(Locale.ROOT));
        if (supplier == null) {
            throw new IllegalArgumentException("Unknown family: " + flag);
        }
        return Objects.requireNonNull(supplier.get(), "Factory supplier returned null");
    }
}
