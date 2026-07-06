# Lesson 02 — Code FTC Robots With No Robot (Full Shooting Script)

**Runtime target:** ~11:00 · **Persona:** see `docs/lessons/README.md`
**Source packet:** `docs/lessons/lesson-02.md` (media packet lives there)
**Code branch:** `lesson-02-workstation` — its LESSON.md is the on-screen
checklist; keep it open in a browser tab while filming.

**Production key:** `[Visual:]`+`[Prompt:]` = generated still ·
`[Video Prompt:]` = generated motion (0 used) · `[Screen:]` = you record it
· `[Edit:]` = you build it in the editor (slide, overlay, or motion graphic)
· `[Layout:]` = Descript scene layout (default pack names) — no cue = **Camera**.

**Filming note:** the setup section (3:00–9:00) is best captured as ONE real
install on a machine (or fresh user account) that has never had Android
Studio — the honest wait times and real errors ARE the content. Do the
install once off-camera to rehearse, wipe, then record the second pass.

---

## Prerequisites

Lesson 01. A computer — Windows, Mac, or Linux, 8GB+ RAM.

## Mental Model Shifts

1. You're not installing "a robotics app" — you're assembling a toolchain:
   editor → build system → SDK, each with one job.
2. The robot is where your code ships at the end, like a console you put a
   finished game on. The robot only has to show up on practice day.
3. Big scary codebase ≠ your code. 95% is the SDK; your team lives in one
   folder.

---

## Hook (0:00–0:40)

[Layout: Media — full-bleed still]
[Visual: A calendar with "HUB ARRIVES" circled three weeks away; a student
coding happily today; a progress bar labeled "software" filling while the
calendar pages flip.]
[Prompt: Split composition dark background, wall calendar with red circled
date three weeks ahead labeled HUB ARRIVES, foreground teenager at laptop
with glowing code editor, blue progress bar labeled SOFTWARE filling to 80
percent, hopeful energetic tech style, 16:9]

(Audio) The number one thing that stalls new FTC software people is one
sentence: "we'll start coding when the robot's ready." That's backwards.
The robot is the LAST thing your code needs — and waiting for it is exactly
why teams show up to their first match with software they've never tested.

(Audio) So today we fix it permanently. We're building a robotics
workstation: by the end of this video, you will compile real competition
robot code on your own laptop, robot not included. Fair warning — this is a
setup video, the only one in the series. It's clicking and waiting. But
it's also the video that puts you ahead of half the teams in your league,
because everything after this, we write code.

[~40 seconds]

## The Stack: Editor, Kit, Builder (0:40–2:00)

[Layout: Media]
[Visual: Three stacked layers — Android Studio logo → TeamCode folder →
Gradle gear — arrow down to an APK landing on a Control Hub]
[Prompt: Vertical three-layer stack diagram dark background, IDE window icon
on top, orange folder labeled TeamCode middle, gear icon labeled Gradle
bottom, arrow flowing down to small circuit board labeled Control Hub, clean
layered architecture diagram style]

(Audio) Three tools, and each one has exactly one job. Memorize this as
"editor, kit, builder."

(Audio) The editor: Android Studio. Why an ANDROID tool for a robot?
Because the brain of an FTC robot — the Control Hub — literally runs
Android. Your robot code is, technically, an Android app. Wild, but true.

(Audio) The kit: the FTC SDK. SDK means software development kit — it's the
robot operating system that FIRST's engineers give every team for free.
Motors, sensors, gamepads, the Driver Station menu from lesson one — the
SDK is what makes all of that work so you don't have to build it. The repo
we read last lesson? Most of it IS the SDK.

(Audio) And the builder: Gradle. Gradle takes your Java code plus the SDK
and assembles the actual app that gets installed on the robot. You don't
write Gradle, you don't configure Gradle — you just learn to recognize its
two moods: syncing, and BUILD SUCCESSFUL.

[~80 seconds]

## The 3,000-File Head Fake (2:00–3:00)

[Paradigm Shift]
(Audio) Before we install anything, one paradigm shift that will save you a
week of feeling lost. If you've coded before — Scratch, a Python file at
school — you've lived in ONE file. Press run, see output. When you open
this FTC project, you're going to see about three thousand files. Here's
the shift: that's not your code. That's the kit. FIRST's engineers wrote
it, they maintain it, and you will basically never touch it. Your team's
ENTIRE season lives in one folder called TeamCode. Knowing what you're
allowed to ignore — that's the actual skill, and it's ninety-five percent
of this repo.

[Layout: Screen — dim/glow as Descript layers]
[Visual: Repo tree with everything grayed out except TeamCode glowing]
[Screen: the real project tree in Android Studio's panel, TeamCode
folder visible among the rest]
[Edit: dim everything except TeamCode, blue glow + "yours" label on it.
DO NOT generate — a fake tree means fake folder names (Rule 3); the real
panel is a ten-second capture]

[~60 seconds]

## The Setup, Live (3:00–9:00)

[Screen: LESSON.md checklist open in a browser — the five steps visible;
this stays as a corner overlay or cutaway throughout the section]

(Audio) Okay. Five steps, and I'm doing every one of them live, with the
real wait times, so you know what normal looks like. The checklist is on
the lesson branch — pinned comment — so you can follow along line by line.

[Screen: developer.android.com/studio download page → installer running]

(Audio) Step one: install Android Studio, the free official version, from
developer dot android dot com. While that installs — it takes a few minutes
— nothing to do. Normal.

[Screen: Terminal or GitHub Desktop — cloning the repo]

(Audio) Step two: get the code. This is called cloning — copying the repo
from GitHub to your machine, history and all. One command, or one click in
GitHub Desktop if you prefer buttons. Either is fine forever.

[Screen: Android Studio opening the project; the Gradle sync progress bar
with a REAL on-screen timer running — do not cut the wait, speed-ramp it]

(Audio) Step three, and this is where everyone panics: open the project and
wait out the first Gradle sync. Watch the real timer — this took me
[SAY THE REAL NUMBER] minutes. First sync downloads the entire build
toolchain, once. It is not broken. It is not your fault. Do not close it.
Every sync after this one takes seconds.

[Screen: Project tree — collapsing everything, then expanding down to
TeamCode/src/main/java/org/firstinspires/ftc/teamcode/HelloWorld.java]

(Audio) Step four: find your folder. Collapse everything, then walk down:
TeamCode, src, main, java — and there it is, HelloWorld dot java, the exact
file we read in lesson one, now on YOUR machine. Everything above TeamCode
in this tree: the kit. You now officially have permission to ignore it.

[Screen: Build → Make Project; the progress; then the green BUILD
SUCCESSFUL line — zoom in on it]

(Audio) Step five: build. This is Gradle turning the code into a robot app
— and there it is. Green. BUILD SUCCESSFUL. Stop and appreciate what that
line means: your laptop just compiled the same competition code that runs
on real FTC robots. No robot involved. That's the whole unlock.

[Layout: Screen — the payoff moment, full-bleed]
[Visual: Terminal with BUILD SUCCESSFUL in green, timer showing real
elapsed time]
[Screen: the REAL first build — terminal running to green BUILD
SUCCESSFUL with the true elapsed time on screen]
[Edit: optional confetti burst on the SUCCESSFUL line. DO NOT generate —
this is the episode's payoff moment; it must be an actual terminal, not a
faked one (Rule 3)]

[~6 minutes]

## The 3 Errors Everyone Hits (9:00–10:15)

[Screen: LESSON.md's error table on the branch, scrolling slowly]

(Audio) Now, odds are decent something went red for you instead. Three
errors cause almost all setup failures, and the fixes are in the LESSON dot
MD on the branch — link in the pinned comment. One: "SDK location not
found" — that's a missing one-line file called local dot properties, and
yes, this exact repo bit me with it too. Two: the sync that hangs forever —
usually antivirus or bad WiFi; let it finish once and it's cached for life.
Three: JDK version complaints — use the Java that Android Studio ships
with, don't install your own.

(Audio) And a mindset thing, because this matters more than any specific
fix: if your build is red, you broke NOTHING. Builds are free. That's the
entire point of having a build — it fails on your laptop so it can't fail
on the robot. When you ask for help — in the comments, from a mentor —
copy the FIRST error message in the output, not the last one. The first
error is the real one. Everything after it is dominoes.

[~75 seconds]

## Conclusion + CTA (10:15–11:00)

(Audio) Take a second. You have a machine that can build competition robot
software, and most FTC teams — genuinely most — don't have a single member
with that setup working. You didn't watch a robot video today; you became
the person on your team whose laptop matters.

(Audio) The pinned comment has this whole setup as a checklist. Send it to
one teammate this week — if they get through it, you just doubled your
software team, and software teams of one are how teams get stuck. Next
lesson: we stop reading code and start writing it. Your first OpMode, from
an empty file. See you there.

[~45 seconds]

---

## Thumbnail Concept

Laptop with green BUILD SUCCESSFUL on screen; behind it, a ghosted/outlined
robot with a "not required" stamp.
[Prompt: YouTube thumbnail 1280x720, laptop front and center with green
BUILD SUCCESSFUL terminal text clearly visible, behind it a translucent
glowing blue wireframe outline of a small cube-shaped wheeled competition
robot chassis with a red NOT REQUIRED stamp across the robot only, bold
white text "NO ROBOT NEEDED" at the top, dark background, high contrast
youth tech style, not humanoid, no human figure, no legs]
[Asset: production/ep02/E02-thumbnail.png]

---

## Shot List (screen recordings you need)

1. Android Studio download page + installer (can be trimmed heavily)
2. Clone: terminal command AND GitHub Desktop click (show both, pick one to
   linger on)
3. First Gradle sync with a real visible timer — the money shot; speed-ramp,
   don't cut
4. Project tree walk: collapse-all → down to HelloWorld.java
5. Build → Make Project → green BUILD SUCCESSFUL (zoom)
6. LESSON.md error table in browser

Generated assets: 4 stills, 0 video clips. Say the REAL sync time on camera
where marked — the honesty is the authority.
