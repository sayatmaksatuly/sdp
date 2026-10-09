package bonus.task04;

/** New part category: protection is independent of weapon power demand. */
public record Shield(String name, int strength) {
    public Shield {
        name = ComponentChecks.name(name);
        ComponentChecks.nonnegative(strength, "Shield strength");
    }
}
