package bonus.task12;

import java.util.Objects;

public final class Checkout {
    private final PaymentProcessor processor;

    public Checkout(PaymentProcessor processor) {
        this.processor = Objects.requireNonNull(processor, "processor");
    }

    public boolean pay(long cents) { return processor.charge(cents); }
}
