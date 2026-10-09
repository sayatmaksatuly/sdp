package bonus.task20;

import java.util.Objects;

public abstract class MessageType {
    private final DeliveryChannel channel;
    protected MessageType(DeliveryChannel channel) {
        this.channel = Objects.requireNonNull(channel, "channel");
    }
    protected final String transmit(String recipient, String payload) {
        Objects.requireNonNull(recipient, "recipient");
        if (recipient.isBlank()) {
            throw new IllegalArgumentException("Recipient required");
        }
        return channel.deliver(recipient, payload);
    }
    protected static String requireContent(String content) {
        Objects.requireNonNull(content, "content");
        if (content.isBlank()) {
            throw new IllegalArgumentException("Message content required");
        }
        return content;
    }
    public abstract String send(String recipient);
}
