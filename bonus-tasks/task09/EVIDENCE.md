# Task 09 — Grandpa's Flip Phone Translator (2 points)

| Условие | Код | JUnit evidence |
| --- | --- | --- |
| Adapter реализует современный Messenger (1 балл) | `FlipPhoneAdapter implements Messenger`, composition `FlipPhone`, `Contact` | `shortAndExactBoundaryMessagesUseOneTransmission` вызывает через интерфейс |
| Реальное соблюдение 20 символов (1 балл) | `send` разбивает по Unicode code points, отправляет все части по порядку | `longMessagesSplitWithoutLossOrBrokenUnicode`: 21/40/41 символ и 43 emoji; склейка полностью равна исходному сообщению |
| Ошибки и границы | Empty/null message, invalid/null contact, legacy-side length check | `invalidInputsNeverReachLegacyDevice`, `legacyLimitAndImmutableHistoryRemainEnforced` |

Демонстрационный телефон хранит immutable history; сеть не используется. Лимит — 20 Unicode code points. Empty message отклоняется. У частей нет дополнительных заголовков: текст сохраняется целиком, а порядок — порядок вызовов legacy API. Отправка нескольких SMS не является атомарной сетевой транзакцией.
