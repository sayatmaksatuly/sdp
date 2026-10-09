# Task 08 — Meme Generator Factory (6 points)

| Условие рубрики | Реализация | Автоматическое подтверждение |
| --- | --- | --- |
| Creator hierarchy / Factory Method | `MemeCreator.generate()` вызывает protected `createFormat()` и общий `MemeFormat.layout()` | `allFormatsProduceGeometricallyBoundedLines`, `thirdFormatAndNewCreatorDoNotChangeOriginalOutputs` |
| Минимум два исходных формата | `TopBottomFormat` + `TopBottomCreator`; `SingleCaptionFormat` + `SingleCaptionCreator` | `allFormatsProduceGeometricallyBoundedLines` |
| Настоящая геометрия и отсутствие overflow | Canvas 320×240; cell 8×16; `TextRegion` задаёт x/y/width/height; `BoundedTextLayout` переносит по code points и считает строки; `MemeLayout` проверяет координаты. SVG задаёт точный `textLength` и clip каждого cell-line | `allFormatsProduceGeometricallyBoundedLines`, `svgUsesExactLengthsClipsAndEscaping` |
| Длинный текст отклоняется | Верх/низ и одиночная caption: по 38×4 = 152 code points; каждый comic panel: 18×14 = 252. Переводы строки потребляют дополнительные строки | `exactCapacityAcceptedOneCharacterOverRejectedForEveryFormat`, `explicitNewlinesConsumeRowsAndUnicodeUsesCodePoints` |
| Третий формат без изменения старых классов | `ComicPanelFormat` + `ComicPanelCreator` описывают две независимые области; существующие форматы не упоминают comic | `thirdFormatAndNewCreatorDoNotChangeOriginalOutputs` сравнивает исходные outputs и добавляет четвёртый Poster только в тесте |
| Ошибки, границы, неизменяемость | Blank/null captions, неправильное количество, controls и непарные surrogate, неверная геометрия, null product; defensive copy layout | `invalidInputsAndInvalidGeometryAreRejected`, `thirdFormatAndNewCreatorDoNotChangeOriginalOutputs` |
| Защита расширения | Сценарий ниже; `Demo` выдаёт валидный SVG каждого формата и показывает отказ для слишком длинной caption | Regression и extension-тест |

Запуск: `./gradlew :task08:run`; тесты: `./gradlew :task08:test` (Windows: `gradlew.bat`). SVG печатается в stdout и может быть сохранён как изображение. Правило layout намеренно использует фиксированную monospace сетку, code points (не UTF-16 chars), переносит длинное слово по cell boundaries и отклоняет текст, которому не хватает строк. Дизайн не требует внешней библиотеки шрифтов. SVG с `textLength` масштабирует glyphs до вычисленной ширины; clip ограничивает необычные glyphs по высоте и ширине, поэтому браузерный fallback-шрифт не может рисовать за пределами области.

## Сценарий защиты

1. Покажите общий `MemeCreator.generate()` и переопределение `createFormat()` в каждом creator. В алгоритме нет проверки concrete types: создание и layout разделены контрактом `MemeFormat`.
2. Пройдите `new TopBottomCreator().generate(List.of("top", "bottom"))`: factory method создаёт `TopBottomFormat`; формат проверяет число captions; каждая caption обрабатывается в своей области; wrapper вычисляет columns/rows, переносит текст, отклоняет недостаток места; `MemeLayout` проверяет canvas; `toSvg()` рисует bounded text со значениями координат, `textLength` и clips.
3. Продемонстрируйте границу 152 / 153 символа, явные newline и supplementary Unicode. Это проверка геометрии каждого rendered line, а не произвольный лимит длины текста.
4. Чтобы добавить comic, нужны только `ComicPanelFormat` и `ComicPanelCreator`, затем выбор creator в composition root. У нового формата свои области; исходные форматы и базовый алгоритм не меняются. Тест добавляет ещё один Poster и повторно проверяет неизменность исходных результатов.
5. If/else объединил бы создание и все правила форматов в центральном алгоритме. Прямой вызов concrete format закрепил бы клиента за одним типом. Factory Method сохраняет OCP и DIP; геометрический utility имеет отдельную ответственность. `thirdFormatAndNewCreatorDoNotChangeOriginalOutputs` доказывает расширяемость и regression safety, а `exactCapacityAcceptedOneCharacterOverRejectedForEveryFormat` доказывает отсутствие overflow для всех форматов на границе.
