# Task 19 — Robot Pet Shop (4 points)
| Rubric condition | Implementation | Automated evidence |
|---|---|---|
| Correct independent split (2 pts) | Abstract `RobotPet` contains `ControlSystem`; `RobotDog/RobotCat` versus `VoiceControl/AppControl` | `RobotPetTest.recordingControlShowsOneDelegationPerPetOperation` |
| All four original combinations (1 pt) | `Demo` crosses two pets with both original controls | `originalFourCombinationsAndThirdControlWork` |
| Walkthrough (1 pt) | Script below; standalone `GestureControl` proves extension | `gestureExtensionKeepsOriginalControlBehavior` |
| Validation/edges | Null control, null/blank pet or action, one-character valid values | `blankInputsAndMissingControlsFailClearly` |

## 3–5 minute walkthrough
1. (0:00–0:45) Identify two axes: what a pet does and how it receives a command. `RobotPet` holds the `ControlSystem` implementor; concrete pets only declare their actions.
2. (0:45–1:45) Trace `new RobotDog(new AppControl()).play()` → dog chooses `fetch a ball` → base `perform` sends name/action → `AppControl.execute` validates data and returns its delivery receipt.
3. (1:45–2:30) Run all four original combinations. No combined classes or concrete type branches are needed.
4. (2:30–3:30) Add a new class implementing `ControlSystem.execute`, then inject it in client composition. `GestureControl` is implemented; `RobotPet`, `RobotDog`, and `RobotCat` remain unchanged.
5. (3:30–4:30) Bridge prevents the inheritance cross-product (one pet subclass per control system). DIP: pets depend on the control interface. OCP: extend either hierarchy independently. Run `recordingControlShowsOneDelegationPerPetOperation` to show polymorphic delegation and `gestureExtensionKeepsOriginalControlBehavior` for regression evidence.

Control systems are simulated command receipts; the examples need no microphone, application account, or gesture camera.
