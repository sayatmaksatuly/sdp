package bonus.task19;

import java.util.Objects;

public final class GestureControl implements ControlSystem {

    @Override
    public String execute(String pet, String action) {
        Objects.requireNonNull(pet, "pet");
        Objects.requireNonNull(action, "action");
        if (pet.isBlank() || action.isBlank()) {
            throw new IllegalArgumentException("Pet and action required");
        }
        return "Gesture command -> " + pet + ": " + action;
    }
}
