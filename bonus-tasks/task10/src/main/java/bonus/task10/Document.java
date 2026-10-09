package bonus.task10;

public record Document(String text) {
    public Document {
        if (text == null || text.isBlank() || text.length() > 2000) {
            throw new IllegalArgumentException("Document requires 1 to 2000 nonblank characters");
        }
    }
}
