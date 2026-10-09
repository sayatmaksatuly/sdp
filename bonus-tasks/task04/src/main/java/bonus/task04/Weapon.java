package bonus.task04;

public record Weapon(String name, int powerDemand) {
    public Weapon {
        name = ComponentChecks.name(name);
        ComponentChecks.nonnegative(powerDemand, "Weapon demand");
    }
}
