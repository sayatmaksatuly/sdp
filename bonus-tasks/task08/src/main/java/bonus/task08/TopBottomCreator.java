package bonus.task08;
public final class TopBottomCreator extends MemeCreator {
    @Override
    protected MemeFormat createFormat() {
        return new TopBottomFormat();
    }
}
