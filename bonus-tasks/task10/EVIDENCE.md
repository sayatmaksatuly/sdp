# Task 10 — Ancient Fax Whisperer (3 points)

| Условие | Код | JUnit evidence |
| --- | --- | --- |
| Adapter между DocumentSender и legacy API (2 балла) | `FaxAdapter.send`, `DocumentRasterizer`, `AncientFaxMachine.sendViaPhoneLine` | `documentIsRasterizedAndAcknowledgementDecodedThroughTargetInterface`: реальные grayscale bytes, ink/background, phone recipient, ACK code |
| Unit test (1 балл) | `FaxAdapterTest` | Четыре теста: success, failure, validation, capacity |
| Разбор static noise | Отдельный token `ACK:<digits>` означает success; BUSY означает busy; неизвестная строка означает failure | `busyAndUnknownNoiseAreMeaningfulFailures` |
| Границы и безопасные данные | До 2000 UTF-16 units, raster 640×512, clone byte arrays | `maximumDocumentFitsInFixedRaster`, `invalidDocumentRecipientAndDependenciesAreRejected` |

Document здесь содержит plain text; recipientId — буквальный phone number, а не адресная книга. Факс принимает raw grayscale plane известного размера, по одному byte на pixel. Это настоящая растеризация текста Java2D, не bytes исходной строки. Fixed cells не сохраняют разметку/newline документа. Grammar подтверждения: `ACK:<digits>` в начале/конце либо между `::`; ACK имеет приоритет перед BUSY. Неизвестное подтверждение не считается доставкой. Legacy-драйвер имитирован локально, физическая линия не нужна.
