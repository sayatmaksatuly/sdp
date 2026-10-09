package bonus.task14;


public final class WeddingCateringFactory implements CateringFactory {

    @Override
    public Menu createMenu() {
        return new WeddingMenu();
    }

    @Override
    public Decor createDecor() {
        return new WeddingDecor();
    }
}
