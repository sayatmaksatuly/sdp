package bonus.task09;

public record Contact(String number) {
    public Contact {
        if (number == null || !number.matches("\\+?[0-9]{3,15}")) {
            throw new IllegalArgumentException("Contact requires a phone number of 3 to 15 digits");
        }
    }
}
