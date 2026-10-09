package bonus.task09;

public final class Demo {
    private Demo() { }

    public static void main(String[] args) {
        FlipPhone phone = new FlipPhone();
        Messenger messenger = new FlipPhoneAdapter(phone);
        messenger.send(new Contact("+77001234567"), "Grandpa, this long message arrives in complete ordered chunks!");
        phone.transmissions().forEach(System.out::println);
    }
}
