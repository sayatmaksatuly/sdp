package bonus.task04;

public record Engine(String name, int thrust) {
    public Engine {
        name = ComponentChecks.name(name);
        ComponentChecks.nonnegative(thrust, "Engine thrust");
    }
}
