package bonus.task04;

public record Armor(String name, int protection) {
    public Armor {
        name = ComponentChecks.name(name);
        ComponentChecks.nonnegative(protection, "Armor protection");
    }
}
