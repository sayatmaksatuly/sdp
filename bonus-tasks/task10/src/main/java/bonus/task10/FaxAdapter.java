package bonus.task10;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class FaxAdapter implements DocumentSender {
    private static final Pattern ACK = Pattern.compile("(?:^|::)ACK:([0-9]+)(?=::|$)");
    private final AncientFaxMachine fax;
    private final DocumentRasterizer rasterizer;

    public FaxAdapter(AncientFaxMachine fax, DocumentRasterizer rasterizer) {
        this.fax = Objects.requireNonNull(fax, "fax");
        this.rasterizer = Objects.requireNonNull(rasterizer, "rasterizer");
    }

    @Override
    public SendResult send(Document document, String recipientId) {
        if (recipientId == null || !recipientId.matches("\\+?[0-9]{3,15}")) {
            throw new IllegalArgumentException("Recipient must be a phone number");
        }
        String noise = fax.sendViaPhoneLine(rasterizer.rasterize(document), recipientId);
        Matcher ack = ACK.matcher(noise);
        if (ack.find()) {
            return new SendResult(true, ack.group(1), "Fax delivered");
        }
        if (noise.contains("BUSY")) {
            return new SendResult(false, "", "Recipient line busy");
        }
        return new SendResult(false, "", "Unrecognized fax confirmation");
    }
}
