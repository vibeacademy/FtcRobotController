# ADR-001: Mecanum Drivetrain Design for DECODE 2025-2026

## Status
Accepted

## Context

Team tck517 is competing in FTC DECODE 2025-2026 and needs a drivetrain subsystem design. The DECODE game requires:

- **High-frequency cycles**: Top teams expected to complete ~20 cycles per match (collect 3 artifacts + score)
- **Omnidirectional movement**: Field navigation, artifact collection, and GOAL alignment
- **BASE return capability**: Endgame scoring requires returning to BASE
- **GATE operation**: Must reach and hold GATE open while artifacts exit RAMP
- **Pattern scoring**: Precise positioning to classify artifacts correctly (9+ for PATTERN points)

The team already has a hardware abstraction layer (IDrivetrain interface, RobotHardware factory) but needs architectural guidance for implementing a competitive mecanum drivetrain.

## Decision

Implement a **4-wheel mecanum drivetrain** with the following architecture:

### 1. Enhanced IDrivetrain Interface

The current interface needs these additions:

```java
public interface IDrivetrain {
    // EXISTING METHODS (keep as-is)
    void mecanumDrive(double drive, double strafe, double turn);
    void stop();
    void resetEncoders();
    int getLeftEncoderPosition();
    int getRightEncoderPosition();
    boolean isBusy();
    void driveByEncoder(double leftInches, double rightInches, double speed);
    void setCountsPerInch(double countsPerInch);

    // NEW METHODS FOR DECODE REQUIREMENTS

    /**
     * Field-centric mecanum drive using IMU heading.
     * Converts robot-relative inputs to field-relative movement.
     * @param fieldDrive Forward/backward relative to field (-1.0 to 1.0)
     * @param fieldStrafe Left/right relative to field (-1.0 to 1.0)
     * @param turn Rotation power (-1.0 to 1.0)
     * @param heading Current robot heading from IMU (radians)
     */
    void fieldCentricDrive(double fieldDrive, double fieldStrafe, double turn, double heading);

    /**
     * Drive to a specific position using encoders.
     * @param forwardInches Distance forward (positive) or backward (negative)
     * @param strafeInches Distance right (positive) or left (negative)
     * @param maxSpeed Maximum speed (0.0 to 1.0)
     */
    void driveToPosition(double forwardInches, double strafeInches, double maxSpeed);

    /**
     * Turn to a specific heading using IMU.
     * @param targetHeading Target heading in degrees (-180 to 180)
     * @param maxSpeed Maximum turn speed (0.0 to 1.0)
     * @param currentHeading Current heading from IMU
     */
    void turnToHeading(double targetHeading, double maxSpeed, double currentHeading);

    /**
     * Get individual wheel encoder positions for advanced odometry.
     * @return Array: [frontLeft, frontRight, backLeft, backRight]
     */
    int[] getWheelPositions();

    /**
     * Set power scaling factor for precision mode.
     * @param scale Scaling factor (0.0 to 1.0, default 1.0)
     */
    void setPowerScale(double scale);

    /**
     * Check if motors are stalled (not moving despite power).
     * @return true if any motor appears stalled
     */
    boolean isStalled();
}
```

### 2. Hardware Configuration

**Motor Selection**: goBILDA 5203-2402-0019 (312 RPM) or 5203-2402-0014 (435 RPM)
- **Rationale**: 312 RPM provides ~2.5 ft/sec with 4" wheels, ideal balance of speed and torque
- 435 RPM option for teams prioritizing cycle time over control

**Wheel Configuration**:
- 4" mecanum wheels (goBILDA 3237-0001-0096)
- Wheel base: ~14-16" (typical FTC sizing)
- Track width: ~12-14"

**Encoder Configuration**:
- Use motor integrated encoders (goBILDA motors have 537.7 counts/revolution)
- Counts per inch calculation: `537.7 / (4 * π) ≈ 42.8 counts/inch`
- Account for mecanum slip factor: multiply by ~1.1 for lateral movement

**IMU Placement**:
- Center of robot, mounted rigidly
- Aligned with robot axes (X = forward, Y = left, Z = up)
- REV Control Hub built-in IMU (BNO055 or BHI260AP)

### 3. Control Strategies

#### TeleOp Control Modes

**Mode 1: Robot-Centric (Default)**
- Left stick Y: Forward/backward
- Left stick X: Strafe left/right
- Right stick X: Rotate
- Simple, intuitive for drivers
- Good for GATE operation where robot orientation matters

**Mode 2: Field-Centric (Toggle)**
- Same controls but relative to field, not robot
- Critical for fast cycles - driver maintains orientation
- Requires IMU heading reset at match start
- Toggle with gamepad button (e.g., dpad_up)

**Precision Mode**:
- Reduce power scale to 0.3-0.5 for fine positioning
- Toggle with trigger or bumper
- Essential for PATTERN scoring precision

#### Autonomous Control

**Encoder-Based Navigation**:
```java
// Example: Drive to GOAL from BASE
driveToPosition(36, 0, 0.6);  // 36" forward
turnToHeading(90, 0.4, imu.getHeading());  // Face GOAL
driveToPosition(12, 0, 0.3);  // Approach slowly
```

**State Machine Structure**:
```java
enum AutoState {
    DRIVE_TO_GOAL,
    SCORE_ARTIFACTS,
    DRIVE_TO_DEPOT,
    COLLECT_ARTIFACTS,
    RETURN_TO_BASE,
    DONE
}
```

**PID Turn Control** (for precision heading):
```
kP = 0.02  (start tuning value)
kI = 0.0   (add if steady-state error)
kD = 0.005 (dampen oscillation)

turnPower = kP * headingError + kD * errorRate
```

### 4. Speed Profiles

**Cycle Time Optimization**:
- Fast travel: 0.8-1.0 power (between waypoints)
- Approach: 0.4-0.6 power (final approach to GOAL/DEPOT)
- Precision: 0.2-0.3 power (GATE alignment, PATTERN scoring)

**Acceleration Ramping** (optional enhancement):
```java
// Gradual power increase to prevent wheel slip
double rampRate = 0.05;  // Per loop iteration
currentPower = Math.min(currentPower + rampRate, targetPower);
```

### 5. Safety and Failure Modes

**Motor Stall Detection**:
```java
// If motor power > 0.2 but encoder not changing for 500ms
if (Math.abs(motorPower) > 0.2 &&
    Math.abs(currentPosition - lastPosition) < 10 &&
    timer.milliseconds() > 500) {
    // Stall detected - stop and alert
    stop();
    telemetry.addData("WARNING", "Motor stall detected");
}
```

**IMU Drift Handling**:
- Reset heading at start of auto
- Manual reset button in TeleOp (dpad_down)
- Don't use IMU for position (only heading)
- If IMU fails, gracefully degrade to robot-centric only

**Encoder Failure Fallback**:
- If encoder reads zero despite motion, flag fault
- Fall back to time-based movement (less accurate)
- Log error for post-match analysis

**Power Limits**:
- Always clip to [-1.0, 1.0] (already implemented)
- Max current limiting via Control Hub (30A combined)
- Monitor battery voltage - reduce speed if < 12V

### 6. Testing Strategy

**Unit Tests** (with MockDrivetrain):
```java
@Test
public void testFieldCentricDrive() {
    MockDrivetrain drivetrain = new MockDrivetrain();
    // Heading 90° (facing left)
    drivetrain.fieldCentricDrive(1.0, 0, 0, Math.PI/2);
    // Robot should drive left, not forward
    assertEquals(-1.0, drivetrain.getStrafePower(), 0.01);
}
```

**Simulator Validation**:
1. Sync code to virtual_robot
2. Test basic mecanum movement (all 8 directions)
3. Verify field-centric rotation compensation
4. Test autonomous waypoint navigation

**Real Hardware Checklist**:
- [ ] All motors spin correct direction (test each wheel)
- [ ] Forward drive goes forward (not backward)
- [ ] Strafe right goes right (not left)
- [ ] Turn right rotates clockwise
- [ ] IMU heading increases counterclockwise (FTC convention)
- [ ] Encoders count up when moving forward
- [ ] Counts per inch accurate (measure 48" drive, check encoder delta)
- [ ] Field-centric maintains direction through 360° rotation
- [ ] Precision mode reduces speed appropriately
- [ ] Motors don't stall under normal load

## Consequences

### Advantages
- **Omnidirectional capability**: Critical for DECODE's high-cycle gameplay
- **Field-centric option**: Faster cycles, easier driver control
- **Encoder-based auto**: Proven reliability for FTC (no complex path following needed initially)
- **Works with existing abstraction**: Minimal disruption to codebase architecture
- **Testable**: Mocks enable offline development, simulator validates before hardware

### Disadvantages
- **Complexity**: More complex than tank drive (4 motors vs 2)
- **Wheel slip**: Mecanum wheels slip more than traction wheels (affects encoder accuracy)
- **Cost**: 4 motors + 4 mecanum wheels more expensive than tank
- **Tuning required**: Each robot will need custom counts-per-inch calibration

### Mitigation Strategies
- Start with robot-centric control, add field-centric after drivers comfortable
- Use encoder averaging and slip compensation factors
- Budget for quality motors (goBILDA) and wheels - critical investment
- Create tuning guide document with step-by-step calibration

## Alternatives Considered

### Alternative 1: Tank Drive
- **Pros**: Simpler, cheaper, more torque
- **Cons**: Cannot strafe, slower cycles, poor GOAL alignment
- **Verdict**: Rejected - DECODE requires lateral movement for competitive cycle times

### Alternative 2: X-Drive (Diagonal Wheels)
- **Pros**: Faster strafing than mecanum
- **Cons**: More complex, harder to control, less common in FTC
- **Verdict**: Rejected - Not enough advantage to justify complexity

### Alternative 3: Swerve Drive
- **Pros**: Maximum maneuverability, no wheel slip
- **Cons**: Very complex, expensive, rarely used in FTC
- **Verdict**: Rejected - Overkill for DECODE requirements

### Alternative 4: Add Advanced Odometry (Dead Wheels)
- **Pros**: More accurate position tracking
- **Cons**: Additional hardware, complexity, only needed for advanced path following
- **Verdict**: Deferred - Start with motor encoders, add if needed for later competition

## Implementation Notes

### Phase 1: Core Functionality
1. Update IDrivetrain interface with new methods
2. Implement in RealDrivetrain (field-centric, driveToPosition, turnToHeading)
3. Implement in MockDrivetrain for testing
4. Add precision mode power scaling

### Phase 2: TeleOp Integration
1. Create TeleOp with robot-centric control
2. Add field-centric toggle
3. Add precision mode toggle
4. Test in simulator

### Phase 3: Autonomous
1. Create basic auto state machine
2. Implement encoder-based navigation
3. Add PID turn control
4. Test waypoint sequences

### Phase 4: Refinement
1. Tune counts per inch on real hardware
2. Tune PID constants for turns
3. Add stall detection
4. Optimize cycle times

## Related Documents
- `/docs/HARDWARE-CONFIG.md` - Robot hardware specifications
- `/docs/DRIVER-GUIDE.md` - TeleOp control mappings
- `/docs/AUTONOMOUS-PLANS.md` - Auto routine specifications
- `/TeamCode/src/main/java/org/firstinspires/ftc/teamcode/hardware/interfaces/IDrivetrain.java`

## References
- FTC DECODE Game Manual: https://ftc-resources.firstinspires.org/ftc/game/manual
- goBILDA Mecanum Drivetrain Guide: https://www.gobilda.com/ftc-starter-kit-mecanum/
- GM0 (Game Manual 0) Drivetrain Section: https://gm0.org/en/latest/docs/robot-design/drivetrains/

## Revision History
- 2025-12-11: Initial design (Systems Engineer)
