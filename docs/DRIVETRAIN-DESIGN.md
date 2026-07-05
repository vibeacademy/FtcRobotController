# Mecanum Drivetrain Design - DECODE 2025-2026

## Overview

This document provides detailed design specifications for the mecanum drivetrain subsystem for FTC DECODE 2025-2026. The design leverages the existing hardware abstraction layer and focuses on competitive cycle times, reliable autonomous navigation, and intuitive driver control.

## Design Philosophy

1. **Hardware Abstraction First**: All OpModes use IDrivetrain interface, never direct motor access
2. **Testable Design**: Mock implementations enable offline development
3. **Incremental Complexity**: Start simple (robot-centric), add features as needed
4. **Safety Critical**: Bounds checking, stall detection, timeout protection

## Game Requirements Analysis

### DECODE Drivetrain Demands

| Requirement | Drivetrain Impact | Solution |
|-------------|-------------------|----------|
| 20 cycles/match | Fast travel between zones | 2.5 ft/sec target speed |
| Artifact collection | Lateral positioning | Mecanum strafe capability |
| GOAL scoring precision | Fine positioning control | Precision mode (0.3x power) |
| GATE operation | Reach and hold position | Encoder-based positioning |
| BASE return | Reliable navigation | IMU heading + encoders |
| PATTERN scoring | Consistent artifact placement | Repeatable approach angles |

**Speed Calculation**:
- Field dimensions: 12ft x 12ft
- Typical cycle: BASE → DEPOT (6ft) → GOAL (6ft) → BASE (8ft) = 20ft
- At 2.5 ft/sec: 8 seconds travel time per cycle
- Target: 6-7 sec total cycle time → need efficient movement

## Interface Specification

### Enhanced IDrivetrain Methods

```java
/**
 * Field-centric mecanum drive.
 * Allows driver to push joystick "forward" relative to field, regardless of robot orientation.
 * Critical for fast cycles - driver doesn't need to mentally rotate inputs.
 *
 * Implementation:
 *   rotatedX = fieldStrafe * cos(-heading) - fieldDrive * sin(-heading)
 *   rotatedY = fieldStrafe * sin(-heading) + fieldDrive * cos(-heading)
 *   mecanumDrive(rotatedY, rotatedX, turn)
 *
 * @param fieldDrive Forward/backward relative to FIELD (-1.0 to 1.0)
 * @param fieldStrafe Left/right relative to FIELD (-1.0 to 1.0)
 * @param turn Rotation power (-1.0 to 1.0, positive = clockwise)
 * @param heading Robot heading from IMU in radians (0 = facing away from drivers)
 */
void fieldCentricDrive(double fieldDrive, double fieldStrafe, double turn, double heading);

/**
 * Drive to position using encoders (blocking or non-blocking).
 * Uses mecanum kinematics to calculate wheel targets.
 *
 * Mecanum position control:
 *   - Forward: all wheels same direction, same target
 *   - Strafe: diagonal wheels opposite, calculate strafe distance
 *   - Combined: vector sum of forward + strafe components
 *
 * @param forwardInches Distance forward (+) or backward (-) in inches
 * @param strafeInches Distance right (+) or left (-) in inches
 * @param maxSpeed Maximum motor power (0.0 to 1.0)
 */
void driveToPosition(double forwardInches, double strafeInches, double maxSpeed);

/**
 * Turn to absolute heading using PID control.
 * Handles wraparound (e.g., turning from 170° to -170° is 20°, not 340°).
 *
 * PID implementation:
 *   error = normalizeAngle(targetHeading - currentHeading)  // -180 to 180
 *   turnPower = kP * error + kD * (error - lastError)
 *   mecanumDrive(0, 0, turnPower)
 *
 * @param targetHeading Target heading in degrees (-180 to 180)
 * @param maxSpeed Maximum turn speed (0.0 to 1.0)
 * @param currentHeading Current heading from IMU in degrees
 */
void turnToHeading(double targetHeading, double maxSpeed, double currentHeading);

/**
 * Get all four wheel encoder positions.
 * Useful for advanced odometry or debugging wheel slip.
 *
 * @return Array of encoder positions [FL, FR, BL, BR] in ticks
 */
int[] getWheelPositions();

/**
 * Set power scaling factor.
 * Used for precision mode - scales all motor powers by this factor.
 *
 * @param scale Power multiplier (0.0 to 1.0, default 1.0)
 */
void setPowerScale(double scale);

/**
 * Detect motor stall condition.
 * Indicates motor receiving power but not moving - potential mechanical issue.
 *
 * Detection logic:
 *   - Motor power > 0.2
 *   - Encoder change < 10 ticks in last 500ms
 *   - Not in brake mode with zero power
 *
 * @return true if any motor appears stalled
 */
boolean isStalled();
```

## Hardware Specifications

### Recommended Components

**Motors**: goBILDA 5203 Series Planetary Gear Motors
- **Option 1 (Recommended)**: 312 RPM (5203-2402-0019)
  - Free speed: 312 RPM
  - Stall torque: 5.3 Nm
  - With 4" wheels: ~2.5 ft/sec max speed
  - Best balance of speed and control for DECODE

- **Option 2**: 435 RPM (5203-2402-0014)
  - Free speed: 435 RPM
  - Stall torque: 3.8 Nm
  - With 4" wheels: ~3.5 ft/sec max speed
  - Higher speed, less torque - for experienced drivers

**Wheels**: goBILDA 96mm Mecanum Wheels (3237-0001-0096)
- 96mm ≈ 3.78" diameter (use 4" for calculations)
- Aluminum rollers for durability
- Left and right wheel sets required

**Encoders**:
- Motor integrated: 537.7 counts/revolution (goBILDA motors)
- Counts per inch (4" wheel): `537.7 / (4 * π) ≈ 42.8`
- Strafe slip factor: 1.1-1.2 (mecanum lateral slip)

**IMU**: REV Control Hub Built-in
- BNO055 or BHI260AP depending on hub version
- Mount hub rigidly at robot center
- Align axes: +X forward, +Y left, +Z up

### Physical Layout

```
         FRONT
    FL -------- FR
     |   IMU    |
     |    ⊕     |
     |          |
    BL -------- BR
         BACK

Wheelbase: 14-16" (front to back)
Track Width: 12-14" (left to right)
Center of Rotation: Geometric center of wheel rectangle
```

**Design Constraints**:
- Wheelbase should be > track width for stability
- Shorter wheelbase = faster turning
- Longer wheelbase = straighter tracking
- Keep IMU at center of rotation to minimize acceleration errors

### Counts Per Inch Calibration

**Forward/Backward**:
```
Theoretical: 537.7 / (π * 4) = 42.8 counts/inch
Actual: Measure 48" drive, calculate: encoderDelta / 48
Typical: 43-45 counts/inch (accounting for wheel compression)
```

**Strafe (Left/Right)**:
```
Theoretical: 42.8 counts/inch
Actual: 1.1 to 1.2x forward value (mecanum slip)
Typical: 47-54 counts/inch
Method: Drive 24" left, measure encoder change
```

**Calibration Procedure**:
1. Mark 48" straight line on floor
2. Reset encoders
3. Drive forward at 0.5 power
4. Stop when front of robot reaches 48" mark
5. Read encoder average: `(getLeftEncoderPosition() + getRightEncoderPosition()) / 2`
6. Calculate: `countsPerInch = encoderValue / 48`
7. Repeat for strafe using perpendicular 24" line

## Control System Design

### TeleOp Control Scheme

**Gamepad Layout** (Xbox-style controller):

```
Left Stick Y:    Forward/Backward (drive)
Left Stick X:    Strafe Left/Right (strafe)
Right Stick X:   Rotate (turn)

Right Bumper:    Precision Mode (hold for 0.3x speed)
DPad Up:         Toggle Field-Centric Mode
DPad Down:       Reset IMU Heading
```

**Control Modes**:

1. **Robot-Centric Mode** (Default)
   - Joystick forward = robot drives forward
   - Intuitive for beginners
   - Good for GATE operation (control relative to robot)

2. **Field-Centric Mode** (Toggle)
   - Joystick forward = drive toward opposite alliance wall (always)
   - Robot can strafe/rotate while maintaining direction
   - Faster cycles - no mental rotation needed
   - Requires IMU heading

3. **Precision Mode** (Hold)
   - Reduces all motor powers to 30%
   - Fine positioning for PATTERN scoring
   - Combine with either robot or field-centric

### TeleOp Code Pattern

```java
@TeleOp(name="DECODE TeleOp")
public class DecodeTeleOp extends LinearOpMode {
    private RobotHardware robot;
    private boolean fieldCentric = false;
    private boolean lastDpadUp = false;

    @Override
    public void runOpMode() {
        RobotConfig config = RobotConfig.mecanumDrive(
            "front_left_motor", "front_right_motor",
            "back_left_motor", "back_right_motor")
            .withIMU(true)
            .withCountsPerInch(43.5);
        robot = RobotHardware.createReal(hardwareMap, config);

        waitForStart();
        robot.imu.resetHeading();

        while (opModeIsActive()) {
            // Read inputs
            double drive = -gamepad1.left_stick_y;   // Reversed (Y up = -1)
            double strafe = gamepad1.left_stick_x;
            double turn = gamepad1.right_stick_x;

            // Precision mode
            if (gamepad1.right_bumper) {
                robot.drivetrain.setPowerScale(0.3);
            } else {
                robot.drivetrain.setPowerScale(1.0);
            }

            // Toggle field-centric
            if (gamepad1.dpad_up && !lastDpadUp) {
                fieldCentric = !fieldCentric;
            }
            lastDpadUp = gamepad1.dpad_up;

            // Reset heading
            if (gamepad1.dpad_down) {
                robot.imu.resetHeading();
            }

            // Drive
            if (fieldCentric && robot.imu != null) {
                double heading = Math.toRadians(robot.imu.getHeading());
                robot.drivetrain.fieldCentricDrive(drive, strafe, turn, heading);
            } else {
                robot.drivetrain.mecanumDrive(drive, strafe, turn);
            }

            // Telemetry
            telemetry.addData("Mode", fieldCentric ? "Field-Centric" : "Robot-Centric");
            telemetry.addData("Precision", gamepad1.right_bumper ? "ON" : "OFF");
            if (robot.imu != null) {
                telemetry.addData("Heading", "%.1f°", robot.imu.getHeading());
            }
            telemetry.update();
        }
    }
}
```

### Autonomous Control Architecture

**State Machine Pattern**:

```java
enum AutoState {
    INIT,
    DRIVE_TO_GOAL,
    SCORE_PRELOADED,
    TURN_TO_DEPOT,
    DRIVE_TO_DEPOT,
    COLLECT_ARTIFACTS,
    DRIVE_BACK_TO_GOAL,
    SCORE_COLLECTED,
    RETURN_TO_BASE,
    DONE
}

private AutoState state = AutoState.INIT;
private ElapsedTime stateTimer = new ElapsedTime();

@Override
public void loop() {
    switch (state) {
        case INIT:
            robot.drivetrain.resetEncoders();
            robot.imu.resetHeading();
            state = AutoState.DRIVE_TO_GOAL;
            stateTimer.reset();
            break;

        case DRIVE_TO_GOAL:
            if (!robot.drivetrain.isBusy() || stateTimer.seconds() > 3.0) {
                state = AutoState.SCORE_PRELOADED;
                stateTimer.reset();
            }
            break;

        // ... additional states
    }

    // Timeout safety
    if (stateTimer.seconds() > 25.0) {  // Leave 5 sec buffer
        state = AutoState.DONE;
    }
}
```

**Example Waypoint Sequence**:

```java
// Start position: BASE (0, 0), facing GOAL (0°)

// Drive to GOAL (36" forward)
robot.drivetrain.driveToPosition(36, 0, 0.6);
while (robot.drivetrain.isBusy() && opModeIsActive() && timer.seconds() < 3.0) {
    sleep(10);
}

// Turn to face DEPOT (90° right)
robot.drivetrain.turnToHeading(90, 0.4, robot.imu.getHeading());
while (robot.drivetrain.isBusy() && opModeIsActive() && timer.seconds() < 2.0) {
    sleep(10);
}

// Strafe to DEPOT (24" right)
robot.drivetrain.driveToPosition(0, 24, 0.5);
while (robot.drivetrain.isBusy() && opModeIsActive() && timer.seconds() < 3.0) {
    sleep(10);
}
```

### PID Tuning Guide

**Turn to Heading PID**:

```java
// Start with P only
double kP = 0.02;   // Initial guess: 2% power per degree error
double kI = 0.0;    // Start at zero
double kD = 0.005;  // Dampen oscillation

// Tuning process:
// 1. Set kI = kD = 0, increase kP until oscillation
// 2. Reduce kP to 60% of oscillation threshold
// 3. Add kD to dampen (start with kP/4)
// 4. If steady-state error, add small kI (kP/100)

double error = normalizeAngle(targetHeading - currentHeading);
integral += error * dt;
derivative = (error - lastError) / dt;
double turnPower = kP * error + kI * integral + kD * derivative;
```

**Angle Normalization** (handle wraparound):

```java
double normalizeAngle(double angle) {
    while (angle > 180) angle -= 360;
    while (angle < -180) angle += 360;
    return angle;
}
```

## Safety and Fault Handling

### Motor Stall Detection

**Purpose**: Detect mechanical binding, wheel caught on field element, or motor failure

**Implementation**:
```java
private long stallCheckTimer = 0;
private int lastEncoderPos = 0;

public boolean isStalled() {
    int[] positions = getWheelPositions();
    long now = System.currentTimeMillis();

    // Check if any motor has significant power
    boolean anyMotorPowered = (Math.abs(leftFront.getPower()) > 0.2 ||
                               Math.abs(rightFront.getPower()) > 0.2 ||
                               Math.abs(leftBack.getPower()) > 0.2 ||
                               Math.abs(rightBack.getPower()) > 0.2);

    if (anyMotorPowered) {
        // Check if encoders haven't changed
        int currentPos = (positions[0] + positions[1] + positions[2] + positions[3]) / 4;
        int delta = Math.abs(currentPos - lastEncoderPos);

        if (delta < 10 && (now - stallCheckTimer) > 500) {
            return true;  // Stall detected
        }

        if (delta >= 10) {
            stallCheckTimer = now;
            lastEncoderPos = currentPos;
        }
    } else {
        stallCheckTimer = now;
        lastEncoderPos = (positions[0] + positions[1] + positions[2] + positions[3]) / 4;
    }

    return false;
}

// In OpMode:
if (robot.drivetrain.isStalled()) {
    robot.drivetrain.stop();
    telemetry.addData("ERROR", "Motor stall detected - check for obstruction");
    telemetry.update();
}
```

### IMU Fault Handling

**Graceful Degradation**:
```java
// In OpMode initialization:
if (robot.imu != null && robot.imu.isCalibrated()) {
    telemetry.addData("IMU", "Ready");
    fieldCentricAvailable = true;
} else {
    telemetry.addData("IMU", "Not available - robot-centric only");
    fieldCentricAvailable = false;
    fieldCentric = false;  // Force robot-centric mode
}

// In loop:
if (fieldCentric && robot.imu != null) {
    double heading = Math.toRadians(robot.imu.getHeading());
    robot.drivetrain.fieldCentricDrive(drive, strafe, turn, heading);
} else {
    robot.drivetrain.mecanumDrive(drive, strafe, turn);
}
```

**IMU Drift Mitigation**:
- Reset at start of each auto run
- Provide manual reset button in TeleOp (dpad_down)
- Don't use IMU for position, only heading
- Consider external heading reference (AprilTags) for long matches

### Timeout Protection

**All Blocking Operations Must Have Timeouts**:

```java
ElapsedTime timer = new ElapsedTime();
robot.drivetrain.driveToPosition(24, 0, 0.5);

// BAD - can hang forever if target never reached
while (robot.drivetrain.isBusy() && opModeIsActive()) {
    sleep(10);
}

// GOOD - timeout after 3 seconds
while (robot.drivetrain.isBusy() && opModeIsActive() && timer.seconds() < 3.0) {
    telemetry.addData("Status", "Driving to position");
    telemetry.update();
    sleep(10);
}

if (timer.seconds() >= 3.0) {
    telemetry.addData("WARNING", "Drive timeout - continuing");
}
```

## Testing and Validation

### Unit Test Examples

**MockDrivetrain Tests**:

```java
@Test
public void testMecanumForward() {
    MockDrivetrain dt = new MockDrivetrain();
    dt.mecanumDrive(1.0, 0, 0);  // Full forward

    // All wheels should have same power
    assertEquals(1.0, dt.getFrontLeftPower(), 0.01);
    assertEquals(1.0, dt.getFrontRightPower(), 0.01);
    assertEquals(1.0, dt.getBackLeftPower(), 0.01);
    assertEquals(1.0, dt.getBackRightPower(), 0.01);
}

@Test
public void testMecanumStrafeRight() {
    MockDrivetrain dt = new MockDrivetrain();
    dt.mecanumDrive(0, 1.0, 0);  // Full right strafe

    // FL and BR positive, FR and BL negative
    assertEquals(1.0, dt.getFrontLeftPower(), 0.01);
    assertEquals(-1.0, dt.getFrontRightPower(), 0.01);
    assertEquals(-1.0, dt.getBackLeftPower(), 0.01);
    assertEquals(1.0, dt.getBackRightPower(), 0.01);
}

@Test
public void testFieldCentricRotation() {
    MockDrivetrain dt = new MockDrivetrain();

    // Robot facing 90° (left), driver pushes forward
    dt.fieldCentricDrive(1.0, 0, 0, Math.PI/2);

    // Should strafe left (not drive forward)
    double[] powers = dt.getWheelPowers();
    assertTrue(powers[0] < 0);  // FL negative
    assertTrue(powers[1] > 0);  // FR positive
}

@Test
public void testPowerScaling() {
    MockDrivetrain dt = new MockDrivetrain();
    dt.setPowerScale(0.5);
    dt.mecanumDrive(1.0, 0, 0);

    assertEquals(0.5, dt.getFrontLeftPower(), 0.01);
}
```

### Simulator Validation Checklist

Sync code to virtual_robot and test:

- [ ] Basic movement: Forward, backward, strafe left, strafe right
- [ ] Diagonal movement: Forward-right, forward-left, back-right, back-left
- [ ] Rotation: Clockwise, counterclockwise, while moving
- [ ] Field-centric: Drive forward while rotating 360° (should maintain field direction)
- [ ] Encoder tracking: Drive 48", verify encoder reads ~2064 ticks (48 * 43)
- [ ] Turn to heading: Turn from 0° to 90°, verify lands within ±2°
- [ ] Power scaling: Precision mode reduces speed appropriately
- [ ] Stop: All motors stop immediately

### Real Hardware Validation

**Pre-Match Checklist**:

1. **Motor Direction Test**
   - [ ] Gamepad forward → robot drives forward
   - [ ] Gamepad strafe right → robot strafes right
   - [ ] Gamepad turn right → robot rotates clockwise
   - [ ] All wheels spin when commanded (no loose connections)

2. **IMU Calibration**
   - [ ] IMU shows green LED (calibrated)
   - [ ] Heading reads ~0° when facing away from drivers
   - [ ] Rotate 90° clockwise → heading changes to -90°
   - [ ] Reset heading button works

3. **Encoder Accuracy**
   - [ ] Drive 48" measured → encoders read 2000-2100 ticks
   - [ ] Strafe 24" measured → encoders show strafe movement
   - [ ] Encoders count up when driving forward (not backward)

4. **Field-Centric Test**
   - [ ] Toggle field-centric mode
   - [ ] Push forward on gamepad
   - [ ] Rotate robot 90° → still drives in same field direction
   - [ ] Repeat at 180°, 270°

5. **Autonomous Validation**
   - [ ] Auto drives to expected positions (± 2 inches)
   - [ ] Turns to expected headings (± 3 degrees)
   - [ ] Completes within 30 second time limit
   - [ ] Handles blocked path gracefully (timeout)

**Debugging Common Issues**:

| Symptom | Likely Cause | Solution |
|---------|--------------|----------|
| Robot drives backward | Motor directions reversed | Swap motor direction in constructor |
| Strafe goes diagonal | One motor direction wrong | Check individual wheel directions |
| Field-centric rotates instead | Heading in degrees not radians | Convert to radians: `Math.toRadians(heading)` |
| Encoders count backward | Motor direction vs encoder phase | Negate encoder values or swap directions |
| Can't turn to heading | PID constants too high/low | Reduce kP, add kD |
| Overshoots position | Speed too high, no decel ramp | Reduce maxSpeed, add ramp-down |
| Motors stall under load | Voltage sag, weak battery | Charge battery, reduce concurrent loads |

## Performance Optimization

### Cycle Time Analysis

**Target Cycle Breakdown** (18 seconds per cycle):
- Travel to DEPOT: 3 sec (6 ft @ 2 ft/sec)
- Collect 3 artifacts: 4 sec (mechanism dependent)
- Travel to GOAL: 3 sec
- Score artifacts: 5 sec (mechanism dependent)
- Reposition: 3 sec

**Drivetrain Contribution** (9 sec travel):
- Current: ~6 ft @ 2.5 ft/sec = 2.4 sec
- Optimization opportunities:
  - Increase speed to 3 ft/sec: Save 0.6 sec/cycle
  - Optimize paths (strafe vs turn+drive): Save 0.5 sec/cycle
  - Concurrent operations (drive while arm moving): Save 1 sec/cycle

### Path Optimization

**Diagonal Movement**:
```java
// SLOW: Turn then drive
turnToHeading(45, 0.5, imu.getHeading());
driveToPosition(24, 0, 0.6);

// FAST: Direct diagonal drive
driveToPosition(17, 17, 0.6);  // 24" at 45° = 17" X, 17" Y
```

**Concurrent Operations**:
```java
// Drivetrain can move while arm extends
robot.drivetrain.driveToPosition(12, 0, 0.4);  // Start approach
robot.arm.setPosition(ArmPosition.HIGH);        // Extend arm simultaneously
while (robot.drivetrain.isBusy() && timer.seconds() < 2.0) {
    sleep(10);
}
// Arrive at GOAL with arm already extended
```

## Related Documentation

- **ADR-001**: Architecture Decision Record for this design
- **HARDWARE-CONFIG.md**: Bill of materials, wiring diagrams
- **AUTONOMOUS-PLANS.md**: Specific auto routines using this drivetrain
- **DRIVER-GUIDE.md**: Driver training and control reference

## Revision History

- 2025-12-11: Initial design (Systems Engineer)
