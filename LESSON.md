# Lesson 05 — Mecanum Math

Two sticks in, four wheel powers out — and the one-line mistake that flips
robots. The code: `opmodes/SimTeleOp.java` (upgraded from lesson 04's tank
drive; the mixing table is in its header comment).

## The mixing table

|            | DRIVE | STRAFE | TURN |
|------------|-------|--------|------|
| frontLeft  | +     | +      | +    |
| frontRight | +     | −      | −    |
| backLeft   | +     | −      | +    |
| backRight  | +     | +      | −    |

Signs depend on how YOUR motors are mounted. Trust the one-wheel test (spin
each wheel alone, check its direction), not a table from the internet —
including this one.

## Why the normalize step matters

Push all three intents at once and a wheel can be asked for 2.4 power.
Motors max at 1.0 and the SDK clips **each wheel separately** — destroying
the ratios that made your motion straight. Normalizing scales all four
together. Delete the normalize block in the sim and feel the difference.

## Challenges

1. **Slow-mode button (~20 min):** while holding right bumper, multiply all
   four powers by 0.35. Your future drivers will love you.
2. **Field-centric teaser (hard):** rotate the (drive, strafe) vector by the
   robot's heading so "up on the stick" always means "away from the driver."
   You'll want lesson 09's IMU first.

## Debugging quickies

- Robot spins instead of strafing → one motor's direction is reversed.
- Robot creeps when sticks are released → add a small deadzone (ignore
  |stick| < 0.05).

## Next

- **Previous:** `lesson-04-simulator`
- **Next:** `lesson-06-hardware-abstraction` — stop talking to motors directly.
- **Series index:** [`LESSONS.md`](../../blob/master/LESSONS.md)
