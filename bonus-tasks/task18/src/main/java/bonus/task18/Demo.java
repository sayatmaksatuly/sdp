package bonus.task18;


public final class Demo {
    private Demo() {
    }
    public static void main(String[] args) {
        for (RemoteProtocol protocol : new RemoteProtocol[]{new InfraredRemote(), new BluetoothRemote()}) {
            Appliance[] appliances = {new TV(protocol), new Fan(protocol), new Lamp(protocol)};
            int[] settings = {50, 2, 75};
            for (int i = 0; i < appliances.length; i++) {
                System.out.println(appliances[i].turnOn());
                System.out.println(appliances[i].adjust(settings[i]));
                System.out.println(appliances[i].turnOff());
            }
        }
    }
}
