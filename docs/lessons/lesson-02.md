# Lesson 02 — The Robotics Workstation

**Arc 1: You Don't Need a Robot · Pain: P1, P3 · Runtime target: 11 min**

## Lesson Summary

- **Assumes:** Lesson 01 (knows what an OpMode and the loop are).
- **Delivers:** a working dev environment — Android Studio + the FTC SDK repo
  cloned, Gradle syncing, and a successful build — with zero hardware. This is
  the lesson that kills "we can't start until the hub arrives."
- **Objectives:** (1) install Android Studio and clone the repo; (2) explain
  what the SDK, TeamCode, and Gradle each are in one sentence; (3) build the
  project and find the green BUILD SUCCESSFUL — the lesson's win.

## Branch Spec

- **Branch:** `lesson-02-workstation` (cut from `lesson-01-hello-robot`)
- **Code state:** identical code; `LESSON.md` becomes a setup checklist
  (JDK/Android Studio versions, clone command, expected first-sync time, the
  three most common setup errors and fixes — this file is the series' most
  linkable asset for mentors).

## Starter Script

### Prerequisites
Lesson 01. A computer (Win/Mac/Linux, 8GB+ RAM).

### Mental Model Shifts
1. You're not installing "a robotics app" — you're assembling a toolchain:
   editor → build system → SDK, each with one job.
2. The robot is where your code ships at the end — like a console you put a
   finished game on. You build and test all week on your laptop; the robot
   only has to show up on practice day.
3. Big scary codebase ≠ your code. 95% of the repo is the SDK; your team lives
   in one folder: TeamCode.

---

## Hook (0:00–0:40)

[Visual: A calendar with "HUB ARRIVES" circled three weeks away; a student
coding happily today; a progress bar labeled "software" filling while the
calendar pages flip.]
[Prompt: Split composition dark background, wall calendar with red circled
date three weeks ahead labeled HUB ARRIVES, foreground teenager at laptop
with glowing code editor, blue progress bar labeled SOFTWARE filling to 80
percent, hopeful energetic tech style, 16:9]

(Audio) The number one thing that stalls new FTC software people: "we'll start
coding when the robot's ready." That's backwards — and it's why teams show up
to their first match with untested code. Today we build a robotics
workstation: by the end of this video, you will compile real competition
robot code on your laptop, robot not included. This is the last setup video —
everything after this, we write code.

## The Stack (0:40–2:00)

Beats: three tools, one sentence each — **Android Studio** (the editor,
because the Control Hub is literally an Android device), **the FTC SDK** (the
robot operating system FIRST gives every team — the repo we cloned), and
**Gradle** (the build robot that turns Java into an app). Anchor: "editor,
kit, builder."

[Visual: Three stacked layers — Android Studio logo → TeamCode folder →
Gradle gear — arrow down to an APK landing on a Control Hub]
[Prompt: Vertical three-layer stack diagram dark background, IDE window icon
on top, orange folder labeled TeamCode middle, gear icon labeled Gradle
bottom, arrow flowing down to small circuit board labeled Control Hub, clean
layered architecture diagram style]

## Old vs. New (2:00–3:00)

[Paradigm Shift]
(Audio) If you've coded before, you've probably lived in one file — press run,
see output. An FTC project is around three thousand files, and here's the
paradigm shift: that's not your code. That's the SDK — FIRST's engineers
shipping you a robot operating system. Your team's entire season lives in one
folder called TeamCode. Everything else, you never touch. Knowing what you're
*allowed to ignore* is the actual skill.

[Visual: Repo tree with everything grayed out except TeamCode glowing]
[Prompt: File tree diagram dark background, dozens of gray dimmed folder rows,
single folder TeamCode glowing bright blue with "yours" label, minimal
developer aesthetic]

## Implementation: The Setup (3:00–9:00)

Beats (checklist on screen, timestamps per step): install Android Studio;
clone `vibeacademy/FtcRobotController`; first Gradle sync (say the honest
number — several minutes, show a real timer, normalize the wait); tour the
tree — find `TeamCode`, find `HelloWorld.java` from Lesson 01; hit build.
Gotchas called out live: wrong JDK version, antivirus slowing sync, the
"SDK location not found" error and `local.properties` (this exact repo's
gotcha — real experience, real credibility).

[Visual: Terminal with BUILD SUCCESSFUL in green, timer showing real elapsed
time]
[Prompt: Dark terminal window screenshot style, green text BUILD SUCCESSFUL
in 2m 14s, subtle confetti particles around the text, developer celebration
aesthetic]

## Gotchas (9:00–10:15)

Beats: the three errors every beginner hits (collected in LESSON.md on the
branch — say so, drive the click); "if your build is red, you broke nothing —
builds are free, that's the point"; how to ask for help with an error message
(copy the *first* error, not the last).

## Conclusion + CTA (10:15–11:00)

(Audio) You now have what most FTC teams don't: a place to write robot code
that doesn't need the robot. Pinned comment has this exact setup as a
checklist — send it to a teammate and you've just doubled your software team.
Next lesson: we stop reading code and start writing it.

---

## Media Packet

### Title options (≤60 chars)
1. **Code FTC Robots With No Robot — Full Setup (Ep. 2)** [51] — RECOMMENDED
2. Your FTC Coding Setup, Start to Green Build (Ep. 2) [51]
3. Stop Waiting for the Hub: FTC Dev Setup (Ep. 2) [47]

### Description
```
"We'll start coding when the robot's built" is why teams compete with
untested software. In this lesson you build a complete FTC dev environment
and compile real competition code — no robot, no Control Hub, one laptop.

📦 CODE + SETUP CHECKLIST FOR THIS LESSON
https://github.com/vibeacademy/FtcRobotController/tree/lesson-02-workstation

📚 CHAPTERS
0:00 Stop waiting for the robot
0:40 The stack: editor, kit, builder
2:00 3,000 files — and why you ignore 95% of them
3:00 Full setup, live (with the real wait times)
9:00 The 3 errors everyone hits
10:15 You're ahead of most teams now
```

### Tags
`FTC programming setup, android studio FTC, FTC SDK install, FIRST Tech
Challenge programming, FTC TeamCode, gradle FTC, robot programming setup,
FTC no robot, FTC beginner`

### Thumbnail concept
Laptop with green BUILD SUCCESSFUL on screen; behind it, a ghosted/outlined
robot with a "not required" stamp.
[Prompt: YouTube thumbnail 1280x720, laptop front and center with green BUILD
SUCCESSFUL terminal text, translucent wireframe robot outline behind with red
NOT REQUIRED stamp, bold white text "NO ROBOT NEEDED", dark background, high
contrast youth tech style]

### Pinned comment
```
📦 Setup checklist + the code (LESSON.md has the 3 most common errors and
fixes):
https://github.com/vibeacademy/FtcRobotController/tree/lesson-02-workstation

Stuck on setup? Post the FIRST error message in your build output below.
Next lesson: you write your first OpMode.
```
