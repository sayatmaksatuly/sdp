package bonus.task08;
public final class ComicPanelCreator extends MemeCreator {
    @Override
    protected MemeFormat createFormat() {
        return new ComicPanelFormat();
    }
}
