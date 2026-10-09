package bonus.task10;

import java.util.Objects;

public class AncientFaxMachine {
    private final String confirmation;
    private byte[] lastPixels = new byte[0];
    private String lastNumber;

    public AncientFaxMachine() {
        this("krrr::ACK:1987::shhh");
    }

    public AncientFaxMachine(String confirmation) {
        this.confirmation = Objects.requireNonNull(confirmation, "confirmation");
    }

    public String sendViaPhoneLine(byte[] rawPixels, String phoneNumber) {
        if (rawPixels == null || rawPixels.length == 0) {
            throw new IllegalArgumentException("Fax requires raster pixels");
        }
        lastPixels = rawPixels.clone();
        lastNumber = Objects.requireNonNull(phoneNumber, "phoneNumber");
        return confirmation;
    }

    public byte[] lastPixels() { return lastPixels.clone(); }

    public String lastNumber() { return lastNumber; }
}
