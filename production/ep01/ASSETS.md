# Episode 01 Production Assets

Camera-ready assets for lesson 01 ("Robot Code Isn't What You Think").
One file per visual cue in the shooting script
(`docs/lessons/scripts/lesson-01-full-script.md` on master); the script's
`[Asset:]` lines point here. Naming: `E{episode}-{cue}-{slug}.{ext}` —
cue numbers follow script order, so gaps below are intentional.

| Cue | File | Status | Script beat |
|-----|------|--------|-------------|
| E01-01 | `E01-01-hook-dead-vs-scoring.png` | ✅ ready (v2 regen) | Hook — dead robot vs scoring robot |
| E01-02 | `E01-02-match-timeline.png` | ✅ ready (v2 regen) | What FTC Is — AUTO/TELEOP/ENDGAME bar |
| E01-03 | `E01-03-zero-dollar-advantage.png` | ✅ ready (v1) | What FTC Is — wrench/battery/code icons, $0 tag |
| E01-04 | `E01-04-control-loop.mp4` | ⏳ TO REGENERATE | Robot Code Runs a Loop — animated gamepad→chip→motors cycle |
| E01-05 | `E01-05-engine-vs-physics.png` | ✅ ready (v1) | Robot Code Runs a Loop — game engine vs physics split |
| E01-06 | `E01-06-teleop-menu-overlay.png` | 🎬 built in post | Reading Real Robot Code — @TeleOp → Driver Station menu callout |
| E01-07 | `E01-07-file-map-overlay.png` | 🎬 built in post | Reading Real Robot Code — color-boxed HelloWorld file map |
| E01-08 | `E01-08-why-software-wins.png` | ✅ ready (v2 regen) | Why Software Wins — three-claim scoreboard |
| E01-09 | `E01-09-thumbnail.png` | ✅ ready (v2 regen) | Thumbnail — "200 LINES" |

**⏳ E01-04 (video):** the v1 Veo clip had the flow direction muddled and
its file never left the old machine. Regenerate (~$0.15) with the fixed
motion prompt now in the script — it states the flow direction explicitly.
Drop the approved clip in here under this name.

**🎬 E01-06 / E01-07:** deliberately NOT generated — per
`docs/lessons/VISUAL-PROMPT-STRATEGY.md` Rule 3 (models invent fake code
and garble UI text), these are real-screenshot-plus-overlay composites you
build in the editor. When built, export them into this folder under the
reserved names so the set stays complete for reuse.

**Provenance:** generated 2026-07-05 with Gemini 2.5 Flash Image via
content-marketing's `generate_assets.py`. Raw v1/v2 workshop output and
`manifest.json` files (including the four v1 rejects) are archived on the
`feat/openclaw-skills` branch of `vibeacademy/content-marketing` under
`videos/ftc-software-robotics-ep01/` — historical only; this folder is the
source of truth.
