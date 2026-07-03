---
description: Pick up the next ticket from Ready column and implement it. Creates feature branch, implements code, and creates PR.
---

# Work Ticket Command

You are the **ftc-developer** agent. Pick up and implement the next ticket.

## Instructions

1. **Switch to va-worker account**:
   ```bash
   gh auth switch --user va-worker
   ```

2. **Find the top ticket in Ready column**:
   ```bash
   gh project item-list 5 --owner tck517 --format json
   ```
   Look for items with Status = "Ready", pick the highest priority one.

3. **Move ticket to In Progress**:
   Update the ticket status and assign to va-worker.

4. **Create feature branch**:
   ```bash
   git checkout main && git pull
   git checkout -b feature/issue-{number}-{short-description}
   ```

5. **Implement the ticket** (via the codex seam — see the agent's Codex
   Delegation Protocol and "Codex-seam gates" below):
   - Read the acceptance criteria carefully
   - Do pre-implementation research (Claude), then delegate code-writing to
     `codex exec` with the Scope Constraint prompt sections
   - Exception: tickets labeled `P1-critical` are implemented by Claude
     directly, never delegated
   - Use hardware abstraction layer
   - Add tests if appropriate
   - Include telemetry for debugging

6. **Commit and push**:
   ```bash
   git add .
   git commit -m "feat(scope): description

   - Detail 1
   - Detail 2

   Closes #{issue_number}"
   git push -u origin feature/issue-{number}-{short-description}
   ```

7. **Create PR**:
   ```bash
   gh pr create --title "feat: description" --body "..." --base main
   ```

8. **Move ticket to In Review**:
   Update status and request review.

## Codex-seam gates

When the worker agent (`.claude/agents/ftc-developer.md`) is operating
in codex-seam mode (frontmatter `engine: claude+codex-seam`), the
orchestration layer runs two mechanical gates around codex invocation. Both
are non-optional; there is no skip flag.

### Gate 1: forbidden-pattern scan (post-codex)

After `codex exec` (or `codex exec resume`) returns 0, before running
`./gradlew :TeamCode:assembleDebug` / `./gradlew :TeamCode:testDebugUnitTest`, run:

```bash
git diff --no-color HEAD > /tmp/codex-diff-{N}.patch
scripts/codex-gates/forbidden-pattern.sh /tmp/codex-diff-{N}.patch
```

If exit 1, capture the matches from stderr and feed them back via
`codex exec resume <session_id>` as the failure output. This consumes one
retry from the 2-retry budget documented in the Codex Delegation Protocol
("Quality Gates and Retry").

Per-line opt-out for genuinely intentional patterns (e.g., a test that
asserts unreachable-code detection): append `# noqa: codex-gate` or
`// noqa: codex-gate` to the matching line.

Catches the "preserve-old-as-dead" shape — an always-false guard
preserving an old branch, which compile/test don't catch and that
positive-presence grep asserts can't detect. The Java pattern list also
flags placeholder throws ("not implemented") and empty catch blocks.

### Gate 2: transitive-references pre-impl scan (pre-codex)

If the ticket's §A "Environment Context" section lists deletions, run:

```bash
scripts/codex-gates/transitive-refs.sh <deletion-paths...>
```

If the output is non-empty, PREPEND a "Transitive references found" section to
the codex prompt naming each referencing file, and instruct codex to either
also modify those files or explain in its final message why each can stay.

Catches the "scope-miss" shape where the pre-impl plan named the obvious
files but missed a co-located reference (an OpMode registering a deleted
subsystem, a test fixture referencing the deletion target, a doc embedding
the deleted class name, etc.).

## Constraints

- Use interfaces from `hardware/interfaces/`
- Never hardcode device names
- Include timeout protection on loops
- Check `opModeIsActive()` in while loops
- Handle null hardware gracefully

## When Done

Report what was implemented and the PR number. Do NOT merge the PR.
