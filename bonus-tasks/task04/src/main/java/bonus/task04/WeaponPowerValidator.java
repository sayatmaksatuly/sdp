package bonus.task04;

import java.util.List;

/** Depends only on weapons and the core, so new part categories cannot affect this rule. */
public final class WeaponPowerValidator {
    private WeaponPowerValidator() { }

    public static void validate(List<Weapon> weapons, PowerCore core) {
        long required = totalDemand(weapons);
        if (required > core.powerSupply()) {
            throw new IllegalStateException("Weapons require " + required
                    + " power, but core supplies " + core.powerSupply());
        }
    }

    public static long totalDemand(List<Weapon> weapons) {
        return weapons.stream().mapToLong(Weapon::powerDemand).sum();
    }
}
