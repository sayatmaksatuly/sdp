package bonus.task20;


public final class Demo {
    private Demo() {
    }
    public static void main(String[] args) {
        for (DeliveryChannel channel : new DeliveryChannel[]{new CarrierPigeon(), new QuantumTeleporter(), new SubspaceRadio()}) {
            MessageType[] messages = {new TextMessage("Meet at the portal", channel), new VoiceMessage("Portal is ready", 3, channel)};
            for (MessageType message : messages) {
                System.out.println(message.send("Universe B"));
            }
        }
    }
}
