# Lesson 12 — Competition Ops (Series Finale)

**Arc 3 finale: Software That Wins Matches · Pain: P2, P3, P4 · Runtime: 13 min**

## Lesson Summary

- **Assumes:** the whole series. First lesson where real hardware appears —
  and by design, it's the LAST step, proving the series' thesis.
- **Delivers:** the undocumented ops knowledge: deploying to a Control Hub,
  the hardware configuration map (where sim names meet real wires), code
  freeze discipline, and the software checklist for match day. The learner
  graduates as "the software person" a team can rely on.
- **Objectives:** (1) deploy TeamCode to a hub and map config names to
  ports; (2) run the pre-match software checklist; (3) explain code freeze
  and why the night-before rewrite loses matches.

## Branch Spec

- **Branch:** `lesson-12-competition-ops` (cut from `lesson-11-p-control`)
- **Code state:** final series state. Adds `docs/PIT-CHECKLIST.md` (printable
  pre-match software checklist) and a `CompetitionTeleOp` +
  `CompetitionAuto` pair marked as the "ship it" configuration. `LESSON.md`
  doubles as the graduation page: series recap + where to go next.

## Starter Script

### Prerequisites
The series. A Control Hub if available — but the video works as a watch-along
for hub-less viewers (that's most of them; say so warmly).

### Mental Model Shifts
1. Deploying isn't the finish line — it's a *release*. Teams that treat the
   robot as their dev environment debug in front of judges.
2. The hardware config map is a *contract*: the names your code asks for
   (lesson 06) meet real wires exactly once, in one screen. Guard it.
3. Match day software work is checklists and telemetry reads — not coding.
   If you're coding at competition, something upstream already failed.

---

## Hook (0:00–0:45)

[Visual: A pit at a real competition. One team frantically typing, cable
spaghetti, panicked faces, 4 minutes to match. Neighboring pit: laptop
closed, robot on cart, checklist on clipboard, kids relaxed.]
[Prompt: Split scene competition pit area, left team hunched over laptop
with red error screen tangled cables panic expressions and countdown timer
4 minutes, right team calm with closed laptop robot on cart printed
checklist clipboard and thumbs up, documentary photo style, dramatic
contrast, 16:9]

(Audio) Two pits, four minutes before the same match. The left team is
rewriting their autonomous — at competition — because it worked at home and
died here. The right team's laptop is closed. Same age, same league.
Everything in this series — the sim, the abstraction layer, the tests, the
telemetry, the five-for-five drill — was secretly building toward being the
right pit. Today: the robot finally shows up, and we do the operational
stuff nobody documents. Twelve lessons in, this is graduation.

## Deploy: Code Meets Metal (0:45–4:30)

Beats: what deploy actually is (Gradle builds an APK, installs onto the hub
— the "deploy target" promise from lesson 02, kept); USB first-time, WiFi
after; then THE moment — the hardware configuration map on the Driver
Station: every name your code asks for (`front_left_motor`…) gets bound to
a physical port; one typo = the crash from lesson 04, now with wires; the
lesson 06 payoff line — "your code doesn't change AT ALL; only the config
answers differently."

[Visual: Driver Station config screen; each name line connects by a drawn
wire to a motor port on a hub photo; one name glowing as it's typed to
match the code's constant]
[Prompt: Smartphone config screen UI beside real control hub photo dark
background, drawn glowing wires connecting name entries to physical ports,
one entry highlighted matching code snippet constant shown above, technical
teaching collage style]

## Old vs. New (4:30–5:30)

[Paradigm Shift]
(Audio) Web developers deploy fifty times a day — deploys are cheap, roll
back in seconds. A robot deploy is expensive: battery, field access, a
teammate's time, and if it's match day, there's no rollback — the match
happens with whatever's on the hub. So the discipline inverts: everything
that CAN be verified before deploying — logic, math, state machines —
already was, in tests and sim. Deploys are for discovering physics, never
for discovering bugs.

## Field Truth: Sim vs. Reality (5:30–8:00)

Beats: honest segment — what changes on real carpet: battery sag (your kP
feels different at 12.1V vs 13.8V), wheel slip, loose wires (the #1 real
"software bug" is a connector); the telemetry ritual from lesson 08 is now
your multimeter — question 4 ("does physics match outputs?") finally earns
its keep; re-run the 5/5 drill ON CARPET — margins from lesson 10 absorb
the sim-to-real gap.

[Visual: Same ParkAuto telemetry side by side — sim run vs field run —
values slightly different, both parking; a battery voltage readout sagging]
[Prompt: Two telemetry dashboards side by side labeled SIM and FIELD dark
background, nearly matching values with small highlighted differences,
battery voltage 13.8 vs 12.3 highlighted amber, both showing PARKED state
green, honest engineering comparison style]

## Match Day Ops (8:00–11:00)

Beats: **code freeze** — pick a date (a week out), after it only bug fixes
with a test proving the bug (the night-before-rewrite story: every mentor
has one, all end the same); **the pit checklist** (on the branch,
printable): battery voltage, right OpModes visible on DS menu, config map
matches robot, encoder reset verified via telemetry, gamepads in the right
ports; **between matches**: read telemetry from the last match BEFORE
touching anything — data first, wrenches second; **the software person's
match-day job**: run checklist, read numbers, protect the freeze — calm is
a deliverable.

[Visual: The printable checklist, checkmarks landing one by one; a red
"CODE FREEZE" banner with a date and a padlock over the repo]
[Prompt: Printed checklist on clipboard dark pit background, green
checkmarks appearing sequentially on five items, red banner CODE FREEZE
with padlock icon over repository symbol, procedural calm aesthetic]

## Graduation (11:00–13:00)

Beats: recap the arc as capability, not topics — "you can build a dev
environment, drive a simulated robot, architect for swappable hardware,
prove logic with tests, debug via telemetry, ship a reliable auto, and run
match-day software ops. That's not 'the coding kid.' That's a robotics
software engineer, junior edition"; where next: PID's I and D, odometry,
vision, and season-specific strategy (future videos); the recruitment CTA —
"the best thing you can do with this series is watch it WITH a teammate;
software teams of one are the fragility we started with" (P1, closed).

(Audio, final) Every branch, every lesson, stays up — the whole series is a
repo you can walk backward and forward, one branch per lesson, and the code
was real competition code all along. Show up to kickoff with this workflow,
and your team's bottleneck isn't software anymore. It's everyone else
keeping up with you.

---

## Media Packet

### Title options (≤60 chars)
1. **The FTC Software Ops Nobody Documents (Ep. 12 Finale)** [55] — RECOMMENDED
2. Deploy, Config, Freeze: FTC Match-Day Software (Ep. 12) [56]
3. From Zero to Robotics Software Engineer (FTC Finale) [53]

### Description
```
The series finale — and the first time real hardware appears, on purpose.
Deploy TeamCode to a Control Hub, bind your code's names to real wires in
the hardware config map, survive the sim-to-real gap (battery sag is real),
and run match day like the calm pit: code freeze, printable checklist,
telemetry-first debugging. Twelve lessons ago you'd never seen an OpMode.

📦 CODE + PRINTABLE PIT CHECKLIST
https://github.com/vibeacademy/FtcRobotController/tree/lesson-12-competition-ops

📚 CHAPTERS
0:00 Two pits, four minutes to match
0:45 Deploy: code meets metal (+ the config map)
4:30 Why robot deploys aren't web deploys
5:30 Sim vs field: what physics changes
8:00 Code freeze + the match-day checklist
11:00 Graduation: what you are now

🎓 FULL SERIES PLAYLIST + all 12 code branches linked in LESSON.md
```

### Tags
`FTC control hub deploy, FTC hardware configuration, FTC match day, FTC
competition checklist, code freeze, FTC pit crew, FTC driver station
config, FIRST Tech Challenge software`

### Thumbnail concept
Closed laptop on a pit cart next to a competition-ready robot, big green
checklist floating; text "THE CALM PIT."
[Prompt: YouTube thumbnail 1280x720, closed laptop resting on pit cart
beside polished competition robot, large floating checklist with all green
checks, bold white text THE CALM PIT, warm confident lighting on dark
background, graduation energy]

### Pinned comment
```
🎓 Series complete. The finale branch + printable pit checklist:
https://github.com/vibeacademy/FtcRobotController/tree/lesson-12-competition-ops

All 12 lesson branches are indexed in LESSONS.md — walk the whole series in
code. What should the next series cover: PID deep-dive, odometry, or
vision? Vote below. And bring a teammate — software teams of one are how
this story started.
```
