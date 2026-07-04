# Lesson 08 — Telemetry: See What the Robot Thinks

**Arc 2 finale: Code Like a Real Team · Pain: P2, P3 · Runtime target: 10 min**

## Lesson Summary

- **Assumes:** Lessons 01–07.
- **Delivers:** debugging methodology for a machine you can't println —
  telemetry as a designed dashboard, not an afterthought; a repeatable
  "robot is misbehaving" diagnostic ritual. Closes Arc 2: the learner now
  has the full write → abstract → test → observe workflow.
- **Objectives:** (1) design a telemetry screen (inputs → decisions →
  outputs); (2) diagnose a planted bug on camera using only telemetry;
  (3) adopt the 4-question field-debug ritual.

## Branch Spec

- **Branch:** `lesson-08-telemetry` (cut from `lesson-07-testing`)
- **Code state:** TeleOp gains a structured telemetry dashboard (sticks →
  computed wheel powers → subsystem states); one intentionally-buggy OpMode
  (`DebugChallengeOpMode`) for the viewer exercise. `LESSON.md` has the
  4-question ritual as a printable card.

## Starter Script

### Prerequisites
Lessons 01–07 (the dashboard displays the lesson-05/06 pipeline).

### Mental Model Shifts
1. You can't println a robot mid-match. Telemetry is a *designed* instrument
   panel, not scattered debug prints.
2. Debug the pipeline, not the symptom: input → decision → output. The bug
   is at the first stage whose numbers surprise you.
3. "The robot is doing something weird" is never the bug report. The numbers
   on the telemetry screen are.

---

## Hook (0:00–0:40)

[Visual: A robot in sim drives in a slow circle instead of straight. Driver
looks confused. Cut to the telemetry panel: one number is obviously wrong —
right stick reads 0.13 with hands off the controller.]
[Prompt: Split screen dark theme, top-down simulator robot curving off course
with confused emoji overlay, telemetry dashboard beside it with rows of
numbers where one row glows red showing 0.13 stick drift, detective
magnifying glass motif, teaching aesthetic, 16:9]

(Audio) This robot pulls left, and nobody's touching the controller. The
build team says it's software. The software kid says it's the wheels. On
most teams this argument lasts a week. With the screen on the right, it
takes eight seconds — one number is wrong, and it points at the exact cause.
Today: how to make your robot narrate its own thoughts, and the four
questions that end every "it's doing something weird" mystery.

## Concept: Dashboard, Not Diary (0:40–3:00)

Beats: pilots don't read logs mid-flight, they read gauges; telemetry
repaints (callback lesson 03) so design it like an instrument panel — three
zones: INPUTS (what the drivers command), DECISIONS (what your math
computed), OUTPUTS (what the motors were told); if you show only one thing,
show the pipeline.

[Visual: Telemetry screen mock split into three labeled zones — INPUTS /
DECISIONS / OUTPUTS — mirroring the loop diagram from lesson 01]
[Prompt: Phone-style telemetry dashboard dark UI, three stacked sections
labeled INPUTS DECISIONS OUTPUTS in blue orange green with sample values,
small circular loop diagram watermark behind, clean instrument panel style]

## Old vs. New (3:00–4:00)

[Paradigm Shift]
(Audio) When a game glitches, you're spoiled: pause it, watch the replay,
read the crash report. A robot is a physical process — you cannot pause
gravity mid-lurch and go poke at a variable. Your instinct is to stop the
program and look inside. The robot way is the opposite: keep it running and
make the inside visible from the outside. Making the robot show its work
isn't extra credit — it's the only debugger you get on a field.

## Implementation (4:00–7:30)

Beats: build the 3-zone dashboard live on the lesson-06 TeleOp (sticks,
computed fl/fr/bl/br, arm state + claw state via null-safe reads); good
habits shown not lectured — units in labels ("arm pos (ticks)"), stable row
order, one screen max; `addLine` section headers.

[Visual: Code on left adding addData rows; live telemetry on right growing
into the three-zone dashboard as each line lands]
[Video Prompt: Split screen dark editor left and phone telemetry panel
right, each new code line typed causes corresponding dashboard row to
materialize with soft glow in its zone, progressive build animation,
satisfying developer aesthetic]

## The Ritual: Diagnose on Camera (7:30–9:00)

Beats: load `DebugChallengeOpMode` (planted bug: strafe sign flipped on one
wheel); run the 4 questions in order — (1) What do the INPUTS say? (sticks
fine) (2) What do the DECISIONS say? (one wheel's sign is wrong → bug found,
no hardware ever suspected) (3) OUTPUTS match decisions? (4) Does physics
match outputs? — and name the rule: *the bug lives at the first stage that
surprises you.* Note where each question's failure points: 1 = controller/
driver, 2 = your math, 3 = config/hardware map, 4 = build team's problem.

[Visual: The 4 questions as a vertical checklist; a glowing marker descends
and stops at question 2, arrow to the flipped sign in code]
[Prompt: Vertical checklist card dark background, four numbered questions,
glowing scanner line descending and locking on question two with red
highlight, arrow to code snippet with wrong minus sign circled, detective
teaching style]

## Conclusion + Arc 2 Capstone (9:00–10:00)

(Audio) Arc two complete — and look at what that actually is: you write
OpModes against clean interfaces, prove the logic with tests before the
robot exists, and when reality misbehaves anyway, your robot tells you
where. That's not a kid version of engineering — it's the real thing, the
same workflow you'll recognize in college labs and internships later. The branch has the buggy OpMode — diagnose it with the four
questions before peeking. Arc three is where this pays in points: sensors,
autonomous, and the software that wins matches.

---

## Media Packet

### Title options (≤60 chars)
1. **Debug a Robot You Can't Pause (FTC Ep. 8)** [43] — RECOMMENDED
2. The 4 Questions That Fix Any FTC Robot Bug (Ep. 8) [51]
3. Make Your Robot Narrate Its Thoughts (FTC Ep. 8) [49]

### Description
```
"It's doing something weird" isn't a bug report. Build a telemetry dashboard
that shows the robot's inputs, decisions, and outputs — then watch the
4-question ritual find a planted bug in under a minute, without touching the
hardware. Ends the build-team-vs-software-team blame war for good.

📦 CODE FOR THIS LESSON (dashboard + the debug challenge OpMode)
https://github.com/vibeacademy/FtcRobotController/tree/lesson-08-telemetry

📚 CHAPTERS
0:00 The robot that pulls left
0:40 Dashboards, not diaries
3:00 Why you can't pause a robot mid-bug
4:00 Building the 3-zone dashboard
7:30 The 4-question ritual, live
9:00 Arc 2 complete: your full workflow
```

### Tags
`FTC telemetry, FTC debugging, robot debugging, FTC driver station
telemetry, FTC troubleshooting, robot won't drive straight, FTC dashboard`

### Thumbnail concept
Robot with a visible "thought bubble" that is a telemetry panel, one row
glowing red; text "SEE WHAT IT THINKS."
[Prompt: YouTube thumbnail 1280x720, stylized robot with large thought
bubble containing telemetry dashboard rows, one row glowing red among green,
bold white text SEE WHAT IT THINKS, dark background, playful detective tech
style]

### Pinned comment
```
📦 Dashboard code + DebugChallengeOpMode (find the planted bug using only
the 4 questions — no peeking at the source):
https://github.com/vibeacademy/FtcRobotController/tree/lesson-08-telemetry

Post which question caught it. Arc 3 starts next: sensors, autonomous, and
the points.
```
