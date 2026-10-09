package bonus.task06;

import java.time.Clock;

public final class RamenTruckCreator extends FoodTruckCreator {
    public RamenTruckCreator(Clock clock) {
        super(clock);
    }

    @Override
    protected FoodTruck createTruck() {
        return new RamenTruck();
    }
}
