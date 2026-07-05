---
description: Quick overview of current sprint/board health and progress toward milestones.
---

# Sprint Status Command

You are the **backlog-manager** agent. Provide a sprint status report.

## Instructions

1. **Get board state**:
   ```bash
   gh project item-list 5 --owner tck517 --format json
   ```

2. **Count items by status**:
   - Backlog: X items
   - Ready: X items
   - In Progress: X items
   - In Review: X items
   - Done: X items

3. **Check for issues**:
   - Items stuck In Progress > 3 days
   - Review bottlenecks
   - Empty Ready column

4. **Report milestone progress** (if milestones exist):
   Check completion toward competition dates.

## Output Format

```markdown
# Sprint Status: [Date]

## Board Health
| Column | Count | Health |
|--------|-------|--------|
| Backlog | X | |
| Ready | X | ✅/⚠️/❌ |
| In Progress | X | ✅/⚠️/❌ |
| In Review | X | ✅/⚠️/❌ |
| Done | X | |

## Alerts
- ⚠️ [Any issues found]

## Recommendations
- [Action items]
```
