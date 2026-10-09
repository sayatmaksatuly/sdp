package bonus.task02;

public final class Demo {
    private Demo() { }

    public static void main(String[] args) {
        Cake original = new CakeBuilder().addSpongeLayer().addFrosting().addCandles(10).build();
        Cake extended = new CakeBuilder().addSpongeLayer().addCrumbCoat()
                .addFondant().addCandles(21).addSparklers().build();
        System.out.println("Original client: " + original);
        System.out.println("Extended client: " + extended);
        try {
            new CakeBuilder().addFondant().addCrumbCoat().build();
            throw new AssertionError("Invalid construction order was accepted");
        } catch (IllegalStateException expected) {
            System.out.println("Validation: " + expected.getMessage());
        }
    }
}
