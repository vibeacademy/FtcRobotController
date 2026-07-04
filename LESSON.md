# Lesson 06 — The Hardware Abstraction Layer

One file, three worlds: real robot, simulator, plain-laptop tests. The trick
is programming to *interfaces* — promises about motors — instead of motors.

## The before/after (the diff IS the lesson)

- **Before:** `opmodes/SimTeleOp.java` — talks to motors directly
  (`hardwareMap.get(DcMotor.class, ...)` in every OpMode). This is the
  pattern in most rookie tutorials.
- **After:** `opmodes/TeamTeleOp.java` — talks to `robot.drivetrain`
  through the repo's abstraction layer. Open both side by side.

## The three folders

```
hardware/
├── interfaces/   the socket shapes  (IDrivetrain, IArm, IClaw, IIMU, ...)
├── real/         FTC-SDK-backed implementations (real motors answer)
└── mock/         pretend hardware that keeps score (tests use these)
```

Anything that "plugs into" `IDrivetrain` can `mecanumDrive(drive, strafe,
turn)`. Your OpMode doesn't know or care who answered.

## Why this matters (beyond tidiness)

- The mecanum math from lesson 05 moved inside the drivetrain — write it
  once, every OpMode gets it.
- Hardware names live in ONE place (the config), not scattered.
- Four coders can work in parallel with zero robots — mock hardware answers.
- Lesson 07 cashes the biggest check: automated tests against the mocks.

## Rules that come with it (this repo enforces them)

- OpModes never call `hardwareMap.get(...)` directly.
- Optional hardware always gets a null check (`robot.arm != null`).
- Don't over-abstract: interfaces for hardware, not for everything.

## Next

- **Previous:** `lesson-05-mecanum`
- **Next:** `lesson-07-testing` — prove it works with no robot at all.
- **Series index:** [`LESSONS.md`](../../blob/master/LESSONS.md)
