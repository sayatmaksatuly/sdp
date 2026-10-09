package bonus.task08;

import java.util.List;
import java.util.Objects;

public abstract class MemeCreator {
    public final MemeLayout generate(List<String> captions) {
        MemeFormat format = Objects.requireNonNull(createFormat(), "Creator returned no format");
        return format.layout(captions);
    }

    protected abstract MemeFormat createFormat();
}
