package bonus.task08;

import java.util.List;

public record MemeLayout(int width, int height, List<LayoutLine> lines) {
    public MemeLayout {
        lines = List.copyOf(lines);
        if (width < 1 || height < 1 || lines.stream().anyMatch(line -> (long) line.x() + line.width() > width
                || (long) line.y() + line.height() > height)) {
            throw new IllegalArgumentException("Text exceeds canvas bounds");
        }
    }

    /** Exact textLength fixes horizontal extent; cell clips also contain unusual glyph ascenders. */
    public String toSvg() {
        var svg = new StringBuilder("<svg xmlns=\"http://www.w3.org/2000/svg\" width=\"").append(width)
            .append("\" height=\"").append(height).append("\" viewBox=\"0 0 ").append(width).append(' ').append(height).append("\">");
        svg.append("<rect width=\"100%\" height=\"100%\" fill=\"#f4f4f4\"/>");
        for (int i = 0; i < lines.size(); i++) {
            LayoutLine line = lines.get(i);
            if (line.width() == 0) {
                continue;
            }
            svg.append("<defs><clipPath id=\"cell").append(i).append("\"><rect x=\"").append(line.x())
                .append("\" y=\"").append(line.y()).append("\" width=\"").append(line.width())
                .append("\" height=\"16\"/></clipPath></defs>");
            svg.append("<text x=\"").append(line.x()).append("\" y=\"").append(line.y() + 13)
                .append("\" font-family=\"monospace\" font-size=\"14\" textLength=\"").append(line.width())
                .append("\" lengthAdjust=\"spacingAndGlyphs\" clip-path=\"url(#cell").append(i).append(")\">")
                .append(escape(line.text())).append("</text>");
        }
        return svg.append("</svg>").toString();
    }

    private static String escape(String text) {
        return text.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;").replace("\"", "&quot;");
    }
}
