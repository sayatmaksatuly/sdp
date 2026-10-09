package bonus.task14;


public final class FuneralDecor implements Decor {

    @Override
    public String family() {
        return "funeral";
    }

    @Override
    public String describe() {
        return "Muted flowers and a remembrance table";
    }
}
