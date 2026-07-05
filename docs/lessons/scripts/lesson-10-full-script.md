# Lesson 10 — Your First FTC Autonomous, Built Right (Full Shooting Script)

**Runtime target:** ~12:00 · **Persona:** see `docs/lessons/README.md`
**Source packet:** `docs/lessons/lesson-10.md` (media packet lives there)
**Code branch:** `lesson-10-autonomous-park` — `ParkAuto.java` (states
DRIVE_TO_ZONE → STOP → DONE); the reusable skeleton is in LESSON.md.

**Production key:** `[Visual:]`+`[Prompt:]` = generated still ·
`[Video Prompt:]` = generated motion (0 used) · `[Screen:]` = you record
· `[Edit:]` = you build it in the editor (slide, overlay, or motion graphic)
· `[Layout:]` = Descript scene layout (default pack names) — no cue = **Camera**.

**Filming note:** the 5/5 drill must be REAL — five consecutive
unedited-looking sim runs with an on-screen tally you add in edit. If a run
fails while filming, keep it and reset the tally on camera; a real 3-then-
restart beats a fake 5/5 and it models the standard honestly.

---

## Prerequisites

Lessons 01–09 — encoders are load-bearing here.

## Mental Model Shifts

1. Autonomous isn't a to-do list — it's a state machine: "I am DRIVING
   until X, then I am STOPPING." States, transitions, exits.
2. Every autonomous motion needs three exit doors: goal reached, time
   expired, ref stopped the match. Two of them are safety.
3. Points are probabilities: your auto's value is points × how often it
   actually works. Software raises the second number — the one builders
   can't touch.

---

## Hook (0:00–0:45)

[Layout: Media — the scoreboard motion graphic]
[Visual: Scoreboard math on screen — Team A "40-pt auto, works 30%" vs
Team B "25-pt park, works 95%" — running point totals tick up across 5
simulated matches; B pulls ahead by match 3.]
[Edit: build the five-match scoreboard as a motion graphic — two columns,
match icons filling in sequence, left column hitting 40 only sometimes
(red X on failures), right scoring 25 nearly every match, running totals
with the right column overtaking at match three. DO NOT generate — the
exact numbers and the overtake ORDER are the argument (order-bearing
motion + Rule 2)]

(Audio) Team A built a forty-point autonomous. It works one match in
three. Team B parks for twenty-five points — nineteen matches out of
twenty. Watch the running totals... by match three, the boring team is
winning. By the end of a league season, it is not close. Points times how
often it actually works — that's the only autonomous math that matters,
and most teams never do it. This is the episode where software starts
putting real points on YOUR scoreboard: your first complete autonomous,
built to the standard that matters. Not "it worked once." It works every
time.

[~45 seconds]

## States, Not Scripts (0:45–4:00)

(Audio) First, the wrong way — and I need you to recognize it, because
it's in every rookie codebase and half the tutorials online. The rookie
auto is a SCRIPT: drive forward, sleep two thousand milliseconds, turn,
sleep fifteen hundred, drop the thing, pray. You already know why it
fails — sleeps are time-based driving, the blindfold from last lesson.
Battery sags, carpet grabs, and "two seconds of driving" lands somewhere
new every match.

(Audio) The competitive shape is a STATE MACHINE, and I want you to draw
it before you ever type it. Boxes and arrows. Each box is a state — a
sentence starting with "I am": I am DRIVING to the zone. I am STOPPING. I
am DONE. Each arrow is a transition — what makes me stop doing this and
start doing that: "traveled twenty-four inches" gets me out of DRIVING.
"Powers at zero" gets me out of STOPPING. The robot is in exactly one box
at any moment, and the telemetry can SAY which one — remember the
dashboard. Debugging an auto becomes "which box is it stuck in," which is
a question you can actually answer.

[Layout: Media]
[Visual: Whiteboard-style state diagram, three boxes DRIVE_TO_ZONE / STOP /
DONE, labeled transition arrows, a red "timeout" arrow exiting every box]
[Prompt: Hand-drawn style state machine on dark background, three rounded
boxes connected by labeled arrows, every box also has red dashed arrow to
ABORT stop-sign icon labeled timeout, clean whiteboard teaching aesthetic]

(Audio) And notice on my drawing: every single box also has a red arrow
out. That's the timeout — the "this is taking too long, move on" escape.
Why every box needs one is the next section, and it's the most important
ninety seconds in this episode.

[~3 minutes 15 seconds]

## The Three Exit Doors (4:00–6:00)

[Paradigm Shift]
(Audio) Here's the line an experienced FTC mentor checks FIRST in any
team's code. In a game loop, "while not there yet, keep going" is fine —
worst case, a sprite jitters in a corner. On a robot, that same loop with
a wheel stalled against a wall is a motor burning at full power... for the
rest of autonomous. It will not stop. It CANNOT stop — "there yet" is
never coming, because the wall gets a vote. I've smelled the motor this
lesson is about.

[Layout: Screen — clause boxes as Descript layers]
[Visual: The while-condition on screen, each of three clauses highlighted
as a labeled door — GOAL / TIMEOUT / REFEREE]
[Screen: the real while-condition line in the IDE, blown up large]
[Edit: box the three clauses in green/yellow/red with GOAL / TIMEOUT /
REFEREE door labels and small door icons beneath. DO NOT generate — models
invent fake Java (Rule 3)]

(Audio) So: every motion loop gets three exit doors, always, no
exceptions. Door one, GOAL: I traveled my twenty-four inches — the happy
door. Door two, TIMEOUT: this state has run four seconds and something's
clearly wrong — stop pushing, move on, save the motor. Door three,
REFEREE: opModeIsActive — the match ended or a human hit stop. Miss door
two and you burn motors. Miss door three and your robot keeps moving after
the ref calls stop — that's not a bug, that's breaking the rules of the
sport. Three doors. Look for them in every loop for the rest of this
series — and in your team's code.

[~2 minutes]

## Build ParkAuto, Then Prove It (6:00–10:00)

[Screen: writing ParkAuto — the enum, the config/createReal init,
resetEncoders + resetHeading in init, then the while/switch skeleton]

(Audio) Let's build it. New OpMode, ParkAuto — and notice the annotation
says at-AUTONOMOUS now, which puts it on the other half of the Driver
Station menu. First, the states as an enum: DRIVE_TO_ZONE, STOP, DONE —
the boxes from the whiteboard, in code. In init: our lesson-six config,
and — pay attention — reset the encoders NOW, in init. Last lesson I told
you stale ticks make autos go insane on run two. This line is the fix, and
you'll see the proof in the gotchas.

[Screen: the DRIVE_TO_ZONE case — traveled calculation via
COUNTS_PER_INCH, the goalReached/timedOut checks, the mecanumDrive call]

(Audio) The loop is a while with our three doors, wrapped around a switch:
"depending on which state I'm in, do that state's job." DRIVE_TO_ZONE's
job: convert encoder ticks to inches — counts-per-inch, last lesson,
doing real work — and check the doors. Goal door: twenty-four inches
reached. Timeout door: an ElapsedTime timer says four seconds passed —
generous for a two-second move, but real. Either way: transition to STOP,
reset the timer for the next state. Otherwise: drive forward. That's the
entire state.

[Screen: STOP and DONE cases; the telemetry narration line]

(Audio) STOP's job: motors to zero, transition to DONE. And DONE does...
nothing. On purpose. A state machine needs a parking spot — a state
that's safe to sit in forever. And the whole time, telemetry narrates:
which state, how far, how long. The dashboard habits from lesson eight,
now narrating a robot that's making its own decisions.

[Screen: THE DRILL — five consecutive sim runs, tally counter overlay
filling to 5/5]

(Audio) Now the standard. One run proving it works is an anecdote —
lesson seven taught us that. The drill is five in a row. Run one...
parked. Two... parked. Three... four... five. Five for five. THAT is what
"my autonomous works" means. Anything less, you found a bug early —
congratulations, that's the system working.

[Layout: Screen — sequential cuts, tally as a layer]
[Visual: Sim runs 5 consecutive times, a tally counter filling 5/5 green
checks, state name changing in telemetry each run]
[Screen: five real consecutive ParkAuto sim runs, telemetry state label
visible cycling DRIVE → STOP → DONE each run]
[Edit: cut the five runs into a rhythmic sequence, tally counter overlay
filling 5/5 green checks. DO NOT generate — five REAL runs is the
reliability claim itself (Rule 3 + order-bearing motion)]

[~4 minutes]

## Gotchas (10:00–11:15)

[Screen: deleting the resetEncoders line, running twice — run 2 stops
instantly (thinks it already traveled); restoring]

(Audio) Four gotchas, and I'm demonstrating the first one because it's the
classic. Delete the encoder reset from init. Run once — perfect. Run
again... the robot barely moves and declares itself parked. It's reading
LAST run's ticks — it thinks it already drove twenty-four inches. "Auto
works first time, insane second time" — now you know the disease AND the
fix. Two: your machine needs the DONE state — a loop that just falls out
the bottom can re-command motors on the way out. Three: timeouts —
generous but real. Four seconds for a two-second move, not thirty; a
thirty-second timeout is no timeout at all. And four, honesty: five for
five in the sim does not guarantee five for five on carpet — battery and
physics still vote. That's why we use margins... and it's why next
episode exists.

[~75 seconds]

## Conclusion + CTA (11:15–12:00)

(Audio) That's a competition-grade autonomous. Not because it's fancy —
it drives straight and stops — but because it exits safely no matter what
happens, it narrates its state, and it went five for five. That
architecture — states, transitions, three doors — is the skeleton of
every autonomous you will EVER write, including the fancy ones. It's on
the branch as a reusable template. Run the drill yourself and post your
tally — five for five or it doesn't count, and yes, four-out-of-five
means post four out of five, that's the point. Next episode: we make it
PRECISE. One multiplication — real control theory, the actual thing — and
your robot starts gliding onto targets instead of lurching at them. See
you there.

[~45 seconds]

---

## Thumbnail Concept

Scoreboard showing 25 × 95% beating 40 × 30%; small robot parked neatly in
a glowing zone; text "BORING WINS."
[Prompt: YouTube thumbnail 1280x720, large scoreboard comparison 25pts 95%
in green beating 40pts 30% in red, small robot parked in glowing blue zone
below, bold white text BORING WINS, dark background sports broadcast
energy]

---

## Shot List (screen recordings you need)

1. Whiteboard (real or tablet) drawing of the 3-box state machine — hand
   drawing it beats a finished graphic for this beat
2. Writing ParkAuto: enum → init with resets → while/switch → states
3. The three-doors while line, big font (for the overlay)
4. THE DRILL: five consecutive sim runs, continuous take (tally in edit)
5. The resetEncoders deletion demo: run 1 fine, run 2 instant-stop, restore
6. Telemetry narrating states during a run (close-up)

Generated assets: 3 stills + 2 video clips (hook scoreboard, 5/5 drill).
