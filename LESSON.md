# Lesson 07 — Prove It Without a Robot

18 experiments on competition robot code, a few seconds, zero hardware.
The mock layer from lesson 06 is the lab bench; JUnit runs the experiments.

## Run the suite

```
./gradlew :TeamCode:testDebugUnitTest
```

Test classes live in `TeamCode/src/test/java/.../hardware/mock/`:

- `DrivetrainTest` (8) — including `mecanumDriveClampsWheelPowers`, the test
  that catches the robot-flipping bug from lesson 05
- `ArmTest` (5), `ClawTest` (4)
- `ImuTest` (1) — the test written on camera this lesson

## Break it on purpose (do this — it's the point)

1. Open `hardware/mock/MockDrivetrain.java`, find the `clip(...)` method.
2. Make it return its input unchanged.
3. Re-run the suite. Exactly one test goes red:
   `mecanumDriveClampsWheelPowers` — the lesson-05 flip bug, caught by a
   laptop.
4. Restore `clip`, re-run, green. That red→green cycle is the whole
   discipline.

## Rules of good robot tests

- Test YOUR decisions (mixing math, clamps, presets) — not the SDK.
- Deterministic only: no sleeps, no randomness. A flaky test is worse than
  no test.
- Tests prove **logic**. A passing suite plus a dead battery is still a dead
  robot — the field proves physics (lessons 09–12).

When an FTC judge asks "how do you know your code works?", running this
suite in front of them beats "we tried it once" — by a lot.

## Next

- **Previous:** `lesson-06-hardware-abstraction`
- **Next:** `lesson-08-telemetry` — see what the robot thinks.
- **Series index:** [`LESSONS.md`](../../blob/master/LESSONS.md)
