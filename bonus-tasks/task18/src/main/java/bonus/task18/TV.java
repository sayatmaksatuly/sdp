package bonus.task18;


public final class TV extends Appliance {
    public TV(RemoteProtocol protocol) {
        super("TV", protocol);
    }

    @Override
    public String adjust(int value) {
        if (value < 0 || value > 100) {
            throw new IllegalArgumentException("VOLUME must be 0..100");
        }
        return send("VOLUME " + value);
    }
}
