# Operator Manual

**Who this is for:** the human operator of this project. The agents write code,
review PRs, and groom the backlog — but you make every decision that matters.
This manual explains what only you can do, which commands to run when, and the
engineering methodology to follow, assuming this is your first robotics project
and you're starting from iteration zero.

---

## 1. Your Role vs. the Agents' Role

The agents are competent and fast, but they optimize whatever you point them at.
Pointing is your job.

**Only you can:**
- Merge PRs and move tickets to Done (this is enforced by the workflow)
- Decide strategy: what the robot should do, what to build first, what to skip
- Touch the physical robot: wiring, configuration, field testing, driving
- Judge whether something *actually works* — the simulator and unit tests
  approximate reality; they don't replace it
- Stop work that's drifting (see §5, "When NOT to use commands")

**The agents can:**
- Implement well-specified tickets (`/work-ticket`)
- Review code for safety and standards (`/review-pr`)
- Analyze scoring math and propose designs (`/analyze-scoring`, `/design-subsystem`)
- Keep the board and journals in order (`/groom-backlog`, `/log-session`)

A useful mental model: **you are the chief engineer; the agents are a very fast
build team.** A fast build team pointed at the wrong thing produces the wrong
thing faster.

---

## 2. The Methodology: How Robots Actually Get Built

You don't have a design yet. That is normal and correct at this stage — the
mistake would be jumping to code. Successful FTC teams follow a version of the
**engineering design process**, and FTC judges literally score you on whether
you can show you followed it (in the Engineering Portfolio). The loop:

```
ANALYZE → STRATEGIZE → DESIGN → PROTOTYPE → BUILD → TEST → ITERATE
   ↑                                                          |
   └──────────────── document everything ────────────────────┘
```

### Phase 0 — Analyze the game (before any design)

When the season's game manual drops (kickoff is early September), read it
twice. Then answer, in writing:

1. **What scores points, and how many?** Every scoring action, its point
   value, and roughly how long it takes. Points-per-second is the metric.
2. **What will most robots do?** The obvious strategy everyone will build.
3. **What's our niche?** As a first-year team, the answer is almost always:
   *do the simple thing reliably*. A robot that parks and scores one game
   element every match outperforms most rookie robots that attempt more.

Use `/analyze-scoring` to do the math with you. Output: a one-page strategy
in `docs/GAME-STRATEGY.md`.

### Phase 1 — Strategize: the priority list

From the analysis, write a ranked capability list. For nearly every game it
looks like this:

1. **Drive reliably** (a drivetrain that never fails is worth more than any mechanism)
2. **Score the easiest element one way**
3. **Park / simple autonomous** (usually free points)
4. Everything else — in points-per-effort order

The cardinal rule of first-year robotics: **a driving robot in week 2 beats a
perfect robot in week 10.** Get a drive base moving early; iterate everything
else on top of it.

### Phase 2 — Design (subsystem by subsystem)

A robot is 3–5 subsystems: drivetrain, intake/claw, arm/lift, maybe a
launcher. Design them **one at a time, in priority order**, using
`/design-subsystem [name]`. For each one you want, before building:

- What it must do (from the strategy doc, with numbers: reach height X, hold game element Y)
- Two or three candidate approaches, and why you picked one
- What hardware it needs
- How you'll know it works (a testable success criterion)

Keep designs in `docs/` and decisions in `docs/adr/`. Judges ask "why did you
choose this design?" — these documents are your answer.

### Phase 3 — Prototype before you build

Prototyping in FTC means **cheap and fast**: cardboard, tape, zip ties, a motor
clamped to a board. The goal is to answer one question per prototype ("can this
wheel type grip the game element?") in under an hour. Only CAD/metal-build a
mechanism after a prototype proved the concept.

The software equivalent — and the reason this repo has a hardware abstraction
layer — is that **code prototypes against the simulator and mock hardware
before the robot exists.** Software and hardware proceed in parallel:

| Hardware track (you) | Software track (agents + you) |
|---|---|
| Prototype mechanism | `/design-subsystem` → tickets → `/work-ticket` |
| Build it | Code runs in simulator (`/run-simulator`) |
| Mount it on robot | Deploy, test on real hardware (only you can do this) |

### Phase 4 — Test, then trust nothing

Testing has levels, and each level only earns you confidence at that level:

- **L0 — unit tests / code review**: the logic is right (`/test-feature`, `/review-pr`)
- **L1 — simulator**: the behavior looks right (`/run-simulator`)
- **L2 — bench test**: the mechanism works off the robot
- **L3 — on-robot, on-field**: it works in match conditions

Nothing is "done" until it passes at the level that matters for it. Drive code
is done at L3, not L1. Only you can perform L2/L3.

### Phase 5 — Iterate, and practice driving

After the minimum viable robot works: driver practice is the highest-return
activity in FTC, and rookie teams always underinvest in it. **Ten hours of
driver practice is worth more than a fourth mechanism.** Iterate mechanisms
between competitions based on what actually failed in matches — keep a list.

### Document as you go

Run `/log-session` at the end of every working session. The journals feed the
Engineering Portfolio (which judges score) and give the agents memory across
sessions. This is not optional bookkeeping; it's how the project stays coherent.

---

## 3. Where You Are Right Now (Iteration Zero)

As of July 2026 you are in the **off-season / preseason**. The next game is
unknown until September kickoff. This is the ideal time to build capability
that transfers to any game:

1. **Learn the stack.** Use `/eli5` liberally — `/eli5 mecanum drive`,
   `/eli5 the hardware abstraction layer`, `/eli5 HelloWorld.java`. There are
   no dumb questions and the command exists precisely for this.
2. **Get the simulator loop working.** Run `/run-simulator`, drive the sample
   OpModes with a gamepad. You cannot evaluate agent-written drive code if you
   can't run it.
3. **Settle the drivetrain.** The repo already assumes mecanum (four wheels,
   can strafe sideways — the FTC default for good reason). Build or acquire the
   drive base; get it driving under the existing TeleOp code.
4. **Timebox the meta-work.** The current board (issues #4–#19) is almost
   entirely workflow-tooling tickets, not robot tickets. Tooling is useful but
   it is not a robot. Pick the 2–3 that pay off soonest, do them, and stop —
   the board should be majority robot-tickets by kickoff.
5. **At kickoff (September):** start Phase 0 above for real.

---

## 4. Command Reference: How, When, Whether

### The core loop (once real work exists)

Run in this order; each feeds the next.

| Command | When | What you do with the output |
|---|---|---|
| `/sprint-status` | Start of every session | Decide today's focus; spot stuck work |
| `/groom-backlog` | When Ready column is empty or stale | Sanity-check priorities — agents don't know your competition pressure |
| `/work-ticket` | When Ready has a well-defined ticket | Let it run; it ends with a PR |
| `/review-pr [n]` | After every PR | Read the GO/NO-GO. **You still read the diff.** GO is a recommendation, not a merge |
| *merge + move to Done* | After review, when YOU are satisfied | Human-only, on purpose |
| `/log-session` | End of every session | Confirm the journal is accurate; approve memory entities |

### Design & strategy (use before building, sparingly after)

| Command | When | Whether |
|---|---|---|
| `/analyze-scoring` | Kickoff, and before each competition | Skip until a game manual exists — there's nothing real to analyze |
| `/design-subsystem [name]` | Before building any subsystem | One at a time, priority order. Don't design subsystem #4 before #1 drives |
| `/plan-autonomous [strategy]` | After TeleOp drive works | Premature before that — autonomous builds on working subsystems. Start with "drive and park" |

### Testing & verification

| Command | When | Whether |
|---|---|---|
| `/run-simulator` | After any drive/OpMode change | Cheap — use constantly. But remember it's L1 confidence, not proof |
| `/test-feature [name]` | When a feature claims to be done | Insist on it for anything safety-related (arm limits, motor bounds) |
| `/competition-prep` | 1–2 weeks before each event | Not useful before you have a robot |

### Learning & housekeeping

| Command | When | Whether |
|---|---|---|
| `/eli5 [thing]` | Whenever anything is unclear | Always appropriate. Understanding the code is part of your job — judges interview *students*, not agents |
| `/log-session` | End of every session | Do not skip — this is the project's memory and portfolio raw material |
| `/validate-memory` | When `/log-session` reports missing entities | Rarely needed directly |

---

## 5. When NOT to Use Commands

Knowing when to *not* delegate is most of operating well:

- **Don't `/work-ticket` from an empty design.** If a ticket can't say what
  "done" looks like, the agent will guess. Design first, ticket second.
- **Don't batch-merge.** Merging is your quality gate. One PR at a time, read
  the diff, ask `/eli5` about anything you don't follow. If you're merging
  code you don't understand, slow down — that's how teams lose the ability to
  fix their own robot at a competition.
- **Don't let agents generate work for agents.** If a grooming session
  produces ten new tooling tickets, that's the system feeding itself. Ask:
  "does this ticket make the robot better?"
- **Don't trust GO on safety code without L2+.** The reviewer checks motor
  bounds and timeouts in code; only a bench test proves the arm doesn't slam
  into the chassis.
- **Don't optimize before something works end-to-end.** No PID tuning tickets
  before the drivetrain drives. No autonomous pathing before TeleOp is stable.

---

## 6. Suggested Cadence

**Every session:** `/sprint-status` → work (implement/review/test) → `/log-session`.

**Weekly (in season):** one grooming pass (`/groom-backlog`), one look at the
strategy doc — is what we're building still what wins matches?

**Before each competition:** `/competition-prep`, freeze code 3+ days out
(only bug fixes after the freeze), driver practice every remaining day.

**After each competition:** write down what failed in matches while it's
fresh (`/log-session`), re-run `/analyze-scoring` with what you learned,
re-rank the backlog.

---

## 7. First-Timer's Reading List

- The season game manual (twice) — everything else is downstream of it
- `docs/GAME-STRATEGY.md` — once it exists, it's the source of truth for "why"
- `CLAUDE.md` — the workflow rules the agents follow
- `game manual 0` (gm0.org) — the community's free FTC design handbook;
  the best answer to "how do most people build robots?"
