package bonus.task08;

import java.util.ArrayList;
import java.util.List;

public final class TopBottomFormat implements MemeFormat {
    @Override
    public String name() {
        return "top-text-bottom-text";
    }

    @Override
    public MemeLayout layout(List<String> captions) {
        var copy = BoundedTextLayout.captions(captions, 2);
        var lines = new ArrayList<>(BoundedTextLayout.wrap(copy.get(0), new TextRegion(8, 8, 304, 64)));
        lines.addAll(BoundedTextLayout.wrap(copy.get(1), new TextRegion(8, 168, 304, 64)));
        return new MemeLayout(320, 240, lines);
    }
}
