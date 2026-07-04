# Lesson 09 — Sensors and State

**Arc 3: Software That Wins Matches · Pain: P2, P4 · Runtime target: 11 min**

## Lesson Summary

- **Assumes:** Arcs 1–2.
- **Delivers:** the feedback mental model — open-loop vs closed-loop — via
  the two sensors every FTC robot has: motor encoders and the IMU. Sets up
  everything autonomous.
- **Objectives:** (1) explain open vs closed loop with the blindfold
  analogy; (2) read encoder ticks and IMU heading into telemetry; (3)
  convert ticks → inches with counts-per-inch, and explain why time-based
  driving loses.

## Branch Spec

- **Branch:** `lesson-09-sensors` (cut from `lesson-08-telemetry`)
- **Code state:** telemetry dashboard gains a SENSORS zone (encoder
  positions, derived inches, IMU heading); `IDrivetrain`'s encoder methods
  and `IIMU` get demonstrated via sim. `LESSON.md` includes the
  counts-per-inch worksheet.

## Starter Script

### Prerequisites
Arcs 1–2. The dashboard from lesson 08 (sensors join it).

### Mental Model Shifts
1. Without sensors your robot is driving blindfolded, counting seconds.
   Sensors take the blindfold off.
2. Commands are wishes; sensors report what actually happened. Competitive
   code is a conversation between the two.
3. A sensor never reports inches or "at the wall" — it reports ticks and
   volts. Turning raw numbers into meaning is software's job (and where the
   bugs live).

---

## Hook (0:00–0:40)

[Visual: Two sim robots told to drive "exactly 24 inches." Robot A
(time-based) stops short on a fresh battery run, overshoots on the second
run. Robot B (encoders) nails the same spot twice. Tape-measure overlay.]
[Video Prompt: Top-down simulator dark field, two robots drive toward glowing
line, left robot stops short first run then overshoots second run with red
distance markers, right robot stops exactly on line both runs with green
checkmarks, tape measure graphic overlay, comparison animation]

(Audio) Both robots ran the exact same mission: drive twenty-four inches.
The left one uses the code in every beginner tutorial — drive at half power
for two seconds — and it's never landed in the same place twice, because
battery voltage sags between matches. The right one asks its own wheels how
far it's gone. Same motors, same field. The difference is one mental model,
and it's the one that separates autonomous programs that score from
autonomous programs that pray.

## Concept: The Blindfold (0:40–3:30)

Beats: walk 10 steps blindfolded vs watching the floor — open loop vs closed
loop, no jargon until the analogy lands; command ("motor, 0.5 power") vs
measurement ("I've turned 537 ticks"); why open loop drifts: battery sag,
carpet nap, wheel wear — physics votes every run.

[Visual: Two loop diagrams — open: brain → motors (one-way arrow); closed:
brain → motors → wheels → encoder → back to brain (circle)]
[Prompt: Two diagrams side by side dark background, left one-way arrow chain
labeled OPEN LOOP brain to motor fading into question marks, right circular
loop brain motor wheel encoder back to brain glowing blue labeled CLOSED
LOOP, clean control theory teaching style]

## Meet the Two Free Sensors (3:30–6:30)

Beats: encoders — every FTC motor ships with one; ticks per revolution →
counts-per-inch math shown once as arrows (wheel circumference ÷ ticks);
IMU — the hub's built-in compass+gyro; heading in degrees; both read into
the lesson-08 dashboard's new SENSORS zone, live in sim; drive by hand and
watch ticks climb + heading swing — sensors are just numbers arriving 50x
a second (callback to the loop).

[Visual: Dashboard SENSORS zone: left ticks 537 → "12.4 in", heading 87.3° →
a compass rose graphic tracking as robot spins]
[Prompt: Telemetry dashboard dark UI with SENSORS section, encoder tick
numbers converting to inches with arrow, compass rose dial spinning to 87
degrees as small robot icon rotates, live instrument aesthetic]

## Old vs. New (6:30–7:30)

[Paradigm Shift]
(Audio) In a game, when you move a character ten units, it moves ten units —
the engine guarantees it. That guarantee is the single biggest habit you
have to unlearn. On a robot, "drive at 0.5 for 2 seconds" is a request
physics is free to edit. Nothing your code commands is guaranteed to have
happened. The only truth is what the sensors report — which is why
competitive robot code reads more than it writes.

## Gotchas (7:30–9:45)

Beats: encoders measure wheel rotation, not robot position — wheels slip,
lie detectable by cross-checking IMU (foreshadows odometry, out of scope);
reset encoders in init or ticks carry over between runs (classic "why is my
auto insane on the second run" bug); IMU axes and mounting orientation —
heading may be flipped/offset; counts-per-inch is per-robot (gear ratios!),
never copied from a tutorial. Each gotcha = one telemetry check.

## Conclusion + CTA (9:45–11:00)

(Audio) Your dashboard now shows what the drivers command AND what the robot
actually did — and the gap between those two numbers is where matches are
won. Branch has the worksheet to compute your own counts-per-inch. Next
lesson we put it all together: a state machine that parks your robot for
free points, every single match.

---

## Media Packet

### Title options (≤60 chars)
1. **Your Robot Is Driving Blindfolded (FTC Ep. 9)** [47] — RECOMMENDED
2. Encoders + IMU: Robot Senses Explained (FTC Ep. 9) [51]
3. Why Time-Based Auto Always Loses (FTC Ep. 9) [45]

### Description
```
"Drive at half power for 2 seconds" lands somewhere different every match —
battery sag guarantees it. Learn the feedback mental model (open vs closed
loop), read the two sensors every FTC robot already has (encoders + IMU),
and convert raw ticks into real inches. This is the foundation under every
autonomous that actually scores.

📦 CODE FOR THIS LESSON (sensor dashboard + counts-per-inch worksheet)
https://github.com/vibeacademy/FtcRobotController/tree/lesson-09-sensors

📚 CHAPTERS
0:00 Same mission, two robots, one winner
0:40 The blindfold: open vs closed loop
3:30 Encoders + IMU on the dashboard
6:30 Commands are wishes; sensors are truth
7:30 The 4 sensor gotchas (with telemetry checks)
9:45 Next: park every match, free points
```

### Tags
`FTC encoders, FTC IMU, closed loop control, FTC sensors tutorial, encoder
ticks to inches, FTC autonomous basics, robot feedback control, FTC gyro`

### Thumbnail concept
Robot wearing a blindfold on the left; same robot with a glowing "eye"
(compass/encoder icons) on the right; text "TAKE OFF THE BLINDFOLD."
[Prompt: YouTube thumbnail 1280x720, split image same stylized robot, left
wearing gray blindfold veering off course, right with glowing blue sensor
eye and compass icons driving true, bold white text TAKE OFF THE BLINDFOLD,
dark background high contrast]

### Pinned comment
```
📦 Sensor dashboard code + the counts-per-inch worksheet for YOUR robot:
https://github.com/vibeacademy/FtcRobotController/tree/lesson-09-sensors

Reply with your robot's counts-per-inch and drivetrain — I'll sanity-check
the math. Next lesson: the free-points parking auto.
```
