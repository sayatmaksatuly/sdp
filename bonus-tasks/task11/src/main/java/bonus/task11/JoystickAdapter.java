package bonus.task11;

import java.util.Objects;

public final class JoystickAdapter implements GameController {
    private final RetroJoystick joystick;

    public JoystickAdapter(RetroJoystick joystick) {
        this.joystick = Objects.requireNonNull(joystick, "joystick");
    }

    @Override
    public double getAxis(String name) {
        if (!"x".equals(name)) {
            throw new IllegalArgumentException("Only the x axis is available");
        }
        int bounded = Math.max(-100, Math.min(100, joystick.readPaddlePosition()));
        return -bounded / 100.0;
    }
}
