package bonus.task06;

import java.time.Clock;

public final class TacoTruckCreator extends FoodTruckCreator {
    public TacoTruckCreator(Clock clock) {
        super(clock);
    }

    @Override
    protected FoodTruck createTruck() {
        return new TacoTruck();
    }
}
