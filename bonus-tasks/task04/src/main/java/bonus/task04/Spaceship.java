package bonus.task04;

import java.util.List;

public record Spaceship(List<Weapon> weapons, PowerCore powerCore,
                        List<Armor> armor, List<Engine> engines, List<Shield> shields) {
    public Spaceship {
        weapons = List.copyOf(weapons);
        armor = List.copyOf(armor);
        engines = List.copyOf(engines);
        shields = List.copyOf(shields);
    }

    public long totalWeaponPower() {
        return WeaponPowerValidator.totalDemand(weapons);
    }
}
