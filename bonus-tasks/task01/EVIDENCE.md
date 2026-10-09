# Task 01 — Villain Lair Builder (2 points)

| Rubric condition | Code | Executable evidence |
| --- | --- | --- |
| Fluent original room steps | `LairBuilder`, immutable `Lair` | `originalClientChainRemainsValid`, `everyStepReturnsSameBuilder` |
| 1 point: shark tank requires observation deck | `LairBuilder.build()` | `sharkTankWithoutDeckIsRejected`, `unrelatedRoomsCannotSatisfyDeckDependency`, `deckMayBeAddedBeforeOrAfterTank` |
| 1 point: new optional step | `addEscapeTunnel()` defaults to absent | `escapeTunnelIsOptionalAndIndependent`, `originalClientChainRemainsValid` |
| Boundary and reuse behavior | repeated rooms count; optional rooms are idempotent | `emptyLairIsValid`, `repeatedRoomStepsCountRoomsAndOptionalStepsAreIdempotent`, `builtSnapshotDoesNotChangeWhenBuilderIsReused`, `failedBuildCanBeCompletedByAddingDeck` |

`Demo.main` runs both the unchanged original chain and the extended chain, and prints a rejected invalid lair. Observation decks may be selected in either order relative to tanks: this is a dependency, not a chronological construction rule. No mandatory rooms beyond the rubric dependency are invented. The new method returns the same builder type; existing method names, argument lists and build return type remain intact.
