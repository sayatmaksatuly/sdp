# Task 05 — Potion Brewery (2 points)

| Условие рубрики | Реализация | Автоматическое подтверждение |
| --- | --- | --- |
| Настоящий Factory Method | `PotionCreator.brew()` вызывает переопределяемый `createPotion()`; `Potion` — общий продукт | `PotionTest.thirdPotionUsesUnchangedCreatorContractAndOriginalProductsStillWork` |
| Три разных типа | `HealingPotion` / `HealingCreator`, `InvisibilityPotion` / `InvisibilityCreator`, `LevitationPotion` / `LevitationCreator` | `thirdPotionUsesUnchangedCreatorContractAndOriginalProductsStillWork` |
| Случайный однострочный эффект каждого типа, минимум три варианта | `RandomPotion.sideEffect()` выбирает через инъецированный `RandomGenerator.nextInt`; каждый продукт задаёт три собственных эффекта | `allThreePotionsSelectAtLeastThreeDistinctEffects` проверяет все индексы 0, 1, 2 и повтор цикла |
| Новый третий тип без изменений старых классов | Расширение находится только в `LevitationPotion.java` и `LevitationCreator.java`; клиент работает через `PotionCreator` | `thirdPotionUsesUnchangedCreatorContractAndOriginalProductsStillWork` дополнительно создаёт четвёртый тип в тесте |
| Ошибки и границы | Проверяются RNG, результат factory method, число вариантов | `invalidRandomAndMalformedProductsAreRejected` |

Запуск: `./gradlew :task05:run`; тесты: `./gradlew :task05:test` (Windows: `gradlew.bat`). Demo вызывает все три creators через одинаковый клиентский код. Тесты используют детерминированный RNG, demo — настоящий случайный генератор.

Обоснование: creator содержит общий алгоритм изготовления и делегирует выбор продукта подклассу. Добавление вида означает новый продукт и creator плюс выбор нового creator в точке сборки клиента; менять существующие продукты и алгоритм не требуется. Это поддерживает OCP и DIP. `HealingCreator.brew()` сначала входит в базовый алгоритм, вызывает `createPotion()`, получает `HealingPotion`, затем клиент вызывает `sideEffect()` и RNG выбирает эффект.
