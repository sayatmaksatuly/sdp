package bonus.task17;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CoffeeDrinkTest {

    @Test
    void allFourCombinationsWork() {
        for (BrewMethod method : new BrewMethod[]{new EspressoMachine(), new PourOver()}) {
            assertEquals(method.brew("latte base", 60) + "; add 180 ml steamed milk", new Latte(method).serve());
            assertEquals(method.brew("americano base", 60) + "; add 180 ml hot water", new Americano(method).serve());
        }
    }

    @Test
    void abstractionDelegatesExactlyOnceToAnIndependentNewImplementor() {
        List<String> calls = new ArrayList<>();
        BrewMethod recording = (coffee, ml) -> {
            calls.add(coffee + ":" + ml);
            return "custom brew";
        };
        assertEquals("custom brew; add 180 ml steamed milk", new Latte(recording).serve());
        assertEquals("custom brew; add 180 ml hot water", new Americano(recording).serve());
        assertEquals(List.of("latte base:60", "americano base:60"), calls);
    }

    @Test
    void invalidDependenciesAndBrewParametersFail() {
        assertThrows(NullPointerException.class, () -> new Latte(null));
        assertThrows(NullPointerException.class, () -> new Americano(null));
        for (BrewMethod method : new BrewMethod[]{new EspressoMachine(), new PourOver()}) {
            assertThrows(IllegalArgumentException.class, () -> method.brew("", 1));
            assertThrows(IllegalArgumentException.class, () -> method.brew("coffee", 0));
            assertThrows(IllegalArgumentException.class, () -> method.brew("coffee", -1));
            assertThrows(NullPointerException.class, () -> method.brew(null, 1));
            assertTrue(method.brew("coffee", 1).contains("1 ml"));
        }
    }
}
