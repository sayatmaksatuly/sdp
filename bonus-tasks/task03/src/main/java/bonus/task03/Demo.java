package bonus.task03;

public final class Demo {
    private Demo() { }

    public static void main(String[] args) {
        DatingProfile first = new DatingProfileBuilder().name("Dr. Drama")
                .bio("Collects dramatic entrance music").addHobby("Chess")
                .addHobby("Moonwalking").redFlag("Owns seventeen capes").build();
        DatingProfile second = new DatingProfileBuilder().redFlag("Owns seventeen capes")
                .addHobby("Moonwalking").bio("Collects dramatic entrance music")
                .addHobby("Chess").name("Dr. Drama").build();
        if (!first.equals(second)) {
            throw new AssertionError("Field order changed the profile");
        }
        System.out.println("First order: " + first);
        System.out.println("Second order: " + second);
        System.out.println("Equal: " + first.equals(second));
        System.out.println("Optional verification: " + new DatingProfileBuilder()
                .name("Captain Kindness").bio("Makes tea").addHobby("Baking").verify().build());
    }
}
