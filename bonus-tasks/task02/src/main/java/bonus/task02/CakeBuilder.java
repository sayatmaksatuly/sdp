package bonus.task02;

public final class CakeBuilder {
    private int spongeLayers;
    private int frostingLayers;
    private boolean crumbCoat;
    private boolean fondant;
    private boolean fondantAppliedTooEarly;
    private int candles;
    private boolean sparklers;

    public CakeBuilder addSpongeLayer() {
        spongeLayers = Math.incrementExact(spongeLayers);
        return this;
    }

    public CakeBuilder addFrosting() {
        frostingLayers = Math.incrementExact(frostingLayers);
        return this;
    }

    public CakeBuilder addCrumbCoat() {
        crumbCoat = true;
        return this;
    }

    public CakeBuilder addFondant() {
        // Remember chronology: a later coat cannot repair an earlier fondant application.
        fondantAppliedTooEarly |= !crumbCoat;
        fondant = true;
        return this;
    }

    public CakeBuilder addCandles(int count) {
        if (count < 0) {
            throw new IllegalArgumentException("Candle count must be nonnegative");
        }
        candles = Math.addExact(candles, count);
        return this;
    }

    public CakeBuilder addSparklers() {
        sparklers = true;
        return this;
    }

    public Cake build() {
        if (fondantAppliedTooEarly) {
            throw new IllegalStateException("Apply a crumb coat before every fondant step");
        }
        return new Cake(spongeLayers, frostingLayers, crumbCoat, fondant, candles, sparklers);
    }
}
