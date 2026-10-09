package bonus.task04;

public final class Demo {
    private Demo() { }

    public static void main(String[] args) {
        Spaceship original = new SpaceshipBuilder().addWeapon(new Weapon("Laser", 60))
                .addWeapon(new Weapon("Plasma cannon", 40)).powerCore(new PowerCore("Fusion", 100))
                .addArmor(new Armor("Titanium", 80)).addEngine(new Engine("Ion", 200)).build();
        Spaceship extended = new SpaceshipBuilder().powerCore(new PowerCore("Fusion", 100))
                .addWeapon(new Weapon("Laser", 100)).addShield(new Shield("Bubble", 500)).build();
        System.out.println("Original client: " + original);
        System.out.println("Extended client: " + extended);
        try {
            new SpaceshipBuilder().powerCore(new PowerCore("Tiny", 10))
                    .addWeapon(new Weapon("Laser", 11)).addShield(new Shield("Bubble", 500)).build();
            throw new AssertionError("Overpowered weapons were accepted");
        } catch (IllegalStateException expected) {
            System.out.println("Validation with shields: " + expected.getMessage());
        }
    }
}
