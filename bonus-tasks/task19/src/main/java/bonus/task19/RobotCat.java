package bonus.task19;


public final class RobotCat extends RobotPet {
    public RobotCat(ControlSystem control) {
        super("Robot cat", control);
    }

    @Override
    public String greet() {
        return perform("purr and blink");
    }

    @Override
    public String play() {
        return perform("chase a laser");
    }
}
