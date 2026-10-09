package bonus.task14;


public final class BirthdayCateringFactory implements CateringFactory {

    @Override
    public Menu createMenu() {
        return new BirthdayMenu();
    }

    @Override
    public Decor createDecor() {
        return new BirthdayDecor();
    }
}
