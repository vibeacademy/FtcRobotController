# Lesson 03 — Write Your First Robot Program (Full Shooting Script)

**Runtime target:** ~11:00 · **Persona:** see `docs/lessons/README.md`
**Source packet:** `docs/lessons/lesson-03.md` (media packet lives there)
**Code branch:** `lesson-03-first-opmode` — contains the finished
`MyFirstOpMode.java`; you TYPE it on camera, don't paste it.

**Production key:** `[Visual:]`+`[Prompt:]` = generated still ·
`[Video Prompt:]` = generated motion (0 used) · `[Screen:]` = you record it
· `[Edit:]` = you build it in the editor (slide, overlay, or motion graphic).

**Filming note:** the run demos (loop counter, stick values) happen on YOUR
screen via the simulator — viewers don't have it until lesson 04, and the
script says so out loud. Their win today is typing the file and building
green. Have the sim pre-configured before recording so the demo cuts are
instant.

---

## Prerequisites

Lessons 01–02 (the mental model + a machine that builds).

## Mental Model Shifts

1. An OpMode is not "the program" — it's one program on a menu of many. A
   team ships dozens; the driver picks one.
2. Telemetry is a dashboard, not a chat log: it repaints, never scrolls.
   State, not history.
3. The loop + telemetry is a complete feedback cycle on your desk — you can
   verify controller code with zero hardware.

---

## Hook (0:00–0:35)

[Visual: Empty Java file on the left; on the right, a Driver Station phone
showing a menu with "MyFirstOpMode" in the list next to real competition
programs.]
[Screen: two captures — the empty MyFirstOpMode.java with a blinking
cursor, and the real Driver Station OpMode list (sim DS is fine) with
MyFirstOpMode in it]
[Edit: split-screen composite, glow on the menu entry. DO NOT generate —
fake DS UI garbles its own labels (Rule 3)]

(Audio) Every program that has ever driven an FTC robot — every world
championship autonomous, every teleop that hit a hundred points — started
exactly like this: an empty file and a blinking cursor. Today you write
one. Not copy. Write. By the end of this video your program will be on a
robot's menu, printing live data, and reading a game controller — and I'll
show you the two lines that confuse literally everyone the first time.
Including me.

[~35 seconds]

## The Shape of an OpMode (0:35–3:00)

[Screen: Android Studio — right-click the opmodes folder → New → Java
Class → typing "MyFirstOpMode"; then typing the class declaration and
@TeleOp annotation live, with autocomplete visible]

(Audio) In the project from last lesson, we go to our folder — TeamCode —
find opmodes, right click, new Java class: MyFirstOpMode. And we get...
basically nothing. Three lines. Good. Everything we add from here, you'll
understand.

(Audio) First line of substance: our class EXTENDS LinearOpMode. Extends
means "start from the SDK's template and fill in the blanks." The kit —
remember, the SDK — has already handled connecting to the Driver Station,
the gamepads, the menu system. LinearOpMode is a form with one blank left,
and the blank is called runOpMode: the method the SDK calls when the driver
picks your program. You don't call it. The robot does.

(Audio) And above the class, the at-sign line from lesson one — at TeleOp,
name, My First OpMode. Say it with me: this is the menu registration. No
annotation, no menu entry, your program effectively doesn't exist. This
one line is the difference between a Java file and a robot program.

[Visual: The OpMode skeleton with two callouts — @TeleOp arrow to a phone
menu entry, runOpMode arrow to a "the SDK calls this" label]
[Screen: the real OpMode skeleton in the IDE, plus the DS menu capture
from the earlier beat]
[Edit: orange arrow from @TeleOp to the menu entry, blue arrow from
runOpMode to a "the SDK calls this for you" label. DO NOT generate —
models invent fake Java (Rule 3)]

[~2 minutes 25 seconds]

## The Line That Breaks Everyone's Brain (3:00–4:30)

[Screen: typing waitForStart(); into the method, then a slow highlight on it]

[Paradigm Shift]
(Audio) Now the first confusing line: waitForStart. Your instinct — my
instinct too — says programs run when you run them. Robot programs don't.
They have a two-phase life, and it's a LEGAL two-phase life. Phase one:
INIT. The drivers pick your OpMode and press init — your code above
waitForStart runs right then, on the field, before the match... and the
robot must NOT move. That's not a convention. Move a motor during init and
your team can be penalized. Phase two: the referee says go, the driver hits
start — and waitForStart releases. Everything below that line IS the match.

(Audio) One method call, and it's literally the rules of the sport encoded
in code. When you see it, read it as: "above me is the pits, below me is
the game."

[Visual: Vertical code file split by the waitForStart line into a blue INIT
zone ("robot must not move") and green MATCH zone ("loop runs here")]
[Screen: MyFirstOpMode.java zoomed out with waitForStart() centered]
[Edit: tint above the line blue labeled "INIT — robot must not move",
below green labeled "MATCH — loop runs 50x/sec", small rulebook icon in
the corner. Same treatment as lesson 01's waitForStart beat — reuse that
project file. DO NOT generate (Rule 3)]

[~90 seconds]

## Make It Alive: Telemetry + Gamepad (4:30–9:00)

[Screen: typing the while(opModeIsActive()) loop, the loopCount variable,
and the telemetry lines — building the file up piece by piece]

(Audio) Time to prove the loop from lesson one is real. Below
waitForStart: while opModeIsActive — our loop. Inside it, a counter that
adds one every pass, and telemetry lines to show it. Telemetry, remember,
is the robot talking back to the drivers' screen. addData is "put this on
the dashboard," and update is "repaint the screen now."

[Screen: YOUR run — the sim's telemetry panel with Loop count climbing
fast; a visible on-screen label: "running on the simulator — you get this
next lesson"]

(Audio) I'm going to run mine so you can see it — I'm using a robot
simulator, which is exactly what we set up together next lesson, so don't
worry that you can't do this step yet. Your win today is the file and the
green build. And... look at that counter. That number is climbing by
about fifty every second. That's not an animation. That's YOUR loop,
asking "what now?" fifty times a second, exactly like the diagram. First
time I saw this, it genuinely changed how I thought about robots.

[Paradigm Shift]
(Audio) Notice the screen is NOT scrolling. If you've used console dot log
or print statements, you expect a history — line after line piling up.
Telemetry repaints. It's a car dashboard, not a group chat: it shows the
state of NOW, and old values are gone. Design for that and it becomes your
best debugging tool. Fight it and you'll wonder where your messages went.

[Screen: typing the gamepad line; then split view — hand on a real gamepad
below, telemetry value changing above as the stick moves]

(Audio) Last piece: input. One line — gamepad1 dot left stick y — and the
robot's controller is in your code. Plug any USB or Bluetooth gamepad into
the laptop... push the stick up... and there's the number, live. Push up.
Negative. Wait. UP is NEGATIVE? Yes. It's an old aviation convention —
pilots push the stick forward to go down — and it is a rite of passage:
every single FTC programmer gets bitten by this exactly once, ships a
robot that drives backwards, and never forgets again. You just got your
bite for free.

[Visual: Split — code loop on left; telemetry panel on right where stick
value changes as an on-screen gamepad stick wiggles]
[Screen: real recording — the loop code on the left half, live telemetry
on the right, while you slowly tilt the stick full up to full down; the
number sweeping -1.0 → 1.0 IS the shot]
[Edit: highlight the telemetry line in the code half; optional small
gamepad overlay mirroring the stick. DO NOT generate — the stick-to-number
sync is the teaching point and it records for real in the sim (Rule 3 +
order-bearing motion)]

[~4 minutes 30 seconds]

## The 4 Mistakes Everyone Makes (9:00–10:15)

[Visual: Checklist card with four items, each with a small red-X icon]
[Edit: build the four-gotcha card as a slide — four numbered rows, red X
icons, the exact labels from the narration. DO NOT generate — text that is
merely described comes back garbled (Rule 4); these words must be right]

(Audio) Four mistakes, so you make them on purpose once instead of by
accident for a week. One: forgetting telemetry dot update. Your dashboard
stays blank forever and the code is "fine." This is the number one
question on every FTC forum. Two: putting code BELOW the loop and
wondering why it never runs — the loop doesn't end until the match does.
Three: stick up is negative — you've been warned, you'll still do it.
Four: heavy work above waitForStart. Keep init fast and light; if it
takes more than about five seconds the Driver Station starts complaining.

[~75 seconds]

## Conclusion + CTA (10:15–11:00)

[Screen: the finished file, then the branch page on GitHub]

(Audio) Look at what's on your screen. A menu registration. The line where
the match starts. A live loop. Controller input. That is a complete,
legal, competition-ready robot program — and you wrote every line. The
branch in the pinned comment has my exact file with comments, but type
yours FIRST. The typos are where the learning is. Post a screenshot of
your build — or your telemetry if you jumped ahead — in the comments.

(Audio) And next lesson is the one this whole series is named for. You're
going to plug in that gamepad... and drive a robot that does not exist.
See you there.

[~45 seconds]

---

## Thumbnail Concept

A hand on a game controller in the foreground, telemetry numbers reacting
on a laptop behind; text "YOUR FIRST ROBOT PROGRAM."
[Prompt: YouTube thumbnail 1280x720, foreground hands on game controller
with tilted stick, laptop screen behind showing large telemetry number
mid-change, bold white text "YOUR FIRST ROBOT PROGRAM", dark background blue
accent lighting, energetic youth tech style]
[Asset: production/ep03/E03-thumbnail.png]

---

## Shot List (screen recordings you need)

1. New Java Class creation in Android Studio (opmodes folder)
2. Typing the skeleton: extends LinearOpMode, @TeleOp, runOpMode — real
   typing with autocomplete, lightly speed-ramped
3. Typing waitForStart + the loop + telemetry lines
4. SIM RUN: telemetry panel, loop counter climbing (pre-configure the sim;
   add the on-screen "you get this next lesson" label in edit)
5. SIM RUN: stick value tracking a real gamepad (film hand + screen)
6. The finished file, slow scroll; the lesson-03 branch page

Generated assets: 4 stills + 1 video clip. The finished on-camera file must
match `opmodes/MyFirstOpMode.java` on the branch — if you improvise a line
while filming, update the branch afterward (runbook pre-publish gate).
