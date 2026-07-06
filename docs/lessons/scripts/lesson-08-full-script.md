# Lesson 08 — Debug a Robot You Can't Pause (Full Shooting Script)

**Runtime target:** ~10:00 · **Persona:** see `docs/lessons/README.md`
**Source packet:** `docs/lessons/lesson-08.md` (media packet lives there)
**Code branch:** `lesson-08-telemetry` — TeamTeleOp has the 3-zone
dashboard; `DebugChallengeOpMode` carries the planted bug (back-left strafe
sign flipped, marked in source for post-diagnosis checking).

**Production key:** `[Visual:]`+`[Prompt:]` = generated still ·
`[Video Prompt:]` = generated motion (0 used) · `[Screen:]` = you record
· `[Edit:]` = you build it in the editor (slide, overlay, or motion graphic)
· `[Layout:]` = Descript scene layout (default pack names) — no cue = **Camera**.

**Filming note:** the diagnosis (7:30) must be played STRAIGHT — run the
buggy OpMode as if you don't know the answer, read the zones top to bottom,
and let question 2 catch it in real time. Rehearse so the "discovery" is
tight, but don't cut the reasoning; the reasoning IS the lesson.

---

## Prerequisites

Lessons 01–07 — the dashboard displays the lesson-05/06 pipeline.

## Mental Model Shifts

1. You can't println a robot mid-match. Telemetry is a *designed*
   instrument panel, not scattered debug prints.
2. Debug the pipeline, not the symptom: input → decision → output. The bug
   is at the first stage whose numbers surprise you.
3. "The robot is doing something weird" is never the bug report. The
   numbers on the telemetry screen are.

---

## Hook (0:00–0:40)

[Layout: Screen — sim window with field + telemetry both visible]
[Visual: A robot in sim drives in a slow circle instead of straight. Driver
looks confused. Cut to the telemetry panel: one number is obviously wrong —
right stick reads 0.13 with hands off the controller.]
[Screen: real sim run with the drift bug in — robot curving instead of
driving straight — and the real telemetry panel reading right stick 0.13
with hands off the controller]
[Edit: side-by-side composite, red glow on the guilty row, magnifying
glass motif, confused-emoji accent. DO NOT generate — real sim + real
telemetry or the detective story is fiction (Rule 3)]

(Audio) This robot pulls left, and nobody is touching the controller. The
build team says it's software. The software kid says it's the wheels. On
most teams, that argument lasts a WEEK — I've watched it happen. With the
screen on the right, it takes eight seconds: one number is wrong, and it
points at the exact cause. Today: how to make your robot narrate its own
thoughts — and the four questions that end every "it's doing something
weird" mystery your team will ever have.

[~40 seconds]

## Dashboard, Not Diary (0:40–3:00)

(Audio) Quick thought experiment. A pilot mid-flight doesn't scroll
through a log of everything the plane has done since takeoff. They read
GAUGES — altitude, speed, fuel — the state of NOW, at a glance. That's
what telemetry is for a robot, and it's why — remember lesson three — the
screen repaints instead of scrolling. It was never a chat log. It's an
instrument panel. So stop sprinkling debug prints and START designing a
panel.

[Layout: Screen — zone labels as Descript layers]
[Visual: Telemetry screen mock split into three labeled zones — INPUTS /
DECISIONS / OUTPUTS — mirroring the loop diagram from lesson 01]
[Screen: the real three-zone dashboard this episode builds, live on the
Driver Station]
[Edit: zone labels INPUTS / DECISIONS / OUTPUTS in blue/orange/green,
faint loop-diagram watermark behind. DO NOT generate — the episode
literally builds this dashboard; show the real one (Rule 3)]

(Audio) And here's the design, and notice it's an old friend. Zone one,
INPUTS: what the drivers are commanding — the sticks, the buttons. Zone
two, DECISIONS: what your math computed from that — the four wheel powers,
the arm command. Zone three, OUTPUTS: what the hardware was actually told,
and what it reports back. Inputs, decisions, outputs... that's the loop
from lesson one, laid out vertically on a screen. Your dashboard isn't
decoration. It's the control loop, made visible.

[~2 minutes 20 seconds]

## Why You Can't Pause a Robot (3:00–4:00)

[Paradigm Shift]
(Audio) When a game glitches, you're spoiled rotten: pause it, watch the
replay, read the crash report. A robot is a physical process. You cannot
pause gravity mid-lurch and go poke at a variable — the robot will finish
falling while you think. So your instinct — stop the program and look
inside — is exactly backwards. The robot way: keep it RUNNING, and make
the inside visible from the OUTSIDE. Making the robot show its work isn't
extra credit. On a field, it's the only debugger that exists.

[~60 seconds]

## Build the Dashboard (4:00–7:30)

[Screen: TeamTeleOp in the editor — adding addLine("— INPUTS —") and the
stick row; sim telemetry updating alongside]

(Audio) Let's build it on our TeamTeleOp, live. addLine gives us a section
header — INPUTS — and under it, one row with all three stick intents.
Run... there's zone one, tracking my thumbs in real time.

[Layout: Screen — one live recording, editor + telemetry]
[Visual: Code on left adding addData rows; live telemetry on right growing
into the three-zone dashboard as each line lands]
[Screen: real recording — editor left, live telemetry right; type each
addData line, redeploy, and let the new row appear for real]
[Edit: cut so each typed line lands with its row appearing, soft glow
accent per new row. DO NOT generate — Rule 3, and the build-up order is
the meaning (order-bearing motion)]

[Screen: adding DECISIONS zone (arm power command), then OUTPUTS zone
(encoder ticks, arm position, claw state with the null checks)]

(Audio) Zone two, DECISIONS: what did our code decide — here, the arm
power we computed from the operator's stick. Zone three, OUTPUTS: what
does the hardware say actually happened — encoder ticks from the wheels,
the arm's real position, the claw's state. Notice the null checks doing
their lesson-six job: no arm plugged in, no arm row, no crash.

(Audio) Three habits as I type, because dashboard quality is a skill. One:
units in every label — "arm pos, ticks" — because a bare 537 means
nothing at a glance. Two: stable row order — your eyes learn WHERE numbers
live; don't shuffle them. Three: one screen, max. A dashboard you have to
scroll is a diary again. If you only remember one thing: show the
PIPELINE — in, decide, out.

[~3 minutes 30 seconds]

## The Ritual, Live (7:30–9:00)

[Screen: loading DebugChallengeOpMode in the sim — the robot veers badly
when strafing; play it straight]

(Audio) Now the payoff. This is DebugChallengeOpMode — it shipped on this
lesson's branch with exactly one bug planted in it, and I'm going to
diagnose it right now using only the dashboard and four questions, in
order. Watch the discipline, not just the answer.

(Audio) The symptom: strafing veers hard. Question one: what do the INPUTS
say? ...Sticks read clean — strafe is point-eight, nothing else. So it's
not the controller, not the driver. Question two: what do the DECISIONS
say? Four wheel values... front-left point-eight, front-right minus
point-eight, back-left... POSITIVE point-eight? That wheel's sign is
WRONG. Found it. Question two caught it — it's in OUR math, and I never
even suspected the hardware.

[Layout: List — the four questions typed natively]
[Visual: The 4 questions as a vertical checklist; a glowing marker
descends and stops at question 2, arrow to the flipped sign in code]
[Screen: the buggy line in the IDE — the wrong minus sign]
[Edit: build the four-question card as a slide with the exact questions
from the narration; animate a scanner line locking onto question two,
arrow to the real code capture with the minus sign circled. DO NOT
generate — described text garbles (Rule 4) and fake code is banned
(Rule 3)]

(Audio) The full ritual, so you have it: one, inputs — wrong here means
controller or driver. Two, decisions — wrong here means YOUR math. Three,
do outputs match decisions — wrong here means config or wiring. Four, does
PHYSICS match outputs — wrong here, hand it to the build team with data.
The rule: the bug lives at the FIRST stage whose numbers surprise you.
Every stage after it is just consequences.

[~90 seconds]

## Arc 2 Capstone (9:00–10:00)

(Audio) Arc two, complete — and step back and look at what it actually is.
You write OpModes against clean interfaces. You PROVE the logic with tests
before any robot exists. And when reality misbehaves anyway — because it
will — your robot tells you where, in numbers, in seconds. That's not a
kid version of engineering. That's the real thing — the same workflow
you'll recognize in college labs and internships later. You just learned
it years early.

(Audio) Your challenge: the buggy OpMode is on the branch. Run it, run the
four questions, and post WHICH question caught it — before you peek at the
source, where the answer is marked. And then get ready, because arc three
is where all of this starts paying in points: sensors, autonomous, and the
software that wins matches. See you there.

[~60 seconds]

---

## Thumbnail Concept

Robot with a visible "thought bubble" that is a telemetry panel, one row
glowing red; text "SEE WHAT IT THINKS."
[Prompt: YouTube thumbnail 1280x720, stylized robot with large thought
bubble containing telemetry dashboard rows, one row glowing red among
green, bold white text SEE WHAT IT THINKS, dark background, playful
detective tech style]

---

## Shot List (screen recordings you need)

1. Building the dashboard zone by zone in TeamTeleOp, sim panel alongside
2. Sim: dashboard tracking real stick input (INPUTS zone proof)
3. DebugChallengeOpMode veering in the sim (the symptom)
4. The straight-played diagnosis: reading zones top to bottom, landing on
   the wrong back-left value (continuous take)
5. The marked bug line in source (for the post-diagnosis confirm)

Generated assets: 4 stills + 1 video clip (dashboard build animation).
