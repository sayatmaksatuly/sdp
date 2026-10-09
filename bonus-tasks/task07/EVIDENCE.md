# Task 07 — Monster Hatchery (4 points)

| Условие рубрики | Реализация | Автоматическое подтверждение |
| --- | --- | --- |
| Creator hierarchy / Factory Method | `MonsterCreator.hatch()` вызывает protected `createMonster()` и возвращает только `Monster` | `thirdMonsterAndExtensionUseSameUnawareClient` |
| У типов разные случайные диапазоны | `SlimeCreator`: health 15–25, fire 0–5, defense 1–4; `DragonCreator`: 120–160 / 70–100 / 20–35; `GolemCreator`: 80–110 / 10–20 / 45–65. Все пределы включены | `deterministicMinimumAndMaximumForEveryType` проверяет каждый минимум и максимум всех девяти диапазонов |
| Dragon fire выше Slime fire | Минимум Dragon 70 > максимум Slime 5 | `deterministicMinimumAndMaximumForEveryType` |
| RNG управляем и реально изменяет rolls | `RandomGenerator` передаётся creator; `StatRange.roll()` использует `nextInt(minimum, maximum + 1)` | `seededRollsAreReproducibleAndVaryWithinBounds` проверяет 100 воспроизводимых, различающихся rolls |
| Третий тип без изменений существующих классов | `Golem.java` + `GolemCreator.java`; общий клиент вызывает `hatch()` без проверок типов | `thirdMonsterAndExtensionUseSameUnawareClient` также добавляет Phoenix только в тесте |
| Ошибки и границы | Некорректные ranges/stats, null RNG/product; диапазон одного значения | `invalidRangesStatsRandomAndProductsFailFast` |
| Walkthrough (3–5 минут) | Сценарий ниже; пример запуска находится в `Demo.java` | Тест расширения доказывает независимость клиента |

Запуск: `./gradlew :task07:run`; тесты: `./gradlew :task07:test` (Windows: `gradlew.bat`).

## Сценарий защиты (3–5 минут)

1. Покажите `Monster`, `MonsterCreator` и `DragonCreator`: продукт задаёт интерфейс, базовый creator задаёт алгоритм, подкласс переопределяет точку создания. Это Factory Method, а не общий switch.
2. Пройдите `MonsterCreator creator = new DragonCreator(random); creator.hatch()`: базовый `hatch()` вызывает динамически выбранный `createMonster()`; тот трижды вызывает `roll(range)`; range использует RNG; создаются `MonsterStats` и `Dragon`; клиент получает `Monster` и читает `species()/stats()`.
3. Покажите детерминированные граничные тесты. Минимальная Dragon fire всё равно выше максимальной Slime fire. Seeded-тест проверяет не только диапазоны, но и реальную изменчивость с воспроизводимостью.
4. Чтобы добавить Phoenix, создайте отдельные `Phoenix` и `PhoenixCreator`. В точке сборки приложения передайте новый creator. Изменять `MonsterCreator`, существующих monsters или клиент, принимающий `MonsterCreator`, не нужно. Именно так действует тест расширения.
5. Альтернатива с if/else заставила бы редактировать центральный выбор и хранить все ranges в одном месте. Здесь изменение изолировано в новом creator. Поддерживаются OCP (новая реализация через расширение), DIP (клиент зависит от абстракций), SRP (ranges принадлежат соответствующему creator). Доказательство: `thirdMonsterAndExtensionUseSameUnawareClient` плюс граничные regression-проверки первоначальных Slime и Dragon.
