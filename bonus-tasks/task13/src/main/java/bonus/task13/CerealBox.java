package bonus.task13;

import java.util.Objects;

public final class CerealBox {
    private final Mascot mascot;
    private final Jingle jingle;
    public CerealBox(CerealFactory factory) {
        Objects.requireNonNull(factory, "factory");
        mascot = Objects.requireNonNull(factory.createMascot(), "mascot");
        jingle = Objects.requireNonNull(factory.createJingle(), "jingle");
        if (!mascot.family().equals(jingle.family())) {
            throw new IllegalArgumentException("Mixed cereal families");
        }
    }
    public String family() {
        return mascot.family();
    }
    public String advertise() {
        return mascot.introduce() + " " + jingle.sing();
    }
}
