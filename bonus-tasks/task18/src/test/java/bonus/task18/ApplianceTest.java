package bonus.task18;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ApplianceTest {

    @Test
    void sixCombinationsSupportPowerAndAdjustment() {
        for (RemoteProtocol protocol : new RemoteProtocol[]{new InfraredRemote(), new BluetoothRemote()}) {
            Appliance[] appliances = {new TV(protocol), new Fan(protocol), new Lamp(protocol)};
            String[] names = {"TV", "Fan", "Lamp"};
            String[] commands = {"VOLUME 50", "SPEED 2", "BRIGHTNESS 75"};
            int[] settings = {50, 2, 75};
            for (int i = 0; i < appliances.length; i++) {
                assertEquals(protocol.transmit(names[i], "POWER ON"), appliances[i].turnOn());
                assertEquals(protocol.transmit(names[i], commands[i]), appliances[i].adjust(settings[i]));
                assertEquals(protocol.transmit(names[i], "POWER OFF"), appliances[i].turnOff());
            }
        }
    }

    @Test
    void lampExtensionAndOriginalAppliancesDelegateWithoutProtocolChanges() {
        List<String> calls = new ArrayList<>();
        RemoteProtocol spy = (target, command) -> {
            calls.add(target + ":" + command);
            return "ack";
        };
        assertEquals("ack", new TV(spy).adjust(100));
        assertEquals("ack", new Fan(spy).adjust(3));
        assertEquals("ack", new Lamp(spy).adjust(100));
        assertEquals(List.of("TV:VOLUME 100", "Fan:SPEED 3", "Lamp:BRIGHTNESS 100"), calls);
    }

    @Test
    void validBoundsAndInvalidSettingsAreCheckedBeforeDelegating() {
        List<String> calls = new ArrayList<>();
        RemoteProtocol spy = (target, command) -> {
            calls.add(command);
            return command;
        };
        Appliance[] appliances = {new TV(spy), new Fan(spy), new Lamp(spy)};
        int[] upper = {100, 3, 100};
        for (int i = 0; i < appliances.length; i++) {
            Appliance appliance = appliances[i];
            int maximum = upper[i];
            assertTrue(appliance.adjust(0).endsWith(" 0"));
            assertTrue(appliance.adjust(maximum).endsWith(" " + maximum));
            assertThrows(IllegalArgumentException.class, () -> appliance.adjust(-1));
            assertThrows(IllegalArgumentException.class, () -> appliance.adjust(maximum + 1));
        }
        assertEquals(6, calls.size());
        assertThrows(NullPointerException.class, () -> new TV(null));
        for (RemoteProtocol protocol : new RemoteProtocol[]{new InfraredRemote(), new BluetoothRemote()}) {
            assertThrows(IllegalArgumentException.class, () -> protocol.transmit("", "POWER ON"));
            assertThrows(IllegalArgumentException.class, () -> protocol.transmit("TV", ""));
            assertThrows(NullPointerException.class, () -> protocol.transmit(null, "POWER ON"));
        }
    }
}
