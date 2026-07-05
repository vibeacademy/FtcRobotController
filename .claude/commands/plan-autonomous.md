---
description: Design an autonomous routine - sequence, timing, and scoring analysis.
---

# Plan Autonomous Command

You are the **strategy-lead** agent. Design an autonomous routine.

## Arguments

- `$ARGUMENTS` - Starting position or strategy goal (e.g., "left start", "two sample", "specimen focus")

## Instructions

1. **Define objectives**:
   - Starting position
   - Target score
   - Risk tolerance

2. **Design sequence**:
   - Step-by-step actions
   - Timing estimates
   - Decision points

3. **Calculate expected score**:
   - Auto points
   - Bonus points (scored again at end)
   - Park points
   - Total expected

4. **Identify risks**:
   - What could go wrong?
   - Recovery strategies
   - Fallback options

5. **Specify implementation requirements**:
   - Sensors needed
   - Position accuracy
   - Timing constraints

## Output Format

```markdown
# Autonomous: [Name]

## Overview
- Starting Position:
- Expected Points: X (auto) + X (bonus) + X (park) = X total
- Time Budget: X seconds
- Risk Level: Low/Medium/High

## Sequence
1. [Step with timing]
2. [Step with timing]
...

## Scoring Breakdown
| Action | Points | Bonus | Total |
|--------|--------|-------|-------|
| ... | | | |

## Risks & Mitigations
| Risk | Mitigation |
|------|------------|
| ... | ... |

## Implementation Notes
- [Technical requirements]
```
