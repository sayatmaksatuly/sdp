# Task 03 — Dating Profile Builder (4 points)

| Rubric condition | Code | Executable evidence |
| --- | --- | --- |
| 2 points: profile builder, minimum one hobby | `DatingProfileBuilder.build()` | `atLeastOneHobbyIsRequired`, `exactlyOneHobbyIsValidAndRedFlagIsOptional`, `fluentStepsReturnSameBuilder` |
| 1 point: two construction orders | two calls in `Demo.main` | `allFieldsAreIndependentOfConstructionOrder` reverses fields and hobby order |
| 1 point: verification walkthrough | script below, optional `verify()` | `optionalVerificationPreservesExistingClientFields`, `verificationCanBeAddedInEitherOrder`, `verificationCannotBypassHobbyRule` |
| Edges and safe snapshots | input validation; sorted unique hobbies; immutable record | remaining tests: missing fields, null/blank input, trimming, duplicates, immutable snapshots and recovery |

## Walkthrough script (3–5 minutes)

1. Show `DatingProfileBuilder`: independent named methods replace a telescoping constructor; each returns `this`. The optional red flag defaults to absent. Names and bios must contain text. Hobbies have set semantics and deterministic alphabetical ordering, so even reversing hobby calls gives the same immutable result.
2. Run `Demo` and `allFieldsAreIndependentOfConstructionOrder`: both calls populate the same fields in different orders and yield equal records. Show zero hobbies failing at `build()` and one hobby succeeding.
3. Trace `name("Alex").bio("Likes tea").addHobby("Baking").build()`: each setter strips/validates its value and stores it; `build` checks required fields and the hobby count; it snapshots the sorted set into an immutable list and returns the product.
4. Add verification by introducing the optional `verify()` builder method and an absent-by-default product marker. Existing signatures and chains remain unchanged; old callers continue producing unverified profiles. `optionalVerificationPreservesExistingClientFields` demonstrates that enabling verification changes only its marker. This is a local demonstration of the extension point, not identity verification or a remote service.
5. A new builder step touches the builder and product because it extends their schema, but requires no change to existing calls or the hobby rule. No concrete-type tests or `if/else` selection of whole profile variants is needed. Builder is appropriate for incrementally collecting independent optional data; inheritance of profile combinations would proliferate classes. This supports the Open/Closed principle for client usage and Single Responsibility by isolating assembly/validation from the immutable product. The original-call regression test and `verificationCannotBypassHobbyRule` show both properties.
