package bonus.task09;

import java.util.ArrayList;
import java.util.List;

public class FlipPhone {
    public record Transmission(String number, String shortMessage) { }

    private final List<Transmission> transmissions = new ArrayList<>();

    public void sendTextViaBeep(String number, String shortMsg) {
        new Contact(number);
        if (shortMsg == null || shortMsg.isEmpty()
                || shortMsg.codePointCount(0, shortMsg.length()) > 20) {
            throw new IllegalArgumentException("Legacy SMS accepts 1 to 20 Unicode characters");
        }
        transmissions.add(new Transmission(number, shortMsg));
    }

    public List<Transmission> transmissions() {
        return List.copyOf(transmissions);
    }
}
