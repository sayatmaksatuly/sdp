package bonus.task14;


public final class FuneralCateringFactory implements CateringFactory {

    @Override
    public Menu createMenu() {
        return new FuneralMenu();
    }

    @Override
    public Decor createDecor() {
        return new FuneralDecor();
    }
}
