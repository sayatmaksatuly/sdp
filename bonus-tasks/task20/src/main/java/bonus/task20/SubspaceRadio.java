package bonus.task20;

import java.util.Objects;

public final class SubspaceRadio implements DeliveryChannel {

    @Override
    public String deliver(String recipient, String payload) {
        Objects.requireNonNull(recipient, "recipient");
        Objects.requireNonNull(payload, "payload");
        if (recipient.isBlank() || payload.isBlank()) {
            throw new IllegalArgumentException("Recipient and payload required");
        }
        return "Subspace radio -> " + recipient + ": " + payload;
    }
}
