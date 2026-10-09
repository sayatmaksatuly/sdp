# Task 15 — Theme Park Switcheroo (4 points)
| Rubric condition | Implementation | Automated evidence |
|---|---|---|
| Both matching factories (2 pts) | `HorrorParkFactory` with `HorrorRide/HorrorBackgroundTrack`; `SummerParkFactory` with `SummerRide/SummerBackgroundTrack` | `ThemeParkTest.fullDaysCoverEveryHourFromOpeningToClosingInBothModes` |
| Full-day simulation | `ThemePark.simulateDay`: OPEN 09:00, hourly activity 10:00–20:00, CLOSE 21:00; immutable `DayEvent` list | `fullDaysCoverEveryHourFromOpeningToClosingInBothModes`, `eachOpenHourDelegatesToBothProductsAndClosingStopsThem` |
| Clean client (1 pt) | `ParkConfiguration.select(flag)`; `ThemePark` depends on abstract products | `winterExtensionDoesNotChangeHorrorOrSummerDays` |
| Walkthrough (1 pt) | Script below, implemented `WinterParkFactory`, `WinterRide`, `WinterBackgroundTrack` extension | `winterExtensionDoesNotChangeHorrorOrSummerDays` |
| Validation/edges | Invalid opening/closing ranges, midnight-to-23:00, one-hour day, mixed families, unknown flag | `invalidSchedulesFlagsAndMixedFamiliesAreRejected`, `eachOpenHourDelegatesToBothProductsAndClosingStopsThem` |

## 3–5 minute walkthrough
1. (0:00–0:45) Identify the product interfaces and explain why a whole family is chosen together: one mode must supply a matching attraction and soundtrack.
2. (0:45–1:45) Trace `select("horror")` → map supplier → `HorrorParkFactory` → `ThemePark` requests both products once and checks family identifiers. `simulateDay()` opens at 09:00, calls each product once per open hour, and adds a closing event with rides/music stopped at 21:00.
3. (1:45–2:45) Run the demo and compare the summer day: the scheduling client is identical; only the supplied factory changes.
4. (2:45–3:45) Add a mode with three new classes, implementing `ParkFactory`, `Ride`, and `BackgroundTrack`, plus a registration at the composition root. Winter is the executable example. Existing factories and `ThemePark` require zero edits.
5. (3:45–4:30) Abstract Factory avoids repeating mode branches for every product and prevents accidental family mixing. It supports DIP (client uses interfaces) and OCP (new family without editing existing families). Run `winterExtensionDoesNotChangeHorrorOrSummerDays` and the recording-factory delegation test to demonstrate this.

Assumption: the default full business day is 09:00–21:00, with hourly simulated activities; custom same-day hours are supported. These are simulations, with no wall-clock waiting, hardware, or audio files.
