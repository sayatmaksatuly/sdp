package bonus.task06;

import java.time.Clock;

public final class WaffleTruckCreator extends FoodTruckCreator {
    public WaffleTruckCreator(Clock clock) {
        super(clock);
    }

    @Override
    protected FoodTruck createTruck() {
        return new WaffleTruck();
    }
}
