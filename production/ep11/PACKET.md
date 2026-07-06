# Lesson 11 — Autonomous II: Move With Precision

**Arc 3: Software That Wins Matches · Pain: P2, P4 · Runtime target: 12 min**

## Lesson Summary

- **Assumes:** Lessons 01–10 (state machines + encoders).
- **Delivers:** proportional control — the first and highest-value slice of
  control theory — applied to driving exact distances and turning to exact
  headings. The learner leaves able to say "P-control" and mean it.
- **Objectives:** (1) explain why full-power-then-stop overshoots;
  (2) implement `power = kP × error` for distance and heading; (3) tune kP
  empirically in sim and describe over/under-tuned symptoms.

## Branch Spec

- **Branch:** `lesson-11-p-control` (cut from `lesson-10-autonomous-park`)
- **Code state:** ParkAuto upgraded to `driveInchesP()` and `turnToHeadingP()`
  helpers with kP constants; a `TuneKpOpMode` letting the viewer bump kP
  live from the gamepad. `LESSON.md` includes the tuning symptom table
  (oscillates → too high; crawls/stalls → too low).

## Starter Script

> **Superseded for visuals.** The full shooting script
> (`SCRIPT.md`, this folder) is the single source
> of truth for every visual decision — prompts, `[Screen:]`/`[Edit:]`
> reclassifications, `[Layout:]` cues, and `[Asset:]` paths. The cues
> below are the original draft, kept for lineage; do not generate or
> film from them.

### Prerequisites
Lessons 09–10. Say plainly: no calculus, one multiplication.

### Mental Model Shifts
1. Don't command speeds — command *relationships*: power proportional to
   remaining error. The approach slows itself.
2. Overshoot isn't a bug in your code; it's physics (momentum) that your
   code failed to respect. Robots don't stop; they decelerate.
3. Tuning is empirical, not shameful: kP comes from experiments, not
   textbooks. Pros tune. That's what the constant is *for*.

---

## Hook (0:00–0:40)

[Visual: Two sim robots drive to a line. Robot A at full power slams past it,
backs up, overshoots again — oscillating. Robot B glides in like a driver
easing up to a stop sign, slowing as it arrives. Error counts to zero
beside B.]
[Video Prompt: Top-down dark simulator, left robot approaches glowing target
line at full speed overshoots reverses overshoots again in decaying
oscillation with red trail, right robot approaches fast then smoothly
decelerates settling exactly on line with blue trail and error counter
ticking to zero, side by side loop animation]

(Audio) Both robots know exactly how far away the line is — same encoders,
same math from last lesson. The left one drives like a beginner: full power
until you're there, then stop. Except robots don't stop — momentum exists —
so it's overshooting, reversing, overshooting again. The right one obeys a
single multiplication that professional control engineers also use. One
line of code. That line is today's whole lesson, and it's the difference
between an auto that's close and an auto that's *exact*.

## Concept: Power ∝ Error (0:40–4:00)

Beats: name the quantity — error = target − current (one number, sign =
direction); the rule — power = kP × error: far away → fast, close → gentle,
past it → sign flips and it self-corrects; the human analogy — this is
exactly how you park a bike: brake harder when closing fast, ease as you
arrive; you already run this algorithm, we're writing it down.

[Visual: Number line — robot position, target flag; an arrow (power) whose
length shrinks as position approaches target, flips direction past it]
[Prompt: Horizontal number line dark background, robot icon and target flag,
bold arrow from robot toward target that visibly shrinks in three snapshots
as gap closes, fourth snapshot past target with small reversed arrow, clean
math teaching diagram]

## Old vs. New (4:00–5:00)

[Paradigm Shift]
(Audio) Games taught you: set velocity, character moves at that velocity —
instantly, exactly. So your instinct writes bang-bang code: full power,
then zero. Physics doesn't do instant. A nine-pound robot at full power
carries momentum through your stop command every time. The shift: stop
thinking in commands, start thinking in relationships. You don't tell the
robot how fast to go — you tell it how its speed should *relate to its
remaining error*, and let the loop — fifty times a second, lesson one —
recompute it forever.

## Implementation (5:00–9:00)

Beats: refactor lesson-10's DRIVE state — replace fixed power with
`clamp(kP * errorInches)` (the lesson-05 clamp reappears — everything
composes); add minimum-power floor (friction eats tiny powers — robot
stalls 2 inches short); same recipe on heading: `turnToHeadingP` with IMU
error (note angle wraparound exists, LESSON.md covers it); state machine
unchanged — P-control slots INSIDE a state (lesson 10 skeleton pays off).

[Visual: The one-line diff — fixed 0.5 power replaced by kP*error expression
— then the loop diagram with the multiply node inserted between sensor and
motor]
[Prompt: Code diff dark editor, single line change from constant 0.5 to kP
times error highlighted green, beneath it circular control loop diagram
with new multiplication node glowing between encoder and motor icons,
minimal teaching style]

## Tuning on Camera (9:00–10:45)

Beats: run TuneKpOpMode; kP too low → robot crawls, stalls short (boring
failure); crank it too high → oscillation, the hook robot returns (dramatic
failure); bisect to good — settles fast, no overshoot; land the symptom
table: *oscillates = too high, crawls = too low* — say it twice, it's the
takeaway; note real teams keep tuned constants in one file and re-tune when
the robot gains weight.

[Visual: Three sim runs stacked — kP = 0.005 (crawl), 0.08 (oscillate), 0.02
(clean settle) — with a "symptom → diagnosis" table appearing]
[Prompt: Three horizontal simulator strips stacked dark background labeled
with kP values, top robot barely moving with snail icon, middle robot
zigzag oscillating with warning icon, bottom robot smooth clean arrival
with checkmark, symptom diagnosis table appearing right side, teaching
comparison style]

## Conclusion + CTA (10:45–12:00)

(Audio) You now hold the actual entry point of control theory — not a
watered-down version; PID's I and D are refinements on the exact loop you
just tuned, and honestly, well-tuned P takes most FTC teams further than
they'd believe. Next lesson is the finale: everything you've built meets a
real Control Hub — deploy, config, and the match-day ops that keep it all
working when it counts.

---

## Media Packet

### Title options (≤60 chars)
1. **One Line of Control Theory (FTC P-Control, Ep. 11)** [52] — RECOMMENDED
2. Why Your Auto Overshoots — and the Fix (FTC Ep. 11) [52]
3. Stop Writing Bang-Bang Robot Code (FTC Ep. 11) [47]

### Description
```
Full power, then stop — except robots don't stop, momentum exists, and your
auto oscillates past the target forever. Learn proportional control: one
multiplication (power = kP × error) that makes robots glide onto targets
like a driver easing up to a stop sign. Implement it for distance and heading, then tune kP
live and learn the two failure symptoms by heart.

📦 CODE FOR THIS LESSON (P-helpers + live tuning OpMode + symptom table)
https://github.com/vibeacademy/FtcRobotController/tree/lesson-11-p-control

📚 CHAPTERS
0:00 Two robots, same sensors, one glides
0:40 Power ∝ error (you already do this on a bike)
4:00 Commands vs relationships — the real shift
5:00 The one-line refactor (+ min-power floor)
9:00 Tuning kP on camera: crawl, oscillate, clean
10:45 You now know real control theory
```

### Tags
`FTC P control, proportional control robotics, FTC PID basics, kP tuning,
FTC autonomous precision, control theory beginner, FTC encoder driving, FTC
turn to heading`

### Thumbnail concept

> Current prompt lives in the full shooting script; the one below is the
> original draft.
A robot gliding onto a target line with a shrinking power arrow; big
equation "P = kP × error"; text "ONE LINE."
[Prompt: YouTube thumbnail 1280x720, robot easing onto glowing target line
with three shrinking motion arrows behind it, large clean equation power
equals kP times error in white, bold text ONE LINE, dark background,
elegant physics aesthetic]

### Pinned comment
```
📦 P-control helpers + the live tuning OpMode:
https://github.com/vibeacademy/FtcRobotController/tree/lesson-11-p-control

Memorize the symptom table: oscillates = kP too high, crawls = too low.
Post your best kP and settle time from the sim. Finale next: real hub,
match day, competition ops.
```
