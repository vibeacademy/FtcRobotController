# Lesson 10 — Autonomous I: Park Every Time

**Arc 3: Software That Wins Matches · Pain: P4 · Runtime target: 12 min**

## Lesson Summary

- **Assumes:** Lessons 01–09.
- **Delivers:** the learner's first complete autonomous — a state-machine
  park routine with timeouts and safety checks — and the strategy argument:
  reliable simple points beat flaky fancy points. This is the series'
  thesis lesson: software = match points.
- **Objectives:** (1) structure an autonomous as a state machine (not a
  script of sleeps); (2) guard every motion with the three-condition while
  loop (target ∨ timeout ∨ opModeIsActive); (3) explain why points × how
  often it actually works is the number that matters: 25 × 90% beats 40 × 30%.

## Branch Spec

- **Branch:** `lesson-10-autonomous-park` (cut from `lesson-09-sensors`)
- **Code state:** `opmodes/ParkAuto.java` — an @Autonomous OpMode driving an
  encoder-measured distance via a small state machine (DRIVE → STOP → DONE),
  every loop guarded, telemetry narrating state. `LESSON.md` includes the
  state-machine template as a reusable skeleton.

## Starter Script

> **Superseded for visuals.** The full shooting script
> (`docs/lessons/scripts/lesson-10-full-script.md`) is the single source
> of truth for every visual decision — prompts, `[Screen:]`/`[Edit:]`
> reclassifications, `[Layout:]` cues, and `[Asset:]` paths. The cues
> below are the original draft, kept for lineage; do not generate or
> film from them.

### Prerequisites
Lessons 01–09 (encoders are load-bearing here).

### Mental Model Shifts
1. Autonomous isn't a to-do list — it's a state machine: "I am DRIVING until
   X, then I am STOPPING." States, transitions, exits.
2. Every autonomous motion needs three exit doors: goal reached, time
   expired, ref stopped the match. Two of them are safety.
3. Points are probabilities: your auto's value is points × reliability.
   Software raises the second number — the one builders can't touch.

---

## Hook (0:00–0:45)

[Visual: Scoreboard math on screen — Team A "40-pt auto, works 30%" vs Team
B "25-pt park, works 95%" — running point totals tick up across 5
simulated matches; B pulls ahead by match 3.]
[Video Prompt: Dark scoreboard interface two team columns, five match icons
fill in sequence, left column occasionally scores 40 with red X marks on
failures, right column scores 25 nearly every match with green checks,
running totals climb with right column overtaking by match three, data
animation, sports broadcast style]

(Audio) Team A built a forty-point autonomous that works one match in three.
Team B parks for twenty-five, nineteen matches out of twenty. By match
three, the boring team is winning — and at league rankings, it isn't close.
This is the video where software starts putting real points on your
scoreboard: your first complete autonomous, built to the standard that
matters, which isn't "it worked once." It's "it works every time."

## Concept: States, Not Scripts (0:45–4:00)

Beats: the rookie auto is a script — drive, sleep(2000), turn, sleep(1500),
pray (name why it fails: sleeps are open-loop time-based driving, lesson 09
callback); the competitive shape is a state machine — draw it as boxes and
arrows BEFORE code: DRIVE_TO_ZONE →(24 in reached)→ STOP →(powers zero)→
DONE; each state = what am I doing; each arrow = what makes me stop doing
it.

[Visual: Whiteboard-style state diagram, three boxes DRIVE_TO_ZONE / STOP /
DONE, labeled transition arrows, a red "timeout" arrow exiting every box]
[Prompt: Hand-drawn style state machine on dark background, three rounded
boxes connected by labeled arrows, every box also has red dashed arrow to
ABORT stop-sign icon labeled timeout, clean whiteboard teaching aesthetic]

## The Three Exit Doors (4:00–6:00)

[Paradigm Shift]
(Audio) Here's the line an experienced FTC mentor checks first in any
team's code. In a
game loop, `while (notThereYet)` is fine — worst case, a sprite jitters. On
a robot, that same loop with a stalled wheel is a motor burning at full
power against a wall while thirty seconds of match tick away — and it will
not stop, because "there yet" is never coming. Every motion loop gets three
exit doors, always: target reached, timeout expired, opModeIsActive. Miss
the second and you burn motors. Miss the third and you're breaking the
rules of the sport — the ref hits stop and your robot keeps going.

[Visual: The while-condition on screen, each of three clauses highlighted as
a labeled door — GOAL / TIMEOUT / REFEREE]
[Prompt: Single line of Java while condition large on dark background, three
clauses boxed in green yellow red labeled GOAL door TIMEOUT door REFEREE
door, three small door icons beneath, code anatomy teaching style]

## Implementation (6:00–10:00)

Beats: write ParkAuto live on the state-machine skeleton — enum states,
switch in the loop; DRIVE_TO_ZONE uses counts-per-inch from lesson 09;
`ElapsedTime` timeout per state (the repo's CLAUDE.md pattern, verbatim);
telemetry narrates current state + progress (lesson 08 dashboard habits);
run in sim: park works; then the reliability drill — run it FIVE times in a
row on camera, count 5/5 (that's the standard; one run is an anecdote,
lesson 07 callback).

[Visual: Sim runs 5 consecutive times, a tally counter filling 5/5 green
checks, state name changing in telemetry each run]
[Video Prompt: Top-down simulator dark field, robot drives to parking zone
and stops five times in rapid sequence, tally counter top right fills five
green check marks one per run, telemetry state label cycles DRIVE STOP DONE
each run, rhythmic repetition animation, satisfying reliability aesthetic]

## Gotchas (10:00–11:15)

Beats: forgetting to reset encoders in init → run 2 goes insane (lesson 09
callback, now with consequences); state machines need a DONE state that
does nothing — loops that fall out the bottom re-command motors; timeout
values: generous but real (3s for a 2s move, not 30); sim 5/5 ≠ field 5/5 —
battery/carpet still vote, which is why margins matter.

## Conclusion + CTA (11:15–12:00)

(Audio) That's a competition-grade autonomous — not because it's fancy, but
because it exits safely no matter what and it went five for five. Next
lesson we make it precise: proportional control, the first ten percent of
control theory that gives you ninety percent of the value. The branch has
the state-machine skeleton — it's the template for every auto you'll ever
write.

---

## Media Packet

### Title options (≤60 chars)
1. **Your First FTC Autonomous (Built Right) — Ep. 10** [50] — RECOMMENDED
2. Boring Auto, Free Points: FTC State Machines (Ep. 10) [54]
3. The 3 Exit Doors Every Robot Loop Needs (Ep. 10) [49]

### Description
```
A 40-point auto that works 30% of the time loses to a 25-point park that
works 95% — by match three. Build your first complete autonomous the
competitive way: a state machine (not a script of sleeps), the
three-exit-door while loop that experienced mentors check first, and the 5/5
reliability drill. Software = points, starting now.

📦 CODE FOR THIS LESSON (ParkAuto + reusable state-machine skeleton)
https://github.com/vibeacademy/FtcRobotController/tree/lesson-10-autonomous-park

📚 CHAPTERS
0:00 The boring team wins by match 3
0:45 States, not scripts
4:00 The three exit doors (safety as code)
6:00 Building ParkAuto live
10:00 The 5-for-5 reliability drill
11:15 Next: precision with P-control
```

### Tags
`FTC autonomous tutorial, FTC state machine, first FTC autonomous, FTC
parking auto, opModeIsActive, FTC timeout, autonomous programming, FTC
strategy points`

### Thumbnail concept

> Current prompt lives in the full shooting script; the one below is the
> original draft.
Scoreboard showing 25 × 95% beating 40 × 30%; small robot parked neatly in a
glowing zone; text "BORING WINS."
[Prompt: YouTube thumbnail 1280x720, large scoreboard comparison 25pts 95%
in green beating 40pts 30% in red, small robot parked in glowing blue zone
below, bold white text BORING WINS, dark background sports broadcast
energy]

### Pinned comment
```
📦 ParkAuto + the state-machine skeleton (your template for every auto):
https://github.com/vibeacademy/FtcRobotController/tree/lesson-10-autonomous-park

Run the 5/5 drill in sim and post your tally. 4/5 doesn't count — that's
the whole point 😄 Next: P-control for precision moves.
```
