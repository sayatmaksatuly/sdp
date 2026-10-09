package bonus.task04;

public record PowerCore(String name, int powerSupply) {
    public PowerCore {
        name = ComponentChecks.name(name);
        ComponentChecks.nonnegative(powerSupply, "Core supply");
    }
}
