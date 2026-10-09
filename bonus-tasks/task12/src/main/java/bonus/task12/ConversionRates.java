package bonus.task12;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public record ConversionRates(BigDecimal majorCents, BigDecimal minorCents) {
    public ConversionRates {
        Objects.requireNonNull(majorCents, "majorCents");
        Objects.requireNonNull(minorCents, "minorCents");
        if (majorCents.signum() <= 0 || minorCents.signum() <= 0) {
            throw new IllegalArgumentException("Rates must be strictly positive cents per unit");
        }
    }

    public long convert(long major, long minor) {
        if (major < 0 || minor < 0) {
            throw new IllegalArgumentException("Counts must be nonnegative");
        }
        // Round the combined value exactly once; never use binary floating point.
        return majorCents.multiply(BigDecimal.valueOf(major))
                .add(minorCents.multiply(BigDecimal.valueOf(minor)))
                .setScale(0, RoundingMode.HALF_UP).longValueExact();
    }
}
