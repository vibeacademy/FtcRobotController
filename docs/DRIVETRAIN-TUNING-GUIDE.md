# Drivetrain Tuning Guide - DECODE 2025-2026

## Purpose

This guide provides step-by-step procedures for tuning the mecanum drivetrain for optimal performance. Follow these steps in order after initial hardware assembly.

## Prerequisites

- [ ] Robot mechanically assembled
- [ ] All motors wired to Control Hub
- [ ] Battery charged
- [ ] Hardware abstraction layer implemented
- [ ] Basic TeleOp OpMode available

## Tuning Sequence

Complete these steps **in order**. Each step builds on previous calibrations.

### Step 1: Motor Direction Verification

**Goal**: Ensure all motors spin the correct direction for forward movement.

**Test OpMode**:
```java
@TeleOp(name="Step 1: Motor Direction Test")
public class Step1_MotorDirectionTest extends LinearOpMode {
    @Override
    public void runOpMode() {
        DcMotor fl = hardwareMap.get(DcMotor.class, "front_left_motor");
        DcMotor fr = hardwareMap.get(DcMotor.class, "front_right_motor");
        DcMotor bl = hardwareMap.get(DcMotor.class, "back_left_motor");
        DcMotor br = hardwareMap.get(DcMotor.class, "back_right_motor");

        waitForStart();

        while (opModeIsActive()) {
            // Test front left
            if (gamepad1.dpad_up) {
                fl.setPower(0.3);
                telemetry.addData("Testing", "Front Left");
            }
            // Test front right
            else if (gamepad1.dpad_right) {
                fr.setPower(0.3);
                telemetry.addData("Testing", "Front Right");
            }
            // Test back left
            else if (gamepad1.dpad_down) {
                bl.setPower(0.3);
                telemetry.addData("Testing", "Back Left");
            }
            // Test back right
            else if (gamepad1.dpad_left) {
                br.setPower(0.3);
                telemetry.addData("Testing", "Back Right");
            }
            else {
                fl.setPower(0);
                fr.setPower(0);
                bl.setPower(0);
                br.setPower(0);
                telemetry.addData("Testing", "None - Use DPad");
            }

            telemetry.addData("Instructions", "DPad Up=FL, Right=FR, Down=BL, Left=BR");
            telemetry.update();
        }
    }
}
```

**Procedure**:
1. Place robot on blocks (wheels off ground)
2. Run test OpMode
3. Press DPad Up - Front Left wheel should spin
4. **Verify direction**: If robot was on ground, would it move FORWARD?
5. Repeat for each wheel (DPad Right, Down, Left)

**Expected Results** (looking from top):
- Front Left: Spins forward (counterclockwise when viewed from left side)
- Front Right: Spins forward (clockwise when viewed from right side)
- Back Left: Spins forward (counterclockwise when viewed from left side)
- Back Right: Spins forward (clockwise when viewed from right side)

**If Wrong Direction**:
Edit `/TeamCode/.../hardware/real/RealDrivetrain.java`:
```java
// Swap direction for offending motor
leftFront.setDirection(DcMotorSimple.Direction.FORWARD);  // Was REVERSE
```

**Validation**: All four wheels spin in direction that would move robot forward.

---

### Step 2: Basic Movement Test

**Goal**: Verify mecanum drive kinematics are correct.

**Test OpMode**:
```java
@TeleOp(name="Step 2: Movement Test")
public class Step2_MovementTest extends LinearOpMode {
    @Override
    public void runOpMode() {
        RobotConfig config = RobotConfig.mecanumDrive(
            "front_left_motor", "front_right_motor",
            "back_left_motor", "back_right_motor");
        RobotHardware robot = RobotHardware.createReal(hardwareMap, config);

        waitForStart();

        while (opModeIsActive()) {
            double drive = -gamepad1.left_stick_y;
            double strafe = gamepad1.left_stick_x;
            double turn = gamepad1.right_stick_x;

            robot.drivetrain.mecanumDrive(drive, strafe, turn);

            telemetry.addData("Drive", "%.2f", drive);
            telemetry.addData("Strafe", "%.2f", strafe);
            telemetry.addData("Turn", "%.2f", turn);
            telemetry.update();
        }
    }
}
```

**Test Matrix**:
| Input | Expected Robot Motion |
|-------|----------------------|
| Left stick forward | Robot drives forward |
| Left stick backward | Robot drives backward |
| Left stick right | Robot strafes right |
| Left stick left | Robot strafes left |
| Right stick right | Robot rotates clockwise |
| Right stick left | Robot rotates counterclockwise |
| Forward + Right | Robot drives forward-right diagonal |
| Forward + Rotate | Robot drives forward while rotating |

**Common Issues**:

| Problem | Cause | Fix |
|---------|-------|-----|
| Drives backward | Drive input inverted | Negate drive value |
| Strafes left when right | Strafe input inverted | Negate strafe value |
| Rotates CCW when CW | Turn input inverted | Negate turn value |
| Goes diagonal on strafe | Mecanum kinematic error | Check wheel arrangement (X pattern) |

**Validation**: All 8 directions work correctly (forward, backward, left, right, 4 diagonals).

---

### Step 3: Encoder Calibration

**Goal**: Determine accurate counts-per-inch for autonomous navigation.

**Setup**:
1. Clear floor space (at least 6 feet)
2. Tape measure or measuring tape
3. Masking tape to mark positions

**Test OpMode**:
```java
@TeleOp(name="Step 3: Encoder Calibration")
public class Step3_EncoderCalibration extends LinearOpMode {
    @Override
    public void runOpMode() {
        RobotConfig config = RobotConfig.mecanumDrive(
            "front_left_motor", "front_right_motor",
            "back_left_motor", "back_right_motor")
            .withCountsPerInch(1.0);  // Set to 1.0 for raw counts
        RobotHardware robot = RobotHardware.createReal(hardwareMap, config);

        waitForStart();

        // Reset encoders
        robot.drivetrain.resetEncoders();
        sleep(100);

        telemetry.addData("Status", "Ready - Press A to drive 48 inches");
        telemetry.update();

        // Wait for button press
        while (!gamepad1.a && opModeIsActive()) {
            sleep(50);
        }

        // Drive 48 inches
        robot.drivetrain.driveByEncoder(48, 48, 0.5);

        ElapsedTime timer = new ElapsedTime();
        while (robot.drivetrain.isBusy() && opModeIsActive() && timer.seconds() < 5.0) {
            telemetry.addData("Status", "Driving...");
            telemetry.addData("Left Encoder", robot.drivetrain.getLeftEncoderPosition());
            telemetry.addData("Right Encoder", robot.drivetrain.getRightEncoderPosition());
            telemetry.update();
            sleep(50);
        }

        robot.drivetrain.stop();

        int leftPos = robot.drivetrain.getLeftEncoderPosition();
        int rightPos = robot.drivetrain.getRightEncoderPosition();
        int avgPos = (leftPos + rightPos) / 2;

        telemetry.addData("Left Encoder", leftPos);
        telemetry.addData("Right Encoder", rightPos);
        telemetry.addData("Average", avgPos);
        telemetry.addData("Counts/Inch", avgPos / 48.0);
        telemetry.addData("", "---");
        telemetry.addData("Measure actual distance", "and calculate");
        telemetry.update();

        sleep(60000);  // Hold results for 1 minute
    }
}
```

**Procedure**:
1. Mark starting position on floor (front of robot)
2. Run OpMode, press A when ready
3. Robot will attempt to drive 48 inches
4. **Measure actual distance traveled** (important!)
5. Calculate: `countsPerInch = encoderAverage / actualDistance`

**Example**:
- Encoder average: 2100 ticks
- Actual distance: 48.5 inches (slightly overshot)
- Counts per inch: 2100 / 48.5 = **43.3**

**Repeat for Strafe**:
Modify OpMode to strafe instead:
```java
// Strafe 24 inches right
robot.drivetrain.mecanumDrive(0, 0.5, 0);  // Manual strafe
sleep(3000);  // Adjust time to get ~24 inches
robot.drivetrain.stop();
```
Measure actual strafe distance, calculate strafe counts/inch.

**Typical Values**:
- Forward/backward: 42-45 counts/inch
- Strafe: 50-55 counts/inch (higher due to slip)

**Update Configuration**:
```java
RobotConfig config = RobotConfig.mecanumDrive(...)
    .withCountsPerInch(43.3);  // Use your calculated value
```

**Validation**: Robot drives 48 inches and lands within ± 1 inch of target.

---

### Step 4: IMU Calibration

**Goal**: Verify IMU heading accuracy for field-centric and auto turns.

**Test OpMode**:
```java
@TeleOp(name="Step 4: IMU Test")
public class Step4_IMUTest extends LinearOpMode {
    @Override
    public void runOpMode() {
        RobotConfig config = RobotConfig.mecanumDrive(...)
            .withIMU(true);
        RobotHardware robot = RobotHardware.createReal(hardwareMap, config);

        waitForStart();

        robot.imu.resetHeading();

        while (opModeIsActive()) {
            if (gamepad1.a) {
                robot.imu.resetHeading();
            }

            telemetry.addData("Heading", "%.1f°", robot.imu.getHeading());
            telemetry.addData("Calibrated", robot.imu.isCalibrated() ? "YES" : "NO");
            telemetry.addData("Pitch", "%.1f°", robot.imu.getPitch());
            telemetry.addData("Roll", "%.1f°", robot.imu.getRoll());
            telemetry.addData("", "Press A to reset heading");
            telemetry.update();
        }
    }
}
```

**Procedure**:
1. Place robot on flat surface facing away from drivers
2. Run OpMode
3. Press A to reset heading (should read ~0°)
4. **90° Test**: Rotate robot clockwise 90° (use square corner as reference)
   - Expected: -90° (± 2°)
5. **180° Test**: Continue to 180° rotation
   - Expected: +180° or -180° (± 3°)
6. **270° Test**: Continue to 270° rotation
   - Expected: +90° (± 2°)
7. **360° Test**: Return to start
   - Expected: 0° (± 3°)

**Calibration Check**:
- "Calibrated" should show "YES"
- If "NO", keep robot still for 30 seconds
- IMU LED on Control Hub should be green

**Common Issues**:

| Problem | Cause | Fix |
|---------|-------|-----|
| "Calibrated" stays NO | IMU not initialized | Check IMU device name = "imu" |
| Heading drifts | Vibration/movement | Mount Control Hub more rigidly |
| Wrong direction | IMU orientation | Verify Control Hub orientation |
| Large errors (>5°) | Magnetic interference | Move away from motors/metal |

**Validation**: Can rotate 360° with heading returning within ±3° of start.

---

### Step 5: Field-Centric Drive Test

**Goal**: Verify field-centric control works correctly.

**Test OpMode**:
```java
@TeleOp(name="Step 5: Field-Centric Test")
public class Step5_FieldCentricTest extends LinearOpMode {
    @Override
    public void runOpMode() {
        RobotConfig config = RobotConfig.mecanumDrive(...)
            .withIMU(true);
        RobotHardware robot = RobotHardware.createReal(hardwareMap, config);

        waitForStart();
        robot.imu.resetHeading();

        while (opModeIsActive()) {
            double drive = -gamepad1.left_stick_y;
            double strafe = gamepad1.left_stick_x;
            double turn = gamepad1.right_stick_x;

            double heading = Math.toRadians(robot.imu.getHeading());
            robot.drivetrain.fieldCentricDrive(drive, strafe, turn, heading);

            telemetry.addData("Heading", "%.1f°", robot.imu.getHeading());
            telemetry.addData("Drive", "%.2f", drive);
            telemetry.addData("Strafe", "%.2f", strafe);
            telemetry.update();
        }
    }
}
```

**Test Procedure**:
1. Face robot away from drivers (0°)
2. Press left stick forward
3. **Verify**: Robot moves away from drivers
4. **While holding forward**, rotate robot 90° clockwise using right stick
5. **Verify**: Robot continues moving away from drivers (not to the side)
6. Repeat at 180° and 270°

**Expected Behavior**:
- Pushing left stick "up" always makes robot drive "away from drivers"
- Robot orientation doesn't change which field direction it drives
- This allows driver to keep mental map aligned with field, not robot

**Common Issues**:

| Problem | Cause | Fix |
|---------|-------|-----|
| Robot rotates instead of drives | Heading not passed to function | Verify `Math.toRadians()` conversion |
| Drives wrong field direction | Heading positive/negative swapped | Check IMU heading sign |
| Jumpy/erratic movement | Heading in degrees not radians | Convert: `Math.toRadians(heading)` |

**Validation**: Can drive in any field direction regardless of robot orientation.

---

### Step 6: PID Turn Tuning

**Goal**: Tune PID constants for accurate autonomous heading control.

**Test OpMode**:
```java
@TeleOp(name="Step 6: PID Turn Tuning")
public class Step6_PIDTurnTuning extends LinearOpMode {
    // Tuning parameters - adjust these
    private static final double kP = 0.02;
    private static final double kI = 0.0;
    private static final double kD = 0.005;

    @Override
    public void runOpMode() {
        RobotConfig config = RobotConfig.mecanumDrive(...)
            .withIMU(true);
        RobotHardware robot = RobotHardware.createReal(hardwareMap, config);

        double targetHeading = 0;

        waitForStart();
        robot.imu.resetHeading();

        while (opModeIsActive()) {
            // Set target with DPad
            if (gamepad1.dpad_up) targetHeading = 0;
            if (gamepad1.dpad_right) targetHeading = -90;
            if (gamepad1.dpad_down) targetHeading = 180;
            if (gamepad1.dpad_left) targetHeading = 90;

            // Turn to target heading
            if (gamepad1.a) {
                robot.drivetrain.turnToHeading(targetHeading, 0.5, robot.imu.getHeading());
            }

            double error = normalizeAngle(targetHeading - robot.imu.getHeading());

            telemetry.addData("Target Heading", "%.1f°", targetHeading);
            telemetry.addData("Current Heading", "%.1f°", robot.imu.getHeading());
            telemetry.addData("Error", "%.1f°", error);
            telemetry.addData("", "DPad to set target, A to turn");
            telemetry.update();
        }
    }

    private double normalizeAngle(double angle) {
        while (angle > 180) angle -= 360;
        while (angle < -180) angle += 360;
        return angle;
    }
}
```

**Tuning Process**:

**Step 6.1: Proportional (kP) Only**
1. Set `kI = 0`, `kD = 0`
2. Set `kP = 0.01`
3. Run OpMode, press DPad Right (target = -90°)
4. Press A to initiate turn
5. Observe behavior:
   - **Too slow / doesn't reach target**: Increase kP by 0.005
   - **Overshoots / oscillates**: Decrease kP by 0.005
6. Repeat until robot turns to target with slight overshoot
7. Note this kP value as "kP_oscillation"
8. Reduce kP to 60% of kP_oscillation

**Example**:
- kP = 0.035 → oscillates
- kP_oscillation = 0.035
- Final kP = 0.035 × 0.6 = **0.021**

**Step 6.2: Derivative (kD)**
1. Set kP to value from 6.1
2. Set `kD = kP / 4` (starting guess)
3. Test turn - should reduce/eliminate oscillation
4. If still oscillates: Increase kD by 0.002
5. If too slow/sluggish: Decrease kD by 0.002

**Example**:
- kP = 0.021
- kD = 0.021 / 4 = 0.005

**Step 6.3: Integral (kI) - Optional**
1. Only add if robot doesn't reach final target (steady-state error)
2. Set `kI = kP / 100` (very small)
3. Test - should eliminate steady-state error
4. If overshoots: Reduce kI by half

**Typical Values** (starting points):
- kP: 0.015 - 0.025
- kI: 0.0 - 0.0002 (usually not needed)
- kD: 0.003 - 0.008

**Validation Criteria**:
- [ ] Turns to 90° target within ± 2°
- [ ] No oscillation (steady at target)
- [ ] Settles within 1.5 seconds
- [ ] Works for all targets (0°, 90°, 180°, 270°)

---

### Step 7: Autonomous Movement Test

**Goal**: Validate autonomous navigation accuracy.

**Test OpMode**:
```java
@Autonomous(name="Step 7: Auto Movement Test")
public class Step7_AutoMovementTest extends LinearOpMode {
    @Override
    public void runOpMode() {
        RobotConfig config = RobotConfig.mecanumDrive(...)
            .withIMU(true)
            .withCountsPerInch(43.3);  // Use your calibrated value
        RobotHardware robot = RobotHardware.createReal(hardwareMap, config);

        waitForStart();
        robot.imu.resetHeading();
        robot.drivetrain.resetEncoders();

        // Drive square pattern
        driveForward(robot, 24);
        turnRight(robot, 90);
        driveForward(robot, 24);
        turnRight(robot, 90);
        driveForward(robot, 24);
        turnRight(robot, 90);
        driveForward(robot, 24);
        turnRight(robot, 90);

        telemetry.addData("Test", "Complete - Check position");
        telemetry.update();
        sleep(10000);
    }

    private void driveForward(RobotHardware robot, double inches) {
        robot.drivetrain.driveToPosition(inches, 0, 0.5);
        ElapsedTime timer = new ElapsedTime();
        while (robot.drivetrain.isBusy() && opModeIsActive() && timer.seconds() < 4.0) {
            sleep(10);
        }
    }

    private void turnRight(RobotHardware robot, double degrees) {
        double targetHeading = robot.imu.getHeading() - degrees;
        robot.drivetrain.turnToHeading(targetHeading, 0.4, robot.imu.getHeading());
        ElapsedTime timer = new ElapsedTime();
        while (robot.drivetrain.isBusy() && opModeIsActive() && timer.seconds() < 3.0) {
            sleep(10);
        }
    }
}
```

**Test Pattern**: 24" × 24" square

**Procedure**:
1. Mark starting position with tape (make a cross for alignment)
2. Run autonomous OpMode
3. Robot should drive a square and return to start
4. **Measure errors**:
   - Forward/backward: Should be within ± 2"
   - Left/right: Should be within ± 2"
   - Heading: Should be within ± 5°

**Common Errors**:

| Error Type | Likely Cause | Solution |
|------------|--------------|----------|
| Undershoots distance | Counts/inch too high | Decrease counts/inch by 2-3% |
| Overshoots distance | Counts/inch too low | Increase counts/inch by 2-3% |
| Curves instead of straight | Wheel speeds unequal | Check motor directions |
| Doesn't turn 90° | PID constants off | Re-tune kP, kD |
| Drifts sideways | Mecanum slip | Normal - use odometry for precision |

**Refinement**:
If errors are consistent, adjust:
```java
// If undershoots by 2 inches every 24 inches (8% error)
newCountsPerInch = oldCountsPerInch × 0.92

// If overshoots by 1 inch every 24 inches (4% error)
newCountsPerInch = oldCountsPerInch × 1.04
```

**Validation**: Completes square within ± 3" of start position.

---

## Final Tuning Checklist

Before competition, verify all parameters:

### Configuration Values
- [ ] Counts per inch (forward): _______
- [ ] Counts per inch (strafe): _______
- [ ] PID kP: _______
- [ ] PID kI: _______
- [ ] PID kD: _______

### Movement Tests
- [ ] Forward drive: 48" ± 1"
- [ ] Strafe right: 24" ± 2"
- [ ] Turn to 90°: ± 2°
- [ ] Field-centric maintains direction through 360° rotation
- [ ] Square test completes within ± 3" of start

### Safety Tests
- [ ] Robot stops when OpMode ends
- [ ] Emergency stop works
- [ ] No motor stalling under normal load
- [ ] Battery maintains >12V during full match

## Performance Optimization

After basic tuning, consider these enhancements:

### Acceleration Ramping
Reduce wheel slip and improve consistency:
```java
double maxAcceleration = 0.05;  // Per loop iteration
currentPower = Math.min(currentPower + maxAcceleration, targetPower);
```

### Deceleration Ramping
Improve stopping accuracy:
```java
double distanceToTarget = Math.abs(targetPosition - currentPosition);
if (distanceToTarget < 6) {  // 6 inches from target
    speed = 0.2;  // Slow down
} else {
    speed = 0.6;  // Full speed
}
```

### Heading Correction While Driving
Maintain straight lines:
```java
double headingError = targetHeading - currentHeading;
double correction = headingError * 0.01;  // Small correction factor
robot.drivetrain.mecanumDrive(drivePower, 0, correction);
```

## Troubleshooting Reference

### Robot Performance Issues

**Symptom**: Robot veers to one side when driving straight
- **Cause**: Motors not matched, wheel slip
- **Solution**: Adjust individual motor powers (±5% trim)

**Symptom**: Encoder values jumping or inconsistent
- **Cause**: Loose encoder connection, damaged encoder
- **Solution**: Check motor connectors, replace motor if needed

**Symptom**: IMU heading drifts over time
- **Cause**: Temperature change, vibration, magnetic interference
- **Solution**: Mount hub rigidly, reset heading periodically, avoid metal

**Symptom**: Turns are inconsistent
- **Cause**: Battery voltage affecting motor speeds
- **Solution**: Test with fully charged battery, add voltage compensation

**Symptom**: Autonomous movements slow
- **Cause**: Max speed too conservative
- **Solution**: Increase maxSpeed parameter (test carefully)

## Documentation

Record your final tuned values:

```java
// Tuned Configuration for Robot: [ROBOT_NAME]
// Date: [DATE]
// Tuned by: [NAME]

public static final double COUNTS_PER_INCH_FORWARD = 43.5;
public static final double COUNTS_PER_INCH_STRAFE = 52.0;

public static final double TURN_PID_KP = 0.021;
public static final double TURN_PID_KI = 0.0;
public static final double TURN_PID_KD = 0.005;

public static final double MAX_DRIVE_SPEED = 0.8;
public static final double APPROACH_SPEED = 0.4;
public static final double PRECISION_SPEED = 0.2;

// Notes:
// - Tested on field tiles with fresh battery
// - IMU heading reset required at start of auto
// - Strafe distance may vary ±1" due to mecanum slip
```

Keep this in your team notebook for reference!

## Related Documentation

- `DRIVETRAIN-DESIGN.md` - Design specifications
- `HARDWARE-CONFIG.md` - Hardware setup
- `AUTONOMOUS-PLANS.md` - Auto routines using these parameters

## Revision History

- 2025-12-11: Initial tuning guide (Systems Engineer)
