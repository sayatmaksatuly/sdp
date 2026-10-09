package bonus.task19;


public final class Demo {
    private Demo() {
    }
    public static void main(String[] args) {
        for (ControlSystem control : new ControlSystem[]{new VoiceControl(), new AppControl(), new GestureControl()}) {
            for (RobotPet pet : new RobotPet[]{new RobotDog(control), new RobotCat(control)}) {
                System.out.println(pet.greet());
                System.out.println(pet.play());
                System.out.println(pet.recharge());
            }
        }
    }
}
