package bonus.task11;

import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class JoystickAdapterTest {
    @Test
    void polarityScalingAndEveryLegalPositionAreCorrect() {
        for (int raw = -100; raw <= 100; raw++) {
            int position = raw;
            GameController controller = new JoystickAdapter(new RetroJoystick(() -> position));
            assertEquals(-position / 100.0, controller.getAxis("x"), 1e-12);
        }
    }

    @Test
    void brokenLegacyReadingsAreClampedWithoutIntegerNegationOverflow() {
        for (int raw : new int[] {Integer.MIN_VALUE, -101, 101, Integer.MAX_VALUE}) {
            GameController controller = new JoystickAdapter(new RetroJoystick(() -> raw));
            assertEquals(raw < 0 ? 1.0 : -1.0, controller.getAxis("x"));
        }
    }

    @Test
    void readsLatestPositionAndRejectsUnavailableAxesBeforeReading() {
        AtomicInteger calls = new AtomicInteger();
        AtomicInteger raw = new AtomicInteger(25);
        GameController controller = new JoystickAdapter(new RetroJoystick(() -> {
            calls.incrementAndGet();
            return raw.get();
        }));
        assertEquals(-0.25, controller.getAxis("x"));
        raw.set(-50);
        assertEquals(0.5, controller.getAxis("x"));
        assertThrows(IllegalArgumentException.class, () -> controller.getAxis("y"));
        assertThrows(IllegalArgumentException.class, () -> controller.getAxis(null));
        assertEquals(2, calls.get());
    }

    @Test
    void anotherControllerCanUseSameClientContract() {
        GameController secondDevice = name -> {
            if (!"x".equals(name)) {
                throw new IllegalArgumentException("Unsupported axis");
            }
            return 0.25;
        };
        assertEquals(0.25, movePlayer(secondDevice));
        assertEquals(0.25, movePlayer(new JoystickAdapter(new RetroJoystick(() -> -25))));
        assertThrows(NullPointerException.class, () -> new JoystickAdapter(null));
        assertThrows(NullPointerException.class, () -> new RetroJoystick(null));
    }

    private double movePlayer(GameController controller) { return controller.getAxis("x"); }
}
