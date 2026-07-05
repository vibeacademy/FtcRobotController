---
description: Review a pull request for FTC code quality, safety, and standards compliance. Posts GO/NO-GO recommendation.
---

# Review PR Command

You are the **code-reviewer** agent. Review the specified PR (or find one in In Review).

## Arguments

- `$ARGUMENTS` - PR number (optional, will find one if not provided)

## Instructions

1. **Switch to va-reviewer account**:
   ```bash
   gh auth switch --user va-reviewer
   ```

2. **Find PR to review** (if not specified):
   ```bash
   gh pr list --repo tck517/FtcRobotController --state open
   ```

3. **Get PR details**:
   ```bash
   gh pr view {number} --repo tck517/FtcRobotController
   gh pr diff {number} --repo tck517/FtcRobotController
   ```

4. **Review against checklist**:

   ### Safety (BLOCKING)
   - [ ] Motor powers bounded [-1.0, 1.0]
   - [ ] Timeout protection on blocking ops
   - [ ] `opModeIsActive()` in loops
   - [ ] No infinite loops

   ### FTC Compliance
   - [ ] Correct annotations
   - [ ] Proper OpMode structure
   - [ ] Telemetry updates

   ### Hardware Abstraction
   - [ ] Uses interfaces (IDrivetrain, IArm, etc.)
   - [ ] RobotConfig for hardware setup
   - [ ] Null checks for optional hardware

   ### Code Quality
   - [ ] No magic numbers
   - [ ] Meaningful names
   - [ ] No duplication

5. **Post review comment**:
   ```bash
   gh pr comment {number} --body "## Code Review..."
   ```

## Output Format

Post a detailed comment with:
- Summary of changes
- Checklist results
- Any issues found (with file/line references)
- **GO** or **NO-GO** recommendation

## Constraints

- Do NOT merge the PR
- Do NOT approve in GitHub UI
- Only post written recommendation
