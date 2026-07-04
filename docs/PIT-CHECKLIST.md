# Pre-Match Software Checklist (printable)

Run this before EVERY match. Data first, wrenches second. If you're writing
code in the pit, something upstream already failed.

## Before queueing

- [ ] Battery voltage ≥ 13.0V on the Driver Station (kP feels different at
      12.1V than 13.8V — margins absorb it, but check)
- [ ] Driver Station shows exactly the right OpModes:
      **COMPETITION TeleOp** and **COMPETITION Auto** (group: Competition)
- [ ] Hardware config map matches the robot — names, ports, after ANY
      rewiring (one renamed motor = crash on init)
- [ ] Run COMPETITION Auto's init: telemetry shows encoders at 0 and
      heading at 0.0 (stale ticks = insane second run)
- [ ] Gamepads in the right ports (driver = 1, operator = 2), both bound
- [ ] Robot controller and Driver Station batteries charged, WiFi solid

## Between matches

- [ ] READ THE TELEMETRY from the last match before touching anything
- [ ] Something misbehaved? Run the 4 questions (lesson 08) — name the zone
      before proposing a fix
- [ ] Any code change after freeze requires: a bug, a test that proves it,
      and a green suite (`./gradlew :TeamCode:testDebugUnitTest`)

## Code freeze rules

- Freeze date: ____________ (a week before the event)
- After freeze: bug fixes only, each with a failing-then-passing test
- The night-before rewrite loses matches. Every mentor has the story.

## The software person's match-day job

Run this checklist. Read the numbers. Protect the freeze. Staying calm is a
skill — and your whole team can feel it.
