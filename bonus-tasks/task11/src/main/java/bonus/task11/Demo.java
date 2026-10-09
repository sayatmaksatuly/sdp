package bonus.task11;

public final class Demo {
    private Demo() { }

    public static void main(String[] args) {
        for (int raw : new int[] {-100, -50, 0, 50, 100}) {
            GameController controller = new JoystickAdapter(new RetroJoystick(() -> raw));
            System.out.println(raw + " -> " + controller.getAxis("x"));
        }
    }
}
