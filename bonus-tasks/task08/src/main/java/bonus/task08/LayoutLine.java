package bonus.task08;

import java.util.Objects;

public record LayoutLine(int x, int y, int width, int height, String text) {
    public LayoutLine {
        Objects.requireNonNull(text, "text");
        if (x < 0 || y < 0 || width < 0 || height != 16 || width != 8L * text.codePointCount(0, text.length())) {
            throw new IllegalArgumentException("Line must use exact 8x16 monospace geometry");
        }
        if (text.codePoints().anyMatch(cp -> Character.isISOControl(cp) || cp >= 0xD800 && cp <= 0xDFFF || cp == 0xFFFE || cp == 0xFFFF)) {
            throw new IllegalArgumentException("Invalid display character");
        }
    }
}
