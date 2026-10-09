# Task 12 — Time Traveler's Currency Exchanger (6 points)

| Условие | Код | JUnit evidence |
| --- | --- | --- |
| Adapter and conversion (3 балла) | `MedievalPaymentAdapter implements PaymentProcessor`; `ConversionRates` читает counts, умножает exact decimal rates, суммирует | `combinesCurrenciesAndRoundsOnceHalfUp`, `exactBalanceInsufficientFundsAndZeroAreHandledAtomically` |
| Rounding and edges (2 балла) | HALF_UP один раз для суммы, `longValueExact`, validation, atomic synchronized ledger | `combinesCurrenciesAndRoundsOnceHalfUp`, `negativeValuesAndInvalidDependenciesAreRejected`, `longBoundaryIsSupportedAndConversionOverflowIsRejected`, `concurrentChargesCannotOverspend` |
| Второй legacy system + defense (1 балл) | `GalacticCreditWallet`, `GalacticPaymentAdapter`; unchanged `PaymentProcessor`, `Checkout` | `newCurrencyWorksThroughUnchangedCheckoutAndOriginalStillWorks`; `DEFENSE_NOTES.md`, раздел 12 |

Rates выражены в cents per unit и инъецируются; никаких реальных валютных курсов. Пример: 1 gold × 100.25 + 2 silver × 10.25 = 120.75 → 121 cents. 0.25 + 0.25 округляется до 1, а не до 0: сначала сумма, потом округление. Negative charge — ошибка; zero — success даже при empty balance; insufficient funds — false без изменения balance; long overflow — ArithmeticException при conversion.

Legacy API предоставляет только счётчики, поэтому adapter захватывает исходный balance и ведёт собственный cents ledger. Один adapter владеет одним purse snapshot; повторное оборачивание одного purse создаст другой независимый ledger. Физические coins не удаляются. Внутри одного adapter concurrent charges не могут превысить balance. Для shared banking storage понадобился бы общий transactional debit API, отсутствующий в задании.
