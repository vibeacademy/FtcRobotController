# Lesson 07 — Test Robot Code With No Robot (Full Shooting Script)

**Runtime target:** ~11:00 · **Persona:** see `docs/lessons/README.md`
**Source packet:** `docs/lessons/lesson-07.md` (media packet lives there)
**Code branch:** `lesson-07-testing` — the suite starts at 17 tests
(Drivetrain 8, Arm 5, Claw 4); `ImuTest` is the 18th, written ON CAMERA in
this video. The break-it exercise targets `MockDrivetrain.clip()`.

**Production key:** `[Visual:]`+`[Prompt:]` = generated still ·
`[Video Prompt:]` = generated motion (0 used) · `[Screen:]` = you record
· `[Edit:]` = you build it in the editor (slide, overlay, or motion graphic).

**Filming note:** the narrative arithmetic matters — hook says seventeen,
you write the eighteenth live, the break-it demo shows 18 run / 1 failed.
Keep the terminal font LARGE; green/red checkmarks are the visual language
of this whole episode.

---

## Prerequisites

Lessons 01–06 — especially the mock layer.

## Mental Model Shifts

1. "It worked when we tried it" is not evidence — it's one anecdote. A test
   is an experiment that reruns itself every time the code changes.
2. Mocks flip hardware scarcity: MockDrivetrain is a robot that exists ONLY
   to be interrogated. Infinite robots, free.
3. Practice-field time is for physics. Logic bugs should never make it to
   the field — they're catchable at zero cost on a laptop.

---

## Hook (0:00–0:40)

[Visual: Terminal runs one command; 17 green checkmarks cascade; cut to a
robot on a practice field doing the same maneuvers the test names describe.]
[Screen: real capture — type the single gradle test command and let the
green checks cascade for real; then cut to sim b-roll of the robot
strafing and turning]
[Edit: highlight each test name as the sim performs the matching maneuver.
DO NOT generate — real test output over fake terminals, always (Rule 3)]

(Audio) Seventeen experiments on a competition robot just ran on my laptop.
Four seconds. No robot. Not even the simulator. And one of those seventeen
exists specifically to catch the bug from lesson five — the one that flips
robots. Later in this video I'm going to reintroduce that bug on purpose,
on camera, so you can watch a test catch it before it ever touches a
field. If anyone on your team still says software is the risky subsystem —
this episode is the counter-argument.

[~40 seconds]

## Tests Are Rerunnable Experiments (0:40–3:00)

[Visual: Science-fair board layout — HYPOTHESIS / EXPERIMENT / RESULT — but
each panel is code: an assertion, a mock call, a green check]
[Prompt: Science fair trifold board dark theme, three panels labeled
"HYPOTHESIS" "EXPERIMENT" "RESULT", first panel with a lightbulb icon,
second with a cardboard-box robot icon, third with a large green
checkmark, absolutely no other text, playful science-meets-code teaching
style]
[Edit: overlay real code snippets onto the panels if the beat needs them —
the generated board must stay code-free (Rule 3)]

(Audio) You already know what a test is — you've done a science fair. A
hypothesis: "no matter what the driver does, wheel power never goes past
one point oh." An experiment: command the drivetrain with a crazy input,
like drive equals two point oh, and measure what the wheels got told. A
result: pass or fail. That's a unit test. Nothing more.

(Audio) Except for one superpower science fairs don't have: the experiment
reruns ITSELF. Every time anyone changes the code — you, a teammate, you
in three months at midnight before a qualifier — all the experiments run
again, in seconds, and tell you if something you believed stopped being
true.

(Audio) And the lab bench for these experiments? The mock folder from last
lesson. Here's the thing about MockDrivetrain: it doesn't spin anything.
When code says "front left, power point-seven," the mock just... writes it
down. It's a robot that exists purely to be interrogated afterward: "what
were you told?" Infinite robots. Free. On any laptop.

[~2 minutes 20 seconds]

## Why "Run It and Watch" Doesn't Scale (3:00–4:15)

[Paradigm Shift]
(Audio) Testing a game means playing it — the bug is on screen, you SEE
it. Your instinct is to test robots the same way: run it and watch. Two
problems. One: you can't "play" a robot that isn't built yet. Two: even
with a robot, checking ONE edge case means the field, a charged battery,
an hour of setup — and you'll check it once, not every time the code
changes. The new model: interrogate the code in a courtroom where hardware
can't hide and can't be busy. That courtroom is the mock layer. Last
lesson I said the mock folder was next episode's star — this is why we
built it.

[~75 seconds]

## Run It, Read It, Write One (4:15–8:30)

[Screen: terminal — ./gradlew :TeamCode:testDebugUnitTest — full run to
BUILD SUCCESSFUL; then the test-class names]

(Audio) One command: gradlew, TeamCode, test-Debug-Unit-Test. It compiles,
runs every experiment... green. And read those class names — Drivetrain
Test, Arm Test, Claw Test. The test suite is organized like the robot.
Eight experiments on the drivetrain, five on the arm, four on the claw.

[Screen: the clamp test method in the editor, then the three-band overlay]

[Visual: The clamp test on screen, three regions highlighted — ARRANGE
(mock + overdrive input), ACT (mecanumDrive call), ASSERT (±1.0 checks)]
[Screen: the real clamp test method in the IDE]
[Edit: three translucent bands over the real code — ARRANGE (blue), ACT
(orange), ASSERT (green). Same treatment as lesson 01's file-map overlay.
DO NOT generate — models invent fake Java (Rule 3)]

(Audio) Open the one I promised you: mecanumDrive clamps wheel powers.
Every test has the same three-beat shape — learn it once, read any test
forever. Beat one, ARRANGE: set up the world — a fresh MockDrivetrain.
Beat two, ACT: do the thing — mecanumDrive at two point oh, deliberately
past the limit. Beat three, ASSERT: check the claim — all four wheel
powers, each within negative one to one. Hypothesis, experiment, result.
There's lesson five's flip bug, standing trial, fifty times a day.

[Screen: writing ImuTest.java live — the empty class, then the test:
setHeading(87.3), resetHeading(), assertEquals(0.0, getHeading())]

(Audio) Now we write experiment eighteen, live, and it's a real one. The
hypothesis: after a heading reset, the IMU — the robot's compass — reports
zero, no matter where it was pointing. Why care? Because an autonomous
that trusts a STALE heading turns the wrong way on its very first move.
Arrange: a mock IMU, pointed at eighty-seven point three degrees — some
random Tuesday angle. Act: reset heading. Assert: heading equals zero.

[Screen: run — 18 tests, all green]

(Audio) Run it... eighteen for eighteen. You just added a permanent
guarantee to this codebase. That test will outlive your season.

[~4 minutes 15 seconds]

## The Money Moment: Break It (8:30–9:45)

[Screen: MockDrivetrain.clip() — editing it to return its input unchanged;
then the terminal run with ONE red line; then restoring and green]

(Audio) Now the demo this episode exists for. I'm going into
MockDrivetrain and breaking the clamp — the safety rule from lesson five —
on purpose. The exact bug that distorts motion and flips robots. If our
suite is real, it should notice. Run... and there it is. One red line.
Eighteen tests ran, one failed: mecanumDrive clamps wheel powers. It
didn't just fail — it POINTED at the crime. Restore the clamp... green.

[Visual: Side-by-side terminal runs — 18/18 green, then 17/18 with one red
line naming the clamp test, then green again]
[Screen: three real test runs — all green; then break the clamp and let
the single red X name the exact test; then fixed and green again]
[Edit: arrange the three captures as a sequence strip. DO NOT generate —
this beat's credibility IS the real output (Rule 3)]

(Audio) That red-green cycle is the entire discipline, and here's the
quiet part said out loud: this exact break-and-catch was performed on this
very repo, for real, to prove the suite bites before anyone trusted it.
And when an FTC judge leans in and asks your team — because they will —
"how do you know your code works?"... running THIS, in front of them, is
the answer that wins awards. "We tried it once" is not.

[~75 seconds]

## What Tests Can't Do (9:45–10:30)

(Audio) Boundaries, because no hype. One: tests prove LOGIC, not physics.
Eighteen green checks plus a dead battery is still a dead robot — arc
three handles the physics side. Two: test YOUR decisions — your math, your
clamps, your presets. Don't write tests proving the SDK works; FIRST
already did. And three: a flaky test — one that passes sometimes — is
worse than no test, because you'll learn to ignore red. No randomness, no
waiting on timers. Every experiment, same result, every time.

[~45 seconds]

## Conclusion + CTA (10:30–11:00)

(Audio) Your team now has something most FTC teams have never seen: robot
code with EVIDENCE. One command, four seconds, eighteen experiments — run
it before every practice, and logic bugs stop reaching the field at all.
Field time becomes what it should be: physics and driving. The branch has
the whole suite plus the break-it-yourself exercise — do it, post your red
test line in the comments, wear it proudly. Next lesson: when something
goes weird ANYWAY — because it will — your robot is going to tell you
exactly where. Telemetry, done properly. See you there.

[~30 seconds]

---

## Thumbnail Concept

A giant green checkmark shielding a small robot from a red "BUG" arrow;
text "TESTED. NO ROBOT NEEDED."
[Prompt: YouTube thumbnail 1280x720, large glowing green checkmark shield
in front of small robot, red jagged arrow labeled BUG deflecting off
shield, bold white text TESTED NO ROBOT NEEDED, dark background, high
contrast protective energy]

---

## Shot List (screen recordings you need)

1. The one-command suite run → green (BIG terminal font)
2. Test tree / class names (Drivetrain 8, Arm 5, Claw 4)
3. The clamp test in editor (for the ARRANGE/ACT/ASSERT overlay)
4. Writing ImuTest live (typed, speed-ramped) → 18/18 run
5. Breaking clip() → the single red line → restore → green (one continuous
   sequence if possible — the cut kills the drama)

Generated assets: 4 stills + 1 video clip (hook cascade).
