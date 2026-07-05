---
description: Sync code to virtual_robot simulator and provide testing instructions.
---

# Run Simulator Command

You are the **test-engineer** agent. Prepare code for simulator testing.

## Instructions

1. **Sync TeamCode to virtual_robot**:
   ```bash
   # Copy TeamCode files
   cp -r /Users/teddykim/projects/tck517/FtcRobotController/TeamCode/src/main/java/org/firstinspires/ftc/teamcode/* \
       /Users/teddykim/projects/tck517/virtual_robot/TeamCode/src/org/firstinspires/ftc/teamcode/
   ```

2. **Verify sync**:
   ```bash
   ls -la /Users/teddykim/projects/tck517/virtual_robot/TeamCode/src/org/firstinspires/ftc/teamcode/
   ```

3. **Check for compatibility issues**:
   - FTC SDK-only imports that won't work in simulator
   - Hardware names that don't match simulator config
   - Android-specific code

4. **Provide testing instructions**:

## To Run Tests

1. Open IntelliJ IDEA
2. Open project: `~/projects/tck517/virtual_robot`
3. Run `VirtualRobotApplication` (Main class)
4. Select robot config: **ArmBot** (has arm + claw) or **MecanumBot** (drive only)
5. Select OpMode from dropdown
6. Click **INIT** then **START**

## Simulator Robot Configs

| Config | Hardware Available |
|--------|-------------------|
| MecanumBot | 4 motors, IMU, color, distance |
| ArmBot | 4 motors, arm, claw, IMU |
| XDriveBot | 4 omni wheels, IMU |

## Common Issues

- **OpMode not showing**: Check @TeleOp/@Autonomous annotation
- **Null hardware**: Using hardware not available in selected config
- **Wrong motor names**: Use simulator names (front_left_motor, etc.)

## Output

Report:
- Sync status
- Any compatibility issues found
- Testing instructions
