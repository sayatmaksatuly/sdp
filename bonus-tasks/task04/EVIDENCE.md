# Task 04 — Spaceship Customizer (6 points)

| Rubric condition | Code | Executable evidence |
| --- | --- | --- |
| 3 points: builder, weapons/core validation | `SpaceshipBuilder`, `WeaponPowerValidator` | `originalPartsBuildWithoutShields`, `exactPowerCapacityIsAccepted`, `oneUnitOverCapacityIsRejected`, `allWeaponDemandsAreSummed`, `summingWeaponsCannotWrapAnInteger` |
| 2 points: new category without editing weapon/core rule | separate `Shield`, additive `addShield()` and immutable product collection | `shieldsIntegrateWithoutChangingExistingFields`, `shieldsDoNotBypassOriginalPowerValidation`, `multipleShieldsAreSupported` |
| 1 point: live defense | script below, `Demo.main` | demo shows original ship, shield ship, and power rejection with shields |
| Edges and regression checks | component constructors and product snapshots | zero power, missing core, null/negative values, construction order, immutable snapshots, recovery and fluent identity tests |

## Defense script (3–5 minutes)

1. Show separate immutable `Weapon`, `PowerCore`, `Armor`, `Engine` components. Named builder steps accumulate components and `build()` returns an immutable snapshot. Power core is mandatory; armor, engines and weapons may be omitted because the rubric adds no minimum counts. Demand and supply are nonnegative integer power units, and the sum uses `long` to avoid integer overflow.
2. Trace `addWeapon(new Weapon("Laser", 60)).addWeapon(new Weapon("Cannon", 40)).powerCore(new PowerCore("Fusion", 100)).build()`: component constructors validate values; steps store components; `build` requires the core; the validator sums demands to 100 and accepts equality; the product snapshots all collections. Changing demand to 101 fails before producing a ship.
3. Demonstrate the extension in `Shield.java`, `addShield()` and `Spaceship.shields()`. Adding this category requires its own component class and additive schema/storage changes to the builder/product. It requires zero changes to `WeaponPowerValidator`: its signature accepts only weapons and core, so it has no dependency on shields. Existing client calls compile unchanged. Shields use strength units independent of power; no unspecified combined power rule is introduced.
4. Run original-call regression plus `shieldsIntegrateWithoutChangingExistingFields` and `shieldsDoNotBypassOriginalPowerValidation`. The extension preserves acceptance at the exact weapon budget and rejection one unit over it. The validator contains no new-category branch or concrete-type checks.
5. A large `if/else` constructor selector would encode every ship combination; direct inheritance would require classes for combinations of parts. Builder supports incremental assembly and validates interdependent choices once. Single Responsibility keeps the existing weapon/core rule separate; Open/Closed is demonstrated for the validator and existing client calls by additive shield integration and the regression tests. The builder/product schema itself is deliberately extended and is not claimed to remain physically unchanged.
