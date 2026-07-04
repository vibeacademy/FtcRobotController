# Lesson 09 — Sensors and State

Without sensors your robot drives blindfolded, counting seconds. Encoders
and the IMU take the blindfold off — Arc 3 starts here.

## What changed

`opmodes/TeamTeleOp.java` gains a **SENSORS** dashboard zone: encoder ticks
converted to inches, and IMU heading in degrees. Drive the sim and watch
what the robot *actually did*, next to what you commanded.

## The counts-per-inch worksheet (do this for YOUR robot)

```
counts_per_inch = (encoder ticks per motor rev × gear ratio)
                  ─────────────────────────────────────────
                        (wheel diameter × π)
```

Example: REV HD Hex 20:1 → 560 ticks/rev, direct drive (1:1), 75mm (2.95in)
wheels → 560 / (2.95 × 3.1416) ≈ **60.4 counts per inch**.

Sanity check: push the robot exactly 24 inches by hand along a tape
measure. The dashboard should read ~24.0. If it reads 12 or 48, a term in
your formula is wrong (usually the gear ratio).

The constant lives at the top of `TeamTeleOp.java` (`COUNTS_PER_INCH`).
The sim/mock default is 100.0 — a real robot's number will differ.

## The 4 sensor gotchas

1. **Encoders measure wheel spin, not robot position** — wheels slip.
   Cross-check with the IMU when it matters.
2. **Reset encoders in init** or ticks carry over between runs — the
   classic "my auto went insane on the second run" bug.
3. **IMU heading**: −180 to 180, positive = counterclockwise. Mounting
   orientation can flip it — verify with the dashboard before trusting it.
4. **Counts-per-inch is per-robot.** Gear ratios change mid-season; re-check
   after drivetrain changes.

## Next

- **Previous:** `lesson-08-telemetry`
- **Next:** `lesson-10-autonomous-park` — free points, every match.
- **Series index:** [`LESSONS.md`](../../blob/master/LESSONS.md)
