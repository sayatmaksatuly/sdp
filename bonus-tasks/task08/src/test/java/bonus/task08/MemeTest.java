package bonus.task08;

import java.util.List;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MemeTest {
    @Test
    void allFormatsProduceGeometricallyBoundedLines() {
        assertBounded(new TopBottomCreator().generate(List.of("Top text", "Bottom text")));
        assertBounded(new SingleCaptionCreator().generate(List.of("One caption")));
        MemeLayout comic = new ComicPanelCreator().generate(List.of("First panel", "Second panel"));
        assertBounded(comic);
        assertEquals(8, comic.lines().get(0).x());
        assertEquals(168, comic.lines().get(1).x());
    }

    @Test
    void exactCapacityAcceptedOneCharacterOverRejectedForEveryFormat() {
        assertEquals(8, new TopBottomCreator().generate(List.of("A".repeat(152), "B".repeat(152))).lines().size());
        assertEquals(4, new SingleCaptionCreator().generate(List.of("A".repeat(152))).lines().size());
        assertEquals(28, new ComicPanelCreator().generate(List.of("A".repeat(252), "B".repeat(252))).lines().size());
        assertThrows(IllegalArgumentException.class, () -> new TopBottomCreator().generate(List.of("A".repeat(153), "B")));
        assertThrows(IllegalArgumentException.class, () -> new TopBottomCreator().generate(List.of("A", "B".repeat(153))));
        assertThrows(IllegalArgumentException.class, () -> new SingleCaptionCreator().generate(List.of("A".repeat(153))));
        assertThrows(IllegalArgumentException.class, () -> new ComicPanelCreator().generate(List.of("A".repeat(253), "B")));
        assertThrows(IllegalArgumentException.class, () -> new ComicPanelCreator().generate(List.of("A", "B".repeat(253))));
    }

    @Test
    void explicitNewlinesConsumeRowsAndUnicodeUsesCodePoints() {
        assertEquals(4, new SingleCaptionCreator().generate(List.of("a\nb\nc\nd")).lines().size());
        assertThrows(IllegalArgumentException.class, () -> new SingleCaptionCreator().generate(List.of("a\nb\nc\nd\ne")));
        String emoji = "\uD83D\uDE00".repeat(152);
        MemeLayout layout = new SingleCaptionCreator().generate(List.of(emoji));
        assertEquals(4, layout.lines().size());
        assertEquals(emoji, layout.lines().stream().map(LayoutLine::text).reduce("", String::concat));
        assertBounded(layout);
    }

    @Test
    void invalidInputsAndInvalidGeometryAreRejected() {
        assertThrows(IllegalArgumentException.class, () -> new SingleCaptionCreator().generate(List.of(" ")));
        assertThrows(IllegalArgumentException.class, () -> new TopBottomCreator().generate(List.of("only one")));
        assertThrows(IllegalArgumentException.class, () -> new SingleCaptionCreator().generate(List.of("one", "two")));
        assertThrows(IllegalArgumentException.class, () -> new ComicPanelCreator().generate(List.of()));
        assertThrows(NullPointerException.class, () -> new SingleCaptionCreator().generate(null));
        assertThrows(IllegalArgumentException.class, () -> new SingleCaptionCreator().generate(List.of("a\tbad")));
        assertThrows(IllegalArgumentException.class, () -> new SingleCaptionCreator().generate(List.of("\uD800")));
        assertThrows(IllegalArgumentException.class, () -> new TextRegion(0, 0, 7, 16));
        assertThrows(IllegalArgumentException.class, () -> new LayoutLine(0, 0, 9, 16, "a"));
        assertThrows(IllegalArgumentException.class, () -> new MemeLayout(8, 16, List.of(new LayoutLine(1, 0, 8, 16, "a"))));
        assertThrows(NullPointerException.class, () -> new MemeCreator() {
            @Override
            protected MemeFormat createFormat() {
                return null;
            }
        }.generate(List.of("caption")));
    }

    @Test
    void svgUsesExactLengthsClipsAndEscaping() throws Exception {
        String svg = new SingleCaptionCreator().generate(List.of("<script>&\" wow")).toSvg();
        assertTrue(svg.contains("textLength=\""));
        assertTrue(svg.contains("lengthAdjust=\"spacingAndGlyphs\""));
        assertTrue(svg.contains("clip-path=\"url(#cell0)\""));
        assertTrue(svg.contains("&lt;script&gt;&amp;&quot;"));
        assertFalse(svg.contains("<script>"));
        var factory = javax.xml.parsers.DocumentBuilderFactory.newInstance();
        var document = factory.newDocumentBuilder().parse(new java.io.ByteArrayInputStream(svg.getBytes(java.nio.charset.StandardCharsets.UTF_8)));
        assertEquals("svg", document.getDocumentElement().getTagName());
    }

    @Test
    void thirdFormatAndNewCreatorDoNotChangeOriginalOutputs() {
        var originalTop = new TopBottomCreator().generate(List.of("top", "bottom"));
        var originalSingle = new SingleCaptionCreator().generate(List.of("caption"));
        assertBounded(new ComicPanelCreator().generate(List.of("one", "two")));
        MemeCreator extension = new MemeCreator() {
            @Override
            protected MemeFormat createFormat() {
                return new MemeFormat() {
                    @Override
                    public String name() {
                        return "Poster";
                    }

                    @Override
                    public MemeLayout layout(List<String> captions) {
                        var copy = BoundedTextLayout.captions(captions, 1);
                        return new MemeLayout(80, 32, BoundedTextLayout.wrap(copy.getFirst(), new TextRegion(0, 0, 80, 32)));
                    }
                };
            }
        };
        assertEquals(80, extension.generate(List.of("poster")).width());
        assertEquals(originalTop, new TopBottomCreator().generate(List.of("top", "bottom")));
        assertEquals(originalSingle, new SingleCaptionCreator().generate(List.of("caption")));
        assertThrows(UnsupportedOperationException.class, () -> originalSingle.lines().clear());
    }

    private void assertBounded(MemeLayout layout) {
        for (LayoutLine line : layout.lines()) {
            assertTrue(line.x() >= 0 && line.y() >= 0);
            assertTrue((long) line.x() + line.width() <= layout.width());
            assertTrue((long) line.y() + line.height() <= layout.height());
            assertEquals(8 * line.text().codePointCount(0, line.text().length()), line.width());
        }
    }
}
