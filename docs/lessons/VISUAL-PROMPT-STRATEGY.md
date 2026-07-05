# Visual Prompt Strategy

Learned by generating lesson 01's full asset set (9 stills + 1 video,
Gemini 2.5 Flash Image + Veo 3 via content-marketing's `generate_assets.py`)
and reviewing every output. Scorecard: 3 usable as-generated, 4 fixed by
prompt revision, 2 reclassified out of generation entirely. Apply this to
lessons 02–12 before generating anything.

## The Six Rules

### 1. Describe the object, never the domain term

"FTC competition robot" produced sci-fi battle mechs with legs — twice.
The model doesn't know FTC, and this audience instantly spots a fake robot.
Always write the physical description, and ban the failure mode:

> small cube-shaped student competition robot built from silver aluminum
> extrusion rails, four mecanum wheels, visible colorful wiring, on light
> gray foam mat field tiles … **not humanoid, no legs, no sci-fi armor**

Same principle for fields (gray foam tiles + school gymnasium, not neon
Tron arenas) and hubs (small plastic box with ports, not glowing cores).

### 2. Diagrams: pin the structure or the model invents one

The timeline bar came back with four segments, duplicate labels, and
invented point values. For any structured diagram, specify: the exact
count ("exactly three segments in one row"), the exact labels in quotes,
the order, and — critically — **"absolutely no other text, numbers, icons,
or decorations."** The negative constraint is what stops improvisation.

### 3. Never generate code, UI, or dashboards

Asked to render an annotation line and a phone menu, the model invented
fake Java (`thif {`) and garbled its own labels ("TELEMTERY",
"Smartphone Draiver"). Any visual whose subject is code, an IDE, the
Driver Station, or telemetry must be a **real screenshot with overlays
added in editing** — recategorize from `[Prompt:]` to `[Screen:]` + an
edit note. This affects visuals in most lessons (see checklist below).

### 4. Text in images: few, short, quoted

Up to ~4 short strings render reliably when given as exact quoted text.
Long strings, many strings, or text merely *described* ("point counter
icons") come back garbled or invented ("TIMOTOTS REMAINING"). Thumbnail
text like "200 LINES" renders well. If a visual needs a paragraph, it's a
slide built in the editor, not a generation.

### 5. Icon-and-metaphor prompts are the sweet spot

The two best first-try results were pure visual metaphors (wrench/code/
battery with a $0 tag; pixel-art game character vs slipping wheel). When a
beat can be told with objects instead of interfaces, prefer it — cheaper,
faster, and reliably on-style.

### 6. Capture-first: never generate what the series can record for real

The corollary that fell out of applying Rules 3–5 across every script:
the simulator, terminals, the Driver Station, telemetry, diffs, and test
output all exist and record for free. A generated fake of any of them
trades authenticity — the series' core asset — for nothing. Same instinct
as Rule 1's "this audience instantly spots a fake robot": they'll spot a
fake sim too. Generation is for what cannot be filmed: metaphors, physics
diagrams, stylized arenas, thumbnails, ambient texture motion.

## Tooling notes

- `generate_assets.py <script.md>` parses ALL `[Prompt:]`/`[Video Prompt:]`
  pairs — including documentation examples in the script header. Strip the
  "Production key" lines before running, or keep literal cue examples out
  of scripts (scripts 02–12 already avoid this; lesson 01's header was
  fixed by preprocessing).
- Images ≈ $0.01–0.02 each (Gemini Flash); video ≈ $0.10–0.20 (Veo 3).
  Iterating on stills is cheap — plan a v1 → review → v2 pass per lesson.
  Iterating on video is not. And explicit flow direction is NOT enough:
  the L01 loop-diagram clip missed twice — v1 muddled the direction, and
  the v2 retry (direction stated explicitly, node by node) came back with
  the causal order reversed, double-headed arrows, and a garbled timer.
  **Rule of thumb: if the motion's ORDER carries the meaning (A must feed
  B must feed C), it's an editor-built motion graphic, not a generation.**
  Generated video is for texture and vibe — ambient motion, b-roll-ish
  loops — where no specific sequence has to be correct.
- Output convention: generation runs in content-marketing
  (`videos/ftc-software-robotics-epNN/assets/`, `assets-v2/` for revision
  passes) — but that output is **workshop scratch, not the deliverable**.
  Curated finals get promoted to `production/epNN/` on the lesson's branch
  in FtcRobotController, named `ENN-CC-slug.ext` by script cue number,
  with an `ASSETS.md` cue map, and the script gets back-edited to match
  (`[Asset:]` refs, final prompt text, Rule-3 reclassifications). Full SOP:
  `docs/lessons/OPERATOR-RUNBOOK.md`, "Asset pass SOP". A pass that leaves
  the script and asset set out of lockstep is not finished — lesson 01
  drifted this way and it was caught during production.

## Reclassification checklist — APPLIED 2026-07-05

The checklist below was executed across all 12 scripts (lesson 01 in its
script-sync pass; 02–12 in one sweep). Every code/UI/terminal/telemetry
visual is now `[Screen:]` + `[Edit:]`, order-bearing motion cues are
editor-built, text-heavy cards are editor slides, and sim-footage video
prompts became real sim captures (Rule 6). What remains `[Prompt:]` /
`[Video Prompt:]` in the scripts is generation-safe: metaphors, physics
diagrams, thumbnails, and one texture clip (L05 wheel close-up). Original
scope, kept for reference:

- **L01:** @TeleOp→menu callout; three-region color-boxed HelloWorld;
  the control-loop clip (order-bearing motion — see the video note above)
- **L03:** OpMode skeleton with callouts; INIT/MATCH split file diagram
- **L06:** before/after code split
- **L07:** ARRANGE/ACT/ASSERT banded test
- **L08:** dashboard-build split
- **L10:** three-exit-doors while-line anatomy
- **L11:** the one-line diff
- **L12:** config-screen collage (use real DS screenshots + drawn wires)

Everything still cued for generation in the scripts is fine under Rules
1–4; harden the prompt text (Rule 1 object descriptions, Rule 2 pinned
structure, Rule 4 quoted text) during each lesson's asset pass.
