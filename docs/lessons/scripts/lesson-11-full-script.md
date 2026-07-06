# Lesson 11 — One Line of Control Theory (Full Shooting Script)

**Runtime target:** ~12:00 · **Persona:** see `docs/lessons/README.md`
**Source packet:** `docs/lessons/lesson-11.md` (media packet lives there)
**Code branch:** `lesson-11-p-control` — `autonomous/PBaseAuto.java`
(helpers), `opmodes/PrecisionAuto.java`, `opmodes/TuneKpOpMode.java`
(dpad adjusts kP, A runs a 24-inch move, B resets encoders).

**Production key:** `[Visual:]`+`[Prompt:]` = generated still ·
`[Video Prompt:]` = generated motion (0 used) · `[Screen:]` = you record
· `[Edit:]` = you build it in the editor (slide, overlay, or motion graphic)
· `[Layout:]` = Descript scene layout (default pack names) — no cue = **Camera**.

**Filming note:** the tuning session (9:00) is the reality-TV of this
episode — do it live in TuneKpOpMode with real values (0.005 crawl, 0.08
oscillate, bisect to ~0.02). Don't rehearse it too clean; a genuine "ooh,
too hot" read is the teaching moment.

---

## Prerequisites

Lessons 09–10. And say it plainly on camera: no calculus today. One
multiplication.

## Mental Model Shifts

1. Don't command speeds — command *relationships*: power proportional to
   remaining error. The approach slows itself.
2. Overshoot isn't a bug in your code; it's physics (momentum) your code
   failed to respect. Robots don't stop; they decelerate.
3. Tuning is empirical, not shameful: kP comes from experiments, not
   textbooks. Pros tune. That's what the constant is *for*.

---

## Hook (0:00–0:40)

[Layout: Media — 2-up composite of the two runs]
[Visual: Two sim robots drive to a line. Robot A at full power slams past
it, backs up, overshoots again — oscillating. Robot B glides in like a
driver easing up to a stop sign, slowing as it arrives. Error counts to
zero beside B.]
[Screen: two real sim runs — bang-bang full power (overshoot, reverse,
oscillate) vs P-control (smooth decelerating settle); both OpModes are on
this lesson's branch]
[Edit: side-by-side composite, red/blue trail accents, error counter
overlay ticking to zero on the P-control side. DO NOT generate — these two
behaviors are the entire lesson; record them (order-bearing motion)]

(Audio) Both robots know EXACTLY how far away that line is — same
encoders, same math as last lesson. The left one drives like a beginner:
full power until you're there, then stop. Except robots don't stop —
momentum exists — so it overshoots, reverses, overshoots again. Forever.
The right one obeys a single multiplication — the same one professional
control engineers use. One line of code. That line is today's entire
lesson, and it's the difference between an autonomous that's CLOSE and an
autonomous that's EXACT. And no — you do not need calculus. You need
multiplication.

[~40 seconds]

## Power ∝ Error (0:40–4:00)

(Audio) One new word, and it's friendlier than it sounds. ERROR: how far
you are from where you want to be. Target minus current position. Robot at
zero, line at twenty-four inches — error is twenty-four. Drive to
twenty-three — error is one. Blow past to twenty-six — error is MINUS two,
and that minus sign is about to matter.

[Layout: Media]
[Visual: Number line — robot position, target flag; an arrow (power) whose
length shrinks as position approaches target, flips direction past it]
[Prompt: Horizontal number line dark background, robot icon and target
flag, bold arrow from robot toward target that visibly shrinks in three
snapshots as gap closes, fourth snapshot past target with small reversed
arrow, clean math teaching diagram]

(Audio) Now the rule. Power equals kP times error. That's it — that's the
lesson. Read what it does: error is big — twenty-four inches away — power
is big, robot hustles. Error shrinks — power shrinks WITH it, the robot
eases off as it arrives. And if momentum carries it past? Error goes
NEGATIVE — so power flips backward, and the robot corrects itself. You
didn't write an if-statement for any of that. The relationship handles
every case, fifty times a second.

(Audio) And here's the thing: you already run this algorithm, in your
body. Rolling a bike up to a stop sign, you don't hold full pedal and
then slam the brake at the line — you ease off proportionally to how
close you are. You ARE a P-controller. Today we just write down something
your hands already know.

(Audio) The kP part? Just a knob. A small number — ours will be around
zero point zero two — that sets how aggressive the relationship is. Where
does it come from? Experiments. That's the last section, and it's the fun
one.

[~3 minutes 20 seconds]

## Commands vs Relationships (4:00–5:00)

[Paradigm Shift]
(Audio) Why does everyone write the bang-bang version first? Because games
taught you: set velocity, the character moves at that velocity. Instantly.
Exactly. So your instinct writes "full power, then zero" — a COMMAND.
Physics doesn't do instant. A nine-pound robot at full power carries
momentum straight through your stop command, every single time. The shift:
stop commanding speeds. Command a RELATIONSHIP — "your power should be
proportional to your remaining error" — and let the loop from lesson one
recompute it forever. The loop was always the point. Today it gets a
brain.

[~60 seconds]

## The One-Line Refactor (5:00–9:00)

[Screen: lesson-10 DRIVE state — the fixed DRIVE_POWER line; then the
diff: clamp(kP * error) replacing it]

(Audio) Watch how small this change is. Lesson ten's drive state, fixed
power, zero point four. New version: power equals kP times error — where
error is target inches minus traveled inches, numbers we already compute.
And look what's wrapped around it: THE CLAMP. Lesson five's clamp! At
twenty-four inches of error, kP times error would ask for way more than
full power — the clamp caps it. Everything in this series composes.
That's not an accident; that's what an architecture is.

[Layout: Screen — highlight as a Descript layer]
[Visual: The one-line diff — fixed 0.5 power replaced by kP*error
expression — then the loop diagram with the multiply node inserted between
sensor and motor]
[Screen: the real one-line diff — constant 0.5 becoming kP * error — in
the IDE or the branch's GitHub diff view]
[Edit: green highlight on the changed line; beneath it, the loop diagram
with a multiplication node glowing between encoder and motor (reuse the
E01-04 motion-graphic styling). DO NOT generate — fake diffs are fake code
(Rule 3)]

[Screen: the MIN_DRIVE_POWER floor in PBaseAuto; telemetry showing a robot
stalled 2 inches short before the floor existed]

(Audio) One patch reality demands: near the target, kP times error gets
TINY — and a command of zero point zero one doesn't move a robot, friction
eats it. The robot stalls, parked politely two inches short. The fix: a
minimum power floor — never command less than about zero point zero eight
until you're inside the tolerance. Gentle, but never stuck.

[Screen: turnToHeadingP — same shape with IMU error; PrecisionAuto running
in sim: drive 24 inches, then square up to heading zero]

(Audio) And because it's a relationship, not a trick, the SAME recipe
works on turning: error is target heading minus IMU heading — the compass
from lesson nine — power on the turn axis, same clamp, same floor. One
wrinkle called angle wraparound — one-seventy-nine and minus one-eighty
are actually neighbors — the branch code handles it and LESSON dot MD
explains it. Put together in PrecisionAuto: drive twenty-four inches,
glide to a stop, square up to exactly zero degrees. Look at that arrival.
That's not a lucky run — that's the relationship.

(Audio) And notice what DIDN'T change: the state machine. Same boxes, same
three exit doors. P-control lives INSIDE a state. Lesson ten gave you the
skeleton; today gave it smooth muscles.

[~4 minutes]

## Tuning, Live (9:00–10:45)

[Screen: TuneKpOpMode — dpad bumping kP down to 0.005, run: the crawl;
up to 0.08, run: oscillation; bisect to ~0.02, run: clean settle]

(Audio) So what's the right kP? Nobody knows — including the pros. You
TUNE it, with experiments, and there's an OpMode on the branch built for
exactly that: dpad changes kP, A runs a twenty-four inch move. Watch
three runs. kP way low, zero point zero zero five... the robot crawls,
and stalls short. Boring failure. kP way high, zero point zero eight...
there's our hook robot — slam, reverse, slam — oscillation. Dramatic
failure. Bisect... zero point zero two... fast approach, smooth arrival,
no overshoot. That's the one. Write it down in your constants.

[Layout: Media — 3-up composite of the three runs]
[Visual: Three sim runs stacked — kP = 0.005 (crawl), 0.08 (oscillate),
0.02 (clean settle) — with a "symptom → diagnosis" table appearing]
[Screen: three real Tune-kP sim runs — too low (crawl), too high
(oscillate), right (clean settle)]
[Edit: stack the three strips with kP value labels and
snail/warning/checkmark icons; build the symptom → diagnosis table as a
slide beside them. DO NOT generate — the three behaviors must be real
runs (Rule 3), and table text garbles (Rule 4)]

(Audio) Memorize the symptom table — I'll say it twice because it's the
takeaway: oscillates means kP too HIGH. Crawls means kP too LOW.
Oscillates, too high. Crawls, too low. And know that tuning isn't a
beginner thing you'll grow out of — it's what the constant is FOR. Real
teams keep tuned values in one file and re-tune when the robot gains
weight. Which it will. Usually the week before a qualifier.

[~105 seconds]

## Conclusion + CTA (10:45–12:00)

(Audio) Let me tell you what you're now holding, because I don't think
you'll believe it. This IS control theory — the actual entry point, not a
kid version. The famous one you'll hear about, PID? The P is literally
what you just built. The I and the D are refinements on the exact loop you
just tuned — future episode. And here's the secret nobody tells rookie
teams: well-tuned P takes most FTC teams further than they'd ever believe.
You're not behind anymore. You're ahead.

(Audio) Run the tuning drill, post your best kP and how clean the settle
was. And clear your schedule for the next one — it's the finale.
Everything you've built across eleven episodes finally meets a REAL
Control Hub: deploy, the config screen where your motor names come home,
code freeze, and the match-day checklist. The calm pit. See you there.

[~75 seconds]

---

## Thumbnail Concept

A robot gliding onto a target line with a shrinking power arrow; big
equation "P = kP × error"; text "ONE LINE."
[Prompt: YouTube thumbnail 1280x720, small boxy wheeled competition robot
easing onto glowing target line with three shrinking motion arrows behind
it, large clean equation "power = kP × error" in white, bold text "ONE
LINE", dark background, elegant physics aesthetic, not humanoid, no legs]
[Asset: production/ep11/E11-thumbnail.png]

---

## Shot List (screen recordings you need)

1. The one-line diff: fixed power → clamp(kP * error) (close-up)
2. The stall-short demo (comment the floor, robot parks 2in early), then
   floored version arriving
3. PrecisionAuto full run: drive + square-up (capture several — b-roll)
4. TuneKpOpMode live session: 0.005 crawl / 0.08 oscillate / 0.02 clean —
   continuous take, real reads
5. The constants at the top of PrecisionAuto (for the "write it down" beat)

Generated assets: 4 stills + 1 video clip (hook comparison).
