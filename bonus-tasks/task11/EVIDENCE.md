# Task 11 — Universal Game Controller (4 points)

| Условие | Код | JUnit evidence |
| --- | --- | --- |
| Adapter, polarity inversion и scaling (2 балла) | `JoystickAdapter.getAxis`: clamp(raw, -100, 100), затем `-bounded / 100.0` | `polarityScalingAndEveryLegalPositionAreCorrect`: все 201 значение |
| Test (1 балл) | `JoystickAdapterTest` | Endpoints, zero, fractions, integer extrema, live updates, errors |
| Walkthrough (1 балл) | `DEFENSE_NOTES.md`, раздел 11 | `anotherControllerCanUseSameClientContract` |
| Невалидная ось и dependency | Единственная ось `x`; unknown/null отклоняются до чтения | `readsLatestPositionAndRejectsUnavailableAxesBeforeReading` |
| Неисправные legacy readings | Saturation перед отрицанием исключает overflow MIN_VALUE | `brokenLegacyReadingsAreClampedWithoutIntegerNegationOverflow` |

Legacy hardware simulated through injectable IntSupplier. Out-of-range data is clamped; unsupported axes fail explicitly. Каждый корректный запрос читает актуальную позицию ровно один раз.
