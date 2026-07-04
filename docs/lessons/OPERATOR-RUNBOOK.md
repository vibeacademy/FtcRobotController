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
- **Not done:** everything below.

---

## Phase 0 — One-time setup (before any filming)

In order:

1. **[YOU] Merge PR #22** (the plan) and PR #20/#21 if you want the repo
   tidy first. The lesson *branches* are already live regardless — but the
   `LESSONS.md` index only lands on master when #22 merges, and every
   branch LESSON.md links to it. ~10 min.
2. **[YOU] Verify simulator compatibility once.** Clone the pinned fork
   (`vibeacademy/virtual_robot`, frozen at `6a65ea57e90b`), run the sync
   command against it, and drive `SimTeleOp` (lesson 04/05 code) and
   `DebugChallengeOpMode` (lesson 08). This is the one thing never verified
   — if the sim's API rejects something, the fix must happen BEFORE those
   scripts are finalized, because scripts describe the code. ~45 min.
   [AGENT] can fix any deltas found.

   *Fork policy:* learners always clone the fork, never upstream — published
   videos can't rot. To upgrade the sim later: fetch upstream into the fork
   on a branch, re-run this verification, then (and only then) fast-forward
   the fork's master and update the pin SHA here and in lesson-04's
   LESSON.md. The fork stays byte-identical to some upstream commit — we
   don't modify it (upstream has no license file; a plain GitHub fork is
   within GitHub's ToS, modifications would be murkier).
3. **[YOU] Recording setup, once:** screen-capture tool (OBS is fine), a
   decent mic, a gamepad, and one test recording of you driving the sim
   while narrating. This test clip is your edit-workflow shakedown — do it
   before scripting so you learn what your future self needs from a script
   (pause points, on-screen beats). ~1–2 hrs.
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
| 2 | **Static visuals** — generate images from every `[Prompt:]` in the script | [AGENT] generates (content-marketing repo has `generate-images` tooling), **[YOU] curate** | Regenerate rejects before edit day, not during. ~30 min human |
| 3 | **Video visuals** — the ≤2 `[Video Prompt:]` clips per script | [AGENT] generates, **[YOU] curate** | Same as above |
| 4 | **Screen recordings** — IDE typing, sim driving, terminal runs, telemetry panels | **[YOU]** | The code is already on the lesson's branch — check it out, follow the script's Implementation beats. Record MORE than you need; b-roll of the sim driving is reusable across lessons. ~1–2 hrs |
| 5 | **Voiceover / on-camera** — record the (Audio) track | **[YOU]** | Nobody else has your voice. Batch-record 2–3 lessons per session once warmed up. ~30–60 min per lesson |
| 6 | **Edit** — assemble VO + screen captures + generated visuals per the script's timeline | **[YOU]** (or an editor you brief with the script) | The script's `[Visual:]` cues + timestamps ARE the edit decision list. ~2–4 hrs; the biggest time sink |
| 7 | **Thumbnail** — generate from the packet's thumbnail prompt, pick/tweak | [AGENT] generates, **[YOU] pick** | ~15 min human |
| 8 | **Metadata** — title, description, tags, chapters | [AGENT] final-checks against the edit (chapter timestamps shift!), **[YOU] paste** | Packet has it all drafted; only the chapter times need syncing to the real cut |
| 9 | **Upload + publish** — schedule on YouTube, add to playlist | **[YOU]** | Account access = you. Schedule, don't insta-publish; it enables step 10 prep |
| 10 | **Pinned comment** — post the packet's pinned-comment text | **[YOU]** | Do it within minutes of publish; it's the whole repo-link mechanism |
| 11 | **Link backfill** — video URL into `LESSONS.md` (master), the branch's `LESSON.md`, and the previous lesson's "next" pointer if you're adding video links there | [AGENT] | One commit per publish; delegable entirely |
| 12 | **Distribution** — X thread / shorts / community post per content-marketing conventions | [AGENT] drafts, **[YOU] post** | Optional per lesson; at minimum do lessons 01, 04 (arc payoff), 07, 12 |
| 13 | **Engagement** — reply to comments, especially error-message help on 02 and challenge submissions on 05/08/10 | **[YOU]** | The packets *promise* this ("post your error, I read everything"). Budget 20 min/day the first week after each publish. This is where authority actually accrues |

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
