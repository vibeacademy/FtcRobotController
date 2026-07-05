---
description: Analyze DECODE scoring strategies and optimize point generation.
---

# Analyze Scoring Command

You are the **strategy-lead** agent. Analyze scoring optimization for DECODE™ presented by RTX.

## Arguments

- `$ARGUMENTS` - Specific question or "full analysis" (optional)

## Instructions

1. **Review current robot capabilities**:
   - What can the robot score?
   - Current cycle times
   - Endgame capability

2. **Calculate points per second**:
   - Each scoring method
   - Factor in travel time
   - Factor in reliability

3. **Compare strategies**:
   - Classified vs Overflow focus
   - Pattern matching priority
   - Aggressive vs Conservative

4. **Recommend priorities**:
   - What to focus on
   - What to skip
   - Endgame strategy

## DECODE Scoring Reference

### Autonomous Period
| Action | Points |
|--------|--------|
| LEAVE (exit starting position) | 3 |
| Classified Artifact | 3 each |
| Overflow Artifact | 1 each |
| Pattern Match (correct color on RAMP) | 2 each |

### TeleOp Period
| Action | Points |
|--------|--------|
| Classified Artifact | 3 each |
| Overflow Artifact | 1 each |
| Depot Artifact | 1 each |
| Pattern Match | 2 each |
| Motif completion | 2 each |

### Endgame (BASE)
| Action | Points |
|--------|--------|
| Base (Partial Return) | 5 |
| Base (Full Return) | 10 |
| Base Bonus (2 robots return) | 10 |

### Ranking Points (RP)
- Movement RP threshold: 16 points (LEAVE + BASE)
- Goal RP threshold: 36 artifacts

### Key Mechanics
- **MOTIF**: Pattern of 2 purple + 1 green artifacts (GPP, PGP, or PPG) - randomized by OBELISK
- **CLASSIFIED**: Artifacts enter GOAL through top, exit under archway, pass through diverting SQUARE
- **PATTERN**: Artifacts on RAMP matching the MOTIF sequence score bonus points
- **GATE**: Can be opened to continue classifying additional artifacts

## Output

Provide actionable recommendations with data to support them.
