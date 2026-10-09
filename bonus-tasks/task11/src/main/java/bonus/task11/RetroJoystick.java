package bonus.task11;

import java.util.Objects;
import java.util.function.IntSupplier;

public final class RetroJoystick {
    private final IntSupplier paddle;

    public RetroJoystick(IntSupplier paddle) {
        this.paddle = Objects.requireNonNull(paddle, "paddle");
    }

    public int readPaddlePosition() { return paddle.getAsInt(); }
}
