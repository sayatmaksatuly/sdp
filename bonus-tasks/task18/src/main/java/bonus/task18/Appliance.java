package bonus.task18;

import java.util.Objects;

public abstract class Appliance {
    private final String name;
    private final RemoteProtocol protocol;
    protected Appliance(String name, RemoteProtocol protocol) {
        this.name = Objects.requireNonNull(name, "name");
        this.protocol = Objects.requireNonNull(protocol, "protocol");
        if (name.isBlank()) {
            throw new IllegalArgumentException("Appliance name required");
        }
    }
    protected final String send(String command) {
        return protocol.transmit(name, command);
    }
    public final String turnOn() {
        return send("POWER ON");
    }
    public final String turnOff() {
        return send("POWER OFF");
    }
    public abstract String adjust(int value);
}
