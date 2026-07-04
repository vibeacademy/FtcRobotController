# Lesson 12 — Competition Ops (Series Finale) 🎓

The robot finally shows up — last, on purpose. Everything before this was
secretly building toward being the calm pit.

## The "ship it" configuration

- `opmodes/CompetitionTeleOp.java` — the arc 2 TeleOp plus slow mode
  (lesson 05's challenge, now standard equipment)
- `opmodes/CompetitionAuto.java` — the precision park, pinned. Season
  scoring gets added as new states AFTER this is 5/5 on carpet — never
  instead of it.
- `docs/PIT-CHECKLIST.md` — **print this.** The pre-match software ritual.

## Deploy in three sentences

Gradle builds your code into an app and installs it on the Control Hub
(USB first time, WiFi after). The **hardware configuration map** on the
Driver Station binds every name your code asks for (`front_left_motor`, …)
to a physical port — one screen, one source of truth, and the reason your
code never changed between the sim and the field. One renamed motor =
crash on init: config is the contract; guard it.

## What changes on real carpet

Battery sag (your kP feels different at 12.1V vs 13.8V), wheel slip, loose
connectors — the #1 "software bug" at competitions is a wire. Your tools
transfer: the 4-question telemetry ritual (lesson 08) is your multimeter,
and question 4 finally earns its keep. Re-run the 5/5 drill on carpet.

## Graduation

You can now: build a dev environment, drive a simulated robot, structure
code against swappable hardware, prove logic with tests, debug through
telemetry, ship a reliable autonomous, and run match-day software ops.

That's not "the coding kid." That's a robotics software engineer, junior
edition — and it's a story both FTC judges (who interview students) and
college applications actually want: *you didn't take a coding class, you
shipped the software on a competition robot.*

## Walk the series in code

Every lesson's branch stays up. Diff any two consecutive branches to see
exactly what a lesson added:
[`LESSONS.md`](../../blob/master/LESSONS.md) — the full index.

Bring a teammate through it. Software teams of one are how this story
started.
