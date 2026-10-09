# Task 16 — Parallel Universe Generator (6 points)
| Rubric condition | Implementation | Automated evidence |
|---|---|---|
| Three consistent trios (3 pts) | `SteampunkUniverseFactory`, `CyberpunkUniverseFactory`, `MedievalUniverseFactory`; each has separate matching `Hero`, `Villain`, `Vehicle` implementations | `UniverseTest.allFourFamiliesProduceConsistentTriosWithDistinctBehavior` |
| Clean swap through one flag (2 pts) | `UniverseConfiguration.select(flag)` and abstract-factory `Universe` client; no concrete type checks | `allFourFamiliesProduceConsistentTriosWithDistinctBehavior` |
| Fourth universe defense (1 pt) | Separate `SpaceUniverseFactory`, `SpaceHero`, `SpaceVillain`, `SpaceVehicle` | `fourthUniverseLeavesOriginalThreeUnchanged` |
| Validation/edges | Unknown/blank/null flag, null factory, mixed universe products | `invalidFlagsAndCrossUniverseProductsFailClearly` |

## Live defense (3–5 minutes)
Show the three independent original factory files. Each method creates its corresponding product type; products identify the same universe. Trace `select("cyberpunk")` through its supplier, then `new Universe(factory)` creating all three products, then `explore()` invoking hero, villain, and vehicle through their interfaces. Run the demo to show all universes.

Add a universe using four new classes (one factory, three products) and register one supplier in configuration. Space is implemented exactly this way. No changes to the original factories, original products, or `Universe` client are needed. No `if/else`, `switch`, `instanceof`, or class inspection selects a concrete family.

Abstract Factory suits coordinated product creation; direct inheritance or repeated selection branches would couple each product choice to the client and invite mismatched families. DIP is visible in the client constructor's `UniverseFactory` parameter; OCP is demonstrated by `fourthUniverseLeavesOriginalThreeUnchanged`. The consistency guard checks domain family identifiers rather than concrete classes. Show the mixed-family negative test and the existing-family regression test.

`clientCreatesEachProductOnceAndDelegatesThroughInterfaces` uses a recording factory with fresh anonymous product implementations. It asserts one creation and one behavior call per product, proving the client accepts new families through interfaces.
