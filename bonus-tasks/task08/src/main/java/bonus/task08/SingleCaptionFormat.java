package bonus.task08;

import java.util.List;

public final class SingleCaptionFormat implements MemeFormat {
    @Override
    public String name() {
        return "single-caption";
    }

    @Override
    public MemeLayout layout(List<String> captions) {
        var copy = BoundedTextLayout.captions(captions, 1);
        return new MemeLayout(320, 240, BoundedTextLayout.wrap(copy.getFirst(), new TextRegion(8, 88, 304, 64)));
    }
}
