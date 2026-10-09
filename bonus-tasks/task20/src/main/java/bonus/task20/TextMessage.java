package bonus.task20;


public final class TextMessage extends MessageType {
    private final String text;
    public TextMessage(String text, DeliveryChannel channel) {
        super(channel);
        this.text = requireContent(text);
    }

    @Override
    public String send(String recipient) {
        return transmit(recipient, "TEXT: " + text);
    }
}
