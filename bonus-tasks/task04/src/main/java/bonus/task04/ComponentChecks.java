package bonus.task04;

final class ComponentChecks {
    private ComponentChecks() { }

    static String name(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Component name must not be blank");
        }
        return value.strip();
    }

    static void nonnegative(int value, String field) {
        if (value < 0) {
            throw new IllegalArgumentException(field + " must be nonnegative");
        }
    }
}
