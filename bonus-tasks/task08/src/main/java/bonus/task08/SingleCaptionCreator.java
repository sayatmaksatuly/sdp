package bonus.task08;
public final class SingleCaptionCreator extends MemeCreator {
    @Override
    protected MemeFormat createFormat() {
        return new SingleCaptionFormat();
    }
}
