package bonus.task09;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FlipPhoneAdapterTest {
    private final Contact contact = new Contact("+77001234567");

    @Test
    void shortAndExactBoundaryMessagesUseOneTransmission() {
        for (String message : new String[] {"Hi", "x".repeat(20)}) {
            FlipPhone phone = new FlipPhone();
            Messenger messenger = new FlipPhoneAdapter(phone);
            messenger.send(contact, message);
            assertEquals(1, phone.transmissions().size());
            assertEquals(new FlipPhone.Transmission(contact.number(), message), phone.transmissions().getFirst());
        }
    }

    @Test
    void longMessagesSplitWithoutLossOrBrokenUnicode() {
        for (String message : new String[] {"x".repeat(21), "x".repeat(40), "x".repeat(41), "😀".repeat(43)}) {
            FlipPhone phone = new FlipPhone();
            new FlipPhoneAdapter(phone).send(contact, message);
            assertEquals(message, phone.transmissions().stream()
                    .map(FlipPhone.Transmission::shortMessage).reduce("", String::concat));
            phone.transmissions().forEach(part -> {
                assertTrue(part.shortMessage().codePointCount(0, part.shortMessage().length()) <= 20);
                assertEquals(contact.number(), part.number());
            });
            assertEquals((message.codePointCount(0, message.length()) + 19) / 20, phone.transmissions().size());
        }
    }

    @Test
    void invalidInputsNeverReachLegacyDevice() {
        FlipPhone phone = new FlipPhone();
        Messenger messenger = new FlipPhoneAdapter(phone);
        assertThrows(IllegalArgumentException.class, () -> messenger.send(contact, ""));
        assertThrows(NullPointerException.class, () -> messenger.send(null, "hi"));
        assertThrows(NullPointerException.class, () -> messenger.send(contact, null));
        assertThrows(NullPointerException.class, () -> new FlipPhoneAdapter(null));
        assertThrows(IllegalArgumentException.class, () -> new Contact("abc"));
        assertThrows(IllegalArgumentException.class, () -> new Contact(null));
        assertTrue(phone.transmissions().isEmpty());
    }

    @Test
    void legacyLimitAndImmutableHistoryRemainEnforced() {
        FlipPhone phone = new FlipPhone();
        assertThrows(IllegalArgumentException.class, () -> phone.sendTextViaBeep(contact.number(), "x".repeat(21)));
        new FlipPhoneAdapter(phone).send(contact, "hi");
        assertThrows(UnsupportedOperationException.class, () -> phone.transmissions().clear());
    }
}
