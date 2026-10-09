# Сценарии защиты bonus tasks

Каждый раздел рассчитан на 3–5 минут: 40 секунд на роли паттерна, 60–90 секунд на конкретный вызов,
60 секунд на расширение и 40–60 секунд на тесты. Запускайте demo и названные тесты из таблицы
`BONUS_TASKS_README.md`. Подробные схемы рубрик находятся в `RUBRIC_CHECKLIST.md` и локальных `EVIDENCE.md`.

## 3. Dating Profile Builder

**Роли и выбор.** `DatingProfileBuilder` постепенно собирает поля и возвращает immutable `DatingProfile`.
Builder удобен для необязательного red flag и разных порядков заполнения: constructor с множеством параметров
труднее читать, а наследование каждой комбинации полей создаёт лишние классы. Проверки обязательных полей —
валидация данных, а не выбор concrete implementation через if/else.

**Вызов.** `name("Alex").bio("Likes tea").addHobby("Baking").build()` проверяет и сохраняет строки,
добавляет hobby в sorted set, проверяет name/bio и минимум одно hobby, копирует коллекцию в продукт.
Две цепочки в `Demo` меняют порядок полей и hobbies, но возвращают равные profiles.

**Расширение.** Добавить optional `verify()` и marker в продукт, оставить default false. Именно это уже
реализовано. Изменяются schema builder/product; старые вызовы и hobby rule остаются прежними.
Здесь OCP относится к клиентским вызовам, а не к невозможности изменить schema builder.

**SOLID и доказательство.** SRP отделяет сборку от результата; OCP сохраняет старые цепочки.
`DatingProfileBuilderTest.allFieldsAreIndependentOfConstructionOrder`,
`optionalVerificationPreservesExistingClientFields`, `verificationCannotBypassHobbyRule`.

## 4. Spaceship Customizer

**Роли и выбор.** `SpaceshipBuilder` собирает weapons, core, armor, engines и shields; `Spaceship` —
immutable snapshot. Builder удерживает межкомпонентное правило в момент завершения сборки.
Наследование вариаций корабля усложнило бы комбинации, а общий switch на модель корабля смешал бы сборку и выбор.

**Вызов.** `powerCore(new PowerCore("Fusion", 100)).addWeapon(new Weapon("Laser", 70)).build()` сохраняет
core/weapon, проверяет core, вызывает `WeaponPowerValidator.validate`, суммирует demand в long,
сравнивает 70 с 100 и возвращает snapshot. Ровно 100 допустимо; 101 отклоняется.

**Расширение.** Для shields добавлен отдельный `Shield`, список в product/builder и `addShield`.
`WeaponPowerValidator` принимает только weapons и core, поэтому его тело и сигнатура не меняются.
Новые щиты не участвуют в исходном energy rule: rubric требует именно weapon demand.

**SOLID и доказательство.** SRP выделяет power validation, OCP сохраняет его при расширении schema.
`SpaceshipBuilderTest` проверяет точную границу, превышение, long-сумму, shields и исходную цепочку.
Точные названия всех этих тестов приведены в `bonus-tasks/task04/EVIDENCE.md`.

## 7. Monster Hatchery

**Роли и выбор.** `MonsterCreator.hatch()` — общий workflow; protected `createMonster()` — factory method;
`DragonCreator`, `SlimeCreator`, `GolemCreator` создают отдельные `Monster` products.
Клиент вызывает только `hatch().stats()`/`species()` и не определяет concrete creator.
Центральный switch пришлось бы менять при каждом новом monster.

**Вызов.** `new DragonCreator(rng).hatch()` вызывает override, `roll` обращается к injected RNG для
health 120–160, fire 70–100, defense 20–35, строит `MonsterStats`, затем `Dragon`.
Slime fire 0–5, то есть даже минимальный Dragon fire выше максимального Slime.

**Расширение.** Добавить product и creator отдельными классами, выбрать creator в composition root.
Общий `MonsterCreator`, исходные creators/products и потребляющий их клиент не меняются.
Golem уже реализован; дополнительный Phoenix показывается в тесте.

**SOLID и доказательство.** OCP и DIP: client зависит от абстракций, RNG инъецируется.
`MonsterTest.deterministicMinimumAndMaximumForEveryType`,
`seededRollsAreReproducibleAndVaryWithinBounds`, `thirdMonsterAndExtensionUseSameUnawareClient`.

## 8. Meme Generator Factory

**Роли и выбор.** `MemeCreator.generate()` вызывает factory method `createFormat()`, затем контракт
`MemeFormat.layout()`. У каждого формата свои области; общий utility проверяет geometry.
Switch собрал бы все layouts и создание в одном месте; прямой concrete вызов связал бы клиента с форматом.

**Вызов.** `new TopBottomCreator().generate(List.of("top", "bottom"))` создаёт `TopBottomFormat`,
проверяет две captions, рассчитывает monospace cells 8×16, переносит code points, проверяет число строк,
создаёт `MemeLayout` 320×240. `toSvg()` устанавливает точные `textLength` и clip каждой строки.
152 символа в caption помещаются, 153 отвергаются; newline расходует строку.

**Расширение.** Добавлены только `ComicPanelFormat` и `ComicPanelCreator`, затем creator выбирается
при композиции. Старые formats и общий workflow не знают comic. Тест дополнительно вводит Poster.

**SOLID и доказательство.** OCP/DIP; SRP отделяет geometry от создания.
`MemeTest.exactCapacityAcceptedOneCharacterOverRejectedForEveryFormat`,
`svgUsesExactLengthsClipsAndEscaping`, `thirdFormatAndNewCreatorDoNotChangeOriginalOutputs`.

## 11. Universal Game Controller

**Роли и выбор.** `JoystickAdapter implements GameController` содержит `RetroJoystick`.
Прямой legacy вызов заставил бы игровой клиент помнить диапазон и инверсию; наследование legacy device
не исправляет контракт современной игры и мешает другим устройствам.

**Вызов.** `getAxis("x")` проверяет поддерживаемую ось, читает paddle ровно один раз, clamps значение
до -100..100, затем делит отрицательное значение на 100.0. Legacy -50 становится +0.5; -100 → +1.0.
Clamp перед отрицанием защищает и от Integer.MIN_VALUE при повреждённых показаниях.

**Расширение.** Для второго legacy device нужен только новый adapter, реализующий `GameController`.
Его calibration живёт внутри adapter. Без adapter gameplay код пришлось бы менять для нового scaling.

**SOLID и доказательство.** DIP и OCP: игровой клиент знает только GameController.
`JoystickAdapterTest.polarityScalingAndEveryLegalPositionAreCorrect`,
`brokenLegacyReadingsAreClampedWithoutIntegerNegationOverflow`, `anotherControllerCanUseSameClientContract`.

## 12. Time Traveler's Currency Exchanger

**Роли и выбор.** `Checkout` зависит от `PaymentProcessor`; `MedievalPaymentAdapter` переводит legacy
counts в cents. Прямой purse вызов перенёс бы conversion и rounding в каждого клиента; наследование purse
не даст современного payment contract для другой валютной системы.

**Вызов.** Purse с 1 gold/2 silver и rates 100.25/10.25 cents даёт exact 120.75. `ConversionRates`
округляет общую сумму HALF_UP до 121 и проверяет long range. `Checkout.pay(120)` вызывает `charge`,
ledger атомарно оставляет 1 cent; `pay(2)` возвращает false и сохраняет balance.

**Расширение.** `GalacticCreditWallet` + `GalacticPaymentAdapter` — отдельные классы второго legacy system.
Уже существующие `Checkout` и `PaymentProcessor` не меняются. Rates инъецируются.
Legacy API count-only: adapter владеет одним snapshot ledger, а не удаляет physical coins.
Один purse следует оборачивать одним adapter; отдельные adapters не делят банковское состояние.

**SOLID и доказательство.** DIP/OCP и SRP для conversion.
`PaymentAdapterTest.newCurrencyWorksThroughUnchangedCheckoutAndOriginalStillWorks`,
`combinesCurrenciesAndRoundsOnceHalfUp`, `longBoundaryIsSupportedAndConversionOverflowIsRejected`,
`concurrentChargesCannotOverspend`.

## 15. Theme Park Switcheroo

**Роли и выбор.** `ParkFactory` создаёт согласованные `Ride` и `BackgroundTrack`; `ThemePark`
получает целую family. Отдельные switches для музыки и аттракциона позволили бы случайно смешать режимы.
Abstract Factory меняет family одним выбором registry flag.

**Вызов.** `new ThemePark(new ParkConfiguration().select("horror")).simulateDay()` создаёт ride/track,
проверяет family identifiers, строит события OPEN в 09:00, OPERATING до 20:00 и CLOSE в 21:00.
В каждом открытом часу вызывает оба product interfaces, при закрытии останавливает ride/music.
Demo показывает полный день в horror и summer; flag может выбрать один режим.

**Расширение.** Новый factory и две product classes плюс регистрация supplier при композиции.
Winter уже реализован. Первые factories, `ThemePark` и их интерфейсы не меняются.

**SOLID и доказательство.** OCP/DIP; consistency проверяется без concrete type checks.
`ThemeParkTest.fullDaysCoverEveryHourFromOpeningToClosingInBothModes`,
`eachOpenHourDelegatesToBothProductsAndClosingStopsThem`, `winterExtensionDoesNotChangeHorrorOrSummerDays`.

## 16. Parallel Universe Generator

**Роли и выбор.** `UniverseFactory` создаёт Hero/Villain/Vehicle единой family.
Steampunk, Cyberpunk, Medieval и Space имеют отдельные concrete products/factories.
Три independent switches или прямое создание concrete products заставили бы менять клиента и рисковать mismatch.

**Вызов.** `UniverseConfiguration.select("cyberpunk")` находит supplier по одному flag, создаёт factory.
`new Universe(factory)` по одному разу получает три products и проверяет family values.
`explore()` вызывает `hero.act`, `villain.act`, `vehicle.travel` через interfaces.
Здесь строковый lookup — composition configuration, а не проверка concrete types.

**Расширение.** Для Space добавлены factory + три products, затем supplier в composition registry.
Первые три factories, products и `Universe` не меняются. Custom registry можно передать извне.

**SOLID и доказательство.** OCP/DIP; Abstract Factory связывает family creation.
`UniverseTest.allFourFamiliesProduceConsistentTriosWithDistinctBehavior`,
`fourthUniverseLeavesOriginalThreeUnchanged`, `clientCreatesEachProductOnceAndDelegatesThroughInterfaces`.

## 19. Robot Pet Shop

**Роли и выбор.** Абстракция `RobotPet` содержит отдельный implementor `ControlSystem`.
RobotDog/RobotCat определяют действия, VoiceControl/AppControl — исполнение команды.
Прямое наследование комбинированных DogVoice/DogApp/CatVoice/CatApp умножило бы классы при новой оси.

**Вызов.** `new RobotDog(new AppControl()).play()` выбирает `fetch a ball`, вызывает base `perform`,
затем `AppControl.execute("Robot dog", "fetch a ball")`, проверяет inputs и возвращает receipt.
Demo показывает все четыре исходные комбинации и обе с GestureControl.

**Расширение.** Добавить только класс `ControlSystem.execute` и inject его при композиции.
`GestureControl` реализован без изменения pet side; нового pet можно аналогично добавить без изменения controls.

**SOLID и доказательство.** DIP/OCP и независимые иерархии Bridge.
`RobotPetTest.recordingControlShowsOneDelegationPerPetOperation`,
`originalFourCombinationsAndThirdControlWork`, `gestureExtensionKeepsOriginalControlBehavior`.

## 20. Multiverse Messenger

**Роли и выбор.** `MessageType` содержит `DeliveryChannel`; TextMessage/VoiceMessage создают payload,
CarrierPigeon/QuantumTeleporter/SubspaceRadio доставляют его. Наследование всех сочетаний или channel switch
в каждом message type привели бы к дублированию и правкам message side.

**Вызов.** `new VoiceMessage("hello", 3, new QuantumTeleporter()).send("Mars")` проверяет content/duration,
создаёт `VOICE[3s]: hello`, base `transmit` проверяет recipient и вызывает `DeliveryChannel.deliver` один раз.
Voice payload в demo — transcript и duration; physical audio/hardware не нужны.

**Расширение.** `SubspaceRadio implements DeliveryChannel` — отдельный класс и injection в composition root.
TextMessage/VoiceMessage/MessageType и исходные channels не меняются. Demo показывает все шесть сочетаний.

**SOLID и доказательство.** OCP/DIP: обе стороны Bridge меняются независимо.
`MessageTypeTest.allSixCombinationsDeliverTheirPayloads`,
`independentNewChannelReceivesExactlyOneCallPerMessage`, `extensionDoesNotRegressOriginalChannels`.
