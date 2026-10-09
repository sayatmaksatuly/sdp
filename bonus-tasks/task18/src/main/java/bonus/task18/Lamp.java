package bonus.task18;


public final class Lamp extends Appliance {
    public Lamp(RemoteProtocol protocol) {
        super("Lamp", protocol);
    }

    @Override
    public String adjust(int value) {
        if (value < 0 || value > 100) {
            throw new IllegalArgumentException("BRIGHTNESS must be 0..100");
        }
        return send("BRIGHTNESS " + value);
    }
}
