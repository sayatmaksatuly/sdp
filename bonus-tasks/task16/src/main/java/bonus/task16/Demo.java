package bonus.task16;


public final class Demo {
    private Demo() {
    }
    public static void main(String[] args) {
        var config = new UniverseConfiguration();
        for (String flag : args.length == 0 ? new String[]{"steampunk", "cyberpunk", "medieval", "space"}
        : args) {
            System.out.println(new Universe(config.select(flag)).explore());
        }
    }
}
