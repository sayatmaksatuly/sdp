package bonus.task09;

import java.util.Objects;

public final class FlipPhoneAdapter implements Messenger {
    private final FlipPhone phone;

    public FlipPhoneAdapter(FlipPhone phone) {
        this.phone = Objects.requireNonNull(phone, "phone");
    }

    @Override
    public void send(Contact contact, String message) {
        Objects.requireNonNull(contact, "contact");
        Objects.requireNonNull(message, "message");
        if (message.isEmpty()) {
            throw new IllegalArgumentException("Message must not be empty");
        }
        int start = 0;
        int remaining = message.codePointCount(0, message.length());
        while (start < message.length()) {
            int chunkSize = Math.min(20, remaining);
            int end = message.offsetByCodePoints(start, chunkSize);
            phone.sendTextViaBeep(contact.number(), message.substring(start, end));
            start = end;
            remaining -= chunkSize;
        }
    }
}
