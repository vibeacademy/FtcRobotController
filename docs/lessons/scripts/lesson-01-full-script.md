# Lesson 01 — Robot Code Isn't What You Think (Full Shooting Script)

**Runtime target:** ~9:00 · **Persona:** see `docs/lessons/README.md`
**Source packet:** `docs/lessons/lesson-01.md` (media packet lives there)
**Code branch:** `lesson-01-hello-robot` — check it out before screen-recording;
the walkthrough matches that branch's `HelloWorld.java` exactly.

**Production key:** `[Visual: …]` + `[Prompt: …]` = generated still.
`[Video Prompt: …]` = generated motion clip. `[Screen: …]` = you record it
(IDE/browser capture, no generation). `[Edit: …]` = composite you build in
the editor over real captures. `[Asset: …]` = the finished file — all
finals live in `production/ep01/` **on the `lesson-01-hello-robot` branch**
(see its `ASSETS.md` for the full cue map).

---

## Prerequisites

None. This is the front door of the series.

## Mental Model Shifts

1. Game code owns the world; robot code *votes* on a world physics owns.
2. Robot programs aren't scripts that run once — they're loops that run ~50
   times a second until someone stops them.
3. Software is a scoring subsystem, not tech support for the builders.

---

## Hook (0:00–0:40)

[Visual: Two FTC robots side by side at a match start. Left robot sits dead
during autonomous. Right robot drives off, scores, parks. Scoreboard ticks up
only on the right.]
[Prompt: Split screen photo inside a school gymnasium robotics competition,
two small cube-shaped student competition robots built from silver aluminum
extrusion rails with four mecanum wheels and visible colorful wiring,
sitting on light gray foam mat field tiles, left robot motionless and
desaturated with a large red X overlay, right robot identical but driving
forward with a glowing blue motion trail, a digital scoreboard above each
robot showing "0" on the left and "25" on the right, documentary sports
photography, dramatic gym lighting, 16:9, not humanoid, no legs, no sci-fi
armor]
[Asset: production/ep01/E01-01-hook-dead-vs-scoring.png]

(Audio) These two robots cost the same. They weigh the same. They were built
by teams the same age as you. And one of them just scored twenty-five points
before any human touched a controller. The difference isn't the metal. It's
about two hundred lines of code.

(Audio) This series is about those lines. You don't need a robot. You don't
need to know anything about robotics. And you definitely don't need to
already be "the coding kid." By the end of this video — today — you're going
to read real competition robot code and understand every important line of
it. Let's go.

[~40 seconds]

## What FTC Is, in 90 Seconds (0:40–2:10)

[Visual: Match timeline bar — AUTO 0:30 glowing (100% software), TELEOP
2:00, ENDGAME 0:30 — robot icon above the AUTO segment]
[Prompt: Minimal flat infographic on a plain dark background, one single
horizontal bar divided into exactly three segments in one row, first
segment glowing bright blue labeled "AUTO 0:30", second segment neutral
gray labeled "TELEOP 2:00", third segment warm amber labeled "ENDGAME
0:30", one small white robot icon above the first segment only, absolutely
no other text numbers icons or decorations, clean flat vector style]
[Asset: production/ep01/E01-02-match-timeline.png]

(Audio) Quick version, for anyone brand new. FTC — FIRST Tech Challenge — is
a robotics league where teams of students build and program a robot to play
a game. A match is two and a half minutes, in three phases.

(Audio) Phase one: autonomous. Thirty seconds where NO human is allowed to
touch the controls. The robot is on its own. Whatever it does in those
thirty seconds — that's one hundred percent software.

(Audio) Phase two: TeleOp. Two minutes of driver control. Feels like a video
game — except good code is quietly helping the driver the whole time. And
phase three, endgame: special scoring in the final thirty seconds, usually
worth a lot.

[Visual: Three subsystem icons — CAD wrench (build), battery/wires
(hardware), code brackets (software) — with the code icon glowing and a
price tag reading "$0"]
[Prompt: Three icons in a row on dark background, wrench icon and battery
icon in muted gray, code brackets icon glowing bright blue with a zero
dollar price tag attached, minimal infographic style]
[Asset: production/ep01/E01-03-zero-dollar-advantage.png]

(Audio) Now here's the thing I noticed competing in this league, and it's
the reason this series exists. Almost every team has builders. Almost no
team has enough coders — and when they do, that coder learned on games or
websites, not robots. Which means software is where most teams are weakest.
And that makes it the cheapest advantage available: better code costs zero
dollars and adds zero grams to the robot. You just have to know how to
write it.

[~90 seconds]

## Robot Code Runs a Loop (2:10–4:30)

(Audio) So let's get the single most important idea in robot software into
your head. Everything else in this series builds on it.

[Paradigm Shift]
(Audio) If you've written any code before — a school assignment, a Scratch
project, a game mod — your program probably ran top to bottom and finished.
Do the thing, print the answer, done. Robot code never finishes. A robot
program is a loop, and it runs about fifty times every single second: read
the controllers, decide what to do, command the motors. Then again. And
again. Fifty times a second, until the match ends.

[Visual: Animated loop — gamepad icon → "decide" brain node → motor icons →
back to gamepad, cycling continuously with a 20ms timer]
[Edit: build this as a motion graphic in the editor — three nodes in a
ring (gamepad left, chip top, motors right), one glowing blue pulse
cycling gamepad → chip → motors → back to gamepad, single-headed arrows
only, and a "20 ms" counter that ticks once per revolution as a clean
text overlay. DO NOT generate — Veo missed this twice (v1: direction
muddled; v2: causal order reversed, double-headed arrows, garbled timer;
clip archived in content-marketing assets-v3). The pulse ORDER is the
teaching point: read, decide, command. Export as
production/ep01/E01-04-control-loop.mp4]

(Audio) Which means your code doesn't really DO things. Your code gets
asked a question, over and over: "what should the motors do RIGHT NOW?" And
it has about twenty milliseconds to answer before it gets asked again.

(Audio) Here's why that's different from a game, and this took me a while
to really get. In a game, when you move a character, the game engine
guarantees it moves. You own that world. On a robot, you command a motor
and then... physics gets a vote. The battery is sagging. The wheel is
slipping on a scuffed tile. Another robot is pushing back. Your code just
votes on what should happen. Physics decides what actually does.

[Visual: Split image — left: game character mid-jump with "ENGINE
GUARANTEES IT" label; right: robot wheel slipping on field tile with
physics force arrows and "PHYSICS VOTES" label]
[Prompt: Split screen dark background, left side pixel-art game character
jumping with clean green trajectory arc labeled engine guarantees it, right
side realistic robot wheel on gray foam tile with red slip arrows and
friction symbols labeled physics votes, contrast illustration style]
[Asset: production/ep01/E01-05-engine-vs-physics.png]

(Audio) That's mental model number one, and honestly, half of robot
programming is just respecting it. The loop asks, you answer, physics
decides. Keep that in your head — because now we're going to go look at
real code, and you're going to see the loop sitting right there.

[~2 minutes 20 seconds]

## Reading Real Robot Code (4:30–7:30)

[Screen: Browser — the pinned-comment link opening
github.com/vibeacademy/FtcRobotController/tree/lesson-01-hello-robot, then
navigating to TeamCode/src/main/java/org/firstinspires/ftc/teamcode/HelloWorld.java]

(Audio) This is a real FTC code repository — the link is in the pinned
comment, and it's the exact repo this entire series builds on. We're
opening one file: HelloWorld dot java. And I'll be straight with you: the
first time you see this file, it looks like a lot. Let's make it look like
a little.

[Screen: HelloWorld.java top — the 28-line FIRST copyright comment block,
then scrolling to the imports]

(Audio) First, this giant wall of text at the top? It's a copyright
license. Every FTC file ships with it. You will never read it, you will
never write one, you get to scroll straight past it. Same with these
"import" lines — that's Java pulling in the tools this file uses. Skim,
don't study.

[Screen: The @TeleOp annotation line and class declaration, highlighted]

(Audio) Now the real stuff, and there are only three things you need to
find. Thing one: this line that starts with an at-sign. "At TeleOp, name
equals Hello World Op Mode." This is the menu registration. On the phone
the drivers hold — it's called the Driver Station — there's a menu of
programs, and the driver picks one before each match. This line is how your
program gets ON that menu. A program is called an OpMode in FTC — literally
"operation mode" — and a team ships dozens of them. This annotation is your
program's name tag.

[Visual: Overlay diagram — the @TeleOp code line with an arrow to the real
Driver Station menu where "Hello World Op Mode" is highlighted]
[Screen: two captures — close-up of the @TeleOp line in the IDE, and the
Driver Station OpMode menu (sim or phone) showing "Hello World Op Mode"]
[Edit: composite the two captures side by side, draw an orange arrow from
the annotation line to the highlighted menu entry. DO NOT generate this —
models invent fake code and garble UI text (VISUAL-PROMPT-STRATEGY Rule 3).
Export the finished composite as production/ep01/E01-06-teleop-menu-overlay.png]

[Screen: waitForStart() line, highlighted, with the code above it tinted
blue and the code below tinted green]

(Audio) Thing two: this line. "waitForStart." This one confuses everybody,
so let's nail it. A match has two phases for your code. Before the match:
the drivers pick your OpMode and press INIT — the robot is awake, but it is
NOT allowed to move. That's a rule of the sport, not a style choice.
Everything ABOVE waitForStart happens in that frozen phase. Then the
referee says go, the driver presses start — and everything BELOW this line
is the actual match. One line of code, and it's literally where the match
begins.

[Screen: The while(opModeIsActive()) block, highlighted; slow zoom]

(Audio) And thing three — there it is. "While opModeIsActive." That's the
loop. The one from the diagram. This block runs about fifty times a second,
top to bottom, until the match ends or someone hits stop. Everything a
robot ever does during a match, some loop like this one is doing it.

(Audio) Inside our loop there are just two kinds of lines. The telemetry
lines — telemetry is the robot talking back, putting live text on the
drivers' screen. This one just says "Running," over and over, fifty times a
second. And these "Log" lines — that's the robot writing notes to a logbook
you can read after the fact. Live dashboard, and a diary. We'll use both
for real in later lessons.

[Visual: HelloWorld.java shrunk to fit one screen with three regions
color-boxed — INIT (blue), THE LOOP (green), TELEMETRY (yellow) — loop
diagram inset in corner]
[Screen: real IDE capture of HelloWorld.java zoomed out so the whole file
fits one screen]
[Edit: draw three translucent color boxes over the real capture — INIT
(blue), THE LOOP (green), TELEMETRY (yellow) — inset the loop diagram
bottom-right in matching colors. DO NOT generate this — models invent fake
Java (VISUAL-PROMPT-STRATEGY Rule 3). Export the finished composite as
production/ep01/E01-07-file-map-overlay.png]

(Audio) Step back and look at what just happened. A name tag for the menu.
A line where the match starts. A loop that answers "what now?" fifty times
a second. That's the whole skeleton — and every OpMode on every FTC robot
on earth, including the ones that win world championships, has exactly this
shape. You just read real robot code.

[~3 minutes]

## Why Software Wins (7:30–8:30)

[Visual: Scoreboard-style graphic with three stacked claims — "AUTO: 25+
pts, software alone" / "DRIVER ASSIST: average driver → good driver" /
"RELIABILITY: scores in round 6, not just round 1" — each with a point icon]
[Prompt: Flat dark sports-broadcast lower-third graphic, exactly three
stacked horizontal stat bars with bold condensed white text, first bar
glowing blue reading "AUTO: 25+ PTS, SOFTWARE ALONE", second bar reading
"DRIVER ASSIST: AVERAGE BECOMES GOOD", third bar reading "RELIABILITY:
SCORES IN ROUND 6", a small glowing point icon at the left of each bar,
absolutely no other text or numbers, clean broadcast graphics style, 16:9]
[Asset: production/ep01/E01-08-why-software-wins.png]

(Audio) So why should YOU be the software person? Three concrete reasons.
One: autonomous is twenty-five plus points, every single match, scored by
software alone — the builders cannot touch that number. Two: driver assist.
Code that gives the driver a slow-mode button, or moves the arm to preset
positions automatically, turns an average driver into a good one. That's
software too. And three — the sneaky one — reliability. The clamps and
timeouts and checks you'll learn in this series are the reason the same
robot still scores in round six, when everyone's battery is tired, not just
in round one.

(Audio) Software is the only part of the robot that gets better without
adding weight, without costing money, and without waiting for parts to
ship. It just takes someone who decided to learn it. That's you, now.

[~60 seconds]

## Conclusion + Homework (8:30–9:00)

[Screen: The lesson-01 branch page on GitHub, cursor hovering LESSON.md]

(Audio) Here's your homework, and it's genuinely ten minutes. The link in
the pinned comment goes to the exact repo we just read. Open the file
called LESSON dot MD — it's a short guide — then open HelloWorld dot java
and find the three things yourself: the name tag, the starting line, and
the loop. When you find all three, you've done something most people on FTC
teams never actually do: read the code.

(Audio) Next lesson, we set up your computer to BUILD robot code — no robot
required, and I'll show you exactly what to click. Subscribe so it's there
when you're ready. See you in lesson two.

[~30 seconds]

---

## Thumbnail Concept

Split image: dead gray robot on the left, glowing robot with motion trail on
the right; overlay text "200 LINES OF CODE" between them.
[Prompt: YouTube thumbnail 1280x720 split composition, two identical small
cube-shaped student competition robots built from silver aluminum extrusion
rails with four mecanum wheels and visible wiring on light gray foam tile
field, left half desaturated gray and motionless, right half vivid with
bright blue glowing motion trail behind the robot, giant bold white text
with black outline "200 LINES" centered across the split, high contrast
dark background, energetic sports poster style, not humanoid, no legs, no
sci-fi armor]
[Asset: production/ep01/E01-09-thumbnail.png]

---

## Shot List (screen recordings you need)

1. Browser: pinned-comment link → lesson-01 branch → HelloWorld.java
   (one continuous take, slow scrolling)
2. HelloWorld.java: license block scroll-past; @TeleOp line; waitForStart
   line; the while loop (separate close-up takes, cursor highlighting)
3. Browser: branch page hovering LESSON.md (for the homework beat)
4. Driver Station OpMode menu (sim or phone) showing "Hello World Op Mode"
   — feeds the E01-06 overlay composite
5. HelloWorld.java zoomed out, whole file on one screen — feeds the E01-07
   overlay composite

**Assets:** all finals in `production/ep01/` on the `lesson-01-hello-robot`
branch — see its `ASSETS.md` for the cue map. 5 generated stills +
thumbnail are ✅ committed; 3 edit-time builds — the E01-04 motion graphic
(nothing to generate) and the E01-06/07 composites from shots 2/4/5 above.
Metadata, tags, description, and pinned comment: see the media packet in
`docs/lessons/lesson-01.md` — chapters match this script's section times.
