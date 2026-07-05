# Lesson 06 — The Architecture Trick Every FTC Team Needs (Full Shooting Script)

**Runtime target:** ~12:00 · **Persona:** see `docs/lessons/README.md`
**Source packet:** `docs/lessons/lesson-06.md` (media packet lives there)
**Code branch:** `lesson-06-hardware-abstraction` — `TeamTeleOp.java` is the
"after"; `SimTeleOp.java` stays as the "before". The branch diff vs
lesson-05 IS the lesson — show it.

**Production key:** `[Visual:]`+`[Prompt:]` = generated still ·
`[Video Prompt:]` = generated motion (0 used) · `[Screen:]` = you record.

**Filming note:** flag on camera that this is the series' first
"architecture" episode — no new robot behavior at the end, better bones
instead. Setting that expectation up front keeps the payoff honest.

---

## Prerequisites

Lessons 01–05.

## Mental Model Shifts

1. Good robot code doesn't talk to motors — it talks to *promises about
   motors* (interfaces). Who keeps the promise is swappable.
2. "Where's the robot?" becomes a config detail, not a code rewrite: real
   hub, simulator, or mock — same OpMode.
3. Abstraction is what lets four coders work in parallel with one (or zero)
   robots. It's a people solution wearing a code costume.

---

## Hook (0:00–0:40)

[Visual: The same TeleOp file shown three times, arrows to three targets —
a real Control Hub, the simulator, and a laptop running tests — all green
checkmarks.]
[Prompt: Dark background center code file icon with three glowing arrows to
real circuit board photo, simulator window screenshot, and terminal with
green test checkmarks, label ONE FILE THREE WORLDS, clean architecture
diagram style, 16:9]

(Audio) One file. Three worlds. A real robot. A simulator. And — here's
the new one — a plain laptop running automated tests, no robot, no
simulator, nothing. Same file, untouched, works in all three. The trick
behind this has the most boring name in software — "hardware abstraction"
— and it is the single highest-leverage idea in this entire series. Fair
warning: this is an architecture episode. The robot won't do anything new
today. But your CODE is about to grow up, and next episode cashes the
biggest check this idea writes.

[~40 seconds]

## Program to the Socket (0:40–4:00)

[Visual: Wall socket labeled IDrivetrain; two plugs approaching — one cable
runs to a real robot, other to a cardboard-box "mock" — both fit]
[Prompt: Stylized wall socket icon labeled IDrivetrain dark background, two
plugs converging, one cable to realistic robot, one cable to cardboard box
with googly eyes labeled MOCK, both plugs identical shape glowing green,
playful teaching illustration]

(Audio) Think about the wall socket in your room. Your phone charger was
designed against the SHAPE of that socket — two little slots, a promise of
power. The charger has no idea if the electricity comes from a solar farm
or a coal plant, and it doesn't care. The socket is a contract: anything
plugged in here gets power. Who provides it is swappable.

(Audio) In code, a socket shape is called an INTERFACE. And this repo has
one called IDrivetrain — the I is for interface — which promises: anything
plugged in here can mecanumDrive with a drive, a strafe, and a turn.
Sound familiar? That's our fifteen lines from last lesson, living behind a
socket.

[Screen: repo tree — expanding hardware/ to show interfaces/, real/, mock/;
opening IDrivetrain.java briefly, pointing at method signatures only]

(Audio) Now the repo tour we've been building toward. The hardware folder
has three rooms. Interfaces: the socket shapes — IDrivetrain, IArm, IClaw,
IIMU. Just promises, no wires. Real: implementations backed by the actual
FTC SDK — real motors answer these. And mock: pretend hardware. A mock
motor doesn't spin anything — it just remembers what you told it, so you
can check up on it later. Keep that one in mind; it's next episode's star.

(Audio) And here's the reveal: lesson four already used this trick on you.
The simulator worked because our code asked for motors by NAME through a
standard interface — and the sim answered instead of metal. Today we stop
benefiting from the idea by accident and start owning it on purpose.

[~3 minutes 20 seconds]

## The Habit This Series Will Break (4:00–5:30)

[Paradigm Shift]
(Audio) If you've scripted games, you grab objects directly — the player,
the enemy, the door. You can do that because the engine GUARANTEES they
exist. Robot hardware makes no such promise. The arm motor exists... on
Tuesdays. When the build team hasn't borrowed it for the new intake
they're prototyping. I'm not joking — mid-season, your robot is a machine
that gets rebuilt weekly, and code that talks directly to hardware is
married to whichever version of the robot existed the day it was written.
The fix: talk to the socket, and let ONE line of configuration decide
what's actually plugged in. Direct hardware access is the number one habit
this series exists to break — and today's the day.

[~90 seconds]

## The Refactor, Live (5:30–9:30)

[Screen: SimTeleOp.java (the "before") — slow scroll over the
hardwareMap.get lines, the direction setup, the mixing math]

(Audio) Here's our lesson-five code — and I want to be fair to it: it
works, and it's the pattern in basically every rookie tutorial on the
internet. Four raw motor grabs, direction setup, mixing math, all living
inside the OpMode. Now watch what it becomes.

[Screen: TeamTeleOp.java — typing/revealing the RobotConfig builder, then
createReal, then the one-line mecanumDrive call]

(Audio) The after. Three moves. Move one: the config. RobotConfig dot
mecanumDrive, with our four motor names — the SAME names from lesson four
— plus withArm, withClaw, withIMU. This object is now the single source of
truth for what the robot HAS and what everything is CALLED. Move two: one
factory call — RobotHardware dot createReal — hands us a robot object with
everything wired. And move three, my favorite: the entire drive section of
our OpMode becomes ONE line. Robot dot drivetrain dot mecanumDrive. Drive,
strafe, turn.

(Audio) Wait — where did our fifteen lines of mecanum math go? INSIDE the
drivetrain implementation, behind the socket. Written once, tested once,
and now every OpMode this team ever writes gets it for free. That's the
division of labor real teams use: OpModes express INTENT — drive this way,
open the claw. Subsystems own the MATH.

[Screen: the null-check block — if robot.arm != null — highlighted]

(Audio) One more habit while we're here: see this null check? "If the arm
isn't plugged in, skip the arm code." That's the Tuesday problem, handled.
The config said there's an arm; if reality disagrees, the OpMode shrugs
instead of crashing. Optional hardware always gets a null check — that's a
team rule in this repo, and now you know why.

[Visual: Before/after code split — left tangled OpMode with raw motors,
right short OpMode reading like English, both compile-check green]
[Prompt: Split code comparison dark editor, left long tangled Java with red
complexity squiggles, right compact clean version with green glow, arrow
labeled REFACTOR between them, developer teaching aesthetic]

[Screen: sim run of TeamTeleOp — driving identically to lesson 05]

(Audio) And in the sim... identical behavior. Nothing new to see — exactly
as promised. Same driving, better bones.

[~4 minutes]

## The Tax, and When It Pays (9:30–11:00)

(Audio) Honest section: abstraction has a tax. More files, more names, one
more hop when you're reading code. If your robot will only ever exist in
one form, the tax isn't worth it. But count your worlds: you ALREADY have
two — the sim and the eventual real robot — and next episode adds a third.
The tax pays off at implementation number two, and you're already there.

(Audio) Two boundaries so you don't over-apply this. One: interfaces for
HARDWARE, not for everything — don't go wrapping your math in sockets.
Two: notice the hardware names now live in exactly ONE place, the config.
Remember lesson four's one-letter typo crash? The fix used to mean hunting
through every OpMode. Now it's one line, one file. Hold that thought —
lesson twelve, when we meet the REAL robot's configuration screen, this
becomes the whole ballgame.

[~90 seconds]

## Conclusion + CTA (11:00–12:00)

(Audio) This layout — interfaces, real, mock — isn't tidiness. It's what
"our team codes without the robot" looks like STRUCTURALLY. Four coders,
one robot that's half-built on a Tuesday, and nobody's blocked. The branch
link in the pinned comment shows the before and after side by side as one
diff — genuinely, open it, the diff is the lesson.

(Audio) And next episode, the mock folder earns its keep: we're going to
PROVE our code works — with a test suite that runs in four seconds on a
bare laptop — including a test that catches the robot-flipping bug from
last lesson automatically. If anyone on your team still thinks software is
the risky subsystem... send them that one. See you there.

[~60 seconds]

---

## Thumbnail Concept

One glowing code file with three cables out to a robot, a sim window, and a
laptop; text "1 FILE. 3 ROBOTS."
[Prompt: YouTube thumbnail 1280x720, central glowing code file icon with
three thick cables to robot photo, simulator screen, laptop with green
checks, bold white text 1 FILE 3 ROBOTS, dark background, high contrast
architecture style]

---

## Shot List (screen recordings you need)

1. Repo tree: hardware/ → interfaces/, real/, mock/ expansion; IDrivetrain
   signatures (brief — shapes, not lines)
2. SimTeleOp "before" slow scroll
3. TeamTeleOp "after": config builder, createReal, one-line drive call
   (typed or progressive-reveal)
4. The null-check block close-up
5. Sim run of TeamTeleOp (reuse lesson-05 driving b-roll for cutaways)
6. The GitHub compare view: lesson-05...lesson-06 diff (for the CTA)

Generated assets: 4 stills, 0 video clips.
