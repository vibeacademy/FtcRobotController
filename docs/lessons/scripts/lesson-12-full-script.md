# Lesson 12 — The FTC Software Ops Nobody Documents (Series Finale — Full Shooting Script)

**Runtime target:** ~13:00 · **Persona:** see `docs/lessons/README.md`
**Source packet:** `docs/lessons/lesson-12.md` (media packet lives there)
**Code branch:** `lesson-12-competition-ops` — `CompetitionTeleOp` +
`CompetitionAuto` (the ship-it pair), `docs/PIT-CHECKLIST.md` (printable),
LESSON.md as the graduation page.

**Production key:** `[Visual:]`+`[Prompt:]` = generated still ·
`[Video Prompt:]` = generated motion (0 used — this episode is real
hardware footage) · `[Screen/Camera:]` = you record
· `[Edit:]` = you build it in the editor (slide, overlay, or motion graphic).

**Filming notes:** this is the one episode with a HARDWARE dependency — a
Control Hub, a Driver Station device, and ideally a rolling chassis. Say
warmly, early, that most viewers won't have a hub yet and that's fine —
this episode is a watch-along that pays off when their team's hub arrives.
Film every hub/config/deploy shot generously; this footage is unique in
the series.

---

## Prerequisites

The series. A Control Hub if you have one — and it's completely fine if
you don't (said on camera, warmly, in the first minute).

## Mental Model Shifts

1. Deploying isn't the finish line — it's a *release*. Teams that treat the
   robot as their dev environment debug in front of judges.
2. The hardware config map is a *contract*: the names your code asks for
   meet real wires exactly once, in one screen. Guard it.
3. Match-day software work is checklists and telemetry reads — not coding.
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

(Audio) Two pits. Four minutes before the same match. The left team is
rewriting their autonomous — AT the competition — because it worked at
home and died here. The right team's laptop is closed. Their robot's on
the cart. They're... chatting. Same age. Same league. Same parts.

(Audio) Every single thing in this series — the simulator, the abstraction
layer, the tests, the telemetry, the five-for-five drill — was secretly
building toward one outcome: being the right-hand pit. Today the robot
finally shows up. Twelve lessons in — and if you don't have a Control Hub
yet, stay: this is the episode you'll rewatch the week yours arrives. This
is graduation.

[~45 seconds]

## Deploy: Code Meets Metal (0:45–4:30)

[Screen/Camera: a real Control Hub on the desk; USB cable to laptop;
Android Studio deploy — the run button, the APK install progress, the hub
chirp/LED]

(Audio) Here it is — a Control Hub. The robot's brain, and as promised in
lesson two, it's literally an Android device. Which means "putting code on
the robot" is exactly what your laptop has been rehearsing all series:
Gradle builds an app, and installs it — first time over this USB cable,
after that over WiFi. Watch... building... installing... and that chirp is
our code, living on a robot for the first time. Lesson two made you a
promise: the robot is where code SHIPS, and it only has to show up on
practice day. Promise kept.

[Screen/Camera: the Driver Station — creating/opening the hardware
configuration; typing "front_left_motor" and binding it to a port; walking
all four motors]

(Audio) But the deploy isn't the moment that matters. THIS is. The Driver
Station's hardware configuration map. Remember — all the way back to
lesson four — our code asks for motors BY NAME: front left motor. The
simulator answered to those names for eight episodes. Now watch me bind
that exact name to a physical port — motor zero, this wire, THIS motor.
One screen. Every name our code asks for, meeting real copper, exactly
once.

[Visual: Driver Station config screen; each name line connects by a drawn
wire to a motor port on a hub photo; one name glowing as it's typed to
match the code's constant]
[Screen: the real Driver Station configuration screen, and a photo of
the actual Control Hub with its ports visible]
[Edit: collage — draw glowing wires from each config name entry to its
physical port; highlight the one entry as it's typed to match the code
constant (real code capture above). DO NOT generate — fake DS config UI
is exactly the Rule 3 failure mode]

(Audio) And now the payoff line of the entire series. To move from
simulator to metal, our code changed... not at all. Zero lines. Only the
config answers differently. That's the abstraction layer from lesson six
cashing its biggest check. And the flip side: one typo in this screen is
the lesson-four crash, with wires — which is why the config map is a
CONTRACT. It gets guarded, it gets checked before every match, and it
never gets edited casually.

[~3 minutes 45 seconds]

## Robot Deploys Aren't Phone Updates (4:30–5:30)

[Paradigm Shift]
(Audio) When a game on your phone breaks, the studio ships a fix
overnight, and by lunch nobody remembers. So your instinct says deploys
are cheap — push, test, push again. On a robot, a deploy costs a charged
battery, field access, a teammate's time — and on match day there is NO
overnight fix: the match happens with whatever's on the hub. So the
discipline inverts completely. Everything that CAN be verified before
deploying — logic, math, state machines — already was: in tests, in the
sim. Say it with me: deploys are for discovering PHYSICS. Never for
discovering bugs.

[~60 seconds]

## Field Truth: What Carpet Changes (5:30–8:00)

[Screen/Camera: (if chassis available) ParkAuto running on real carpet
next to the sim run; the DS battery voltage readout; a wiggled loose
encoder cable]

(Audio) So what DOES physics change? Three honest things. One: battery
sag. Your kP — tuned so lovingly last episode — feels different at twelve
volts than at thirteen point eight. Same code, mushier robot. The voltage
readout on the Driver Station isn't decoration; it's the first line of
your checklist. Two: wheel slip — encoders count distance the robot
didn't actually travel, lesson nine warned you. And three — write this
one down — the number one "software bug" at real competitions is a loose
wire. A connector that walks out of its port between matches presents
EXACTLY like a code bug. Which is why...

(Audio) ...the four-question ritual from lesson eight is now your
multimeter. Inputs fine, decisions fine, outputs fine, but physics
disagrees? Question four finally earns its keep: that's not your code.
That's a wire, a wheel, a battery — hand it to the build team WITH the
numbers. No more week-long arguments. And when your robot exists: re-run
the five-for-five drill on real carpet. The generous margins from lesson
ten were built for exactly this gap.

[Visual: Same ParkAuto telemetry side by side — sim run vs field run —
values slightly different, both parking; a battery voltage readout sagging]
[Screen: two real ParkAuto telemetry captures — one from the sim, one
from the field robot (grab the field one at any practice session)]
[Edit: side-by-side with SIM / FIELD labels, amber highlight on the
battery-voltage gap, green on both PARKED states. DO NOT generate — an
"honest engineering comparison" requires honest screenshots (Rule 3)]

[~2 minutes 30 seconds]

## Match Day Ops (8:00–11:00)

(Audio) Now the part that separates teams more than any mechanism: how
the software person runs match day. Three disciplines.

(Audio) One: code freeze. Pick a date about a week before the event.
After it, no new features — only bug fixes, and only with a failing test
that proves the bug, then passes. I know the night-before rewrite feels
heroic. Every mentor has the story, and every version of that story ends
the same way: the rewrite loses matches the old code would have won.
Freeze means your robot runs code with a week of testing on it, not a
night of adrenaline.

[Screen/Camera: the printed PIT-CHECKLIST on a clipboard; checking items
against the real robot — voltage, OpMode menu, config screen, telemetry
zeros, gamepad ports]

(Audio) Two: the checklist. It's on the branch, it's printable, and it's
five lines: battery voltage over thirteen. The two COMPETITION OpModes —
and only those — visible on the menu. Config map matches the robot —
especially after ANY rewiring. Init the auto and read the telemetry:
encoders zero, heading zero — the stale-tick bug from lesson ten, caught
in five seconds. Gamepads in the right ports. Every match. EVERY match.
The checklist isn't there because you're forgetful — it's there so you
don't have to be brilliant at eight a.m.

(Audio) Three: between matches — data first, wrenches second. Something
went weird? READ THE TELEMETRY from that match before anyone touches the
robot. Then the four questions. The software person's whole match-day
job: run the checklist, read the numbers, protect the freeze. And here's
the thing nobody puts on a checklist: staying calm IS the job. Your team
can feel it. Be the closed laptop.

[Visual: The printable checklist, checkmarks landing one by one; a red
"CODE FREEZE" banner with a date and a padlock over the repo]
[Edit: build from the episode's REAL printable checklist
(docs/PIT-CHECKLIST.md) — render the actual document on a clipboard,
animate green checks landing on its real items, then the red CODE FREEZE
banner with padlock over a repo icon. Described list text garbles when
generated (Rule 4), and this artifact ships to learners — the words must
match the printable]

[~3 minutes]

## Graduation (11:00–13:00)

[Visual: The 12 lesson branches as a subway-map line, each stop lit, from
"hello-robot" to "competition-ops"]
[Edit: build the subway map as a slide — one line, twelve stations
labeled with the real lesson names, starburst on the final stop. Twelve
labels is 3x past what generation renders reliably (Rule 4); optionally
generate an UNLABELED station-line background and set the names in the
editor]

(Audio) Twelve lessons ago, you'd never seen an OpMode. Count what you
can do now — and I mean actually do, because you did each one: build a
dev environment. Drive a simulated robot. Structure code so hardware is
swappable. PROVE logic with tests before the robot exists. Debug through
telemetry. Ship an autonomous that goes five for five. And run match day
like the calm pit. That's not "the coding kid." That's a robotics
software engineer — junior edition, sure, but the real thing.

(Audio) And one more thing, because it matters for where you're headed:
FTC judges interview STUDENTS — not mentors, not parents. And college
essays want a story. "I took a coding class" is neither. "I shipped the
software on a competition robot, and here's the test suite" — that's
both. You didn't take a course these twelve weeks. You shipped a robot.

(Audio) Where next: PID's I and D. Odometry — the wheel-slip fix from
lesson nine. Vision. Season strategy when the new game drops. Subscribe —
that's the next series. But the best thing you can do TODAY is rewatch
this one WITH a teammate — because software teams of one are the exact
fragility this series started with, and you're the one who can fix that
now.

(Audio, final) Every branch, every lesson, stays up. The whole series is
a repo you can walk backward and forward — one branch per lesson — and
the code was real competition code all along. Show up to kickoff with
this workflow... and your team's bottleneck isn't software anymore. It's
everyone else keeping up with you. Go build. 🎓

[~2 minutes]

---

## Thumbnail Concept

Closed laptop on a pit cart next to a competition-ready robot, big green
checklist floating; text "THE CALM PIT."
[Prompt: YouTube thumbnail 1280x720, closed laptop resting on pit cart
beside polished competition robot, large floating checklist with all green
checks, bold white text THE CALM PIT, warm confident lighting on dark
background, graduation energy]

---

## Shot List (hardware + screen)

1. Control Hub hero shot; USB deploy from Android Studio (build → install
   → hub chirp/LED)
2. Driver Station config screen: creating the config, binding
   front_left_motor to a port (film ALL four — unique footage)
3. The one-typo config crash (rename a motor, init, crash, fix)
4. If chassis available: ParkAuto on carpet + DS voltage readout; a loose
   encoder cable wiggle
5. Printed PIT-CHECKLIST on clipboard, checked against the robot
6. The 12-branch dropdown on GitHub (for the graduation beat)

Generated assets: 5 stills, 0 video clips (real hardware carries this one).
