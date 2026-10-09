package bonus.task08;

public record TextRegion(int x, int y, int width, int height) {
    public TextRegion {
        if (x < 0 || y < 0 || width < 8 || height < 16) {
            throw new IllegalArgumentException("Region must fit at least one 8x16 text cell");
        }
    }
}
