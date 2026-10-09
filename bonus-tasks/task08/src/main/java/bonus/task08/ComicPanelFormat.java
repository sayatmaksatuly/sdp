package bonus.task08;

import java.util.ArrayList;
import java.util.List;

public final class ComicPanelFormat implements MemeFormat {
    @Override
    public String name() {
        return "comic-panel";
    }

    @Override
    public MemeLayout layout(List<String> captions) {
        var copy = BoundedTextLayout.captions(captions, 2);
        var lines = new ArrayList<>(BoundedTextLayout.wrap(copy.get(0), new TextRegion(8, 8, 144, 224)));
        lines.addAll(BoundedTextLayout.wrap(copy.get(1), new TextRegion(168, 8, 144, 224)));
        return new MemeLayout(320, 240, lines);
    }
}
