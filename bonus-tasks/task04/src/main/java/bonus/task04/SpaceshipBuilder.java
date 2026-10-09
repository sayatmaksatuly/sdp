package bonus.task04;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class SpaceshipBuilder {
    private final List<Weapon> weapons = new ArrayList<>();
    private PowerCore powerCore;
    private final List<Armor> armor = new ArrayList<>();
    private final List<Engine> engines = new ArrayList<>();
    private final List<Shield> shields = new ArrayList<>();

    public SpaceshipBuilder addWeapon(Weapon weapon) {
        weapons.add(Objects.requireNonNull(weapon, "weapon"));
        return this;
    }

    public SpaceshipBuilder powerCore(PowerCore core) {
        powerCore = Objects.requireNonNull(core, "core");
        return this;
    }

    public SpaceshipBuilder addArmor(Armor plate) {
        armor.add(Objects.requireNonNull(plate, "armor"));
        return this;
    }

    public SpaceshipBuilder addEngine(Engine engine) {
        engines.add(Objects.requireNonNull(engine, "engine"));
        return this;
    }

    public SpaceshipBuilder addShield(Shield shield) {
        shields.add(Objects.requireNonNull(shield, "shield"));
        return this;
    }

    public Spaceship build() {
        if (powerCore == null) {
            throw new IllegalStateException("Choose a power core before building");
        }
        WeaponPowerValidator.validate(weapons, powerCore);
        return new Spaceship(weapons, powerCore, armor, engines, shields);
    }
}
