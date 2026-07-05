# Driver Guide

## Control Mapping

### Gamepad 1 (Driver - Movement)

| Control | Action |
|---------|--------|
| **Left Stick Y** | Drive forward/backward |
| **Left Stick X** | Strafe left/right |
| **Right Stick X** | Rotate left/right |
| **Left Bumper** | Slow mode (50% speed) |
| **Right Bumper** | Turbo mode (100% speed) |
| **D-Pad** | Fine positioning |

### Gamepad 2 (Operator - Mechanisms)

| Control | Action |
|---------|--------|
| **Left Stick Y** | Manual arm control |
| **A Button** | Arm to ground pickup |
| **B Button** | Arm to low basket |
| **Y Button** | Arm to high basket |
| **X Button** | Arm to chamber height |
| **Left Bumper** | Close claw |
| **Right Bumper** | Open claw |
| **D-Pad Up** | [Reserved for future] |
| **D-Pad Down** | [Reserved for future] |

---

## Pre-Match Checklist

### Before Entering Queue
- [ ] Batteries charged (> 13V)
- [ ] Robot inspected
- [ ] Correct OpMode selected
- [ ] Alliance color confirmed

### On Field Setup
- [ ] Robot placed at starting mark
- [ ] Sample preloaded (if applicable)
- [ ] Controller connected
- [ ] IMU calibrated (robot still)

### After INIT
- [ ] Telemetry shows "Initialized"
- [ ] All hardware detected
- [ ] Arm at starting position

---

## Match Phases

### Autonomous (0:00 - 0:30)
- **Driver Action**: NONE - hands off controllers
- Watch for:
  - Robot completing sequence
  - Any collision or issue
  - Scoring confirmation

### TeleOp Start (0:30)
1. Wait for buzzer
2. Take control immediately
3. Begin first scoring cycle

### TeleOp Phase 1 (0:30 - 1:30)
**Goal**: Maximize scoring cycles

- Focus on high-value targets (baskets)
- Maintain steady pace
- Communicate with alliance partner

### TeleOp Phase 2 (1:30 - 2:00)
**Goal**: Prepare for endgame

- Complete current cycle if possible
- **Do NOT start new cycle after 1:50**
- Position toward submersible

### Endgame (2:00 - 2:30)
**Goal**: Execute ascent

1. At 2:00, abandon any scoring
2. Drive to submersible
3. Execute ascent sequence
4. Hold position until buzzer

---

## Scoring Quick Reference

| Target | Points | Priority |
|--------|--------|----------|
| High Basket | 8 | ⭐⭐⭐ |
| High Chamber | 10 | ⭐⭐⭐ |
| Low Basket | 4 | ⭐⭐ |
| Low Chamber | 6 | ⭐⭐ |
| Net Zone | 2 | ⭐ |
| Level 3 Ascent | 30 | If practiced |
| Level 2 Ascent | 15 | Default |
| Level 1 Ascent | 3 | Minimum |

---

## Common Situations

### Sample Dropped
1. Don't panic
2. Reposition to pickup
3. Try again if time permits
4. Skip to next action if behind

### Stuck/Collision
1. Back up slowly
2. Reassess situation
3. Take alternate route
4. Communicate with partner

### Mechanism Not Responding
1. Try manual control
2. If still stuck, focus on driving
3. Report issue post-match

### Running Low on Time
1. Abandon current cycle
2. Prioritize endgame
3. At minimum, park (3 pts)

---

## Communication Calls

### With Alliance Partner
- "Heading to basket" - claiming scoring position
- "Your turn" - yielding position
- "Need sample" - requesting pickup assistance
- "Going for climb" - starting endgame

### With Coach
- "Time check" - request time remaining
- "Switch target" - changing strategy
- "Help needed" - problem occurring

---

## Post-Match

### Immediately After
1. Note final score
2. Report any issues
3. Return robot to pit

### In Pit
1. Check for damage
2. Charge batteries
3. Review match video (if available)
4. Update strategy as needed

---

## Emergency Procedures

### Robot Won't Move
1. Check connection status
2. Restart controller
3. If persistent, call for field reset

### Mechanism Jammed
1. Don't force it
2. Use manual override if safe
3. May need to skip that mechanism for match

### Low Battery Warning
- If battery < 12V, swap immediately
- Always have backup charged
