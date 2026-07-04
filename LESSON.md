# Lesson 01 — Robots Run a Loop

Welcome! If you came here from the video: you're in the right place. This is
a **real FTC robot code repository** — the same kind your team will have.
Nothing here is a toy.

## What an OpMode is (3 sentences)

An OpMode is one program on the robot's menu — the driver picks it before a
match, like picking a level in a game. It has a setup phase (INIT, robot must
not move) and a match phase where your code runs in a loop about 50 times per
second. Everything a robot does in a match, some OpMode is doing it.

## Your 10-minute homework

Open this file and read it top to bottom:

```
TeamCode/src/main/java/org/firstinspires/ftc/teamcode/HelloWorld.java
```

Don't worry about understanding every symbol. Instead, find these three
things from the video:

1. **The menu registration** — the `@TeleOp(...)` line that puts this program
   on the driver's menu.
2. **The dividing line** — `waitForStart()`. Everything above it happens
   before the match; everything below it *is* the match.
3. **The loop** — `while (opModeIsActive())`. That's the loop from the
   diagram, running 50 times a second, asking "what should the motors do
   right now?"

If you found all three: you just read real competition robot code. Most
people on FTC teams never do that.

## Poke around (optional, encouraged)

- `TeamCode/src/main/java/org/firstinspires/ftc/teamcode/` — everything your
  team would write lives here. Everything else in the repo is the FTC SDK
  (the robot operating system FIRST provides). You get to ignore it.
- See `opmodes/`, `hardware/`, `subsystems/`? Later lessons explain exactly
  why competitive teams organize code this way.

## Next

- **Next lesson:** The Robotics Workstation — set up your machine and compile
  this exact repo (no robot needed). Branch: `lesson-02-workstation`.
- **Series index:** [`LESSONS.md`](../../blob/master/LESSONS.md) — every
  lesson, every branch.
- Questions? Ask in the video comments — they're read.
