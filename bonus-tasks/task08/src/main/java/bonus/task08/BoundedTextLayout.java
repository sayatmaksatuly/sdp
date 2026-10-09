package bonus.task08;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class BoundedTextLayout {
    private BoundedTextLayout() {
    }

    public static List<LayoutLine> wrap(String text, TextRegion region) {
        Objects.requireNonNull(text, "text");
        Objects.requireNonNull(region, "region");
        if (text.isBlank()) {
            throw new IllegalArgumentException("Caption cannot be blank");
        }
        int columns = region.width() / 8;
        int rows = region.height() / 16;
        if (text.codePointCount(0, text.length()) > (long) columns * rows + rows - 1) {
            throw new IllegalArgumentException("Caption is too long for its text region");
        }
        var lines = new ArrayList<LayoutLine>();
        for (String paragraph : text.split("\n", -1)) {
            int[] points = paragraph.codePoints().toArray();
            int count = Math.max(1, (points.length + columns - 1) / columns);
            if ((long) lines.size() + count > rows) {
                throw new IllegalArgumentException("Caption exceeds available rows");
            }
            for (int start = 0; start < Math.max(1, points.length); start += columns) {
                int length = Math.min(columns, points.length - start);
                String fragment = new String(points, start, length);
                lines.add(new LayoutLine(region.x(), region.y() + lines.size() * 16, length * 8, 16, fragment));
            }
        }
        return List.copyOf(lines);
    }

    public static List<String> captions(List<String> captions, int expected) {
        List<String> copy = List.copyOf(captions);
        if (copy.size() != expected) {
            throw new IllegalArgumentException("Expected " + expected + " captions");
        }
        return copy;
    }
}
