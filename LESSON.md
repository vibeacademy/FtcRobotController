# Lesson 08 — Telemetry: See What the Robot Thinks

You can't pause a robot mid-bug. Telemetry is the instrument panel that
makes the inside visible while it runs.

## The dashboard

`opmodes/TeamTeleOp.java` now has a three-zone dashboard mirroring the
control loop:

```
— INPUTS —      what the drivers command
— DECISIONS —   what our math computed
— OUTPUTS —     what the hardware was told / reports back
```

Habits worth stealing: units in the labels ("ticks"), stable row order, one
screen max.

## The 4-question ritual (printable)

When the robot "does something weird", read the dashboard top to bottom:

| # | Question | If the numbers are wrong here… |
|---|----------|-------------------------------|
| 1 | What do the INPUTS say? | controller / driver problem |
| 2 | What do the DECISIONS say? | **your math** — the code |
| 3 | Do OUTPUTS match decisions? | config / hardware map |
| 4 | Does physics match outputs? | build team's problem |

**The bug lives at the first zone whose numbers surprise you.**

## The challenge

Run `opmodes/DebugChallengeOpMode.java` in the simulator. It has exactly one
planted bug and a full dashboard. Diagnose it with the four questions ONLY —
don't read the mixing lines until you've named the zone. (The answer is
marked in the source for checking yourself afterward.)

## Next

- **Previous:** `lesson-07-testing`
- **Next:** `lesson-09-sensors` — encoders, the IMU, and taking the
  blindfold off. Arc 3 begins.
- **Series index:** [`LESSONS.md`](../../blob/master/LESSONS.md)
