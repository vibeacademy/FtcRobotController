# Lesson 06 — The Hardware Abstraction Layer

**Arc 2: Code Like a Real Team · Pain: P2, P3 · Runtime target: 12 min**

## Lesson Summary

- **Assumes:** Lessons 01–05.
- **Delivers:** the architecture idea that makes everything else in this
  series possible — programming against *interfaces* (IDrivetrain, IArm)
  with swappable real/mock implementations. This is the most
  "operational-knowledge-nobody-documents" lesson in the series.
- **Objectives:** (1) explain interface vs implementation with the
  wall-socket analogy; (2) navigate this repo's `hardware/interfaces`,
  `hardware/real`, `hardware/mock` layout; (3) refactor the lesson-05 OpMode
  to use `RobotHardware` + `RobotConfig` instead of raw motors.

## Branch Spec

- **Branch:** `lesson-06-hardware-abstraction` (cut from `lesson-05-mecanum`)
- **Code state:** the lesson-05 OpMode refactored onto the repo's existing
  abstraction layer (`RobotConfig.mecanumDrive(...)`, `robot.drivetrain.
  mecanumDrive(drive, strafe, turn)`); before/after kept as two files so the
  branch diff teaches. `LESSON.md` maps the three folders and links the
  before/after files.

## Starter Script

> **Superseded for visuals.** The full shooting script
> (`SCRIPT.md`, this folder) is the single source
> of truth for every visual decision — prompts, `[Screen:]`/`[Edit:]`
> reclassifications, `[Layout:]` cues, and `[Asset:]` paths. The cues
> below are the original draft, kept for lineage; do not generate or
> film from them.

### Prerequisites
Lessons 01–05. This is the series' first "architecture" lesson — flag it.

### Mental Model Shifts
1. Good robot code doesn't talk to motors — it talks to *promises about
   motors* (interfaces). Who keeps the promise is swappable.
2. "Where's the robot?" becomes a config detail, not a code rewrite: real
   hub, simulator, or mock — same OpMode.
3. Team scaling: abstraction is what lets four coders work in parallel with
   one (or zero) robots. It's a people solution wearing a code costume.

---

## Hook (0:00–0:40)

[Visual: The same TeleOp file shown three times, arrows to three targets — a
real Control Hub, the simulator, and a laptop running tests — all green
checkmarks.]
[Prompt: Dark background center code file icon with three glowing arrows to
real circuit board photo, simulator window screenshot, and terminal with
green test checkmarks, label ONE FILE THREE WORLDS, clean architecture
diagram style, 16:9]

(Audio) One file. Three worlds: a real robot, a simulator, and — this is the
new one — a plain laptop running tests with no robot and no simulator at
all. Same code, untouched. The trick behind this has a boring name —
hardware abstraction — and it is the single highest-leverage idea in this
entire series. It's also why this repo is laid out the way it is, and today
we finally read that layout.

## Concept: Program to the Socket (0:40–4:00)

Beats: wall-socket analogy — appliances are written against the *socket
shape*, not the power plant; `IDrivetrain` is a socket: "anything that plugs
in here can `mecanumDrive(drive, strafe, turn)`"; tour the repo's three
folders live: `interfaces/` (the socket shapes), `real/` (SDK-backed motors),
`mock/` (pretend hardware that keeps score). Callback: the sim in lesson 04
already exploited this idea — now we own it.

[Visual: Wall socket labeled IDrivetrain; two plugs approaching — one cable
runs to a real robot, other to a cardboard-box "mock" — both fit]
[Prompt: Stylized wall socket icon labeled IDrivetrain dark background, two
plugs converging, one cable to realistic robot, one cable to cardboard box
with googly eyes labeled MOCK, both plugs identical shape glowing green,
playful teaching illustration]

## Old vs. New (4:00–5:30)

[Paradigm Shift]
(Audio) In game scripting you grab objects directly — the player, the
enemy — because they always exist; the engine guarantees it. Robot hardware
gives you no such guarantee: the arm motor exists on Tuesdays, when the
build team hasn't borrowed it. Your instinct — talk to hardware directly —
couples your code to a machine that's literally being rebuilt weekly. The
fix: talk to the interface, and let one line of config decide what's really
on the other end. Direct hardware access is the number one habit this
series will break.

## Implementation: The Refactor (5:30–9:30)

Beats: show the "before" (raw `hardwareMap.get(DcMotor...)` — call it out as
the pattern in every rookie tutorial online); refactor live to
`RobotConfig.mecanumDrive("front_left_motor", ...)` + `RobotHardware.
createReal(...)` + `robot.drivetrain.mecanumDrive(...)`; the mecanum math
from lesson 05 now lives *inside* the drivetrain implementation — OpModes
express intent, subsystems own math; run in sim: identical behavior, better
bones. Null-check beat: optional hardware (`robot.arm != null`) because
"the arm exists on Tuesdays."

[Visual: Before/after code split — left tangled OpMode with raw motors, right
short OpMode reading like English, both compile-check green]
[Prompt: Split code comparison dark editor, left long tangled Java with red
complexity squiggles, right compact clean version with green glow, arrow
labeled REFACTOR between them, developer teaching aesthetic]

## Gotchas (9:30–11:00)

Beats: abstraction tax is real (more files, more names — worth it from the
second implementation onward, and you already have two: real + sim);
don't over-abstract (interfaces for hardware, not for everything);
hardware names now live in ONE place (config) — the lesson-04 typo-crash
gets fixed at one line, foreshadows lesson 12 config maps.

## Conclusion + CTA (11:00–12:00)

(Audio) This layout — interfaces, real, mock — is what "being productive
without the robot" actually looks like structurally. The branch diff shows
the exact before and after side by side. Next lesson we cash the biggest
check this architecture writes: proving your code works — with a test suite
— before the robot is even built.

---

## Media Packet

### Title options (≤60 chars)
1. **The Architecture Trick Every FTC Team Needs (Ep. 6)** [52] — RECOMMENDED
2. One File, Three Robots: FTC Hardware Abstraction (Ep. 6) [56]
3. Stop Talking to Motors Directly (FTC Ep. 6) [44]

### Description
```
One TeleOp file that runs on a real hub, a simulator, and a plain laptop —
unchanged. Hardware abstraction is the highest-leverage idea in FTC software:
program to interfaces, swap real/mock implementations, and your whole team
can code without fighting over one robot.

📦 CODE FOR THIS LESSON (before/after refactor in one diff)
https://github.com/vibeacademy/FtcRobotController/tree/lesson-06-hardware-abstraction

📚 CHAPTERS
0:00 One file, three worlds
0:40 Interfaces are sockets (repo tour)
4:00 Why game-dev instincts fail on hardware
5:30 The refactor, live
9:30 The abstraction tax (and when it pays)
11:00 Next: prove your code without a robot
```

### Tags
`FTC hardware abstraction, FTC code architecture, robot code interfaces, FTC
RobotHardware, FTC team programming, mock hardware, FTC software design`

### Thumbnail concept

> Current prompt lives in the full shooting script; the one below is the
> original draft.
One glowing code file with three cables out to a robot, a sim window, and a
laptop; text "1 FILE. 3 ROBOTS."
[Prompt: YouTube thumbnail 1280x720, central glowing code file icon with
three thick cables to robot photo, simulator screen, laptop with green
checks, bold white text 1 FILE 3 ROBOTS, dark background, high contrast
architecture style]

### Pinned comment
```
📦 The before/after refactor (open the branch diff — it IS the lesson):
https://github.com/vibeacademy/FtcRobotController/tree/lesson-06-hardware-abstraction

If your team's code talks to hardwareMap in every OpMode, this is the video
to show them. Next: a test suite that catches bugs before the robot exists.
```
