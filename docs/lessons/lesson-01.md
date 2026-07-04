# Lesson 01 — Robots Run a Loop

**Arc 1: You Don't Need a Robot · Pain: P2, P4 · Runtime target: 9 min**

## Lesson Summary

- **Assumes:** nothing. No robotics, no FTC, minimal coding (can read a short
  file with comments).
- **Delivers:** the single most important mental model in robot software — the
  control loop — plus the motivation for the whole series: software is how
  small teams beat big ones.
- **Objectives:** (1) explain init → start → loop in your own words;
  (2) read the repo's `HelloWorld.java` OpMode and identify where the loop is;
  (3) name two ways software scores points a builder can't.

## Branch Spec

- **Branch:** `lesson-01-hello-robot` (cut from `master`)
- **Code state:** the stock repo plus `LESSON.md`. Nothing else changes — this
  lesson is *reading* code, and the learner's first repo visit should look
  like a real FTC project, because it is one.
- **LESSON.md contains:** what an OpMode is (3 sentences), the path to
  `TeamCode/src/main/java/org/firstinspires/ftc/teamcode/HelloWorld.java`,
  "read this file top to bottom — the video walks through it," and next-lesson
  link.

## Starter Script

### Prerequisites
None. This is the front door.

### Mental Model Shifts
1. Game code owns the world; robot code *votes* on a world physics owns.
2. Robot programs aren't scripts that run once — they're loops that run
   50 times a second until someone stops them.
3. Software is a scoring subsystem, not tech support for the builders.

---

## Hook (0:00–0:40)

[Visual: Two FTC robots side by side at a match start. Left robot sits dead
during autonomous. Right robot drives off, scores, parks. Scoreboard ticks up
only on the right.]
[Prompt: Split screen dark arena, two hexagonal competition robots on game
field, left robot dim and motionless with red X overlay, right robot with
glowing blue motion trail scoring into goal, scoreboard showing 0 vs 25,
dramatic stadium lighting, tech aesthetic, 16:9]

(Audio) These two robots cost the same, weigh the same, and were built by
teams the same age. One of them scored twenty-five points before a human
touched the controls. The difference isn't the metal — it's about two hundred
lines of code. This series is about those lines. You don't need a robot, you
don't need to know robotics, and you don't need to be "the coding kid." By the
end of this video you'll read real competition robot code and understand every
line of it.

## What FTC Is, in 90 Seconds (0:40–2:10)

Beats: the 30-second version of a match (auto period → TeleOp → endgame);
where software shows up in each phase; the observation this series is built
on — most teams' bottleneck is software, which means it's the *cheapest*
competitive edge available.

[Visual: Match timeline bar — 30s AUTO glowing (100% software), 2min TELEOP
(software-assisted), 30s ENDGAME — with point icons above each phase]
[Prompt: Horizontal timeline bar on dark background, three segments labeled
AUTO TELEOP ENDGAME, first segment glowing bright blue with robot icon and
"pure software" label, point counter icons floating above each segment, clean
infographic style]

## Old vs. New: The Loop (2:10–4:30)

[Paradigm Shift]
(Audio) If you've written any code before — a game, a website, a school
assignment — your program probably ran top to bottom and finished. Robot code
never finishes. It runs a loop, around fifty times every second: read the
controllers, decide, command the motors, repeat. Your code doesn't *do*
things. It gets asked, over and over, "what should the motors do *right
now*?" — and it has about twenty milliseconds to answer.

[Visual: Animated loop — gamepad icon → "decide" brain node → motor icons →
back to gamepad, cycling continuously with a 20ms timer]
[Video Prompt: Dark background circular flow diagram, glowing pulse travels
from gamepad icon to brain/chip node to four motor icons and back
continuously, small 20ms timer counting each cycle, smooth loop animation,
tech aesthetic]

Beats: contrast with a game loop (in Minecraft mods/Roblox you set the world's
state; here the world pushes back — friction, battery sag, defenders);
"your code just votes, physics decides."

## Read Real Robot Code (4:30–7:30)

Beats: open `HelloWorld.java` on screen. Do NOT read line-by-line — walk the
*shape*: the class is an OpMode (the SDK's word for "a program the driver can
pick"), `waitForStart()` splits setup from action, `while (opModeIsActive())`
IS the loop from the diagram, telemetry is the robot talking back. One
callout: every piece of the scary boilerplate maps to a box in the loop
diagram they already understand.

[Visual: HelloWorld.java with three regions color-boxed — INIT (blue), THE
LOOP (green), TELEMETRY (yellow) — loop diagram inset in corner]
[Prompt: Dark IDE code editor screenshot style, Java file with three colored
translucent overlay boxes labeled INIT, THE LOOP, TELEMETRY in blue green
yellow, small circular loop diagram inset bottom right matching colors,
developer tooling aesthetic]

## Why Software Wins (7:30–8:30)

Beats: three concrete claims with numbers — (1) autonomous is a pure-software
25+ points/match annuity; (2) driver-assist code (field-centric drive, preset
arm positions) turns an average driver into a good one; (3) reliability code
(clamps, timeouts) is why the same robot scores in round 6, not just round 1.
Software is the only subsystem that improves without adding a single gram.

## Conclusion + CTA (8:30–9:00)

(Audio) Here's your homework, and it takes ten minutes: the link in the pinned
comment goes to the exact code we just read — a real FTC repo, the same one
this whole series builds on. Open `HelloWorld.java`, read it top to bottom,
and see how much of it you now recognize. Next lesson, we set up your machine
so you can *run* robot code — no robot required.

---

## Media Packet

### Title options (≤60 chars)
1. **Robot Code Isn't What You Think (FTC Software Ep. 1)** [53] — RECOMMENDED
2. How Software Wins FTC Matches (Beginner Series Ep. 1) [54]
3. You Can Code a Robot Without Owning One — Ep. 1 [48]

### Description
```
Two identical robots. One scores 25 points before a human touches the
controls. The difference is ~200 lines of code — and this series teaches you
to write them, starting from zero. No robot required.

📦 CODE FOR THIS LESSON
https://github.com/vibeacademy/FtcRobotController/tree/lesson-01-hello-robot

📚 CHAPTERS
0:00 The 25-point difference
0:40 What FTC is in 90 seconds
2:10 Robot code runs a loop (the big idea)
4:30 Reading real competition code
7:30 Why software wins matches
8:30 Your 10-minute homework

🤖 SOFTWARE ROBOTICS SERIES
A beginner-to-competitive series on the software side of FTC robotics —
the part most teams struggle with and nobody documents.
```

### Tags
`FTC programming, FTC robotics, FIRST Tech Challenge, robot programming for
beginners, java robotics, FTC OpMode, learn robotics, robotics for kids,
FTC software, virtual robot`

### Thumbnail concept
Split image: dead gray robot on the left, glowing robot with motion trail on
the right; overlay text "200 LINES OF CODE" between them.
[Prompt: YouTube thumbnail 1280x720, split composition, left half desaturated
motionless competition robot, right half vibrant robot with blue glowing
motion trail on game field, bold white text "200 LINES" center, high contrast
dark background, energetic youth tech style]

### Pinned comment
```
📦 The code from this video (real FTC repo — start here):
https://github.com/vibeacademy/FtcRobotController/tree/lesson-01-hello-robot

Read LESSON.md first, then HelloWorld.java. Questions? Ask below — I read
everything. Next lesson: setting up your robotics workstation (no robot
needed).
```
