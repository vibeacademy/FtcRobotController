# Visual Prompt Strategy

Learned by generating lesson 01's full asset set (9 stills + 1 video,
Gemini 2.5 Flash Image + Veo 3 via content-marketing's `generate_assets.py`)
and reviewing every output. Scorecard: 3 usable as-generated, 4 fixed by
prompt revision, 2 reclassified out of generation entirely. Apply this to
lessons 02–12 before generating anything.

## The Five Rules

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

## Tooling notes

- `generate_assets.py <script.md>` parses ALL `[Prompt:]`/`[Video Prompt:]`
  pairs — including documentation examples in the script header. Strip the
  "Production key" lines before running, or keep literal cue examples out
  of scripts (scripts 02–12 already avoid this; lesson 01's header was
  fixed by preprocessing).
- Images ≈ $0.01–0.02 each (Gemini Flash); video ≈ $0.10–0.20 (Veo 3).
  Iterating on stills is cheap — plan a v1 → review → v2 pass per lesson.
  Iterating on video is not — get motion prompts right first try:
  explicitly state flow direction ("pulse travels clockwise: gamepad, then
  chip, then motors, then back to gamepad"), or the model muddles it.
- Output convention: `content-marketing/videos/ftc-software-robotics-epNN/
  assets/` (+ `assets-v2/` for revision passes), manifest.json included.

## Reclassification checklist (code-ish visuals → edit-time overlays)

These scripted `[Prompt:]` visuals are subject to Rule 3 — build them in
the editor over real screenshots instead of generating:

- **L01:** @TeleOp→menu callout; three-region color-boxed HelloWorld
- **L03:** OpMode skeleton with callouts; INIT/MATCH split file diagram
  (generate the *abstract* split-zones version only if no code visible)
- **L06:** before/after code split
- **L07:** ARRANGE/ACT/ASSERT banded test
- **L08:** dashboard-build split (the video prompt: keep motion abstract,
  no readable code/UI)
- **L10:** three-exit-doors while-line anatomy
- **L11:** the one-line diff
- **L12:** config-screen collage (use real DS screenshots + drawn wires)

Everything else in the scripts (metaphors, arenas, scoreboard stat cards,
loop diagrams) generates fine under Rules 1–4.
