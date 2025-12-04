# FTC Development Workflow Guide

This guide explains how to develop FTC robot code using the **FtcRobotController** and **virtual_robot** projects together.

## Overview

```
┌─────────────────────────────────────────────────────────────────────┐
│                        DEVELOPMENT WORKFLOW                         │
├─────────────────────────────────────────────────────────────────────┤
│                                                                     │
│   FtcRobotController                      virtual_robot             │
│   ══════════════════                      ═════════════             │
│   (Primary Development)                   (Simulation Testing)      │
│                                                                     │
│   ┌─────────────────┐    sync_teamcode    ┌─────────────────┐      │
│   │   TeamCode/     │ ─────────────────►  │   TeamCode/     │      │
│   │   ├─ opmodes/   │     ./sync to-sim   │   ├─ opmodes/   │      │
│   │   └─ hardware/  │ ◄─────────────────  │   └─ hardware/  │      │
│   └─────────────────┘    ./sync from-sim  └─────────────────┘      │
│           │                                        │                │
│           ▼                                        ▼                │
│   ┌─────────────────┐                    ┌─────────────────┐       │
│   │  Android Build  │                    │  JavaFX 2D Sim  │       │
│   │  Deploy to Robot│                    │  Test on Desktop│       │
│   └─────────────────┘                    └─────────────────┘       │
│                                                                     │
└─────────────────────────────────────────────────────────────────────┘
```

## Project Structure

Both projects share the same **hardware abstraction layer**:

```
teamcode/
├── hardware/
│   ├── interfaces/           # Abstract interfaces (shared)
│   │   ├── IDrivetrain.java
│   │   ├── IArm.java
│   │   ├── IClaw.java
│   │   ├── IIMU.java
│   │   ├── IColorSensor.java
│   │   ├── IDistanceSensor.java
│   │   └── ITouchSensor.java
│   ├── real/                 # FTC SDK implementations
│   │   └── Real*.java
│   ├── mock/                 # Test/simulation mocks (shared)
│   │   └── Mock*.java
│   └── RobotHardware.java    # Factory class (platform-specific)
└── opmodes/                  # Your OpModes (shared)
    └── *.java
```

**What's shared vs platform-specific:**

| Component | Shared? | Notes |
|-----------|---------|-------|
| `interfaces/` | Yes | Abstract hardware contracts |
| `mock/` | Yes | For unit testing |
| `real/` | No | FTC SDK vs simulator APIs differ |
| `RobotHardware.java` | No | Factory logic differs slightly |
| `opmodes/` | Yes | Your game code |

---

## Workflow Steps

### Step 1: Write Code in FtcRobotController

Write your OpModes in `TeamCode/src/main/java/org/firstinspires/ftc/teamcode/opmodes/`

```java
package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.hardware.RobotHardware;
import org.firstinspires.ftc.teamcode.hardware.RobotHardware.RobotConfig;

@TeleOp(name = "My TeleOp", group = "Competition")
public class MyTeleOp extends LinearOpMode {

    private RobotHardware robot;

    @Override
    public void runOpMode() {
        // Configure for YOUR robot's hardware names
        RobotConfig config = RobotConfig.mecanumDrive(
                "left_front", "right_front",
                "left_back", "right_back")
            .withArm("arm_motor")
            .withClaw("claw_servo", 0.7, 0.3)
            .withIMU(true);

        robot = RobotHardware.createReal(hardwareMap, config);

        waitForStart();

        while (opModeIsActive()) {
            double drive = -gamepad1.left_stick_y;
            double strafe = gamepad1.left_stick_x;
            double turn = gamepad1.right_stick_x;

            robot.drivetrain.mecanumDrive(drive, strafe, turn);

            // Arm and claw control...
            telemetry.update();
        }
    }
}
```

### Step 2: Sync to Simulator

Use the sync script to copy your code to virtual_robot:

```bash
# Preview what will be synced (dry run)
./sync_teamcode.sh to-sim --dry-run

# Sync opmodes only
./sync_teamcode.sh to-sim

# Sync everything (opmodes + hardware interfaces + mocks)
./sync_teamcode.sh to-sim --all
```

### Step 3: Adapt for Simulator (if needed)

The synced code may need minor adjustments for the simulator:

**Option A: Use Simulator Presets**

Create a simulator-specific OpMode that uses built-in presets:

```java
// In virtual_robot - for MecanumBot (no arm)
RobotConfig config = RobotConfig.simulatorMecanumBot();

// In virtual_robot - for ArmBot (has arm and claw)
RobotConfig config = RobotConfig.simulatorArmBot();
```

**Option B: Use the Same Config**

If your hardware names match the simulator's virtual hardware, your code works as-is.

**Simulator Hardware Names:**
| Component | Simulator Name |
|-----------|----------------|
| Front Left Motor | `front_left_motor` |
| Front Right Motor | `front_right_motor` |
| Back Left Motor | `back_left_motor` |
| Back Right Motor | `back_right_motor` |
| Arm Motor | `arm_motor` |
| Claw Servo | `hand_servo` |
| Color Sensor | `color_sensor` |
| Distance Sensor | `front_distance` |
| IMU | `imu` |

### Step 4: Test in Simulator

1. Open `virtual_robot` in IntelliJ IDEA
2. Run `Controller/Main.java`
3. Select your OpMode from the dropdown
4. Click INIT, then START
5. Use keyboard/gamepad to control the robot

### Step 5: Iterate

```
┌──────────────────────────────────────────────────────────────┐
│                    DEVELOPMENT LOOP                          │
│                                                              │
│   ┌─────────┐     ┌─────────┐     ┌─────────┐     ┌───────┐ │
│   │  Write  │ ──► │  Sync   │ ──► │  Test   │ ──► │ Works │ │
│   │  Code   │     │  to-sim │     │  in Sim │     │   ?   │ │
│   └─────────┘     └─────────┘     └─────────┘     └───┬───┘ │
│        ▲                                              │     │
│        │              No                              │     │
│        └──────────────────────────────────────────────┘     │
│                                              │ Yes          │
│                                              ▼              │
│                                      ┌──────────────┐       │
│                                      │ Deploy to    │       │
│                                      │ Real Robot   │       │
│                                      └──────────────┘       │
└──────────────────────────────────────────────────────────────┘
```

### Step 6: Deploy to Real Robot

1. Connect your phone/Control Hub to your computer
2. Open FtcRobotController in Android Studio
3. Build and deploy
4. Your OpModes appear in the Driver Station app

---

## Sync Script Reference

```bash
# Show help
./sync_teamcode.sh --help

# Sync opmodes to simulator
./sync_teamcode.sh to-sim

# Sync opmodes from simulator back to FtcRobotController
./sync_teamcode.sh from-sim

# Sync hardware interfaces and mocks too
./sync_teamcode.sh to-sim --hardware

# Sync everything
./sync_teamcode.sh to-sim --all

# Preview changes without copying
./sync_teamcode.sh to-sim --dry-run
```

---

## Examples

### Example 1: Simple TeleOp (Works in Both)

This example uses the same hardware names as the simulator:

```java
package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.hardware.RobotHardware;
import org.firstinspires.ftc.teamcode.hardware.RobotHardware.RobotConfig;

@TeleOp(name = "Universal TeleOp", group = "Examples")
public class UniversalTeleOp extends LinearOpMode {

    @Override
    public void runOpMode() {
        // These names work in both real robot and simulator
        RobotConfig config = RobotConfig.mecanumDrive(
                "front_left_motor", "front_right_motor",
                "back_left_motor", "back_right_motor")
            .withIMU(true);

        RobotHardware robot = RobotHardware.createReal(hardwareMap, config);

        waitForStart();

        while (opModeIsActive()) {
            robot.drivetrain.mecanumDrive(
                -gamepad1.left_stick_y,
                gamepad1.left_stick_x,
                gamepad1.right_stick_x
            );
            telemetry.addData("Heading", "%.1f°", robot.imu.getHeading());
            telemetry.update();
        }
    }
}
```

### Example 2: Unit Testing with Mocks

Test your logic without hardware or simulator:

```java
package org.firstinspires.ftc.teamcode.opmodes;

import org.firstinspires.ftc.teamcode.hardware.RobotHardware;
import org.firstinspires.ftc.teamcode.hardware.RobotHardware.RobotConfig;

public class MyOpModeTest {

    public static void main(String[] args) {
        // Create mock hardware for testing
        RobotConfig config = RobotConfig.mecanumDrive(
                "fl", "fr", "bl", "br")
            .withArm("arm")
            .withClaw("claw", 1.0, 0.0);

        RobotHardware robot = RobotHardware.createMock(config);

        // Test your logic
        robot.drivetrain.mecanumDrive(1.0, 0.0, 0.0);
        System.out.println("Mock mode: " + robot.isMock());  // true

        robot.arm.setTargetPosition(100);
        System.out.println("Arm target: " + robot.arm.getTargetPosition());

        robot.claw.open();
        System.out.println("Claw open: " + robot.claw.isOpen());  // true
    }
}
```

### Example 3: Autonomous with Sensor Checks

```java
package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.hardware.RobotHardware;
import org.firstinspires.ftc.teamcode.hardware.RobotHardware.RobotConfig;
import org.firstinspires.ftc.teamcode.hardware.interfaces.IColorSensor.DetectedColor;

@Autonomous(name = "Sample Auto", group = "Examples")
public class SampleAutonomous extends LinearOpMode {

    @Override
    public void runOpMode() {
        RobotConfig config = RobotConfig.simulatorArmBot();  // Or your real config
        RobotHardware robot = RobotHardware.createReal(hardwareMap, config);

        waitForStart();

        // Drive forward until obstacle detected
        while (opModeIsActive() && !robot.distanceSensor.isObjectDetected(15)) {
            robot.drivetrain.mecanumDrive(0.3, 0, 0);
            telemetry.addData("Distance", "%.1f cm",
                robot.distanceSensor.getDistanceCm());
            telemetry.update();
        }

        robot.drivetrain.stop();

        // Check color and react
        DetectedColor color = robot.colorSensor.getDetectedColor();
        telemetry.addData("Detected", color.name());
        telemetry.update();

        if (color == DetectedColor.RED) {
            // Do red action
            robot.arm.setTargetPosition(500);
        } else if (color == DetectedColor.BLUE) {
            // Do blue action
            robot.arm.setTargetPosition(1000);
        }

        sleep(2000);
    }
}
```

---

## Common Issues

### "Motor not found" in Simulator

The simulator has specific hardware names. Use the presets:
```java
RobotConfig.simulatorMecanumBot()  // For MecanumBot
RobotConfig.simulatorArmBot()      // For ArmBot
```

### Code Compiles but Doesn't Work

1. Check the `@Disabled` annotation - remove it to enable the OpMode
2. Verify hardware names match your configuration
3. Check telemetry for error messages

### Sync Script Errors

```bash
# Make sure the script is executable
chmod +x sync_teamcode.sh

# Make sure virtual_robot is in the right place
ls ../virtual_robot  # Should show the project
```

---

## File Locations

| Project | TeamCode Path |
|---------|---------------|
| FtcRobotController | `TeamCode/src/main/java/org/firstinspires/ftc/teamcode/` |
| virtual_robot | `TeamCode/src/org/firstinspires/ftc/teamcode/` |

---

## Quick Reference

| Task | Command/Action |
|------|----------------|
| Sync to simulator | `./sync_teamcode.sh to-sim` |
| Sync from simulator | `./sync_teamcode.sh from-sim` |
| Run simulator | Open IntelliJ, run `Main.java` |
| Deploy to robot | Android Studio > Run |
| Test with mocks | `RobotHardware.createMock(config)` |
