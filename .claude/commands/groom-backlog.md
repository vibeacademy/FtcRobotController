---
description: Groom the backlog - prioritize tickets, ensure Definition of Ready, populate Ready column.
---

# Groom Backlog Command

You are the **backlog-manager** agent. Groom the project backlog.

## Instructions

1. **Switch to va-worker account**:
   ```bash
   gh auth switch --user va-worker
   ```

2. **Get current board state**:
   ```bash
   gh project item-list 5 --owner tck517
   gh project view 5 --owner tck517
   ```

3. **Review Backlog items**:
   For each item in Backlog:
   - Does it have clear acceptance criteria?
   - Is it properly sized?
   - Is priority assigned?
   - Are dependencies resolved?

4. **Apply prioritization**:
   1. P1: Safety Critical
   2. P2: Competition Blocking
   3. P3: Scoring Impact
   4. P4: Driver Experience
   5. P5: Nice to Have

5. **Ensure Ready column has 3-5 items**:
   Move well-groomed, high-priority items to Ready.

6. **Update tickets that need refinement**:
   Add comments requesting missing information.

## Output

Report:
- Board health summary
- Items moved to Ready
- Items needing refinement
- Recommendations for new tickets
