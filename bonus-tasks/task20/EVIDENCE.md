# Task 20 — Multiverse Messenger (6 points)
| Rubric condition | Implementation | Automated evidence |
|---|---|---|
| Bridge with independent variation (3 pts) | Abstract `MessageType` contains `DeliveryChannel`; `TextMessage/VoiceMessage` versus `CarrierPigeon/QuantumTeleporter` | `MessageTypeTest.independentNewChannelReceivesExactlyOneCallPerMessage` |
| All four original combinations (2 pts) | `Demo` runs both message types over both original channels | `allSixCombinationsDeliverTheirPayloads` |
| Third channel defense (1 pt) | Separate `SubspaceRadio` implementation; message side unchanged | `extensionDoesNotRegressOriginalChannels`, `allSixCombinationsDeliverTheirPayloads` |
| Validation/edges | Null dependency/content/recipient, blank content/recipient, nonpositive voice duration, unicode, 1 second and maximum integer duration | `boundaryAndInvalidInputsFailBeforeDelivery`, `independentNewChannelReceivesExactlyOneCallPerMessage` |

## Live defense (3–5 minutes)
Explain the two hierarchies: message classes encode content; channel classes transport a generic recipient/payload. Trace `new VoiceMessage("Portal is ready", 3, new QuantumTeleporter()).send("Universe B")`: constructor validates transcript/duration and stores the implementor; `send` formats `VOICE[3s]: Portal is ready`; base `transmit` validates recipient and delegates once; channel returns a deterministic simulated receipt.

Add one new class implementing `DeliveryChannel.deliver`, and inject it at the composition root. `SubspaceRadio` demonstrates this with zero edits to `MessageType`, `TextMessage`, or `VoiceMessage`. The demo shows all six combinations. A third message format can similarly extend `MessageType` without changing any delivery class.

Bridge fits independent variation and avoids a subclass for every message/channel pair or repeated channel branches. DIP is shown by interface composition, OCP by independent extensions. Run `independentNewChannelReceivesExactlyOneCallPerMessage` for a fresh recording channel and `extensionDoesNotRegressOriginalChannels` to prove original channels remain functional.

Assumption: voice is represented by a transcript plus duration; channels are deterministic simulated transports. No real recordings or network/pigeon infrastructure is required by the assignment.
