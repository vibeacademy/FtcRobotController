# Lesson 07 — Prove It Without a Robot

**Arc 2: Code Like a Real Team · Pain: P3, P4 · Runtime target: 11 min**

## Lesson Summary

- **Assumes:** Lessons 01–06 (especially the mock layer).
- **Delivers:** unit testing robot code with JUnit + mock hardware — the
  repo's real test suite (17 tests) as the example, including the clamp test
  that catches the lesson-05 flip bug automatically. The "software as risk"
  narrative dies here: tested code is *less* risky than no code.
- **Objectives:** (1) run `./gradlew :TeamCode:testDebugUnitTest` and read
  the results; (2) write one new test against MockDrivetrain; (3) break the
  clamp on purpose and watch the test catch it — proof tests bite.

## Branch Spec

- **Branch:** `lesson-07-testing` (cut from `lesson-06-hardware-abstraction`)
- **Code state:** merges the repo's real JUnit source set (DrivetrainTest 8,
  ArmTest 5, ClawTest 4 — from PR #21) onto the lesson line, plus one extra
  test written on camera. `LESSON.md` includes the one-command test run and
  a "break it on purpose" exercise.

## Starter Script

### Prerequisites
Lessons 01–06.

### Mental Model Shifts
1. "It worked when we tried it" is not evidence — it's one anecdote. A test
   is an experiment that reruns itself every time the code changes.
2. Mocks flip hardware scarcity: MockDrivetrain is a robot that exists ONLY
   to be interrogated by tests. Infinite robots, free.
3. Practice-field time is for physics. Logic bugs should never make it to
   the field — they're catchable at zero cost on a laptop.

---

## Hook (0:00–0:40)

[Visual: Terminal runs one command; 17 green checkmarks cascade; cut to a
robot on a practice field doing the same maneuvers the test names describe.]
[Video Prompt: Dark terminal, single command typed, seventeen green
checkmarks cascade down test names list rapidly, then cut to top-down robot
field where robot performs strafe and turn maneuvers matching highlighted
test names, satisfying completion animation, developer aesthetic]

(Audio) Seventeen experiments on a competition robot just ran on my laptop
in four seconds. No robot, no simulator even. One of these tests exists
specifically to catch the bug from lesson five — the one that flips robots —
and I'm going to reintroduce that bug on camera so you can watch the test
catch it. If your team thinks software is the risky subsystem, this video is
the counter-argument.

## Concept: Tests Are Rerunnable Experiments (0:40–3:00)

Beats: science-fair framing — hypothesis ("full-stick input never exceeds
1.0 power"), experiment (call mecanumDrive(2.0, 0, 0) on a mock, measure),
conclusion (pass/fail), and the superpower: it reruns on every change
forever; the mock is the lab bench — MockDrivetrain remembers every power
it was commanded so the test can measure it.

[Visual: Science-fair board layout — HYPOTHESIS / EXPERIMENT / RESULT — but
each panel is code: an assertion, a mock call, a green check]
[Prompt: Science fair trifold board dark theme, three panels labeled
HYPOTHESIS EXPERIMENT RESULT containing short code snippet, mock robot icon,
large green checkmark, playful science-meets-code teaching style]

## Old vs. New (3:00–4:15)

[Paradigm Shift]
(Audio) Testing a game means playing it — you SEE the bug. You can't "play"
a robot that isn't built, and even with one, you'd need the field, the
battery, and an hour of setup to check one edge case. Your instinct — "run
it and watch" — doesn't scale past hello world in robotics. The new model:
interrogate the code in a courtroom where hardware can't hide. That
courtroom is the mock layer you met last lesson. This is WHY we built it.

## Implementation (4:15–8:30)

Beats: run the suite live (`testDebugUnitTest`), read the three test-class
names — they're the robot's subsystems; open the clamp test — walk its three
beats (drive at 2.0 → read all four mock powers → assert each within ±1.0);
write one new test on camera (encoder reset, or claw toggle) — red first,
then green; land the rhythm: red → fix → green is the whole discipline.

[Visual: The clamp test on screen, three regions highlighted — ARRANGE (mock
+ overdrive input), ACT (mecanumDrive call), ASSERT (±1.0 checks)]
[Prompt: Dark code editor with JUnit test method, three translucent colored
bands labeled ARRANGE ACT ASSERT in blue orange green over respective lines,
annotated teaching code style]

## The Money Moment: Break It (8:30–9:45)

Beats: comment out the clamp in MockDrivetrain's clip(); rerun; ONE test
goes red — `mecanumDriveClampsWheelPowers`, exactly the flip bug; restore;
green. Say the quiet part: this exact break-and-catch was performed on this
repo for real, to prove the suite bites — and when an FTC judge asks your
team "how do you know your code works?", THIS is the answer that wins
awards, not "we tried it once."

[Visual: Side-by-side terminal runs — 17/17 green, then 16/17 with one red
line naming the clamp test, then green again]
[Prompt: Three sequential terminal windows dark background, first all green
checks, second showing single red X on highlighted test name among greens,
third all green again, storytelling sequence, developer aesthetic]

## Gotchas (9:45–10:30)

Beats: tests prove logic, not physics (a passing suite + a dead battery is
still a dead robot — arc 3 covers the physics side); don't test the SDK,
test YOUR decisions; flaky tests are worse than no tests — keep them
deterministic (no sleeps, no randomness).

## Conclusion + CTA (10:30–11:00)

(Audio) Your team now has something most FTC teams have never seen: robot
code with evidence. One command, four seconds, seventeen experiments — run
it before every practice and you'll never burn field time on a logic bug
again. Next lesson: when something DOES go wrong on the field, telemetry is
how you see inside the robot's head.

---

## Media Packet

### Title options (≤60 chars)
1. **Test Robot Code With No Robot (FTC Ep. 7)** [43] — RECOMMENDED
2. 17 Tests, 4 Seconds, 0 Robots (FTC Ep. 7) [42]
3. Watch a Test Catch a Robot-Flipping Bug (Ep. 7) [48]

### Description
```
17 experiments on competition robot code, 4 seconds, zero hardware. Set up
JUnit with mock hardware, write your first test, then watch me reintroduce
the robot-flipping clamp bug from Ep. 5 — and watch the suite catch it
instantly. Tested code isn't the risky subsystem. Untested code is.

📦 CODE FOR THIS LESSON (full test suite + exercises)
https://github.com/vibeacademy/FtcRobotController/tree/lesson-07-testing

📚 CHAPTERS
0:00 17 experiments in 4 seconds
0:40 Tests are rerunnable science
3:00 Why "run it and watch" fails in robotics
4:15 Reading + writing real tests
8:30 Breaking the clamp on purpose (tests bite)
9:45 What tests can't prove
10:30 One command before every practice
```

### Tags
`FTC unit testing, JUnit robotics, mock hardware FTC, test robot code, FTC
gradle test, robot code quality, FTC software testing, FIRST Tech Challenge`

### Thumbnail concept
A giant green checkmark shielding a small robot from a red "BUG" arrow;
text "TESTED. NO ROBOT NEEDED."
[Prompt: YouTube thumbnail 1280x720, large glowing green checkmark shield in
front of small robot, red jagged arrow labeled BUG deflecting off shield,
bold white text TESTED NO ROBOT NEEDED, dark background, high contrast
protective energy]

### Pinned comment
```
📦 The full 17-test suite + the break-it-yourself exercise:
https://github.com/vibeacademy/FtcRobotController/tree/lesson-07-testing

Run ./gradlew :TeamCode:testDebugUnitTest and reply with your 17/17
screenshot. Then break the clamp on purpose — LESSON.md shows you how.
```
