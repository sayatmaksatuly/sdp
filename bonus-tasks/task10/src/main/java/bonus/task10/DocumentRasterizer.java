package bonus.task10;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.util.Objects;

public final class DocumentRasterizer {
    public static final int WIDTH = 640;
    public static final int HEIGHT = 512;

    public byte[] rasterize(Document document) {
        Objects.requireNonNull(document, "document");
        BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_BYTE_GRAY);
        Graphics2D graphics = image.createGraphics();
        try {
            graphics.setColor(Color.WHITE);
            graphics.fillRect(0, 0, WIDTH, HEIGHT);
            graphics.setColor(Color.BLACK);
            graphics.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
            // Fixed-size cells wrap every code point without leaving the raster.
            int[] points = document.text().codePoints().toArray();
            for (int i = 0; i < points.length; i++) {
                graphics.drawString(new String(Character.toChars(points[i])), 16 + i % 80 * 7,
                        24 + i / 80 * 16);
            }
        } finally {
            graphics.dispose();
        }
        return ((DataBufferByte) image.getRaster().getDataBuffer()).getData().clone();
    }
}
