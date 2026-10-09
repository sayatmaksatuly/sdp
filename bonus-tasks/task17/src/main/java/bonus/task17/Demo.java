package bonus.task17;


public final class Demo {
    private Demo() {
    }
    public static void main(String[] args) {
        for (BrewMethod method : new BrewMethod[]{new EspressoMachine(), new PourOver()}) {
            System.out.println(new Latte(method).serve());
            System.out.println(new Americano(method).serve());
        }
    }
}
