# Robot Hardware Configuration - DECODE 2025-2026

## Control System

### REV Control Hub
- **Part Number**: REV-31-1595
- **Firmware**: Latest stable (check https://docs.revrobotics.com)
- **IMU**: Built-in BNO055 or BHI260AP
- **Motor Ports**: 0-3 (4 ports for drivetrain)
- **Servo Ports**: 0-5
- **I2C Ports**: For additional sensors

**Configuration Name**: `FtcRobotController.xml`

## Drivetrain

### Motors

| Motor Port | Device Name | Component | Specs |
|------------|-------------|-----------|-------|
| 0 | `front_left_motor` | goBILDA 5203-2402-0019 | 312 RPM, 5.3 Nm |
| 1 | `front_right_motor` | goBILDA 5203-2402-0019 | 312 RPM, 5.3 Nm |
| 2 | `back_left_motor` | goBILDA 5203-2402-0019 | 312 RPM, 5.3 Nm |
| 3 | `back_right_motor` | goBILDA 5203-2402-0019 | 312 RPM, 5.3 Nm |

**Motor Specifications**:
- Encoder counts per revolution: 537.7 (built-in)
- Free speed: 312 RPM
- Stall torque: 5.3 Nm (47 oz-in)
- Stall current: 11.5 A
- Zero power behavior: BRAKE

**Motor Direction Configuration**:
```java
// Left motors: REVERSE
leftFront.setDirection(DcMotorSimple.Direction.REVERSE);
leftBack.setDirection(DcMotorSimple.Direction.REVERSE);

// Right motors: FORWARD
rightFront.setDirection(DcMotorSimple.Direction.FORWARD);
rightBack.setDirection(DcMotorSimple.Direction.FORWARD);
```

### Wheels

**Type**: goBILDA 96mm Mecanum Wheels (3237-0001-0096)
- Diameter: 96mm (3.78", use 4" for calculations)
- Left set: 2 wheels (FL, BL)
- Right set: 2 wheels (FR, BR)
- Roller material: Aluminum
- Grip: Moderate (smooth surface optimized)

**Mounting**: Hub mount pattern (goBILDA compatible)

### Drivetrain Geometry

```
Wheelbase: 15.0 inches (front to back)
Track width: 13.5 inches (left to right)
Robot footprint: 18" x 18" (max for FTC)
Center of rotation: Geometric center
```

### Calibration Values

**Counts Per Inch** (calibrate on your robot):
```java
// Forward/backward movement
private static final double COUNTS_PER_INCH = 43.5;

// Lateral strafe movement (adjusted for slip)
private static final double STRAFE_COUNTS_PER_INCH = 52.0;

// Robot configuration
RobotConfig config = RobotConfig.mecanumDrive(
    "front_left_motor", "front_right_motor",
    "back_left_motor", "back_right_motor")
    .withCountsPerInch(43.5);
```

**Speed Characteristics**:
- Max theoretical speed: `(312 RPM / 60) * (4 * π) ≈ 65 in/sec ≈ 5.4 ft/sec`
- Practical max speed: ~3.0 ft/sec (accounting for motor curves, battery voltage)
- Target operational speed: 2.5 ft/sec (good speed/control balance)

## IMU Configuration

**Built-in IMU**: REV Control Hub internal sensor
- **Sensor**: BNO055 or BHI260AP (depending on hub version)
- **I2C Bus**: Built-in, automatically configured
- **Device Name**: `imu` (standard FTC SDK name)

**Mounting Considerations**:
- Control Hub mounted rigidly at robot center
- Avoid mounting near motors (vibration)
- Ensure flat, level mounting surface
- Mark orientation for reference (+X forward, +Y left, +Z up)

**Initialization**:
```java
IIMU imu = new RealIMU(hardwareMap);
imu.initialize();  // Calibrates sensor
imu.resetHeading();  // Set current orientation to 0°
```

**Coordinate System** (FTC convention):
- X-axis: Forward (positive) / Backward (negative)
- Y-axis: Left (positive) / Right (negative)
- Z-axis: Up (positive) / Down (negative)
- Heading: Counterclockwise positive, range -180° to +180°

## Battery

**Type**: REV Slim Battery (REV-31-1302)
- Voltage: 12V nominal (fully charged: 13.0V, discharged: 11.5V)
- Capacity: 3000 mAh
- Chemistry: NiMH

**Battery Management**:
- Charge before each match
- Monitor voltage - performance degrades below 12V
- Replace if voltage drops quickly under load
- Keep spare batteries charged

**Current Budget**:
- Control Hub: 1-2A
- 4 drive motors (stall): 4 × 11.5A = 46A
- 4 drive motors (typical): 4 × 3A = 12A
- Additional subsystems: Budget 5-10A
- **Total typical draw**: ~20A
- **Max continuous**: 30A (Control Hub limit)

## Wiring Diagram

```
REV Control Hub
┌─────────────────────────────────┐
│ Motor Ports:                    │
│  [0] Front Left Motor (REVERSE) │
│  [1] Front Right Motor          │
│  [2] Back Left Motor (REVERSE)  │
│  [3] Back Right Motor           │
│                                 │
│ Servo Ports:                    │
│  [0] (Available)                │
│  [1] (Available)                │
│  [2] (Available)                │
│  [3] (Available)                │
│  [4] (Available)                │
│  [5] (Available)                │
│                                 │
│ I2C Bus:                        │
│  Built-in IMU                   │
│  [Port 0] (Available)           │
│  [Port 1] (Available)           │
│  [Port 2] (Available)           │
│  [Port 3] (Available)           │
│                                 │
│ Digital:                        │
│  [0-7] (Available)              │
│                                 │
│ Analog:                         │
│  [0-3] (Available)              │
└─────────────────────────────────┘
        │
        ├─── Battery (12V)
        │
        └─── Power Switch
```

## Configuration Files

### FTC Robot Controller Configuration

**Configuration Name**: `DecodeRobot`

**XML Configuration** (export from REV Hardware Client):
```xml
<?xml version='1.0' encoding='UTF-8' standalone='yes' ?>
<Robot type="FirstInspires-FTC">
    <LynxUsbDevice name="Control Hub Portal" serialNumber="..." parentModuleAddress="...">
        <LynxModule name="Control Hub" port="2">

            <!-- Motors -->
            <DcMotor name="front_left_motor" port="0" />
            <DcMotor name="front_right_motor" port="1" />
            <DcMotor name="back_left_motor" port="2" />
            <DcMotor name="back_right_motor" port="3" />

            <!-- Built-in IMU -->
            <LynxEmbeddedIMU name="imu" port="0" bus="0" />

        </LynxModule>
    </LynxUsbDevice>
</Robot>
```

### OpMode Configuration

**Standard Configuration**:
```java
public class MyOpMode extends LinearOpMode {
    private RobotHardware robot;

    @Override
    public void runOpMode() {
        // Standard mecanum configuration
        RobotConfig config = RobotConfig.mecanumDrive(
            "front_left_motor",
            "front_right_motor",
            "back_left_motor",
            "back_right_motor"
        )
        .withIMU(true)
        .withCountsPerInch(43.5);

        robot = RobotHardware.createReal(hardwareMap, config);

        // Ready to use robot.drivetrain, robot.imu, etc.
        waitForStart();
        // ...
    }
}
```

## Sensor Expansion Slots

The following ports are available for additional DECODE-specific sensors:

### I2C Ports (0-3 available)
- **Port 0**: Color sensor for artifact detection
- **Port 1**: Distance sensor for wall alignment
- **Port 2**: (Reserved)
- **Port 3**: (Reserved)

### Servo Ports (0-5 available)
- **Port 0**: Claw servo
- **Port 1**: Wrist servo
- **Port 2-5**: (Reserved for scoring mechanism)

### Digital Ports (0-7 available)
- **Port 0**: Touch sensor (arm limit)
- **Port 1**: Touch sensor (claw grip detection)
- **Port 2-7**: (Reserved)

## Bill of Materials - Drivetrain

| Qty | Part Number | Description | Supplier | Est. Cost |
|-----|-------------|-------------|----------|-----------|
| 4 | 5203-2402-0019 | 312 RPM Yellow Jacket Motor | goBILDA | $60 ea |
| 1 | 3237-0001-0096 | 96mm Mecanum Wheel Set | goBILDA | $85/set |
| 1 | REV-31-1595 | Control Hub | REV Robotics | $300 |
| 1 | REV-31-1302 | Slim Battery (3000mAh) | REV Robotics | $50 |
| 1 | REV-31-1387 | Battery Charger | REV Robotics | $50 |
| 4 | Various | Motor mounting brackets | goBILDA | $20 |
| 1 | Various | Structural components | goBILDA/REV | $150 |

**Estimated Total**: ~$700 (drivetrain only)

## Calibration Procedures

### 1. Motor Direction Calibration

**Test Program**:
```java
@TeleOp(name="Motor Direction Test")
public class MotorDirectionTest extends LinearOpMode {
    @Override
    public void runOpMode() {
        DcMotor fl = hardwareMap.get(DcMotor.class, "front_left_motor");
        DcMotor fr = hardwareMap.get(DcMotor.class, "front_right_motor");
        DcMotor bl = hardwareMap.get(DcMotor.class, "back_left_motor");
        DcMotor br = hardwareMap.get(DcMotor.class, "back_right_motor");

        waitForStart();

        // Test each motor individually
        telemetry.addData("Testing", "Front Left");
        telemetry.update();
        fl.setPower(0.5);
        sleep(2000);
        fl.setPower(0);

        // Repeat for each motor...
        // Verify wheel spins FORWARD (robot moves forward)
    }
}
```

**Expected Results**:
- Front Left: Wheel spins, robot would move forward
- Front Right: Wheel spins, robot would move forward
- Back Left: Wheel spins, robot would move forward
- Back Right: Wheel spins, robot would move forward

If any wheel spins backward, adjust direction in RealDrivetrain constructor.

### 2. Counts Per Inch Calibration

**Test Program**:
```java
@TeleOp(name="Encoder Calibration")
public class EncoderCalibration extends LinearOpMode {
    @Override
    public void runOpMode() {
        RobotConfig config = RobotConfig.mecanumDrive(...).withCountsPerInch(1.0);
        RobotHardware robot = RobotHardware.createReal(hardwareMap, config);

        waitForStart();
        robot.drivetrain.resetEncoders();

        // Drive forward 48 inches (measure with tape measure)
        robot.drivetrain.driveByEncoder(48, 48, 0.5);
        while (robot.drivetrain.isBusy() && opModeIsActive()) {
            sleep(10);
        }

        int leftPos = robot.drivetrain.getLeftEncoderPosition();
        int rightPos = robot.drivetrain.getRightEncoderPosition();

        telemetry.addData("Left Encoder", leftPos);
        telemetry.addData("Right Encoder", rightPos);
        telemetry.addData("Counts/Inch", leftPos / 48.0);
        telemetry.update();
        sleep(30000);
    }
}
```

**Procedure**:
1. Mark starting position on floor
2. Mark 48" line on floor
3. Run test program
4. Manually stop robot when it reaches 48" mark
5. Read encoder values from telemetry
6. Calculate: `countsPerInch = encoderValue / 48`
7. Update RobotConfig with calculated value
8. Repeat for strafe (use 24" perpendicular line)

**Typical Values**:
- Forward: 42-45 counts/inch
- Strafe: 50-55 counts/inch (higher due to mecanum slip)

### 3. IMU Heading Calibration

**Test Program**:
```java
@TeleOp(name="IMU Calibration")
public class IMUCalibration extends LinearOpMode {
    @Override
    public void runOpMode() {
        IIMU imu = new RealIMU(hardwareMap);
        imu.initialize();

        waitForStart();
        imu.resetHeading();

        while (opModeIsActive()) {
            telemetry.addData("Heading", "%.1f°", imu.getHeading());
            telemetry.addData("Calibrated", imu.isCalibrated() ? "YES" : "NO");
            telemetry.update();
        }
    }
}
```

**Procedure**:
1. Place robot on flat surface facing away from drivers
2. Run program and reset heading (should read ~0°)
3. Slowly rotate robot clockwise 90° (use square corner as guide)
4. Verify heading reads -90° (±2°)
5. Continue to 180° (reads +/-180°), 270° (reads +90°)
6. Return to 0° - should read within ±3° of start

**Troubleshooting**:
- If not calibrated: Wait 10-30 seconds for IMU to calibrate
- If heading drifts: IMU not mounted rigidly (vibration)
- If heading wrong direction: Check IMU orientation in Control Hub
- If large errors: Re-flash Control Hub firmware

## Maintenance Schedule

### Before Each Practice
- [ ] Charge battery to full
- [ ] Check motor connections (no loose wires)
- [ ] Verify wheels spin freely
- [ ] Test emergency stop

### Weekly
- [ ] Clean mecanum rollers (remove debris)
- [ ] Check for loose screws/brackets
- [ ] Verify encoder readings consistent
- [ ] Test IMU calibration

### Before Competition
- [ ] Full system test (all OpModes)
- [ ] Backup battery charged
- [ ] Spare motors available
- [ ] Configuration file backed up
- [ ] Re-calibrate encoder counts if wheels replaced

## Troubleshooting Guide

| Problem | Possible Cause | Solution |
|---------|----------------|----------|
| Robot drives backward | Motors reversed | Swap motor directions in code |
| Strafe goes diagonal | One motor wrong direction | Test each wheel individually |
| Encoders don't count | Encoder not enabled | Set motor mode: RUN_USING_ENCODER |
| IMU not calibrated | Hub moved during calibration | Keep robot still for 30 sec |
| Motors overheat | Stall current, binding | Check for mechanical interference |
| Inconsistent speed | Low battery | Charge/replace battery |
| Can't turn in place | One side not powering | Check motor connections |

## Related Documentation

- `DRIVETRAIN-DESIGN.md` - Detailed design and control strategies
- `ADR-001-Mecanum-Drivetrain-Design.md` - Architecture decision rationale
- `DRIVER-GUIDE.md` - TeleOp controls and driver training

## Revision History

- 2025-12-11: Initial hardware configuration (Systems Engineer)
