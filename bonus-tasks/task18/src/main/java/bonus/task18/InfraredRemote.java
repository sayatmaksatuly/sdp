package bonus.task18;

import java.util.Objects;

public final class InfraredRemote implements RemoteProtocol {

    @Override
    public String transmit(String appliance, String command) {
        Objects.requireNonNull(appliance, "appliance");
        Objects.requireNonNull(command, "command");
        if (appliance.isBlank() || command.isBlank()) {
            throw new IllegalArgumentException("Target and command required");
        }
        return "Infrared -> " + appliance + ": " + command;
    }
}
