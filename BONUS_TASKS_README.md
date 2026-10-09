# Все 20 SDP bonus tasks

Проект реализует пять паттернов в 20 независимых Java 21 Gradle-модулях. Каждый модуль имеет собственные
products/interfaces, demo, JUnit 5 tests и `EVIDENCE.md`. Между решениями нет зависимостей.
Общая рубрика из DOCX содержит 75 возможных баллов; checklist подтверждает каждое техническое условие,
а фактическую оценку определяет преподаватель.

## Запуск

На этом Windows-компьютере установлен JDK 21 внутри IntelliJ IDEA. Скрипт выбирает JDK 21,
настраивает локальный Gradle cache и обходит ошибку Windows loopback в JBR.

```powershell
powershell -ExecutionPolicy Bypass -File .\run-gradle.ps1 clean build
powershell -ExecutionPolicy Bypass -File .\run-gradle.ps1 runAllDemos
powershell -ExecutionPolicy Bypass -File .\run-gradle.ps1 :task12:test
powershell -ExecutionPolicy Bypass -File .\run-gradle.ps1 :task16:run --args=cyberpunk
```

При уже настроенном `JAVA_HOME` на JDK 21 используйте `gradlew.bat` на Windows либо `./gradlew` на Unix.
Первый запуск на новой машине требует интернета для Gradle 8.13, JUnit 5.11.4 и Checkstyle 10.21.2.
После заполнения cache можно добавить `--offline`. Java source encoding — UTF-8; Gradle worker arguments
используют native charset (`file.encoding=COMPAT`), чтобы кириллица в Windows paths не мешала тестам.
Официальный SHA-256 Gradle distribution закреплён в wrapper properties.

`build` компилирует main/test с `-Xlint:all -Werror`, запускает JUnit и Checkstyle и создаёт JAR/дистрибутив
каждого приложения. `runAllDemos` запускает все 20 отдельных main methods.

## Таблица решений

Команды в таблице используют Unix spelling wrapper. Windows: замените `./gradlew` на `gradlew.bat`
или на `powershell -ExecutionPolicy Bypass -File .\run-gradle.ps1`.
В каждом каталоге main код расположен в `src/main/java/bonus/taskNN`, тесты — в `src/test/java/bonus/taskNN`.

| № | Задание | Паттерн | Код и локальные доказательства | Demo | Tests |
| --- | --- | --- | --- | --- | --- |
| 01 | Villain Lair Builder | Builder | [task01](bonus-tasks/task01/EVIDENCE.md) | `./gradlew :task01:run` | `./gradlew :task01:test` |
| 02 | Birthday Cake Architect | Builder | [task02](bonus-tasks/task02/EVIDENCE.md) | `./gradlew :task02:run` | `./gradlew :task02:test` |
| 03 | Dating Profile Builder | Builder | [task03](bonus-tasks/task03/EVIDENCE.md) | `./gradlew :task03:run` | `./gradlew :task03:test` |
| 04 | Spaceship Customizer | Builder | [task04](bonus-tasks/task04/EVIDENCE.md) | `./gradlew :task04:run` | `./gradlew :task04:test` |
| 05 | Potion Brewery | Factory Method | [task05](bonus-tasks/task05/EVIDENCE.md) | `./gradlew :task05:run` | `./gradlew :task05:test` |
| 06 | Food Truck Spawner | Factory Method | [task06](bonus-tasks/task06/EVIDENCE.md) | `./gradlew :task06:run` | `./gradlew :task06:test` |
| 07 | Monster Hatchery | Factory Method | [task07](bonus-tasks/task07/EVIDENCE.md) | `./gradlew :task07:run` | `./gradlew :task07:test` |
| 08 | Meme Generator Factory | Factory Method | [task08](bonus-tasks/task08/EVIDENCE.md) | `./gradlew :task08:run` | `./gradlew :task08:test` |
| 09 | Grandpa's Flip Phone Translator | Adapter | [task09](bonus-tasks/task09/EVIDENCE.md) | `./gradlew :task09:run` | `./gradlew :task09:test` |
| 10 | Ancient Fax Whisperer | Adapter | [task10](bonus-tasks/task10/EVIDENCE.md) | `./gradlew :task10:run` | `./gradlew :task10:test` |
| 11 | Universal Game Controller | Adapter | [task11](bonus-tasks/task11/EVIDENCE.md) | `./gradlew :task11:run` | `./gradlew :task11:test` |
| 12 | Time Traveler's Currency Exchanger | Adapter | [task12](bonus-tasks/task12/EVIDENCE.md) | `./gradlew :task12:run` | `./gradlew :task12:test` |
| 13 | Cereal Box Family | Abstract Factory | [task13](bonus-tasks/task13/EVIDENCE.md) | `./gradlew :task13:run` | `./gradlew :task13:test` |
| 14 | Wedding vs Funeral Caterer | Abstract Factory | [task14](bonus-tasks/task14/EVIDENCE.md) | `./gradlew :task14:run` | `./gradlew :task14:test` |
| 15 | Theme Park Switcheroo | Abstract Factory | [task15](bonus-tasks/task15/EVIDENCE.md) | `./gradlew :task15:run` | `./gradlew :task15:test` |
| 16 | Parallel Universe Generator | Abstract Factory | [task16](bonus-tasks/task16/EVIDENCE.md) | `./gradlew :task16:run` | `./gradlew :task16:test` |
| 17 | Coffee Order Translator | Bridge | [task17](bonus-tasks/task17/EVIDENCE.md) | `./gradlew :task17:run` | `./gradlew :task17:test` |
| 18 | Universal Remote for Appliances | Bridge | [task18](bonus-tasks/task18/EVIDENCE.md) | `./gradlew :task18:run` | `./gradlew :task18:test` |
| 19 | Robot Pet Shop | Bridge | [task19](bonus-tasks/task19/EVIDENCE.md) | `./gradlew :task19:run` | `./gradlew :task19:test` |
| 20 | Multiverse Messenger | Bridge | [task20](bonus-tasks/task20/EVIDENCE.md) | `./gradlew :task20:run` | `./gradlew :task20:test` |

Флаги demo: task13 `superhero`/`dinosaur`; task14 `wedding`/`funeral`/`birthday`;
task15 `horror`/`summer`/`winter`; task16 `steampunk`/`cyberpunk`/`medieval`/`space`.
Без аргументов demo показывает все families. Factories выбираются через map suppliers; concrete type checks отсутствуют.

## Материалы для защиты

- [DEFENSE_NOTES.md](DEFENSE_NOTES.md): 3–5 минут на задания 3, 4, 7, 8, 11, 12, 15, 16, 19, 20;
  extension edits, выбор паттерна, конкретный call trace, SOLID и tests.
- [RUBRIC_CHECKLIST.md](RUBRIC_CHECKLIST.md): каждое условие → class/file/test; исходные баллы.
- [VERIFICATION_REPORT.md](VERIFICATION_REPORT.md): фактические результаты сборки, checks, demos и test counts.
- [docs/source-rubric.txt](docs/source-rubric.txt): полный текст, извлечённый из указанного DOCX,
  включая rubric и shared quick-check questions.

## Допущения и границы примеров

Папка была пустой: не было Git metadata, README/AGENTS, существующего стека или пользовательского кода.
Создан новый Gradle multi-project по указанию пользователя. Документ использован как техническое задание;
файлы вне проекта не редактировались. Git init/commit и внешняя публикация не требовались.

- Task01: допустим пустой lair; единственное взаимозависимое правило — shark tank требует observation deck.
  Optional extension — escape tunnel.
- Task02: crumb coat — явный отдельный шаг, обычный frosting его не заменяет. Fondant до coat навсегда
  делает эту цепочку невалидной; ошибка выдаётся в build. Candles неотрицательны; пустой cake допустим.
  Optional sparklers сохраняют исходные chains; explanation находится в EVIDENCE task02.
- Task03: name/bio обязательны, hobbies — уникальный sorted set. Verification — local optional marker,
  не сервис проверки личности. Optional red flag по умолчанию absent.
- Task04: core обязателен; nonnegative powers; weapons суммируются в long. Shields имеют собственную
  strength и не меняют исходный weapon/core energy rule. Armor и engines могут отсутствовать.
- Tasks05/07: RNG инъецируется; stats ranges включают оба endpoints. Тесты используют controlled endpoints
  и seed, а не вероятностные assertions.
- Task06: day определяется injected Clock и его timezone; все семь дней имеют разные specials.
- Task08: canvas 320×240, monospace cells 8×16; code points, newline rows, reject overflow.
  SVG печатается в stdout. TextLength и clipping удерживают fallback glyphs внутри областей.
- Task09: 20 Unicode code points, lossless ordered splitting; empty message отвергается.
- Task10: plain-text document до 2000 UTF-16 units; raw grayscale 640×512, fixed-cell rendering,
  recipientId — literal phone number. Rich layout/newlines не сохраняются. ACK-token grammar локальная.
- Task11: единственная ось `x`; malformed readings clamp до [-100,100], unknown axes отвергаются.
- Task12: rates — injected illustrative cents/unit; суммарный balance округляется HALF_UP один раз.
  Count-only legacy API означает adapter-owned snapshot ledger: один adapter на один purse;
  separate adapters не делят balance, physical coins не удаляются. Charge thread-safe внутри adapter.
- Tasks13–16: family strings — metadata consistency checks, не проверки concrete types.
  Flag registries — composition roots; extension требует регистрации supplier, а не изменения клиента.
  Полный рабочий день парка — 09:00–21:00 с явным закрытием; simulation не требует real-time waits.
- Tasks17–20: implementors локально моделируют receipts и операции; physical hardware/network не нужны.
  VoiceMessage содержит transcript и duration. Для реального транспорта сохраняются interfaces,
  но понадобились бы implementors соответствующих служб.

Это запускаемые учебные модели паттернов. Ограничения external hardware и adapter-owned balance
не скрыты и не заменяют требуемые validations, extension examples или tests.
