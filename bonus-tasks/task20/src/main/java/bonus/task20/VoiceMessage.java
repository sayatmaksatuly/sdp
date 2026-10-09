package bonus.task20;


public final class VoiceMessage extends MessageType {
    private final String transcript;
    private final int durationSeconds;
    /** A transcript and duration are the portable simulated voice payload. */
    public VoiceMessage(String transcript, int durationSeconds, DeliveryChannel channel) {
        super(channel);
        this.transcript = requireContent(transcript);
        if (durationSeconds <= 0) {
            throw new IllegalArgumentException("Voice duration must be positive");
        }
        this.durationSeconds = durationSeconds;
    }

    @Override
    public String send(String recipient) {
        return transmit(recipient, "VOICE[" + durationSeconds + "s]: " + transcript);
    }
}
