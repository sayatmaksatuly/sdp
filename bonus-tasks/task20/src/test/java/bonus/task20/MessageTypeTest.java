package bonus.task20;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MessageTypeTest {

    @Test
    void allSixCombinationsDeliverTheirPayloads() {
        for (DeliveryChannel channel : new DeliveryChannel[]{new CarrierPigeon(), new QuantumTeleporter(), new SubspaceRadio()}) {
            assertEquals(channel.deliver("Mars", "TEXT: hello"), new TextMessage("hello", channel).send("Mars"));
            assertEquals(channel.deliver("Mars", "VOICE[3s]: hello"), new VoiceMessage("hello", 3, channel).send("Mars"));
        }
    }

    @Test
    void independentNewChannelReceivesExactlyOneCallPerMessage() {
        List<String> calls = new ArrayList<>();
        DeliveryChannel spy = (recipient, payload) -> {
            calls.add(recipient + ":" + payload);
            return "delivered";
        };
        assertEquals("delivered", new TextMessage("🌌", spy).send("A"));
        assertEquals("delivered", new VoiceMessage("x", 1, spy).send("B"));
        assertEquals(List.of("A:TEXT: 🌌", "B:VOICE[1s]: x"), calls);
    }

    @Test
    void extensionDoesNotRegressOriginalChannels() {
        assertEquals("Carrier pigeon -> Earth: TEXT: hello", new TextMessage("hello", new CarrierPigeon()).send("Earth"));
        assertEquals("Quantum teleporter -> Earth: VOICE[1s]: hi", new VoiceMessage("hi", 1, new QuantumTeleporter()).send("Earth"));
        assertEquals("Subspace radio -> Earth: TEXT: hello", new TextMessage("hello", new SubspaceRadio()).send("Earth"));
    }

    @Test
    void boundaryAndInvalidInputsFailBeforeDelivery() {
        List<String> calls = new ArrayList<>();
        DeliveryChannel spy = (recipient, payload) -> {
            calls.add(payload);
            return payload;
        };
        assertThrows(IllegalArgumentException.class, () -> new TextMessage("", spy));
        assertThrows(IllegalArgumentException.class, () -> new VoiceMessage("hi", 0, spy));
        assertThrows(IllegalArgumentException.class, () -> new VoiceMessage("hi", -1, spy));
        assertThrows(NullPointerException.class, () -> new TextMessage(null, spy));
        assertThrows(NullPointerException.class, () -> new TextMessage("hi", null));
        var message = new TextMessage("x", spy);
        assertThrows(IllegalArgumentException.class, () -> message.send(" "));
        assertThrows(NullPointerException.class, () -> message.send(null));
        assertTrue(calls.isEmpty());
        assertEquals("VOICE[2147483647s]: x", new VoiceMessage("x", Integer.MAX_VALUE, spy).send("A"));
        for (DeliveryChannel channel : new DeliveryChannel[]{new CarrierPigeon(), new QuantumTeleporter(), new SubspaceRadio()}) {
            assertThrows(IllegalArgumentException.class, () -> channel.deliver("", "text"));
            assertThrows(IllegalArgumentException.class, () -> channel.deliver("A", ""));
            assertThrows(NullPointerException.class, () -> channel.deliver("A", null));
        }
    }
}
