# Lesson 05 — Mecanum Math

**Arc 2: Code Like a Real Team · Pain: P2, P4 · Runtime target: 12 min**

## Lesson Summary

- **Assumes:** Arc 1 (can write an OpMode and drive in sim).
- **Delivers:** the signature FTC skill — turning two joysticks into four
  mecanum wheel powers — plus the first *safety-as-code* lesson: clamping
  power to [-1, 1] and why unbounded power flips robots.
- **Objectives:** (1) explain how angled rollers let a robot strafe;
  (2) implement `drive + strafe + turn` mixing for four wheels; (3) clamp
  and normalize wheel powers, and explain what breaks without it.

## Branch Spec

- **Branch:** `lesson-05-mecanum` (cut from `lesson-04-simulator`)
- **Code state:** `SimTeleOp` upgraded to full mecanum drive with explicit
  mixing math and clamping, heavily commented. `LESSON.md` includes the
  mixing table and two challenge exercises (slow-mode button; field-centric
  teaser).

## Starter Script

> **Superseded for visuals.** The full shooting script
> (`SCRIPT.md`, this folder) is the single source
> of truth for every visual decision — prompts, `[Screen:]`/`[Edit:]`
> reclassifications, `[Layout:]` cues, and `[Asset:]` paths. The cues
> below are the original draft, kept for lineage; do not generate or
> film from them.

### Prerequisites
Arc 1. The sim from lesson 04 (all demos run in it).

### Mental Model Shifts
1. A mecanum robot isn't driven like a car — it's a *vector* machine: any
   direction, any rotation, simultaneously.
2. Each wheel gets a *recipe*, not a command: drive ± strafe ± turn. Four
   recipes, one motion.
3. Motor power isn't a suggestion — outside [-1, 1] the SDK clips silently
   and your robot's motion *distorts*. Bounding is correctness, not politeness.

---

## Hook (0:00–0:40)

[Visual: Sim robot drives a perfect diagonal while spinning slowly —
impossible for a car — then a real mecanum wheel close-up with its angled
rollers.]
[Video Prompt: Top-down robot simulator dark field, square robot slides
diagonally across field while rotating smoothly at same time leaving glowing
trail, cut to close-up render of mecanum wheel with 45-degree angled silver
rollers spinning, smooth motion, tech aesthetic]

(Audio) That robot is driving diagonally and spinning at the same time. No
car can do that. The secret is half hardware — those weird angled rollers —
and half software: about fifteen lines of math that every competitive FTC
team on earth has some version of. Today you write those lines, understand
them for real, and learn the one-line mistake in this code that flips robots.

## Concept: How Angled Rollers Cheat (0:40–3:30)

Beats: one wheel spins → force comes out at 45°; four wheels' diagonal forces
are four arrows; choose wheel directions so arrows cancel/add → net motion
any direction. No trig needed at this level — arrows and addition.

[Visual: Top-down robot diagram, four force arrows at 45° from each wheel;
arrows combine into one big sideways arrow as the "strafe" case]
[Prompt: Top-down schematic of square robot dark background, four diagonal
orange force arrows at each wheel corner, arrows visually summing into single
large blue arrow pointing right labeled STRAFE, vector addition teaching
diagram, clean physics style]

## Implementation: The Mixing Lines (3:30–8:00)

Beats: three intents from the sticks — drive, strafe, turn; the four recipes
(`fl = drive + strafe + turn`, `fr = drive − strafe − turn`, …) shown as a
color-coded table, not read aloud; type it into SimTeleOp; drive the sim —
the "whoa it strafes" moment on camera.

[Visual: 4×3 color grid — rows are wheels, columns are drive/strafe/turn,
each cell + or − , matching colored signs in the code beside it]
[Prompt: Matrix table dark background, four rows labeled FL FR BL BR, three
columns labeled DRIVE STRAFE TURN in blue orange yellow, plus and minus signs
in cells, adjacent code snippet with same colors highlighted, clean teaching
infographic]

## The Safety Lesson: Clamp It (8:00–10:30)

[Paradigm Shift]
(Audio) Push drive and strafe and turn at once and the math says front-left
gets 2.4. In a game engine, fine — bigger number, faster sprite. On a robot,
motors max at 1.0, and the SDK just *clips* silently. Here's the trap: it
clips each wheel differently, so the *ratios* between wheels — the thing that
made your motion straight — get destroyed. The robot lurches, curves, tips.
Your instinct from games — "the engine will handle it" — is exactly wrong.
On robots, YOUR code is the engine.

Beats: show distorted vs normalized motion in sim side-by-side; implement
normalize-by-max (divide all four by the largest magnitude when >1); note
this exact rule is enforced in this repo's CLAUDE.md and tested in its test
suite — real teams encode safety as code (seed for lesson 07).

[Visual: Two sim runs side by side — "clipped" robot curving off path,
"normalized" robot driving true, both from same stick input]
[Video Prompt: Split screen top-down simulator dark field, both robots
receive identical glowing stick input overlay, left robot labeled CLIPPED
drifts off course curving badly, right robot labeled NORMALIZED drives
perfectly straight diagonal, synchronized animation, teaching comparison
style]

## Gotchas (10:30–11:30)

Beats: a reversed motor makes the robot spin in place (how to diagnose in 10
seconds: one wheel test); stick deadzones (tiny drift → robot creeps);
which mixing signs go with which wheel depends on motor mounting — trust the
one-wheel test, not the internet's table.

## Conclusion + CTA (11:30–12:00)

(Audio) You just wrote the drive code most rookie teams copy without
understanding — and you can now debug it, which they can't. The branch has
the commented version plus two challenges; the slow-mode button is twenty
minutes and your future drivers will love you. Next lesson: the architecture
trick that lets your whole team code without fighting over the robot.

---

## Media Packet

### Title options (≤60 chars)
1. **The 15 Lines Behind Every Good FTC Drivetrain (Ep. 5)** [55] — RECOMMENDED
2. Mecanum Drive Explained For Real (FTC Ep. 5) [45]
3. This One-Line Bug Flips FTC Robots (Ep. 5) [42]

### Description
```
Diagonal driving while spinning — no car can do it, every good FTC robot can.
Learn the mecanum mixing math for real (arrows, not trig), write it, drive it
in the simulator, and fix the silent clipping bug that distorts motion and
flips robots.

📦 CODE FOR THIS LESSON (commented + 2 challenges)
https://github.com/vibeacademy/FtcRobotController/tree/lesson-05-mecanum

📚 CHAPTERS
0:00 A robot no car can imitate
0:40 How angled rollers cheat physics
3:30 The mixing lines, written live
8:00 The clamp: why unbounded power flips robots
10:30 Diagnosing a reversed motor in 10 seconds
11:30 Challenges: slow mode + field-centric teaser
```

### Tags
`mecanum drive FTC, mecanum wheel programming, FTC drivetrain code, mecanum
math, FTC TeleOp tutorial, strafe FTC, motor power clamp, FTC java`

### Thumbnail concept

> Current prompt lives in the full shooting script; the one below is the
> original draft.
Top-down robot with four 45° force arrows visibly combining into a big
sideways arrow; text "THE MECANUM MATH."
[Prompt: YouTube thumbnail 1280x720, top-down stylized robot with four
glowing orange diagonal arrows merging into one large blue horizontal arrow,
bold white text THE MECANUM MATH, dark background high contrast, physics
diagram energy]

### Pinned comment
```
📦 The commented mecanum code + 2 challenges (slow-mode button = 20 min,
do it):
https://github.com/vibeacademy/FtcRobotController/tree/lesson-05-mecanum

Robot spinning in place instead of strafing? That's a reversed motor — the
10-second test is at 10:30. Next lesson: how real teams code without the
robot at all.
```
