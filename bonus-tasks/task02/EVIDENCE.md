# Task 02 — Birthday Cake Architect (3 points)

| Rubric condition | Code | Executable evidence |
| --- | --- | --- |
| 1 point: fluent builder and chronological crumb-coat rule | `CakeBuilder.addFondant()`, `build()` | `everyStepReturnsSameBuilder`, `crumbCoatThenFondantIsValid`, `fondantWithoutCoatIsRejectedAtBuild`, `lateCrumbCoatDoesNotEraseOrderingViolation`, `ordinaryFrostingDoesNotCountAsCrumbCoat` |
| 1 point: optional sparklers, no regressions | `addSparklers()` | `originalChainWorksWithoutNewStep`, `sparklersAreOptionalAndAddNoOtherChanges`, `sparklersDoNotBypassOrderingRule` |
| 1 point: compatibility note | paragraph below | unchanged original chain in `Demo.main` and `originalChainWorksWithoutNewStep` |
| Edges and immutable snapshots | `Math.addExact`, record product | `zeroCandlesAndEmptyCakeAreAllowed`, `negativeCandlesAreRejectedWithoutChangingBuilder`, `candleOverflowIsRejectedWithoutCorruptingCount`, `layersAndCandlesAccumulate`, `builtCakeIsIndependentOfReusedBuilder` |

Adding `addSparklers()` is an additive API change. It returns the existing `CakeBuilder` type, uses an absent-by-default field, and changes neither existing method signatures nor the return type of `build()`. Therefore the existing fluent chain compiles and has the same observable fields. Clients opt into sparklers by adding one step. Fondant chronology is remembered when the step executes and validated by `build()`; adding a crumb coat later cannot retroactively satisfy the rule. Regular frosting is deliberately distinct from a crumb coat. Repeated candles add their count, zero is valid, negatives and overflow fail, and the specification imposes no minimum sponge count.
