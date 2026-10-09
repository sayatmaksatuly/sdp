package bonus.task19;

import java.util.Objects;

public abstract class RobotPet {
    private final String name;
    private final ControlSystem control;
    protected RobotPet(String name, ControlSystem control) {
        this.name = Objects.requireNonNull(name, "name");
        this.control = Objects.requireNonNull(control, "control");
        if (name.isBlank()) {
            throw new IllegalArgumentException("Pet name required");
        }
    }
    protected final String perform(String action) {
        return control.execute(name, action);
    }
    public final String recharge() {
        return perform("recharge");
    }
    public abstract String greet();
    public abstract String play();
}
