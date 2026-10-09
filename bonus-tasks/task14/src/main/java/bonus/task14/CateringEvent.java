package bonus.task14;

import java.util.Objects;

public final class CateringEvent {
    private final Menu menu;
    private final Decor decor;
    public CateringEvent(CateringFactory factory) {
        Objects.requireNonNull(factory, "factory");
        menu = Objects.requireNonNull(factory.createMenu(), "menu");
        decor = Objects.requireNonNull(factory.createDecor(), "decor");
        if (!menu.family().equals(decor.family())) {
            throw new IllegalArgumentException("Mixed event moods");
        }
    }
    public String mood() {
        return menu.family();
    }
    public String host() {
        return mood() + ": " + menu.describe() + " | " + decor.describe();
    }
}
