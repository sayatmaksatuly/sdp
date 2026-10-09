package bonus.task02;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CakeBuilderTest {
    @Test void originalChainWorksWithoutNewStep() {
        Cake cake = new CakeBuilder().addSpongeLayer().addFrosting().addCandles(10).build();
        assertEquals(1, cake.spongeLayers());
        assertEquals(1, cake.frostingLayers());
        assertEquals(10, cake.candles());
        assertFalse(cake.sparklers());
    }

    @Test void crumbCoatThenFondantIsValid() {
        Cake cake = new CakeBuilder().addSpongeLayer().addCrumbCoat().addFondant().build();
        assertTrue(cake.crumbCoat());
        assertTrue(cake.fondant());
    }

    @Test void fondantWithoutCoatIsRejectedAtBuild() {
        CakeBuilder builder = new CakeBuilder().addFondant();
        assertThrows(IllegalStateException.class, builder::build);
    }

    @Test void lateCrumbCoatDoesNotEraseOrderingViolation() {
        CakeBuilder builder = new CakeBuilder().addFondant().addCrumbCoat().addFondant();
        assertThrows(IllegalStateException.class, builder::build);
    }

    @Test void ordinaryFrostingDoesNotCountAsCrumbCoat() {
        assertThrows(IllegalStateException.class,
                () -> new CakeBuilder().addFrosting().addFondant().build());
    }

    @Test void sparklersDoNotBypassOrderingRule() {
        assertThrows(IllegalStateException.class,
                () -> new CakeBuilder().addSparklers().addFondant().build());
    }

    @Test void sparklersAreOptionalAndAddNoOtherChanges() {
        Cake original = new CakeBuilder().addSpongeLayer().addCrumbCoat().addFondant().build();
        Cake extended = new CakeBuilder().addSpongeLayer().addCrumbCoat().addFondant()
                .addSparklers().build();
        assertEquals(new Cake(original.spongeLayers(), original.frostingLayers(),
                original.crumbCoat(), original.fondant(), original.candles(), true), extended);
    }

    @Test void zeroCandlesAndEmptyCakeAreAllowed() {
        assertEquals(new Cake(0, 0, false, false, 0, false),
                new CakeBuilder().addCandles(0).build());
    }

    @Test void negativeCandlesAreRejectedWithoutChangingBuilder() {
        CakeBuilder builder = new CakeBuilder().addCandles(3);
        assertThrows(IllegalArgumentException.class, () -> builder.addCandles(-1));
        assertEquals(3, builder.build().candles());
    }

    @Test void candleOverflowIsRejectedWithoutCorruptingCount() {
        CakeBuilder builder = new CakeBuilder().addCandles(Integer.MAX_VALUE);
        assertThrows(ArithmeticException.class, () -> builder.addCandles(1));
        assertEquals(Integer.MAX_VALUE, builder.build().candles());
    }

    @Test void layersAndCandlesAccumulate() {
        Cake cake = new CakeBuilder().addSpongeLayer().addSpongeLayer().addFrosting()
                .addFrosting().addCandles(2).addCandles(3).build();
        assertEquals(2, cake.spongeLayers());
        assertEquals(2, cake.frostingLayers());
        assertEquals(5, cake.candles());
    }

    @Test void builtCakeIsIndependentOfReusedBuilder() {
        CakeBuilder builder = new CakeBuilder().addSpongeLayer();
        Cake first = builder.build();
        builder.addSparklers().addSpongeLayer();
        assertEquals(new Cake(1, 0, false, false, 0, false), first);
        assertTrue(builder.build().sparklers());
    }

    @Test void everyStepReturnsSameBuilder() {
        CakeBuilder builder = new CakeBuilder();
        assertSame(builder, builder.addSpongeLayer());
        assertSame(builder, builder.addFrosting());
        assertSame(builder, builder.addCrumbCoat());
        assertSame(builder, builder.addFondant());
        assertSame(builder, builder.addCandles(1));
        assertSame(builder, builder.addSparklers());
    }
}
