package bonus.task14;


public final class Demo {
    private Demo() {
    }
    public static void main(String[] args) {
        var config = new CateringConfiguration();
        for (String flag : args.length == 0 ? new String[]{"wedding", "funeral", "birthday"}
        : args) {
            System.out.println(new CateringEvent(config.select(flag)).host());
        }
    }
}
