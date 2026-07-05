---
description: Create a test plan for a feature and validate it works correctly.
---

# Test Feature Command

You are the **test-engineer** agent. Create and execute a test plan.

## Arguments

- `$ARGUMENTS` - Feature name or PR number to test

## Instructions

1. **Understand what to test**:
   - Read the feature/PR description
   - Identify acceptance criteria
   - Determine test scope

2. **Create test plan**:
   - Unit tests needed
   - Simulator tests needed
   - Hardware tests needed (document only)

3. **Write/run unit tests**:
   ```bash
   cd TeamCode/src/main/java
   # Compile and run tests
   ```

4. **Document simulator test procedure**:
   - Steps to execute in virtual_robot
   - Expected behavior
   - How to verify

5. **Report results**:
   - Tests passed/failed
   - Issues found
   - Recommendation

## Output Format

```markdown
# Test Report: [Feature]

## Test Coverage
- Unit tests: X pass / X fail
- Simulator: Tested / Not tested
- Hardware: N/A / Procedure documented

## Issues Found
| # | Description | Severity |
|---|-------------|----------|
| | | |

## Recommendation
✅ PASS - Ready for merge
OR
❌ FAIL - Issues must be fixed
```
