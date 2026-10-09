package bonus.task08;

import java.util.List;

public final class Demo {
    public static void main(String[] args) {
        show(new TopBottomCreator(), List.of("WHEN THE BUILD FINALLY PASSES", "AND THERE ARE NO TODOs"));
        show(new SingleCaptionCreator(), List.of("One caption. Zero overflowing text."));
        show(new ComicPanelCreator(), List.of("Wizard: I fixed the bug!", "Dragon: Why is everything on fire?"));
        try {
            new SingleCaptionCreator().generate(List.of("Too long! ".repeat(100)));
        }
        catch (IllegalArgumentException exception) {
            System.out.println("Rejected: " + exception.getMessage());
        }
    }

    private static void show(MemeCreator creator, List<String> captions) {
        MemeLayout layout = creator.generate(captions);
        System.out.println("Validated " + layout.width() + "x" + layout.height() + " layout: " + layout.lines());
        System.out.println(layout.toSvg());
    }
}
