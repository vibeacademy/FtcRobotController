# Lesson 05 — The 15 Lines Behind Every Good FTC Drivetrain (Full Shooting Script)

**Runtime target:** ~12:00 · **Persona:** see `docs/lessons/README.md`
**Source packet:** `docs/lessons/lesson-05.md` (media packet lives there)
**Code branch:** `lesson-05-mecanum` — SimTeleOp upgraded to mecanum; the
mixing table lives in its header comment. Both video-prompt slots used.

**Production key:** `[Visual:]`+`[Prompt:]` = generated still ·
`[Video Prompt:]` = generated motion (1 used) · `[Screen:]` = you record
· `[Edit:]` = you build it in the editor (slide, overlay, or motion graphic)
· `[Layout:]` = Descript scene layout (default pack names) — no cue = **Camera**.

**Filming note:** the clipped-vs-normalized comparison (8:00) is the money
demo. Record it in the sim by literally commenting out the normalize block,
driving a diagonal-plus-turn, then restoring it and repeating the same stick
input. If the sim's distortion is too subtle on camera, the generated video
clip carries the beat — but try the real one first; real beats generated.

---

## Prerequisites

Arc 1 — you can write an OpMode and drive it in the sim.

## Mental Model Shifts

1. A mecanum robot isn't driven like a car — it's a *vector* machine: any
   direction, any rotation, simultaneously.
2. Each wheel gets a *recipe*, not a command: drive ± strafe ± turn. Four
   recipes, one motion.
3. Motor power isn't a suggestion — outside [-1, 1] the SDK clips silently
   and your motion *distorts*. Bounding is correctness, not politeness.

---

## Hook (0:00–0:40)

[Layout: Screen — sim capture full-bleed; switch to Media for the wheel clip]
[Visual: Sim robot drives a perfect diagonal while spinning slowly —
impossible for a car — then a real mecanum wheel close-up with its angled
rollers.]
[Screen: real sim capture — drive the Mecanum Bot in a diagonal strafe
while rotating, the impossible-for-a-car move; add a motion-trail accent
in edit if wanted. Never generate fake simulator footage — the real sim
is the series' whole promise]
[Video Prompt: Close-up render of a mecanum wheel with 45-degree angled
silver rollers spinning slowly, dark background, smooth ambient motion,
tech aesthetic]

(Audio) Look at this robot. It's driving diagonally... while spinning. No
car on earth can do that. The secret is half hardware — those weird wheels
with the angled rollers — and half software: about fifteen lines of math
that every competitive FTC team on the planet has some version of. Today
you write those fifteen lines and actually understand them. And I'll show
you the one-line mistake hiding in this code that flips real robots.

[Layout: Big Fact — "15 LINES" punches in as the hook lands]

[~40 seconds]

## How Angled Rollers Cheat Physics (0:40–3:30)

[Screen: (or physical prop if you have a mecanum wheel) — a single mecanum
wheel, pointing out the rollers mounted at 45 degrees]

(Audio) First, the hardware half — because the software only makes sense
once you see the trick. A mecanum wheel is a normal wheel wearing little
rollers, mounted at forty-five degrees. When the wheel spins, those rollers
mean the push doesn't go straight forward — it comes out at an angle.
Diagonally.

(Audio) One wheel pushing diagonally is useless. But we have FOUR — and
their diagonals are mirrored. So think of it as four arrows, one per
corner. Spin the wheels one way, all four arrows point forward: the
diagonal parts cancel, robot drives straight. Spin them in the right
combination and the FORWARD parts cancel instead — all that's left is
sideways. The robot strafes. No steering, no turning. Pure slide.

[Layout: Media]
[Visual: Top-down robot diagram, four force arrows at 45° from each wheel;
arrows combine into one big sideways arrow as the "strafe" case]
[Prompt: Top-down schematic of square robot dark background, four diagonal
orange force arrows at each wheel corner, arrows visually summing into
single large blue arrow pointing right labeled STRAFE, vector addition
teaching diagram, clean physics style]

(Audio) That's it. That's the whole trick: four diagonal arrows, and
addition. No trig, no matrices — if you can add arrows, you can understand
a mecanum drivetrain. The software's job is just deciding how hard each
wheel spins so the arrows add up to whatever the driver's asking for.

[~2 minutes 50 seconds]

## The Mixing Lines (3:30–8:00)

[Screen: SimTeleOp in Android Studio — the three intent lines: drive,
strafe, turn from the sticks]

(Audio) The driver gives us three intentions on two sticks. Left stick up
and down: drive. Left stick side to side: strafe. Right stick side to
side: turn. Three numbers, each between negative one and one. Our job:
turn three intentions into four wheel powers.

[Layout: List — the sign table typed natively, code capture as a layer]
[Visual: 4×3 color grid — rows are wheels, columns are drive/strafe/turn,
each cell + or −, matching colored signs in the code beside it]
[Screen: the real mecanum mixing code in the IDE]
[Edit: build the 4×3 sign table as a slide — rows FL FR BL BR, columns
DRIVE/STRAFE/TURN in blue/orange/yellow — and color-match highlights onto
the real code beside it. DO NOT generate — one hallucinated minus sign
teaches the wrong math (Rules 2 and 3)]

(Audio) Here's the recipe card — don't memorize it, understand its shape.
Every wheel gets all three ingredients: drive, strafe, turn. The only
difference between wheels is the SIGNS. Front-left: drive PLUS strafe
PLUS turn. Front-right: drive MINUS strafe MINUS turn. The signs are just
each wheel's diagonal from the arrow picture, written down. Four recipes,
one motion.

[Screen: typing the four mixing lines into SimTeleOp, colors matching the
table if your editor theme allows; then the four setPower calls]

(Audio) In code, the recipe card is four lines. F-L equals drive plus
strafe plus turn... and so on, straight off the table. Four little
formulas replace our tank drive from last lesson.

[Screen: THE MOMENT — sim full-screen: drive forward, then pure strafe,
then diagonal, then diagonal-while-spinning; corner cam on the sticks]

(Audio) Sim time. Forward... normal. Left stick sideways... IT STRAFES.
Look at that — full sideways slide, no turning. Diagonal... yes. And the
party trick: diagonal WHILE spinning. You just wrote the signature move of
FTC robotics in four lines of addition. Every time you see a robot slide
sideways into a scoring position at a competition — this exact math is
running, fifty times a second.

[~4 minutes 30 seconds]

## The Safety Lesson: Clamp It (8:00–10:30)

[Screen: telemetry showing wheel values while pushing all sticks — a wheel
value reading 2.4]

(Audio) Now the mistake that flips robots. Push drive AND strafe AND turn
at the same time, and watch the telemetry: the math just asked front-left
for two point four. Motors max out at one point oh.

[Paradigm Shift]
(Audio) In a game engine, a too-big number is fine — the engine handles
it, bigger number, faster sprite. Your instinct says "it'll get handled."
Here's what actually happens on a robot: the SDK CLIPS the number.
Silently. No error, no warning — two point four just becomes one point oh.
And the trap is that it clips each wheel SEPARATELY. Front-left wanted 2.4
and got 1.0. Back-right wanted 0.8 and kept 0.8. The RATIOS between the
wheels — the thing that made your diagonal a straight line — just got
destroyed. The robot lurches, curves off course, and at full speed with a
tall robot? It tips. Nobody handles it for you. On a robot, YOUR code is
the engine.

[Layout: Media — 2-up composite of the two runs]
[Visual: Two sim runs side by side — "clipped" robot curving off path,
"normalized" robot driving true, both from same stick input]
[Screen: two real sim runs from the same stick input — one on the
clipped code, one on the normalized code (both versions are in this
lesson's git history)]
[Edit: side-by-side composite, CLIPPED / NORMALIZED labels, stick-input
overlay. DO NOT generate — the behavioral difference is the teaching
point; record it, don't fake it (order-bearing motion)]

[Screen: typing the normalize block — find the max magnitude, divide all
four when max > 1]

(Audio) The fix is five lines, and it's called normalizing. Find the
biggest wheel value. If it's over one point oh, divide ALL FOUR wheels by
it. Now the largest is exactly one, and — this is the point — the ratios
between wheels survive. Motion stays true, just at the fastest speed the
motors can actually do. And a fun fact for later: this exact rule —
powers bounded, always — is written into this repo's team rules and
checked by an automated test. Real teams don't remember safety. They
encode it. That idea gets its own episode in two lessons.

[~2 minutes 30 seconds]

## Gotchas: The 10-Second Diagnoses (10:30–11:30)

(Audio) Three things that WILL happen to you, and the fast diagnosis for
each. One: the robot spins in place when you push forward — that's a
reversed motor. Don't stare at the math; run the one-wheel test: power
each wheel alone for a second and check its direction. Ten seconds, found
it. Two: the robot slowly creeps when you're not touching anything —
that's stick drift; ignore inputs smaller than about zero point zero five.
It's called a deadzone. Three: your signs don't match my table — that's
not you being wrong, that's your motors being mounted differently. The
one-wheel test settles it. Trust the test, not the internet's table.
Including mine.

[~60 seconds]

## Conclusion + CTA (11:30–12:00)

(Audio) Here's what you actually did today: you wrote the drive code that
most rookie teams copy off the internet without understanding — and
because you understand it, you can debug it, which they can't. That's the
difference between having code and being the software person. The branch
has my commented version plus two challenges — do the slow-mode button,
it's twenty minutes and your future drivers will genuinely love you for
it. Next lesson: the architecture trick that lets your whole team write
robot code at once... without fighting over one robot. See you there.

[~30 seconds]

---

## Thumbnail Concept

Top-down robot with four 45° force arrows visibly combining into a big
sideways arrow; text "THE MECANUM MATH."
[Prompt: YouTube thumbnail 1280x720, top-down stylized robot with four
glowing orange diagonal arrows merging into one large blue horizontal
arrow, bold white text THE MECANUM MATH, dark background high contrast,
physics diagram energy]

---

## Shot List (screen recordings you need)

1. Mecanum wheel close-up (physical wheel if available — worth borrowing
   one; else the generated hook clip covers it)
2. The three intent lines + four mixing lines typed in SimTeleOp
3. THE STRAFE MOMENT: forward → strafe → diagonal → diagonal-spin in sim,
   corner cam on sticks (capture generously — b-roll gold)
4. Telemetry showing 2.4 on a wheel with all sticks pushed
5. Real clipped-vs-normalized A/B in the sim (comment out normalize block,
   same input, restore) — if too subtle, the generated clip is backup
6. Typing the normalize block

Generated assets: 3 stills + 2 video clips (hook + clipped-vs-normalized).
