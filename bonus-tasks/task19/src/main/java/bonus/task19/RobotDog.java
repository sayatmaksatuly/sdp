package bonus.task19;


public final class RobotDog extends RobotPet {
    public RobotDog(ControlSystem control) {
        super("Robot dog", control);
    }

    @Override
    public String greet() {
        return perform("wag tail and bark");
    }

    @Override
    public String play() {
        return perform("fetch a ball");
    }
}
