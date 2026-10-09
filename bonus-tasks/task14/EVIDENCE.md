# Task 14 — Wedding vs Funeral Caterer (3 points)
| Rubric condition | Implementation | Automated evidence |
|---|---|---|
| Matching families (1 pt) | `WeddingCateringFactory`, `FuneralCateringFactory`; separate `WeddingMenu/Decor`, `FuneralMenu/Decor` | `CateringEventTest.threeMoodsHaveMatchingMenusAndDecor` |
| Clean flag swap (1 pt) | `CateringConfiguration.select(flag)` injects `CateringFactory` into `CateringEvent` | `threeMoodsHaveMatchingMenusAndDecor` |
| Third mood added cleanly (1 pt) | `BirthdayCateringFactory`, `BirthdayMenu`, `BirthdayDecor`; registry registration only | `thirdMoodDoesNotRegressOriginalFactories` |
| Validation/edges | Unknown/empty/null flags, null factory, mixed mood rejection | `invalidFlagsAndMixedMoodsAreRejected` |

`Demo` runs wedding, funeral, birthday. The third mood does not change either original concrete factory or the event client. The immutable registry can also be supplied by callers.

`clientCreatesEachProductOnceAndDelegatesThroughInterfaces` uses a recording factory with fresh anonymous product implementations. It asserts one creation and one behavior call per product, proving the client accepts new families through interfaces.
