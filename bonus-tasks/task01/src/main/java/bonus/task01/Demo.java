package bonus.task01;

public final class Demo {
    private Demo() { }

    public static void main(String[] args) {
        Lair original = new LairBuilder().addTrapRoom().addHenchmenQuarters()
                .addMonologueBalcony().build();
        Lair extended = new LairBuilder().addSharkTank().addObservationDeck()
                .addEscapeTunnel().build();
        System.out.println("Original client: " + original);
        System.out.println("Extended client: " + extended);
        try {
            new LairBuilder().addSharkTank().build();
            throw new AssertionError("Invalid lair was accepted");
        } catch (IllegalStateException expected) {
            System.out.println("Validation: " + expected.getMessage());
        }
    }
}
