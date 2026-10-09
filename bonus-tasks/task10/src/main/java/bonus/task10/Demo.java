package bonus.task10;

public final class Demo {
    private Demo() { }

    public static void main(String[] args) {
        DocumentSender sender = new FaxAdapter(new AncientFaxMachine(), new DocumentRasterizer());
        System.out.println(sender.send(new Document("Bonus tasks completed!"), "+77001234567"));
    }
}
