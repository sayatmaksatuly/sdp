package bonus.task15;


public final class Demo {
    private Demo() {
    }
    public static void main(String[] args) {
        var config = new ParkConfiguration();
        for (String flag : args.length == 0 ? new String[]{"horror", "summer", "winter"}
        : args) {
            System.out.println("Full park day: " + flag);
            new ThemePark(config.select(flag)).simulateDay().forEach(event -> System.out.println(event.describe()));
        }
    }
}
