# Software Robotics — Lesson Series Plan

A progressive YouTube lesson series for the **vibeacademy** channel teaching the
software side of FTC robotics, from absolute beginner to competitive.

> **Where lesson content lives (consolidated 2026-07-06):** everything for
> lesson NN is on its own branch under `production/epNN/` — `SCRIPT.md`
> (full shooting script), `PACKET.md` (lesson + media packet), `SOCIAL.md`
> (YouTube/TikTok publish kit), thumbnail + assets, `ASSETS.md` (cue map) —
> alongside the branch's code and learner-facing `LESSON.md`. This folder
> on master keeps only series-wide docs: this plan/persona, the operator
> runbook, and the visual prompt strategy.

## Positioning

**"Software Robotics" — not robotics, not software.**

The series exists because of a specific, observed gap: most FTC teams are set
back by their software. If a team has a coder at all, that coder knows games or
websites — not control loops, not hardware abstraction, not how to be
productive without a Control Hub in hand. The operational side of robot
software is barely documented anywhere. And almost no team realizes that
software is a *scoring strategy*: reliable autonomous and well-tuned TeleOp
win matches, and they cost zero grams of robot weight.

Every lesson attacks at least one of these four pains:

| # | Pain | Series answer |
|---|------|---------------|
| P1 | "We don't have enough coders" | Zero-prerequisite on-ramp; each lesson assumes only the previous one |
| P2 | "Our coder knows games/websites, not robots" | Explicit mental-model bridges from familiar coding to control loops |
| P3 | "We can't code without the robot / hub" | Simulator, mocks, and unit tests are the *spine* of the series, not an appendix |
| P4 | "Software feels like risk, not advantage" | Every arc ends by connecting the code to match points |

**Audience — the persona every script is written for:**

A 12–18-year-old boy who's into STEM and plans to study it in college. He
games (and has maybe modded or scripted one), had some Scratch or Python at
school, and either just joined a robotics team or is deciding whether to.
He's YouTube-native: he gives a video 30 seconds to earn the next minute.

- **He wants:** to be genuinely useful to his team fast; to be "the software
  person" (identity and status, not just a task); to win matches; and —
  quietly — something real for the college application: FTC judges interview
  *students*, and an engineering portfolio beats "took a coding class."
- **He fears:** being the kid who breaks the robot; math gatekeeping ("you
  need calculus for robotics"); and being talked down to.
- **He is NOT:** a professional developer. He has never deployed a website,
  used a debugger at work, or sat in a code review. Zero workplace metaphors
  land. Games, phones, school, sports, and science fairs land.

Never condescend — he's smart, he just hasn't seen this domain. Every
technical term gets defined in-sentence the first time it appears.

**Authority goal:** the channel becomes the answer to "how do we actually do
FTC software?" — the thing a mentor links to a new student on day one.

## The Repo Link Mechanism

**Decision: one flat branch per lesson, frozen at the end-of-lesson state.**

```
lesson-01-hello-robot
lesson-02-workstation
lesson-03-first-opmode
...
lesson-12-competition-ops
```

The learner experience:

1. Watch the video on YouTube.
2. Open the **pinned comment** (also in the description): one link, straight to
   `github.com/vibeacademy/FtcRobotController/tree/lesson-NN-slug`.
3. The branch root has a **`LESSON.md`**: what this branch contains, what was
   built in the video, how to run it, and links to the previous/next lesson
   branches and videos.

Why branches beat the alternatives:

- **vs tags:** branches can be fixed (typos, SDK bumps) without republishing;
  tags are immutable and rot.
- **vs folders (`lessons/01/…`):** learners see a *real FTC project layout* —
  the same tree they'll have on their own team — not a nested teaching
  artifact. This is the point of the series.
- **vs a separate repo per lesson:** one repo accumulates stars/traffic and
  compounds the authority goal; GitHub's branch compare view
  (`compare/lesson-03...lesson-04`) becomes a free "what changed this lesson"
  diff, which is itself teachable.

Conventions:

- Branch names are flat (`lesson-01-hello-robot`, not `lesson/01`) so GitHub's
  branch dropdown sorts them in curriculum order.
- Each branch is cut from the previous lesson's branch, so the diff between
  consecutive branches is exactly "what this lesson added."
- `master` carries `LESSONS.md` — the index table (lesson → video link →
  branch link) that every LESSON.md points back to.
- If the team's working repo gets too noisy to double as a teaching repo, the
  escape hatch is a public `vibeacademy/software-robotics` repo seeded from
  these branches — the mechanism transfers unchanged. Start here; split only
  if it hurts.

## Series Map

**Arc 1 — You Don't Need a Robot (Lessons 1–4).** Kills P3 on day one. By the
end of arc 1 the learner has driven a simulated robot with a gamepad using
code they edited, without touching hardware.

1. **Robots Run a Loop** — what robot software actually is; why software wins
   matches; read your first OpMode.
2. **The Robotics Workstation** — Android Studio + FTC SDK setup; the repo
   tour; compile without a robot.
3. **Your First OpMode** — write, not just read: telemetry, init/loop
   lifecycle, gamepad input.
4. **Drive a Robot That Doesn't Exist** — the virtual_robot simulator; the
   sim-to-real contract (same code, two worlds).

**Arc 2 — Code Like a Real Team (Lessons 5–8).** The undocumented operational
core (P2). This is the arc no other channel teaches.

5. **Mecanum Math** — sticks to wheel powers; clamping and why bounded power
   is a safety rule, not a style choice.
6. **The Hardware Abstraction Layer** — interfaces / real / mock; why "works
   on my simulator" transfers to the field.
7. **Prove It Without a Robot** — JUnit + mock hardware; write a test that
   catches a real bug; the clamp test.
8. **Telemetry: See What the Robot Thinks** — debugging a machine you can't
   println; failure stories and how telemetry catches them.

**Arc 3 — Software That Wins Matches (Lessons 9–12).** Cashes in P4.

9. **Sensors and State** — encoders and the IMU; open-loop vs feedback; why
   "drive for 2 seconds" loses to "drive 24 inches."
10. **Autonomous I: Park Every Time** — state machines, timeouts,
    `opModeIsActive()`; reliable simple points beat flaky fancy points.
11. **Autonomous II: Move With Precision** — encoder-based driving and a
    first taste of proportional control.
12. **Competition Ops** — deploy to a real hub, hardware config maps, code
    freeze, the pit checklist; the software team's match-day job.

Season-agnostic on purpose: no DECODE-specific scoring, so the series stays
evergreen across seasons. Season-specific strategy videos can layer on later.

## Per-Lesson Packet

Each `lesson-NN.md` in this directory contains:

1. **Lesson summary** — audience contract (assumes/delivers), learning
   objectives, the pain (P1–P4) it attacks.
2. **Branch spec** — branch name, exact code state at freeze, LESSON.md
   contents.
3. **Starter script** — hook (full audio), section beats with `[Visual:]` +
   `[Prompt:]` cues and `[Paradigm Shift]` moments, in the channel's script
   format (see `~/.claude/skills/youtube-scriptwriter`). A starter script is
   the skeleton a full script session expands from — hook and paradigm shifts
   written out, middle sections as beats.
4. **Media packet** — title options (≤60 chars), YouTube description with
   chapters, tags, thumbnail concept + prompt, and the pinned-comment text
   with the branch link.

## Production Workflow

Fits the existing GembaFlow board:

1. One ticket per lesson: `content` label, §A names the lesson doc + branch
   to produce. Build the branch first, film against it, then freeze.
2. Branch build order = curriculum order (each branch cuts from the last).
3. Publish cadence: 1/week is sustainable solo; 2/week through preseason
   (July–August) front-loads Arc 1 before kickoff when search interest spikes.
4. After each publish: drop the video URL into `LESSONS.md`, the branch
   `LESSON.md`, and the pinned comment. `/log-session` the production notes.

## Voice Calibration (delta from the channel style guide)

The scriptwriter skill targets staff engineers; this series targets the
persona above. Keep from the skill: hook-first structure, no hype,
visuals-over-code-reading, paradigm-shift bridges, respect for the viewer's
time. Change:

- **Bridge FROM his world, never from a workplace.** Paradigm shifts anchor
  in games ("in a game loop you own the world; on a robot, physics owns it
  and your code just votes"), phones, sports, bikes, and school — never in
  REST APIs, web deploys, breakpoints-at-work, or "production."
- **Banned vocabulary → teen swap table** (scripts must use the right column):

  | Never say | Say instead |
  |---|---|
  | "in production" | "at a real match" |
  | "deploys are cheap, roll back" | "your phone updates games overnight" |
  | "breakpoints / console logs" | "pausing a game / reading a crash report" |
  | "observability" | "making the robot show its work" |
  | "expected value" | "points × how often it actually works" |
  | "an annuity / ROI" | "points every single match" |
  | "a deliverable" | "a skill / a job you own" |
  | "code review / reviewers" | "a teammate checking your work" (or FTC judges) |

- **The college/portfolio motivator is real — use it, sparingly.** One beat
  per arc, max: FTC judges interview students; this workflow is the story a
  college essay wants; "you didn't take a course, you shipped a robot."
- Shorter runtime target: **8–12 min** (attention + school schedules), hooks
  under 40 seconds.
- Concrete physical stakes beat abstractions: "this bug flips your robot,"
  not "this violates an invariant."
- Celebrate the win at the end of every lesson — each one ends with something
  the learner can *show someone* (a teammate, a mentor, a parent).
