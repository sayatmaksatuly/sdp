package bonus.task13;


public final class Demo {
    private Demo() {
    }
    public static void main(String[] args) {
        var configuration = new CerealConfiguration();
        for (String flag : args.length == 0 ? new String[]{"superhero", "dinosaur"}
        : args) {
            System.out.println(new CerealBox(configuration.select(flag)).advertise());
        }
    }
}
