# Lesson 09 — Your Robot Is Driving Blindfolded (Full Shooting Script)

**Runtime target:** ~11:00 · **Persona:** see `docs/lessons/README.md`
**Source packet:** `docs/lessons/lesson-09.md` (media packet lives there)
**Code branch:** `lesson-09-sensors` — TeamTeleOp's dashboard gains the
SENSORS zone (`COUNTS_PER_INCH` constant at the top of the file); LESSON.md
has the counts-per-inch worksheet with a worked REV HD Hex example.

**Production key:** `[Visual:]`+`[Prompt:]` = generated still ·
`[Video Prompt:]` = generated motion (0 used) · `[Screen:]` = you record
· `[Edit:]` = you build it in the editor (slide, overlay, or motion graphic)
· `[Layout:]` = Descript scene layout (default pack names) — no cue = **Camera**.

**Filming note:** if you can film the blindfold bit physically (you, five
steps, eyes closed, in the workshop), DO it — 15 seconds of real footage
beats any diagram, and it's the analogy the whole arc hangs on.

---

## Prerequisites

Arcs 1–2. The dashboard from lesson 08 — sensors join it today.

## Mental Model Shifts

1. Without sensors your robot is driving blindfolded, counting seconds.
   Sensors take the blindfold off.
2. Commands are wishes; sensors report what actually happened. Competitive
   code is a conversation between the two.
3. A sensor never reports "inches" or "at the wall" — it reports ticks and
   degrees. Turning raw numbers into meaning is software's job, and where
   the bugs live.

---

## Hook (0:00–0:40)

[Layout: Media — 2-up composite of the runs]
[Visual: Two sim robots told to drive "exactly 24 inches." Robot A
(time-based) stops short on a fresh battery run, overshoots on the second
run. Robot B (encoders) nails the same spot twice. Tape-measure overlay.]
[Screen: real sim runs — the time-based auto twice (stops short, then
overshoots) and the encoder auto twice (nails the line both times)]
[Edit: side-by-side composite with red distance markers, green checks, and
the tape-measure overlay. DO NOT generate — inconsistency vs repeatability
is the teaching point; record it (order-bearing motion)]

(Audio) Both robots got the same mission: drive exactly twenty-four
inches. The left one runs the code from every beginner tutorial on the
internet — drive at half power for two seconds — and it has never landed
in the same place twice, because battery voltage sags between matches and
"two seconds of half power" means something different every run. The right
one asks its own wheels how far it's gone. Same motors. Same field. One
mental model apart — and it's the model that separates autonomous programs
that SCORE from autonomous programs that PRAY. Arc three starts now.

[~40 seconds]

## The Blindfold (0:40–3:30)

[Screen: (ideally) YOU, physically — five steps across the room with eyes
closed, missing a target on the floor; then repeating with eyes open]

(Audio) Try this for real: pick a spot on the floor about five steps away.
Close your eyes. Walk five steps. Open. ...Close, right? But not ON it —
and if the floor were carpet, or your shoes were different, you'd miss by
more. Now do it with your eyes open. You didn't count steps at all — you
WATCHED, and adjusted, the whole way. Same legs. Different information.

(Audio) That's the entire lesson, so let's name it. Eyes closed is called
OPEN LOOP: send a command, hope. "Motor, half power, two seconds" is your
robot walking with its eyes shut, counting steps. Eyes open is CLOSED
LOOP: command, MEASURE what actually happened, adjust, repeat. The
measuring is what sensors are for.

[Layout: Media]
[Visual: Two loop diagrams — open: brain → motors (one-way arrow); closed:
brain → motors → wheels → encoder → back to brain (circle)]
[Prompt: Two diagrams side by side dark background, left one-way arrow
chain labeled OPEN LOOP brain to motor fading into question marks, right
circular loop brain motor wheel encoder back to brain glowing blue labeled
CLOSED LOOP, clean control theory teaching style]

(Audio) And why does open loop miss? Because physics votes every single
run — remember lesson one. Battery sag: fresh battery, your robot's a
sprinter; round six, it's tired, and "two seconds" covers less floor.
Carpet nap, worn wheels, a bump from another robot. None of it shows up
in your code. All of it shows up on the field.

[~2 minutes 50 seconds]

## The Two Free Sensors (3:30–6:30)

[Screen: close-up photo/footage of an FTC motor's encoder cable; then the
dashboard's new SENSORS zone in the sim]

(Audio) Good news: your robot already owns the two sensors that fix this —
zero dollars, already in the kit. Sensor one: the encoder. Every FTC motor
has one built in — a tiny counter that ticks as the shaft turns. It
doesn't know inches. It knows TICKS — five hundred and sixty per
revolution on a typical REV motor. Ticks to inches is one conversion:
how many ticks is one wheel-turn, and how far does one wheel-turn roll
you — that's the wheel's circumference. Divide, done. It's called
counts-per-inch, the worksheet on the branch computes it for YOUR robot
in two minutes, and hear me now: it is different for every robot. Copying
mine is a bug you'll meet in the gotchas.

(Audio) Sensor two: the IMU — inertial measurement unit — built into the
hub itself. It's the robot's compass: "which way am I facing," in degrees.
Zero when you reset it, positive counterclockwise, up to one-eighty each
way.

[Screen: sim — driving by hand while the SENSORS zone updates: ticks
climbing, inches derived, heading swinging as the robot spins]

(Audio) Both are on our dashboard now — new zone, SENSORS, straight onto
the lesson-eight panel. Watch it live: I push the robot forward, ticks
climb, and next to them the inches conversion — five hundred ticks, five
inches. I spin the robot, heading swings. And notice these are just...
numbers, arriving fifty times a second, in the same loop as everything
else. Sensors aren't magic. They're rows on the dashboard the robot fills
in itself.

[Layout: Screen — annotations as Descript layers]
[Visual: Dashboard SENSORS zone: left ticks 537 → "12.4 in", heading
87.3° → a compass rose graphic tracking as robot spins]
[Screen: the real SENSORS telemetry zone — encoder ticks and heading
updating live while the robot moves and spins in the sim]
[Edit: annotate ticks → inches with an arrow; add a compass-rose graphic
tracking the real heading value. DO NOT generate — real telemetry only
(Rule 3)]

[~3 minutes]

## Commands Are Wishes (6:30–7:30)

[Paradigm Shift]
(Audio) Here's the habit to unlearn, and it's the deepest one in this
series. In a game, when you move a character ten units... it moves ten
units. The engine guarantees it, every time, and you have never once
checked. On a robot, "drive at half power for two seconds" is a REQUEST —
and physics is free to edit it. Nothing your code commands is guaranteed
to have happened. The only truth is what the sensors report. Which is why
competitive robot code has a funny shape: it READS more than it WRITES.
Wishes out, truth in, adjust, repeat — fifty times a second.

[~60 seconds]

## The 4 Sensor Gotchas (7:30–9:45)

[Layout: List — the four gotchas typed natively]
[Visual: Four-row warning card, each row a gotcha with a small icon —
slipping wheel, stale counter, flipped compass, copied worksheet]
[Edit: build the four-gotcha warning card as a slide — numbered rows with
the exact labels from the narration. The icons (slipping wheel, cobwebbed
counter, flipped compass, photocopied worksheet) may be generated
individually as clean single-subject stills. Multi-row described text
garbles when generated (Rule 4)]

(Audio) Four ways sensors will lie to you, each with a ten-second
telemetry check. One: encoders measure WHEEL rotation, not robot position.
If the wheel slips — someone pushes you, you hit a wall — the encoder
happily counts distance you didn't travel. The check: does the encoder
distance agree with the compass and your eyes? Serious teams eventually
add dedicated tracking wheels for this — that's called odometry, future
episode. Two: reset your encoders in init, or the ticks from the LAST run
carry over — this is the classic "my auto was perfect, then went insane on
the second run" bug, and you'll see the fix in code next lesson. Three:
the IMU's idea of "counterclockwise" depends on how the hub is mounted —
verify with the dashboard before trusting it: spin the robot left, does
heading go UP? Four: counts-per-inch is per-robot — gear ratios change
mid-season, wheels get swapped. The check: push the robot exactly
twenty-four inches along a tape measure and see what the dashboard says.
If it says twelve, your gear ratio term is wrong, and better to learn that
on a Tuesday than in a match.

[~2 minutes 15 seconds]

## Conclusion + CTA (9:45–11:00)

(Audio) Look at your dashboard now. Top zone: what the drivers WISH.
Bottom zone: what the robot says ACTUALLY happened. The gap between those
two numbers — that's where matches are won, and everything in the rest of
this arc lives in that gap. The branch has the counts-per-inch worksheet —
do it for your robot, post your number and your drivetrain in the
comments, and I'll sanity-check the math. Next lesson, we cash all of this
in: a state machine that parks your robot for free points, every single
match — built to the standard real teams use. Five out of five, or it
doesn't count. See you there.

[~75 seconds]

---

## Thumbnail Concept

Robot wearing a blindfold on the left; same robot with a glowing "eye"
(compass/encoder icons) on the right; text "TAKE OFF THE BLINDFOLD."
[Prompt: YouTube thumbnail 1280x720, split image same stylized robot, left
wearing gray blindfold veering off course, right with glowing blue sensor
eye and compass icons driving true, bold white text TAKE OFF THE
BLINDFOLD, dark background high contrast]

---

## Shot List (screen recordings + physical)

1. PHYSICAL: the blindfold walk (5 steps, eyes closed vs open) — worth it
2. Encoder cable close-up on a real FTC motor (borrow one if needed)
3. Sim: SENSORS zone live — ticks/inches climbing, heading swinging
4. The tape-measure push test (physical if you have any robot/chassis;
   else sim equivalent with the on-screen dashboard)
5. Dashboard reading during a deliberate wheel-slip (pin the robot against
   a wall in sim — encoder climbs, robot doesn't move)

Generated assets: 4 stills + 1 video clip (hook comparison).
