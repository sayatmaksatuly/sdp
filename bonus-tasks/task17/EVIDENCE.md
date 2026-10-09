# Task 17 — Coffee Order Translator (2 points)
| Rubric condition | Implementation | Automated evidence |
|---|---|---|
| Correct independent split (1 pt) | Abstract `CoffeeDrink` contains `BrewMethod`; `Latte/Americano` refine drinks, `EspressoMachine/PourOver` implement brewing | `CoffeeDrinkTest.abstractionDelegatesExactlyOnceToAnIndependentNewImplementor` |
| All four combinations (1 pt) | `Demo` constructs each drink with each method | `allFourCombinationsWork` |
| Validation/edges and extension | Null method, empty coffee, nonpositive volume, minimum 1 ml; independent test implementor | `invalidDependenciesAndBrewParametersFail`, `abstractionDelegatesExactlyOnceToAnIndependentNewImplementor` |

The drink decides its recipe (60 ml base plus milk/water); the method decides brewing. No combined classes such as `PourOverLatte` are necessary. Pour-over latte uses a filtered coffee base as a deliberate illustrative recipe.
