package bonus.task04;

import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SpaceshipBuilderTest {
    private SpaceshipBuilder base() {
        return new SpaceshipBuilder().powerCore(new PowerCore("Fusion", 100));
    }

    @Test void originalPartsBuildWithoutShields() {
        Spaceship ship = base().addWeapon(new Weapon("Laser", 30))
                .addArmor(new Armor("Titanium", 50)).addEngine(new Engine("Ion", 200)).build();
        assertEquals(30L, ship.totalWeaponPower());
        assertEquals(1, ship.armor().size());
        assertEquals(1, ship.engines().size());
        assertTrue(ship.shields().isEmpty());
    }

    @Test void exactPowerCapacityIsAccepted() {
        assertEquals(100L, base().addWeapon(new Weapon("Laser", 60))
                .addWeapon(new Weapon("Cannon", 40)).build().totalWeaponPower());
    }

    @Test void oneUnitOverCapacityIsRejected() {
        assertThrows(IllegalStateException.class,
                () -> base().addWeapon(new Weapon("Laser", 101)).build());
    }

    @Test void allWeaponDemandsAreSummed() {
        assertThrows(IllegalStateException.class, () -> base().addWeapon(new Weapon("Laser", 60))
                .addWeapon(new Weapon("Cannon", 60)).build());
    }

    @Test void summingWeaponsCannotWrapAnInteger() {
        SpaceshipBuilder builder = new SpaceshipBuilder()
                .powerCore(new PowerCore("Maximum", Integer.MAX_VALUE))
                .addWeapon(new Weapon("A", Integer.MAX_VALUE))
                .addWeapon(new Weapon("B", Integer.MAX_VALUE));
        assertThrows(IllegalStateException.class, builder::build);
        assertEquals(4_294_967_294L, WeaponPowerValidator.totalDemand(
                List.of(new Weapon("A", Integer.MAX_VALUE), new Weapon("B", Integer.MAX_VALUE))));
    }

    @Test void coreIsRequiredEvenWithoutWeapons() {
        assertThrows(IllegalStateException.class, () -> new SpaceshipBuilder().build());
    }

    @Test void zeroSupplyAndZeroDemandAreValid() {
        Spaceship ship = new SpaceshipBuilder().powerCore(new PowerCore("Dormant", 0))
                .addWeapon(new Weapon("Decorative", 0)).build();
        assertEquals(0L, ship.totalWeaponPower());
    }

    @Test void weaponlessShipIsValid() {
        assertEquals(0L, base().build().totalWeaponPower());
    }

    @Test void weaponsAndCoreMayBeSelectedInEitherOrder() {
        assertEquals(base().addWeapon(new Weapon("Laser", 30)).build(), new SpaceshipBuilder()
                .addWeapon(new Weapon("Laser", 30)).powerCore(new PowerCore("Fusion", 100)).build());
    }

    @Test void shieldsIntegrateWithoutChangingExistingFields() {
        Spaceship original = base().addWeapon(new Weapon("Laser", 100)).build();
        Spaceship extended = base().addWeapon(new Weapon("Laser", 100))
                .addShield(new Shield("Bubble", 1_000)).build();
        assertEquals(original.weapons(), extended.weapons());
        assertEquals(original.powerCore(), extended.powerCore());
        assertEquals(original.totalWeaponPower(), extended.totalWeaponPower());
        assertEquals(List.of(new Shield("Bubble", 1_000)), extended.shields());
    }

    @Test void shieldsDoNotBypassOriginalPowerValidation() {
        assertThrows(IllegalStateException.class, () -> base().addShield(new Shield("Bubble", 1_000))
                .addWeapon(new Weapon("Laser", 101)).build());
    }

    @Test void multipleShieldsAreSupported() {
        assertEquals(2, base().addShield(new Shield("Front", 10))
                .addShield(new Shield("Rear", 20)).build().shields().size());
    }

    @Test void invalidComponentValuesAreRejected() {
        assertThrows(IllegalArgumentException.class, () -> new Weapon("Laser", -1));
        assertThrows(IllegalArgumentException.class, () -> new PowerCore("Fusion", -1));
        assertThrows(IllegalArgumentException.class, () -> new Armor("Titanium", -1));
        assertThrows(IllegalArgumentException.class, () -> new Engine("Ion", -1));
        assertThrows(IllegalArgumentException.class, () -> new Shield("Bubble", -1));
        assertThrows(IllegalArgumentException.class, () -> new Weapon(" ", 0));
        assertThrows(IllegalArgumentException.class, () -> new PowerCore(null, 0));
        assertThrows(IllegalArgumentException.class, () -> new Armor("", 0));
        assertThrows(IllegalArgumentException.class, () -> new Engine(null, 0));
        assertThrows(IllegalArgumentException.class, () -> new Shield("\t", 0));
    }

    @Test void nullPartsAreRejectedImmediately() {
        SpaceshipBuilder builder = base();
        assertThrows(NullPointerException.class, () -> builder.addWeapon(null));
        assertThrows(NullPointerException.class, () -> builder.powerCore(null));
        assertThrows(NullPointerException.class, () -> builder.addArmor(null));
        assertThrows(NullPointerException.class, () -> builder.addEngine(null));
        assertThrows(NullPointerException.class, () -> builder.addShield(null));
        assertEquals("Fusion", builder.build().powerCore().name());
    }

    @Test void builtPartsAreImmutableSnapshots() {
        SpaceshipBuilder builder = base().addWeapon(new Weapon("Laser", 30))
                .addArmor(new Armor("Plate", 10)).addEngine(new Engine("Ion", 10))
                .addShield(new Shield("Bubble", 10));
        Spaceship first = builder.build();
        builder.addWeapon(new Weapon("Cannon", 20)).addShield(new Shield("Rear", 20));
        assertEquals(1, first.weapons().size());
        assertEquals(1, first.shields().size());
        assertThrows(UnsupportedOperationException.class, () -> first.weapons().clear());
        assertThrows(UnsupportedOperationException.class, () -> first.armor().clear());
        assertThrows(UnsupportedOperationException.class, () -> first.engines().clear());
        assertThrows(UnsupportedOperationException.class, () -> first.shields().clear());
    }

    @Test void invalidBuildCanBeFixedWithStrongerCore() {
        SpaceshipBuilder builder = base().addWeapon(new Weapon("Laser", 101));
        assertThrows(IllegalStateException.class, builder::build);
        assertEquals(101L, builder.powerCore(new PowerCore("Upgrade", 101)).build().totalWeaponPower());
    }

    @Test void fluentStepsReturnSameBuilder() {
        SpaceshipBuilder builder = new SpaceshipBuilder();
        assertSame(builder, builder.addWeapon(new Weapon("Laser", 10)));
        assertSame(builder, builder.powerCore(new PowerCore("Fusion", 100)));
        assertSame(builder, builder.addArmor(new Armor("Plate", 10)));
        assertSame(builder, builder.addEngine(new Engine("Ion", 10)));
        assertSame(builder, builder.addShield(new Shield("Bubble", 10)));
    }
}
