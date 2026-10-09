package bonus.task19;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RobotPetTest {

    @Test
    void originalFourCombinationsAndThirdControlWork() {
        for (ControlSystem control : new ControlSystem[]{new VoiceControl(), new AppControl(), new GestureControl()}) {
            assertEquals(control.execute("Robot dog", "wag tail and bark"), new RobotDog(control).greet());
            assertEquals(control.execute("Robot dog", "fetch a ball"), new RobotDog(control).play());
            assertEquals(control.execute("Robot cat", "purr and blink"), new RobotCat(control).greet());
            assertEquals(control.execute("Robot cat", "chase a laser"), new RobotCat(control).play());
            assertEquals(control.execute("Robot cat", "recharge"), new RobotCat(control).recharge());
        }
    }

    @Test
    void recordingControlShowsOneDelegationPerPetOperation() {
        List<String> calls = new ArrayList<>();
        ControlSystem spy = (pet, action) -> {
            calls.add(pet + ":" + action);
            return "executed";
        };
        assertEquals("executed", new RobotDog(spy).greet());
        assertEquals("executed", new RobotCat(spy).play());
        assertEquals("executed", new RobotDog(spy).recharge());
        assertEquals(List.of("Robot dog:wag tail and bark", "Robot cat:chase a laser", "Robot dog:recharge"), calls);
    }

    @Test
    void gestureExtensionKeepsOriginalControlBehavior() {
        assertEquals("Voice command -> Robot dog: fetch a ball", new RobotDog(new VoiceControl()).play());
        assertEquals("App command -> Robot cat: purr and blink", new RobotCat(new AppControl()).greet());
        assertEquals("Gesture command -> Robot dog: fetch a ball", new RobotDog(new GestureControl()).play());
    }

    @Test
    void blankInputsAndMissingControlsFailClearly() {
        assertThrows(NullPointerException.class, () -> new RobotDog(null));
        assertThrows(NullPointerException.class, () -> new RobotCat(null));
        for (ControlSystem control : new ControlSystem[]{new VoiceControl(), new AppControl(), new GestureControl()}) {
            assertThrows(IllegalArgumentException.class, () -> control.execute("", "play"));
            assertThrows(IllegalArgumentException.class, () -> control.execute("cat", " "));
            assertThrows(NullPointerException.class, () -> control.execute("cat", null));
            assertTrue(control.execute("c", "p").endsWith(" -> c: p"));
        }
    }
}
