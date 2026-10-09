package bonus.task18;


public final class Fan extends Appliance {
    public Fan(RemoteProtocol protocol) {
        super("Fan", protocol);
    }

    @Override
    public String adjust(int value) {
        if (value < 0 || value > 3) {
            throw new IllegalArgumentException("SPEED must be 0..3");
        }
        return send("SPEED " + value);
    }
}
