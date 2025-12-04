package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.hardware.interfaces.*;
import org.firstinspires.ftc.teamcode.hardware.mock.*;
import org.firstinspires.ftc.teamcode.hardware.real.*;

/**
 * Central hardware factory and robot configuration.
 * Provides dependency injection for all hardware components.
 *
 * Usage in OpMode:
 *   RobotHardware robot = RobotHardware.createReal(hardwareMap, config);
 *   // or for testing:
 *   RobotHardware robot = RobotHardware.createMock();
 */
public class RobotHardware {

    // Hardware components
    public final IDrivetrain drivetrain;
    public final IArm arm;
    public final IClaw claw;
    public final IIMU imu;
    public final IDistanceSensor distanceSensor;
    public final IColorSensor colorSensor;
    public final ITouchSensor touchSensor;

    // Configuration
    private final boolean isMock;

    /**
     * Private constructor - use factory methods.
     */
    private RobotHardware(IDrivetrain drivetrain, IArm arm, IClaw claw,
                          IIMU imu, IDistanceSensor distanceSensor,
                          IColorSensor colorSensor, ITouchSensor touchSensor,
                          boolean isMock) {
        this.drivetrain = drivetrain;
        this.arm = arm;
        this.claw = claw;
        this.imu = imu;
        this.distanceSensor = distanceSensor;
        this.colorSensor = colorSensor;
        this.touchSensor = touchSensor;
        this.isMock = isMock;
    }

    /**
     * Check if using mock hardware.
     */
    public boolean isMock() {
        return isMock;
    }

    /**
     * Create a mock robot for offline testing.
     * All components are mocked with configurable behavior.
     */
    public static RobotHardware createMock() {
        return new RobotHardware(
            new MockDrivetrain(),
            new MockArm(),
            new MockClaw(),
            new MockIMU(),
            new MockDistanceSensor(),
            new MockColorSensor(),
            new MockTouchSensor(),
            true
        );
    }

    /**
     * Create a real robot with standard hardware configuration.
     */
    public static RobotHardware createReal(HardwareMap hardwareMap, RobotConfig config) {
        IDrivetrain drivetrain;
        if (config.isMecanum) {
            drivetrain = new RealDrivetrain(hardwareMap,
                config.leftFrontMotor, config.rightFrontMotor,
                config.leftBackMotor, config.rightBackMotor);
        } else {
            drivetrain = new RealDrivetrain(hardwareMap,
                config.leftFrontMotor, config.rightFrontMotor);
        }
        drivetrain.setCountsPerInch(config.countsPerInch);

        IArm arm = null;
        if (config.armMotor != null) {
            arm = new RealArm(hardwareMap, config.armMotor);
        }

        IClaw claw = null;
        if (config.clawServo != null) {
            claw = new RealClaw(hardwareMap, config.clawServo,
                config.clawOpenPosition, config.clawClosedPosition);
        }

        IIMU imu = null;
        if (config.useIMU) {
            imu = new RealIMU(hardwareMap);
            imu.initialize();
        }

        IDistanceSensor distanceSensor = null;
        if (config.distanceSensor != null) {
            distanceSensor = new RealDistanceSensor(hardwareMap, config.distanceSensor);
        }

        IColorSensor colorSensor = null;
        if (config.colorSensor != null) {
            colorSensor = new RealColorSensor(hardwareMap, config.colorSensor);
        }

        ITouchSensor touchSensor = null;
        if (config.touchSensor != null) {
            touchSensor = new RealTouchSensor(hardwareMap, config.touchSensor);
        }

        return new RobotHardware(drivetrain, arm, claw, imu,
            distanceSensor, colorSensor, touchSensor, false);
    }

    /**
     * Robot configuration holder.
     * Set the device names and parameters before creating RobotHardware.
     */
    public static class RobotConfig {
        // Drive motors
        public String leftFrontMotor = "left_front";
        public String rightFrontMotor = "right_front";
        public String leftBackMotor = "left_back";
        public String rightBackMotor = "right_back";
        public boolean isMecanum = true;
        public double countsPerInch = 100.0;

        // Arm
        public String armMotor = null;

        // Claw
        public String clawServo = null;
        public double clawOpenPosition = 1.0;
        public double clawClosedPosition = 0.0;

        // IMU
        public boolean useIMU = true;

        // Sensors
        public String distanceSensor = null;
        public String colorSensor = null;
        public String touchSensor = null;

        /**
         * Create a basic tank drive config.
         */
        public static RobotConfig tankDrive(String leftMotor, String rightMotor) {
            RobotConfig config = new RobotConfig();
            config.leftFrontMotor = leftMotor;
            config.rightFrontMotor = rightMotor;
            config.isMecanum = false;
            return config;
        }

        /**
         * Create a mecanum drive config.
         */
        public static RobotConfig mecanumDrive(String leftFront, String rightFront,
                                                String leftBack, String rightBack) {
            RobotConfig config = new RobotConfig();
            config.leftFrontMotor = leftFront;
            config.rightFrontMotor = rightFront;
            config.leftBackMotor = leftBack;
            config.rightBackMotor = rightBack;
            config.isMecanum = true;
            return config;
        }

        // Fluent setters for chaining

        public RobotConfig withArm(String motorName) {
            this.armMotor = motorName;
            return this;
        }

        public RobotConfig withClaw(String servoName, double openPos, double closedPos) {
            this.clawServo = servoName;
            this.clawOpenPosition = openPos;
            this.clawClosedPosition = closedPos;
            return this;
        }

        public RobotConfig withClaw(String servoName) {
            return withClaw(servoName, 1.0, 0.0);
        }

        public RobotConfig withIMU(boolean enabled) {
            this.useIMU = enabled;
            return this;
        }

        public RobotConfig withDistanceSensor(String sensorName) {
            this.distanceSensor = sensorName;
            return this;
        }

        public RobotConfig withColorSensor(String sensorName) {
            this.colorSensor = sensorName;
            return this;
        }

        public RobotConfig withTouchSensor(String sensorName) {
            this.touchSensor = sensorName;
            return this;
        }

        public RobotConfig withCountsPerInch(double counts) {
            this.countsPerInch = counts;
            return this;
        }
    }
}
