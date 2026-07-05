# FTC Robot Controller - Claude Code Configuration

This project uses Claude Code with specialized agents for FTC (FIRST Tech Challenge) robotics development.

## Project Overview

- **Season**: DECODE™ presented by RTX (2025-2026)
- **Repository**: vibeacademy/FtcRobotController
- **Project Board**: https://github.com/orgs/vibeacademy/projects/32
- **Simulator**: ~/projects/tck517/virtual_robot

## Agents

| Agent | Purpose | GitHub Account |
|-------|---------|----------------|
| `ftc-developer` | Implements OpModes and subsystems | va-worker |
| `code-reviewer` | Reviews PRs for safety and quality | va-reviewer |
| `systems-engineer` | Designs architecture and control systems | - |
| `test-engineer` | Creates tests and validates code | va-worker |
| `strategy-lead` | Plans autonomous and match strategy | - |
| `backlog-manager` | Manages tickets and sprint planning | va-worker |

## Commands

### Development
- `/work-ticket` - Pick up next ticket and implement
- `/review-pr [number]` - Review a pull request

### Architecture & Strategy
- `/design-subsystem [name]` - Design a new subsystem
- `/plan-autonomous [strategy]` - Design autonomous routine
- `/analyze-scoring` - Analyze scoring optimization

### Planning
- `/groom-backlog` - Prioritize and refine tickets
- `/sprint-status` - Current board health
- `/competition-prep` - Pre-competition checklist

### Testing
- `/test-feature [name]` - Create test plan and validate
- `/run-simulator` - Sync and test in virtual_robot

## Code Standards

### Directory Structure
```
TeamCode/src/main/java/org/firstinspires/ftc/teamcode/
├── hardware/
│   ├── interfaces/    # Hardware contracts
│   ├── real/          # FTC SDK implementations
│   └── mock/          # Test implementations
├── subsystems/        # High-level coordination
├── opmodes/           # TeleOp and Autonomous
├── autonomous/        # Auto utilities
├── util/              # Shared utilities
└── test/              # Unit tests
```

### Hardware Abstraction (REQUIRED)

All OpModes MUST use the hardware abstraction layer:

```java
// CORRECT
RobotConfig config = RobotConfig.mecanumDrive(
    "front_left_motor", "front_right_motor",
    "back_left_motor", "back_right_motor")
    .withArm("arm_motor")
    .withClaw("hand_servo");
RobotHardware robot = RobotHardware.createReal(hardwareMap, config);
robot.drivetrain.mecanumDrive(drive, strafe, turn);

// WRONG - Direct hardware access
DcMotor motor = hardwareMap.get(DcMotor.class, "motor");
```

### Safety Requirements

1. **Motor powers** must be bounded to [-1.0, 1.0]
2. **Blocking operations** must have timeout protection
3. **While loops** must check `opModeIsActive()`
4. **Optional hardware** must have null checks

```java
// Timeout protection example
ElapsedTime timer = new ElapsedTime();
while (arm.isBusy() && timer.seconds() < 3.0 && opModeIsActive()) {
    telemetry.addData("Status", "Moving arm...");
    telemetry.update();
}
```

### Commit Messages

Use conventional commits:
```
feat(subsystem): add arm position presets
fix(teleop): correct strafe direction
docs(readme): update control mappings
test(arm): add unit tests for limits
```

## Workflow

### Trunk-Based Development

1. `main` branch is protected
2. All work on feature branches: `feature/issue-{number}-{description}`
3. PRs required for all changes
4. Human approval required to merge

### Ticket Flow

```
Backlog → Ready → In Progress → In Review → Done
           ↑          ↑             ↑          ↑
    backlog-manager  ftc-developer  ftc-developer  HUMAN ONLY
```

### Review Process

1. `ftc-developer` creates PR, moves to In Review
2. `code-reviewer` reviews, posts GO/NO-GO
3. Human reviews recommendation
4. Human merges and moves to Done

## Board Configuration

### Required Columns
- Backlog
- Ready
- In Progress
- In Review
- Done

### Labels
- `feature` - New functionality
- `bug` - Something broken
- `tech-debt` - Code improvements
- `autonomous` - Auto-related
- `teleop` - TeleOp-related
- `hardware` - Hardware abstraction
- `P1-critical` - Safety critical
- `P2-competition` - Competition blocking
- `P3-scoring` - Scoring impact

## Competition Schedule

Update this section with your competition dates:

```
- League Meet 1: [DATE]
- League Meet 2: [DATE]
- Qualifier: [DATE]
- Championship: [DATE]
```

## Key Documents

- `docs/GAME-STRATEGY.md` - DECODE scoring analysis
- `docs/AUTONOMOUS-PLANS.md` - Autonomous routine specifications
- `docs/DRIVER-GUIDE.md` - Controls and match procedures
- `docs/HARDWARE-CONFIG.md` - Robot hardware documentation
- `docs/ARCHITECTURE.md` - System design decisions

## Simulator Integration

Code should work in both FtcRobotController (real robot) and virtual_robot (simulator).

**Sync command**:
```bash
cp -r TeamCode/src/main/java/org/firstinspires/ftc/teamcode/* \
    ~/projects/tck517/virtual_robot/TeamCode/src/org/firstinspires/ftc/teamcode/
```

**Simulator hardware names** (use these for compatibility):
- Motors: `front_left_motor`, `front_right_motor`, `back_left_motor`, `back_right_motor`
- Arm: `arm_motor`
- Claw: `hand_servo`
- IMU: `imu`
