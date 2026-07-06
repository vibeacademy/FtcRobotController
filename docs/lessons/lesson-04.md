# Lesson 04 — Drive a Robot That Doesn't Exist

**Arc 1 finale: You Don't Need a Robot · Pain: P3 · Runtime target: 10 min**

## Lesson Summary

- **Assumes:** Lessons 01–03.
- **Delivers:** the arc payoff — driving a simulated robot with a gamepad in
  the virtual_robot simulator, and the sim-to-real contract: the same OpMode
  code runs in both worlds. This is the video to send anyone who says "we
  can't practice without the hub."
- **Objectives:** (1) run the virtual_robot simulator; (2) sync TeamCode into
  it and drive with a gamepad; (3) explain why the same code works in sim and
  on a field (standard hardware names + the loop).

## Branch Spec

- **Branch:** `lesson-04-simulator` (cut from `lesson-03-first-opmode`)
- **Code state:** adds a simple `opmodes/SimTeleOp.java` (tank drive on two
  sticks, telemetry) written for simulator compatibility (standard hardware
  names: `front_left_motor` etc.). `LESSON.md` documents the simulator
  install, the sync command from the repo's CLAUDE.md, and the SDK/simulator
  API deltas the team has hit.
- **Simulator dependency (pinned):** all learner-facing links clone
  `vibeacademy/virtual_robot` — our fork (pinned at `13a8bc80f501` =
  upstream `6a65ea57e90b` + three FTC-SDK-parity shims), not upstream
  `Beta8397/virtual_robot` — so published videos never break when upstream
  changes. Credit Beta8397 on camera and in descriptions (Apache-2.0).
  Upgrading the pin = re-verify lessons 04/05/08 in the sim, then push to
  the fork.

## Starter Script

> **Superseded for visuals.** The full shooting script
> (`docs/lessons/scripts/lesson-04-full-script.md`) is the single source
> of truth for every visual decision — prompts, `[Screen:]`/`[Edit:]`
> reclassifications, `[Layout:]` cues, and `[Asset:]` paths. The cues
> below are the original draft, kept for lineage; do not generate or
> film from them.

### Prerequisites
Lessons 01–03. ~30 min.

### Mental Model Shifts
1. The simulator isn't a toy or a cheat — pro robotics teams (and NASA)
   simulate first. Sim time is real practice time.
2. Code doesn't know it's in a simulator. If your OpMode only touches the
   standard hardware interface, the real robot is just one more place the
   same code runs.
3. Practice ratio flips: teams fight for robot time; sim time is unlimited.
   The software team can now out-practice everyone.

---

## Hook (0:00–0:35)

[Visual: A gamepad in hands; camera pulls back — the robot being driven is on
a laptop screen, a top-down FTC field with a robot smoothly strafing.]
[Video Prompt: Camera slowly pulls back from close-up of hands on game
controller to reveal laptop screen showing top-down robot simulator with
small robot driving smooth curves across an FTC game field, dark room blue
screen glow, cinematic reveal, tech aesthetic]

(Audio) I'm driving an FTC robot right now. It has four mecanum wheels, an
IMU, and it responds to this controller in real time. It also doesn't exist.
By the end of this video you'll be driving one too — and here's the part that
matters: the code steering this fake robot is the same code that steers a
real one. Not similar. The same.

## The Stack (0:35–1:30)

Beats: virtual_robot (community FTC simulator by Beta8397 — credit them on
camera; learners clone OUR pinned fork so the lesson never breaks), our
TeamCode from lessons 1–3, one sync command that copies team code into the
sim. That's the whole stack.

## Old vs. New (1:30–3:00)

[Paradigm Shift]
(Audio) If you play games, you're thinking: simulators are practice mode —
they don't count. Flip that. In robotics, the simulator is where professionals
do MOST of their engineering. Mars rovers drive simulated Mars for months
before touching sand. Why? Robot time is expensive and scarce; sim time is
free and infinite. Your team gets the robot two evenings a week — you now
have it 24/7.

[Visual: Two clocks — "robot time: 4 hrs/week" small and red-limited,
"sim time: unlimited" large and glowing]
[Prompt: Two stopwatch icons dark background, small red-tinted clock labeled
robot time 4 hrs per week with queue of students waiting, large glowing blue
clock labeled sim time unlimited with single relaxed student, contrast
infographic style]

## Implementation (3:00–8:00)

Beats: clone/open virtual_robot; run it; the sync command (show it, explain
it's just copying TeamCode in); pick SimTeleOp from the sim's menu — the same
menu concept as the Driver Station (callback to lesson 3); drive. Write the
two-stick tank code on camera in ~10 lines: left stick → left motors, right
stick → right motors, negate Y (callback to the aviation gotcha). Show the
hardware names (`front_left_motor`…) and land the contract: *the code asks
for motors by name; whoever answers — sim or metal — the code can't tell.*

[Visual: Same OpMode file shown center, arrows out to a simulator window on
one side and a photo-real robot on the other, "same names, same code" label]
[Prompt: Center dark code editor with Java file, two arrows to left simulator
screenshot with top-down field and to right realistic competition robot
photo, glowing label same code both worlds, symmetric composition, teaching
diagram style]

## Gotchas (8:00–9:15)

Beats: sim ≠ reality honesty (no battery sag, no wheel slip, perfect sensors
— sim proves *logic*, the field proves *physics*); hardware names must match
exactly (one typo = crash on init — real robots do this too, foreshadows
lesson 12's config maps); API deltas listed in LESSON.md.

## Conclusion + CTA (9:15–10:00)

(Audio) Arc one, complete: you understand the loop, you built a workstation,
you wrote an OpMode, and you just drove it — no robot ever touched. Show a
teammate the sim this week; that demo recruits coders better than any speech.
Next arc, we start coding the way real teams do — starting with the math that
makes mecanum wheels feel like magic.

---

## Media Packet

### Title options (≤60 chars)
1. **Drive an FTC Robot That Doesn't Exist (Ep. 4)** [47] — RECOMMENDED
2. FTC Simulator: Unlimited Robot Practice, Free (Ep. 4) [53]
3. Same Code, Fake Robot: FTC Sim Setup (Ep. 4) [45]

### Description
```
I drive a mecanum FTC robot with a gamepad, in real time — and it doesn't
exist. Set up the virtual_robot simulator, sync your TeamCode into it, and
drive code you wrote. The same OpMode runs on a real robot unchanged. Robot
time is scarce; sim time is unlimited.

📦 CODE + SIM SETUP FOR THIS LESSON
https://github.com/vibeacademy/FtcRobotController/tree/lesson-04-simulator

📚 CHAPTERS
0:00 Driving a robot that doesn't exist
0:35 The stack: virtual_robot + your TeamCode
1:30 Simulators are how pros practice
3:00 Sync, write tank drive, and DRIVE
8:00 Where sim lies to you (and why that's fine)
9:15 Arc 1 complete — what you can do now
```

### Tags
`FTC simulator, virtual robot FTC, FTC without robot, robot simulator, FTC
tank drive, FTC practice, FIRST Tech Challenge simulator, mecanum simulator`

### Thumbnail concept

> Current prompt lives in the full shooting script; the one below is the
> original draft.
Gamepad in the foreground wired (glowing cable) into a laptop showing the
top-down sim field; text "NO ROBOT. STILL DRIVING."
[Prompt: YouTube thumbnail 1280x720, game controller foreground with glowing
blue cable arcing into laptop screen showing top-down robot field simulator,
bold white text NO ROBOT STILL DRIVING, dark background, high energy youth
tech style]

### Pinned comment
```
📦 Sim setup + the tank-drive OpMode from this video:
https://github.com/vibeacademy/FtcRobotController/tree/lesson-04-simulator

Clip your best sim driving and tag the channel — best lap gets pinned next
episode. Arc 2 starts next lesson: the mecanum math real teams use.
```
