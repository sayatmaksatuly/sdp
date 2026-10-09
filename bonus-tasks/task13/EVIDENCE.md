# Task 13 — Cereal Box Family (2 points)
| Rubric condition | Implementation | Automated evidence |
|---|---|---|
| Both matching families (1 pt) | `SuperheroCerealFactory` → `SuperheroMascot` + `SuperheroJingle`; `DinosaurCerealFactory` → `DinosaurMascot` + `DinosaurJingle` | `CerealBoxTest.everyFlagCreatesAMatchingFamily` |
| Swap one flag without concrete type checks (1 pt) | `CerealConfiguration.select(flag)`, abstract `CerealFactory`, `CerealBox` client | `everyFlagCreatesAMatchingFamily`, `registryExtensionLeavesExistingFamiliesWorking` |
| Validation and edge cases | Unknown/empty/null flag; empty registry; null factory; mixed family rejection | `rejectsInvalidConfigurationAndMixedProducts` |

`Demo` shows both flags by default; one argument selects one family. Registry lookup occurs only in configuration. Client composes products through interfaces. Family string comparison enforces consistency without testing runtime classes.

`clientCreatesEachProductOnceAndDelegatesThroughInterfaces` uses a recording factory with fresh anonymous product implementations. It asserts one creation and one behavior call per product, proving the client accepts new families through interfaces.
