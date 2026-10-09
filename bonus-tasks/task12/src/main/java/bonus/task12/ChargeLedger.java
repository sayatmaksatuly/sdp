package bonus.task12;

final class ChargeLedger {
    private long remaining;

    ChargeLedger(long cents) {
        if (cents < 0) {
            throw new IllegalArgumentException("Negative balance");
        }
        remaining = cents;
    }

    synchronized boolean charge(long cents) {
        if (cents < 0) {
            throw new IllegalArgumentException("Charge must be nonnegative");
        }
        if (cents > remaining) {
            return false;
        }
        remaining -= cents;
        return true;
    }

    synchronized long remaining() { return remaining; }
}
