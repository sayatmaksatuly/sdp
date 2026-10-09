# Task 06 — Food Truck Spawner (3 points)

| Условие рубрики | Реализация | Автоматическое подтверждение |
| --- | --- | --- |
| Creator hierarchy / Factory Method | `FoodTruckCreator.spawnToday()` вызывает `createTruck()`; три concrete creators переопределяют его | `creatorUsesInjectedClockForEachDayAndOriginalTrucksRegressions` |
| TacoTruck и RamenTruck | Отдельные `TacoTruck`, `RamenTruck` и их creators | `everyTruckHasSevenDifferentSpecials` |
| Special действительно меняется по дню недели | У каждого продукта семь собственных блюд; день определяется из инъецированного `Clock` с его часовым поясом | `everyTruckHasSevenDifferentSpecials`, `creatorUsesInjectedClockForEachDayAndOriginalTrucksRegressions`, `clockZoneDeterminesDayAcrossMidnight` |
| Третий тип без изменений исходных продуктов / creators | `WaffleTruck` + `WaffleTruckCreator`; общий клиент принимает базовый creator | `extensionWorksThroughExistingClientContract`; regression-проверка запускает все исходные trucks на всех семи днях |
| Валидация | Запрещены null clock, day и продукт factory method | `nullClockAndNullProductAreRejected`, `everyTruckHasSevenDifferentSpecials` |

Запуск: `./gradlew :task06:run`; тесты: `./gradlew :task06:test` (Windows: `gradlew.bat`). Demo показывает все 21 сочетание «3 trucks × 7 дней» с фиксированными датами, поэтому результат воспроизводим.

Обоснование: factory method отвечает за создание truck, а сам truck отвечает за свои specials. Массив блюд индексируется общим `DayOfWeek.getValue()`, не конкретным типом продукта; условной цепочки создания нет. Новый truck требует отдельного продукта и creator, а существующий `spawnToday()` продолжает работать. Это OCP и DIP; расширение дополнительным truck в тесте проходит через старый контракт. Конкретный вызов: `TacoTruckCreator.spawnToday()` → `createTruck()` → дата из Clock → `TacoTruck.special(day)` → неизменяемый `DailyServing`.
