# Session Handoff — read this first on the new machine

Written 2026-07-05 at the end of a long working session, for the next
Claude session (and the human operator) picking this project up on a
different computer. Everything of value is committed to this repo or
pushed to a remote; this file is the map.

## What this project is

FTC robot code repo (`vibeacademy/FtcRobotController`) doubling as the
foundation of the **Software Robotics** YouTube lesson series on the
vibeacademy channel. GembaFlow agent workflow (agents + commands in
`.claude/`), board at **vibeacademy project 32** ("FTC DECODE 2025-26").

## State: what's DONE and merged to master

- **Workflow tooling**: `/eli5`, `/log-session`, `/validate-memory`
  commands; codex seam (ftc-developer delegates code-writing to codex CLI,
  gates in `scripts/codex-gates/`); operator manual
  (`docs/OPERATOR-MANUAL.md`); session journal 2026-07-03.
- **Ticket #4**: JUnit test source set — 18 tests green via
  `./gradlew :TeamCode:testDebugUnitTest` (needs `local.properties`, see
  machine setup).
- **Lesson series, fully authored**: plan + persona (`docs/lessons/README.md`
  — the persona and banned-vocab table are LAW for all series content),
  12 lesson packets (`docs/lessons/lesson-NN.md`), 12 full shooting scripts
  (`docs/lessons/scripts/`), operator runbook
  (`docs/lessons/OPERATOR-RUNBOOK.md`), visual prompt strategy
  (`docs/lessons/VISUAL-PROMPT-STRATEGY.md` — in PR #28 if not yet merged),
  `LESSONS.md` index at repo root.
- **12 lesson branches live** (`lesson-01-hello-robot` …
  `lesson-12-competition-ops`), each cut from the previous, each compiling,
  each with a learner-facing `LESSON.md`. These are permanent teaching
  artifacts — NEVER merge or delete them.
- **Simulator pinned**: `vibeacademy/virtual_robot` fork at `13a8bc80f501`
  (= upstream Beta8397 `6a65ea57e90b` + 3 FTC-SDK-parity shims we added:
  TouchSensor, SwitchableLight, Color.colorToHSV). Apache-2.0
  (LEGAL/LICENSE.txt — GitHub doesn't detect it). Full lesson codebase
  compiles against it; all five drivable OpModes runtime-verified with a
  gamepad. Upgrade policy in the runbook.
- **Lesson 01 visual assets generated** (~$0.35, 7 of 8 stills
  camera-ready): `content-marketing/videos/ftc-software-robotics-ep01/`
  (`assets/` = v1, `assets-v2/` = the four regenerations). Committed on
  content-marketing branch `feat/openclaw-skills`.

## Open items (check `gh pr list` — states may have moved)

- **PR #28**: visual prompt strategy doc — merge it.
- **This handoff PR**: also carries the previously-untracked GembaFlow
  files (agents, remaining commands, CLAUDE.md, .mcp.json, docs/, ADR,
  HelloWorld.java on master) that existed only on the old machine.
- **Veo loop clip** (lesson 01, asset 004): conceptually right, flow
  direction muddled — review in motion before re-spending ~$0.15.
  Motion-prompt fix: state flow direction explicitly ("pulse travels
  clockwise: gamepad → chip → motors → gamepad").

## New machine setup checklist

1. **Clone** FtcRobotController + content-marketing + the sim fork
   (`vibeacademy/virtual_robot` — clone the FORK, never upstream).
2. **gh auth**: three accounts — va-worker (worker), va-reviewer
   (reviewer), tck517. `gh repo set-default vibeacademy/FtcRobotController`
   in the repo (prevents gh resolving the upstream FIRST repo — this bit
   us).
3. **Android builds**: create `local.properties` with
   `sdk.dir=<path to Android SDK>` (never commit it). Verify:
   `./gradlew :TeamCode:testDebugUnitTest` → 18 green.
4. **Simulator**: IntelliJ IDEA CE + a **JavaFX-bundled JDK** (BellSoft
   Liberica 17 **Full** — the standard Liberica looks identical and has no
   JavaFX; this is the #1 setup trap, documented in lesson-04's LESSON.md).
   Run config "Main" → `VirtualRobotApplication`. Select **Mecanum Bot**.
5. **Codex seam** (only if using `/work-ticket` delegation): npm-global
   `@openai/codex` (was 0.142.5), `codex login` — and CRITICALLY, do NOT
   pin `model = "gpt-5-codex"` in `~/.codex/config.toml` on a ChatGPT
   login; the unpinned default works. Flag is `--output-last-message`
   (not `-o` as older docs say).
6. **Asset generation**: `REPLICATE_API_TOKEN` env var; venv with
   `pip install -r content-marketing/generate-images/requirements.txt`.
7. **Memory caveat**: the previous machine's Claude auto-memory and memory
   MCP graph do NOT transfer. Every load-bearing fact was re-encoded in
   this file and the repo docs. The memory MCP server starts empty —
   `/log-session` will rebuild it over time.

## Knowledge that lived only in local memory (re-encoded here)

- The **board is vibeacademy project 32**; the `/work-ticket` command
  boilerplate still says `tck517` project 5 — known bug, fix on next
  touch of that command.
- **Series audience** (user feedback, firm): 12–18yo STEM-bound boy, never
  adult engineers. Persona + banned-vocabulary swap table in
  `docs/lessons/README.md` "Voice Calibration". The lesson-01 script voice
  is USER-VALIDATED — match it.
- **Codex seam history**: it was blocked four times by codex-external
  issues (stale token, ChatGPT-account model entitlement, npm/PATH
  version confusion, unfunded API-key org) before the root cause turned
  out to be the config.toml model pin. Full trail in the merged session
  journal + `Lesson-*` entities (old machine only) + this summary.
- **GembaFlow lineage**: this repo's workflow is a GembaFlow instance;
  template `vibeacademy/gembaflow`, changes via `vibeacademy/gembaflow-meta`.
  Ticket-format rendering bug was fixed downstream in issues #4–#19 and
  reported upstream as gembaflow-meta#150.

## Where the work continues (in order)

1. Merge PR #28 + this handoff PR.
2. Runbook Phase 0 leftovers (all human): recording rig + one test clip;
   YouTube playlist; lesson 01 publish date.
3. Reclassification pass on scripts 02–12 per
   `VISUAL-PROMPT-STRATEGY.md` (code-ish visuals → `[Screen:]` overlays)
   — one editing session, delegable.
4. Film lesson 01 (script: `docs/lessons/scripts/lesson-01-full-script.md`,
   assets ready, shot list at the script's bottom).
5. Per-lesson pipeline per the runbook: publish → pinned comment →
   backfill video URL into `LESSONS.md` + branch `LESSON.md`.

## Repo conventions the next session must respect

- master is protected — every change via branch + PR; the human merges.
- Worker never merges PRs, never moves board tickets to Done.
- Lesson branches are permanent; consecutive-branch diffs are teaching
  material.
- `.claude/settings.local.json` and `local.properties` stay machine-local.
- Conventional commits; PRs end with the Claude Code attribution footer.
