# Operator Runbook — Software Robotics Series

What has to happen, in what order, to turn the 12 lesson packets into 12
published videos. Split by who must do it: **[YOU]** = only the human
operator can do this (voice, camera, accounts, judgment). **[AGENT]** =
delegable to Claude/agents, you review. **[DONE]** = already exists.

## Current state

- [DONE] Series plan + persona + 12 lesson packets (`docs/lessons/`)
- [DONE] All 12 code branches, compiling, with learner-facing LESSON.md
- [DONE] Media packets: title options, descriptions w/ chapters, tags,
  thumbnail prompts, pinned-comment text
- [DONE] Simulator verification, both halves: full codebase compiles
  against the pinned fork (`13a8bc80f501`), and all five sim OpModes
  (lessons 05, 08, 09, 10, 11) runtime-verified by driving them with a
  gamepad (2026-07-04). The wrong-JDK JavaFX trap is documented in
  lesson-04's LESSON.md.
- **Not done:** everything below except Phase 0 item 2.

---

## Phase 0 — One-time setup (before any filming)

In order:

1. **[YOU] Merge PR #22** (the plan) and PR #20/#21 if you want the repo
   tidy first. The lesson *branches* are already live regardless — but the
   `LESSONS.md` index only lands on master when #22 merges, and every
   branch LESSON.md links to it. ~10 min.
2. **[DONE 2026-07-04] Verify simulator compatibility.** The
   compile half is DONE: the full lesson codebase (all OpModes + the entire
   hardware layer) compiles clean against the pinned fork
   (`vibeacademy/virtual_robot` @ `13a8bc80f501`). Three FTC SDK APIs the
   sim lacked (TouchSensor, SwitchableLight, Color.colorToHSV) were added
   to the fork as compile shims. What remains for you: clone the fork, run
   the sync command, and actually DRIVE `SimTeleOp` (04/05),
   `DebugChallengeOpMode` (08), and run `ParkAuto`/`Tune kP` (10/11) with a
   gamepad — confirming runtime behavior, not just compilation. ~20 min.
   [AGENT] fixes anything you find.

   *Fork policy:* learners always clone the fork, never upstream — published
   videos can't rot. virtual_robot is Apache-2.0 (LEGAL/LICENSE.txt; GitHub
   doesn't detect it in the subfolder), so the fork may carry minimal
   SDK-parity shims — keep them small, attributed, and documented in the
   fork's commit messages. To upgrade the sim later: fetch upstream into
   the fork on a branch, rebase the shims, re-run this verification, then
   (and only then) fast-forward the fork's master and update the pin SHA
   here and in lesson-04's LESSON.md.
3. **[YOU] Recording setup, once:** the operator records and edits in
   **Descript** (camera + screen + VO + scene layouts in one tool), plus a
   decent mic and a gamepad. Make one test recording of driving the sim
   while narrating — the edit-workflow shakedown; do it before filming so
   you learn what your future self needs from a script (pause points,
   on-screen beats, layout switches). ~1–2 hrs.
4. **[YOU] Channel prep:** create the "Software Robotics" playlist, decide
   publish cadence (recommended: 2/week July–August so Arc 1 is live before
   September kickoff), and pick lesson 01's target publish date. Everything
   downstream schedules off that date. ~30 min.
5. **[AGENT] Optional: GembaFlow tickets.** One ticket per lesson
   (`content` label) so the board tracks production. Say the word.

---

## Phase 1 — Per-lesson production pipeline

Repeat for each lesson, **in curriculum order** (scripts reference earlier
lessons' callbacks; filming out of order breaks continuity). Steps in
execution order; the human-only steps are the schedule's critical path.

| # | Deliverable | Who | Notes / time |
|---|------------|-----|--------------|
| 1 | **Full script** — blow out the starter script: every (Audio) block written word-for-word, section beats become sentences, visuals confirmed | [AGENT] drafts, **[YOU] voice-pass** | The draft must pass YOUR read-aloud test: if a sentence doesn't sound like you, rewrite it. This is the highest-leverage human hour in the pipeline. ~1–2 hrs total |
| 2 | **Static visuals** — generate images from every `[Prompt:]` in the script | [AGENT] generates (content-marketing repo has `generate-images` tooling), **[YOU] curate** | Regenerate rejects before edit day, not during. Pass isn't done until the Asset pass SOP below is complete. ~30 min human |
| 3 | **Video visuals** — the ≤2 `[Video Prompt:]` clips per script | [AGENT] generates, **[YOU] curate** | Same as above — SOP applies |
| 4 | **Screen recordings** — IDE typing, sim driving, terminal runs, telemetry panels | **[YOU]** | The code is already on the lesson's branch — check it out, follow the script's Implementation beats. Record MORE than you need; b-roll of the sim driving is reusable across lessons. ~1–2 hrs |
| 5 | **Voiceover / on-camera** — record the (Audio) track | **[YOU]** | Nobody else has your voice. Batch-record 2–3 lessons per session once warmed up. ~30–60 min per lesson |
| 6 | **Edit** — assemble VO + screen captures + generated visuals per the script's timeline | **[YOU]** (or an editor you brief with the script) | The script's `[Visual:]` cues + timestamps ARE the edit decision list, and each `[Layout:]` cue names the Descript scene layout to apply (conventions below). ~2–4 hrs; the biggest time sink |
| 7 | **Thumbnail** — generate from the full script's thumbnail prompt (the packet's copy is the superseded draft), pick/tweak | [AGENT] generates, **[YOU] pick** | ~15 min human |
| 8 | **Metadata** — title, description, tags, chapters | [AGENT] final-checks against the edit (chapter timestamps shift!), **[YOU] paste** | Packet has it all drafted; only the chapter times need syncing to the real cut |
| 9 | **Upload + publish** — schedule on YouTube, add to playlist | **[YOU]** | Account access = you. Schedule, don't insta-publish; it enables step 10 prep |
| 10 | **Pinned comment** — post the packet's pinned-comment text | **[YOU]** | Do it within minutes of publish; it's the whole repo-link mechanism |
| 11 | **Link backfill** — video URL into `LESSONS.md` (master), the branch's `LESSON.md`, and the previous lesson's "next" pointer if you're adding video links there | [AGENT] | One commit per publish; delegable entirely |
| 12 | **Distribution** — X thread / shorts / community post per content-marketing conventions | [AGENT] drafts, **[YOU] post** | Optional per lesson; at minimum do lessons 01, 04 (arc payoff), 07, 12 |
| 13 | **Engagement** — reply to comments, especially error-message help on 02 and challenge submissions on 05/08/10 | **[YOU]** | The packets *promise* this ("post your error, I read everything"). Budget 20 min/day the first week after each publish. This is where authority actually accrues |

### Descript layout conventions

Every visual cue in the scripts carries a `[Layout:]` line naming the
Descript scene layout (default layout pack vocabulary — survives a
Change-Layout-Pack re-skin). The operator is on camera throughout, so:

| `[Layout:]` | Used for |
|---|---|
| **Camera** (default — no cue needed) | Connective narration, no visual |
| **Screen** | Live captures: IDE, sim, terminal, browser, DS — camera bubble on; annotations (arrows, tints, boxes) added as Descript layers |
| **Media** | Generated stills, exported motion graphics/composites — full-bleed for impact beats |
| **List** | Rule-4 text cards (gotchas, checklists, the L05 sign table) — type the exact words natively; garble-proof by construction |
| **Quote** | Mental-model one-liners, held on screen |
| **Big Fact** | Single-number punch beats ("15 LINES") |

Scripts specify layout per beat; scene boundaries and timing stay
edit-day decisions. When a new lesson's script is drafted or an asset
pass back-edits one, `[Layout:]` lines are part of the deliverable.

### Asset pass SOP (steps 2–3 aren't done until all of this is)

Learned the hard way on lesson 01, where the script drifted from the
generated set and the gap was caught during production. A generation pass
ends with the script and the asset set in lockstep — [AGENT] work, all of
it, in one PR pair:

1. **Generate** with content-marketing's tooling (that repo keeps the venv
   + `generate_assets.py`); iterate v1 → review → v2 per
   `VISUAL-PROMPT-STRATEGY.md`. The raw workshop output is scratch — do
   not treat it as the deliverable.
2. **Promote finals into THIS repo, on the lesson's branch**, under
   `production/epNN/`, named `ENN-CC-slug.ext` where `CC` is the visual's
   cue number in script order. Add/update the folder's `ASSETS.md` cue
   map. Cue numbers are reserved even for non-generated visuals, so the
   folder reads in script order with intentional gaps.
3. **Back-edit the script on master, same pass:** every `[Prompt:]` /
   `[Video Prompt:]` carries the text that actually produced (or will
   reproduce) the final, followed by an `[Asset: production/epNN/…]` line;
   any visual reclassified during review (Rule 3 code/UI cases) is flipped
   to `[Screen:]` + `[Edit: … export as production/epNN/ENN-CC-….png]`;
   the shot list reflects the real capture needs.
4. **Definition of done:** every generated cue in the script points at a
   file that exists on the lesson branch (or is explicitly marked
   PENDING with a reason), and `ASSETS.md` has no orphans. If the script
   and the folder disagree, the pass is not finished.

During post, export edit-time composites (and the approved video clips)
into the same `production/epNN/` folder under their reserved cue names —
the folder ends production complete, not just generation complete.

**Rough per-lesson human time: 6–9 hours.** The agent-delegable steps
(1-draft, 2, 3, 7, 8, 11, 12-draft) cut maybe a third of the total —
filming, VO, and editing stay yours.

---

## Phase 2 — Cross-lesson orchestration

**The dependency rule:** a lesson's *script* can be finalized any time
(code exists), but its *screen recordings* need the branch checked out, and
its *publish* needs the previous lesson live (the scripts say "last
lesson…"). So the pipeline stages overlap like this:

```
Lesson N:    script → visuals → record → edit → publish
Lesson N+1:          script → visuals →         record → edit → publish
```

**Batching that works:**
- Scripts: finalize 2–3 ahead of filming (they're the cheapest step).
- VO: batch-record 2–3 lessons per mic session.
- Thumbnails + metadata: batch with each script.

**Batching that fails:** editing more than one lesson at once (each edit
teaches you something the next script should absorb), and publishing ahead
of engagement capacity — a published lesson with unanswered comments works
against the authority goal.

**Suggested calendar to kickoff (Sept):** at 2/week starting mid-July,
Arc 1 (01–04) is live by end of July — that's the arc that catches "how do
I start FTC programming" searches before the season. Arcs 2–3 carry
August. At 1/week, start Arc 1 now and accept finishing mid-season (fine —
lessons 9–12 land exactly when teams hit autonomous panic).

---

## Pre-publish gate (per lesson, 5 minutes)

- [ ] Branch link in description + pinned comment resolves (test in an
      incognito window)
- [ ] `LESSON.md` on the branch matches what the video actually built
      (scripts drift during filming — reconcile before publish, the branch
      is fixable, [AGENT] can do the edit)
- [ ] Chapter timestamps match the final cut
- [ ] Read the description's first two lines on a phone — that's all most
      viewers see
- [ ] Previous lesson's video is live and its "next" hook matches this one

---

## What to delegate back, verbatim

Things you can hand to a Claude session as-is:

- "Expand the lesson NN starter script into a full script" (it has the
  persona + voice rules in `docs/lessons/README.md` to obey)
- "Generate the images for lesson NN's script" (content-marketing repo's
  image tooling)
- "Backfill the video URL for lesson NN: <url>" (LESSONS.md + branch
  LESSON.md commits)
- "Draft the X thread for lesson NN" (distribution conventions are in
  content-marketing)
- "Reconcile lesson NN's branch LESSON.md with the final cut: <what
  changed>"

The four things that are always yours: **voice, camera, publish button,
and replies.** Everything else is a review job.
