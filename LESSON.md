# Lesson 11 — Autonomous II: Move With Precision

Robots don't stop — they decelerate. One multiplication makes the approach
slow itself: **power = kP × error**.

## The code

- `autonomous/PBaseAuto.java` — the P-control helpers (`driveInchesP`,
  `turnToHeadingP`), plus the clamp from lesson 05, a minimum-power floor
  (friction eats tiny powers), the three exit doors from lesson 10, and
  angle wraparound for headings.
- `opmodes/PrecisionAuto.java` — lesson 10's park, upgraded: drives 24
  inches with P-control, then squares up to heading 0.
- `opmodes/TuneKpOpMode.java` — live tuning: dpad adjusts kP, A runs a
  24-inch move, B resets encoders.

## The symptom table (memorize it)

| Symptom | Diagnosis |
|---|---|
| Robot overshoots, reverses, overshoots — oscillates | kP too **high** |
| Robot crawls and stalls short of the target | kP too **low** |
| Settles fast, no overshoot | just right — write it down |

## Tuning drill

1. Run `Tune kP` in the simulator, start at 0.02.
2. Drop it to 0.005 — watch the crawl.
3. Crank it to 0.08 — watch the oscillation.
4. Bisect until it settles clean. That's YOUR kP — it changes when the
   robot gains weight, so keep the constant in one place and re-tune.

## What you now know

This is real control theory — the actual entry point, not a watered-down
version. PID's I and D terms are refinements on the exact loop you just
tuned. Well-tuned P takes most FTC teams further than they'd believe.

## Next

- **Previous:** `lesson-10-autonomous-park`
- **Next:** `lesson-12-competition-ops` — the finale: real hub, match day.
- **Series index:** [`LESSONS.md`](../../blob/master/LESSONS.md)
