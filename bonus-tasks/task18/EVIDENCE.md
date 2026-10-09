# Task 18 — Universal Remote for Appliances (3 points)
| Rubric condition | Implementation | Automated evidence |
|---|---|---|
| Correct split (1 pt) | Abstract `Appliance` contains `RemoteProtocol`; `TV/Fan` versus `InfraredRemote/BluetoothRemote` | `ApplianceTest.lampExtensionAndOriginalAppliancesDelegateWithoutProtocolChanges` |
| All four original combinations (1 pt) | `Demo` runs two protocols × original two appliances | `sixCombinationsSupportPowerAndAdjustment` |
| Third appliance (1 pt) | Separate `Lamp` class uses existing protocols with no changes to protocol side | `sixCombinationsSupportPowerAndAdjustment`, `lampExtensionAndOriginalAppliancesDelegateWithoutProtocolChanges` |
| Validation/edges | TV volume and lamp brightness 0..100, fan speed 0..3; limits included; invalid values never delegate | `validBoundsAndInvalidSettingsAreCheckedBeforeDelegating` |

The demo includes all six combinations after extension, each with on/adjust/off. Both protocol implementations carry generic target/command data; neither knows concrete appliance classes. Protocol transmission is simulated by deterministic receipts.
