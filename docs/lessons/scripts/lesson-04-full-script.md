# Lesson 04 — Drive a Robot That Doesn't Exist (Full Shooting Script)

**Runtime target:** ~10:00 · **Persona:** see `docs/lessons/README.md`
**Source packet:** `docs/lessons/lesson-04.md` (media packet lives there)
**Code branch:** `lesson-04-simulator` — contains `SimTeleOp.java` (tank
drive) and the LESSON.md with sim setup + the JavaFX full-JDK gotcha.

**Production key:** `[Visual:]`+`[Prompt:]` = generated still ·
`[Video Prompt:]` = generated motion (1 used) · `[Screen:]` = you record
· `[Edit:]` = you build it in the editor (slide, overlay, or motion graphic).

**Filming notes:** clone the PINNED fork (`vibeacademy/virtual_robot`) on
camera — that's the link viewers get. Credit Beta8397 verbally where marked.
Select **Mecanum Bot** in the sim before driving. Capture LOTS of driving
b-roll — it's reusable in every later lesson.

---

## Prerequisites

Lessons 01–03. About 30 minutes.

## Mental Model Shifts

1. The simulator isn't a toy or a cheat — professional robotics teams (and
   NASA) simulate first. Sim time is real practice time.
2. Code doesn't know it's in a simulator. If your OpMode only touches the
   standard hardware interface, the real robot is just one more place the
   same code runs.
3. The practice ratio flips: teams fight for robot time; sim time is
   unlimited. The software team can now out-practice everyone.

---

## Hook (0:00–0:35)

[Visual: A gamepad in hands; camera pulls back — the robot being driven is
on a laptop screen, a top-down FTC field with a robot smoothly strafing.]
[Video Prompt: Camera slowly pulls back from close-up of hands on game
controller to reveal laptop screen showing top-down robot simulator with
small robot driving smooth curves across an FTC game field, dark room blue
screen glow, cinematic reveal, tech aesthetic]

(Audio) I am driving an FTC robot right now. Four mecanum wheels, an IMU,
responding to this controller in real time... and it does not exist. By the
end of this video, you'll be driving one too. And here's the part that
actually matters: the code steering this fake robot is the same code that
steers a real one. Not similar code. The same file. That sentence is the
whole reason this series works, and today I get to prove it.

[~35 seconds]

## The Stack (0:35–1:30)

[Screen: the vibeacademy/virtual_robot GitHub page, then the sim window
open next to Android Studio]

(Audio) Two pieces today. Piece one: virtual underscore robot — a free FTC
simulator built by a community member, Beta8397. Genuine respect: this
project is one of the best things in FTC, and it's free. You'll clone it
from OUR copy — the link in the pinned comment — which is frozen at the
exact version I'm using on screen, so what you see is what you get, even
if you're watching this a year from now.

(Audio) Piece two: our TeamCode from lessons one through three. And
connecting them, one copy command that drops our code into the simulator.
Editor, kit, builder — and now, a field. That's the whole stack.

[~55 seconds]

## Simulators Are How Pros Practice (1:30–3:00)

[Paradigm Shift]
(Audio) If you play games, I know what you're thinking: simulators are
practice mode. They don't count. Flip that completely. In real robotics,
the simulator is where professionals do MOST of their engineering. The
Mars rovers? They drove simulated Mars for months before their wheels
touched sand — because rover time is priceless and sim time is free.

(Audio) Same math applies to your team, just smaller. Your team gets the
physical robot — when it exists — maybe two evenings a week, and the
builders need it too. The simulator is yours twenty-four seven. Starting
today, the software people can out-practice everyone in the building. This
isn't the toy version of robotics. This is how the pros do it.

[Visual: Two clocks — "robot time: 4 hrs/week" small and red-limited,
"sim time: unlimited" large and glowing]
[Prompt: Two stopwatch icons dark background, small red-tinted clock
labeled robot time 4 hrs per week with queue of students waiting, large
glowing blue clock labeled sim time unlimited with single relaxed student,
contrast infographic style]

[~90 seconds]

## Setup, Sync, Write, DRIVE (3:00–8:00)

[Screen: cloning the fork; opening in IntelliJ IDEA; the Main run config;
the sim window appearing with the empty field]

(Audio) Let's go. Step one: clone the simulator from the pinned link and
open it in IntelliJ IDEA — that's a second editor, free, from the same
family as Android Studio. The LESSON dot MD on the branch has the
click-by-click, including the one error that gets everybody — a Java
complaint about "javafx does not exist" — and its thirty-second fix. Run
it... and there's our field.

[Screen: the sync command in a terminal; the sim's OpMode menu now showing
"Sim TeleOp (Lesson 04)"]

(Audio) Step two: give the sim our code. One copy command — it's in
LESSON dot MD — that drops our TeamCode folder into the simulator's. Rerun
the sim, open its menu... and look at that list. Same menu idea as the
Driver Station from lesson three — and there's ours.

[Screen: Android Studio — writing SimTeleOp's core lines: hardwareMap.get
for the four motors, the direction reversals, then the loop with two stick
reads and four setPower calls]

(Audio) Step three — the code, about ten real lines, and you know almost
all of it. The new thing: hardware map dot get. This is your code asking
for a motor BY NAME — front left motor, front right motor. Remember those
names; they're about to matter. Two motors get reversed — left side's
mounted mirror-image, so "forward" means opposite spins. Then our loop:
left stick drives the left wheels, right stick drives the right wheels —
that's called tank drive, simplest drive there is. And yes: negative on
the sticks. You got your bite last lesson.

[Screen: THE PAYOFF — full-screen sim, robot driving; hands on gamepad in
a corner cam if available; at least 20 seconds of clean driving]

(Audio) Pick Sim TeleOp. INIT. START. ...We're driving. You are watching
code YOU understand, reading a controller in YOUR hand, driving a robot
that does not exist. Take the lap. You earned this one.

[Screen: split — SimTeleOp.java on one side, the sim's config screen
showing motor names on the other, highlighting "front_left_motor" in both]

(Audio) Now the sentence that makes this real practice and not a game.
Our code asks for motors by name. In the simulator, the fake motors answer
to those names. On a real robot, the real motors are REGISTERED to those
names — you'll literally type them into a config screen in lesson twelve.
The code can't tell who answered. Which means the code you write and test
here, all season, deploys to metal unchanged. Same names. Same code. Two
worlds.

[Visual: Same OpMode file shown center, arrows out to a simulator window
on one side and a photo-real robot on the other, "same names, same code"
label]
[Screen: two captures — the OpMode file in the IDE, and a real simulator
screenshot of the top-down field]
[Edit: symmetric collage — code capture center, sim screenshot one side,
robot photo the other (reuse the ep01 robot still or a real photo),
glowing "same names, same code" label. DO NOT generate — fake Java + fake
sim UI (Rule 3)]

[~5 minutes]

## Where the Sim Lies to You (8:00–9:15)

(Audio) Honesty section, because I promised no hype. The simulator lies to
you in three ways: perfect battery, perfect grip, perfect sensors. A real
robot's battery sags and it drives different in round six than round one.
Real wheels slip. Real sensors are noisy. So here's the rule that makes
sim time count: the simulator proves your LOGIC. The field proves PHYSICS.
Wrong math, backwards turns, bad button logic — the sim catches all of it
for free. What it can't catch, we handle in arc three.

(Audio) One more real-world preview: change a motor name by one letter and
rerun. Crash on init. Real robots do exactly this, and it's one of the
most common ways teams break on match day. Remember this crash — lesson
twelve turns it into a checklist item.

[~75 seconds]

## Arc 1 Complete (9:15–10:00)

(Audio) Arc one, done. Count what you have: you understand the loop. You
built a workstation. You wrote an OpMode from an empty file. And today you
drove it — and no robot was ever involved. That was the entire promise of
this arc.

(Audio) Your homework is social this time: show a teammate the sim this
week. Thirty seconds of driving recruits coders better than any speech —
it's how you stop being a software team of one. Clip your best lap and tag
the channel; best one gets pinned on the next video. Because next lesson,
arc two: we code the way real teams do, starting with the math that makes
mecanum wheels feel like a cheat code. See you there.

[~45 seconds]

---

## Thumbnail Concept

Gamepad in the foreground wired (glowing cable) into a laptop showing the
top-down sim field; text "NO ROBOT. STILL DRIVING."
[Prompt: YouTube thumbnail 1280x720, game controller foreground with
glowing blue cable arcing into laptop screen showing top-down robot field
simulator, bold white text "NO ROBOT STILL DRIVING", dark background, high
energy youth tech style]
[Asset: production/ep04/E04-thumbnail.png]

---

## Shot List (screen recordings you need)

1. Clone of vibeacademy/virtual_robot + IntelliJ open + Main run → field
   window (trim heavily; LESSON.md carries the detail)
2. Sync command in terminal; sim menu before/after showing SimTeleOp
3. Writing SimTeleOp's core lines in Android Studio (speed-ramped typing)
4. THE DRIVE: 20+ clean seconds full-screen sim + corner cam on hands
   (capture extra — series-wide b-roll)
5. Split: code + sim config screen, same motor name highlighted in both
6. The one-letter name typo → init crash (for the gotcha beat)

Generated assets: 3 stills + 1 video clip (the hook pull-back).
Verbal credit to Beta8397 happens in The Stack section — don't cut it.
