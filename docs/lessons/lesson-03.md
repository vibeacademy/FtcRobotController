# Lesson 03 — Your First OpMode

**Arc 1: You Don't Need a Robot · Pain: P1, P2 · Runtime target: 11 min**

## Lesson Summary

- **Assumes:** Lessons 01–02 (mental model + working build).
- **Delivers:** the learner *writes* an OpMode from an empty file: telemetry
  output, the init/loop lifecycle, and reading gamepad state. First real code
  authorship.
- **Objectives:** (1) create an OpMode class from scratch and register it with
  `@TeleOp`; (2) print live telemetry; (3) read a gamepad stick and show its
  value on screen — proving the loop runs.

## Branch Spec

- **Branch:** `lesson-03-first-opmode` (cut from `lesson-02-workstation`)
- **Code state:** adds `opmodes/MyFirstOpMode.java` — the exact file written
  on camera (annotated with comments matching video timestamps). `LESSON.md`
  includes a "type it yourself first" challenge before showing the file.

## Starter Script

> **Superseded for visuals.** The full shooting script
> (`docs/lessons/scripts/lesson-03-full-script.md`) is the single source
> of truth for every visual decision — prompts, `[Screen:]`/`[Edit:]`
> reclassifications, `[Layout:]` cues, and `[Asset:]` paths. The cues
> below are the original draft, kept for lineage; do not generate or
> film from them.

### Prerequisites
Lessons 01–02.

### Mental Model Shifts
1. An OpMode is not "the program" — it's *one* program on a menu of many. A
   team ships dozens; the driver picks one.
2. `telemetry` is your `console.log` — but it repaints, never scrolls. State,
   not history.
3. You can verify gamepad code with zero hardware: the loop + telemetry is a
   complete feedback cycle on your desk.

---

## Hook (0:00–0:35)

[Visual: Empty Java file on the left; on the right, a Driver Station phone
showing a menu with "MyFirstOpMode" in the list next to real competition
programs.]
[Prompt: Split screen dark background, left side empty code editor with
blinking cursor, right side smartphone driver station UI with program list
where MyFirstOpMode glows highlighted among other entries, achievement
aesthetic, 16:9]

(Audio) Every program that has ever driven an FTC robot started as this: an
empty file. Today you write one — not copy, write. By the end, your program
will be on the robot's menu, printing live data, and reading a game
controller. Still no robot required. And I'll show you the two lines that
confuse literally everyone the first time.

## The Shape of an OpMode (0:35–3:00)

Beats: create the file live; class extends `LinearOpMode` (the SDK's "fill in
the blanks" template — you write what's unique, it handles the rest);
`@TeleOp(name = ...)` is the menu registration — the annotation IS how the
driver finds your program; `runOpMode()` is the blank the SDK calls.

[Visual: The OpMode skeleton with two callouts — @TeleOp arrow to a phone
menu entry, runOpMode arrow to a "the SDK calls this" label]
[Prompt: Dark code editor showing short Java class skeleton, orange arrow
from annotation line to small phone menu mockup, blue arrow from method name
to label "the SDK calls this for you", clean annotated-code teaching style]

## Old vs. New: waitForStart (3:00–4:30)

[Paradigm Shift]
(Audio) Here's the line that breaks everyone's brain: `waitForStart()`. Your
instinct says programs run when you run them. Robot programs have a *legal*
two-phase life: INIT — the drivers pick your program and the robot must NOT
move — and START, when the referee says go. Everything above `waitForStart()`
happens in the pits and on the field before the match. Everything below is
the match. Move a motor above that line and you can literally be penalized.
It's not style — it's the rules of the sport encoded in one method call.

[Visual: Vertical code file split by the waitForStart line into a blue INIT
zone ("robot must not move") and green MATCH zone ("loop runs here")]
[Prompt: Code file diagram dark background split horizontally by glowing
white line labeled waitForStart, upper zone tinted blue labeled INIT robot
must not move, lower zone tinted green labeled MATCH loop runs 50x per
second, rulebook icon in corner, clean teaching diagram style]

## Implementation: Telemetry + Gamepad (4:30–9:00)

Beats: add `telemetry.addData` / `update` in the loop — run it, watch the
loop counter climb (visible proof the loop from Lesson 01 is real); paradigm
beat: telemetry repaints (it's a dashboard, not a log); plug in any USB/
Bluetooth gamepad, read `gamepad1.left_stick_y`, display it live; the
`-1 is up` gotcha (aviation convention — everyone gets bitten, say it now,
laugh about it).

[Visual: Split — code loop on left; telemetry panel on right where stick
value changes as an on-screen gamepad stick wiggles]
[Video Prompt: Split screen dark UI, left side short code loop with
highlighted telemetry line, right side telemetry dashboard where number
updates continuously from -1.0 to 1.0 as small gamepad icon stick tilts up
and down, synchronized smooth animation, developer tooling aesthetic]

## Gotchas (9:00–10:15)

Beats: forgetting `telemetry.update()` (dashboard stays blank — #1 forum
question); code below the loop never runs; stick up = negative; "if init
takes >5s the Driver Station complains — keep init light."

## Conclusion + CTA (10:15–11:00)

(Audio) You've written a program that a real competition robot would run —
menu entry, live data, controller input. The branch link has my exact file,
but type yours first: the typos are where the learning is. Next lesson is the
one this series is named for: you drive a robot that doesn't exist.

---

## Media Packet

### Title options (≤60 chars)
1. **Write Your First Robot Program (FTC Ep. 3)** [44] — RECOMMENDED
2. FTC OpModes From an Empty File (Ep. 3) [39]
3. The 2 Lines That Confuse Every New FTC Coder (Ep. 3) [52]

### Description
```
Every FTC robot program starts as an empty file. Write your first OpMode from
scratch: menu registration, the init/start lifecycle (and the waitForStart
line that confuses everyone), live telemetry, and real gamepad input — all
with zero hardware.

📦 CODE FOR THIS LESSON
https://github.com/vibeacademy/FtcRobotController/tree/lesson-03-first-opmode

📚 CHAPTERS
0:00 Every program starts empty
0:35 The shape of an OpMode
3:00 waitForStart — the rules of the sport, in code
4:30 Telemetry + reading a gamepad live
9:00 The 4 mistakes everyone makes
10:15 What you can show someone now
```

### Tags
`FTC OpMode tutorial, first FTC program, FTC telemetry, FTC gamepad, LinearOpMode,
FTC java tutorial, FIRST Tech Challenge coding, robot programming beginner`

### Thumbnail concept

> Current prompt lives in the full shooting script; the one below is the
> original draft.
A hand on a game controller in the foreground, telemetry numbers reacting on
a laptop behind; text "YOUR FIRST ROBOT PROGRAM."
[Prompt: YouTube thumbnail 1280x720, foreground hands on game controller with
tilted stick, laptop screen behind showing large telemetry number mid-change,
bold white text YOUR FIRST ROBOT PROGRAM, dark background blue accent
lighting, energetic youth tech style]

### Pinned comment
```
📦 My exact file from this video (but type yours first — really):
https://github.com/vibeacademy/FtcRobotController/tree/lesson-03-first-opmode

Post your telemetry screenshot below when the loop counter climbs 🎉
Next lesson: drive a robot that doesn't exist.
```
