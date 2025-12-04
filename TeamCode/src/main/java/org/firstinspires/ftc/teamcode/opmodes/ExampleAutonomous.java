package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.hardware.RobotHardware;
import org.firstinspires.ftc.teamcode.hardware.RobotHardware.RobotConfig;

/**
 * Example Autonomous using the hardware abstraction layer.
 *
 * This OpMode demonstrates:
 * - Encoder-based driving
 * - Using IMU for heading
 * - Arm and claw manipulation
 *
 * Remove @Disabled to enable this OpMode.
 */
@Autonomous(name = "Example: Autonomous", group = "Examples")
@Disabled
public class ExampleAutonomous extends LinearOpMode {

    private RobotHardware robot;
    private ElapsedTime runtime = new ElapsedTime();

    // Drive constants
    static final double DRIVE_SPEED = 0.6;
    static final double TURN_SPEED = 0.5;
    static final double COUNTS_PER_INCH = 100.0;  // Adjust for your robot

    @Override
    public void runOpMode() {
        // Configure the robot hardware
        RobotConfig config = RobotConfig.mecanumDrive(
                "left_front", "right_front",
                "left_back", "right_back")
            .withArm("arm_motor")
            .withClaw("claw_servo", 0.7, 0.3)
            .withIMU(true)
            .withCountsPerInch(COUNTS_PER_INCH);

        // Create the robot hardware instance
        robot = RobotHardware.createReal(hardwareMap, config);

        // Reset encoders
        robot.drivetrain.resetEncoders();
        if (robot.imu != null) {
            robot.imu.resetHeading();
        }

        telemetry.addData("Status", "Ready to run");
        telemetry.update();

        waitForStart();
        runtime.reset();

        // Example autonomous routine:
        // 1. Close claw to grab game element
        if (robot.claw != null) {
            robot.claw.close();
            sleep(500);
        }

        // 2. Raise arm
        if (robot.arm != null) {
            robot.arm.moveToPosition(500, 0.5);
            waitForArm(2.0);
        }

        // 3. Drive forward 24 inches
        driveByEncoder(24, 24, DRIVE_SPEED, 5.0);

        // 4. Turn right 90 degrees (using encoder-based turn)
        driveByEncoder(12, -12, TURN_SPEED, 3.0);

        // 5. Drive forward 12 inches
        driveByEncoder(12, 12, DRIVE_SPEED, 3.0);

        // 6. Lower arm and release
        if (robot.arm != null) {
            robot.arm.moveToPosition(200, 0.3);
            waitForArm(2.0);
        }

        if (robot.claw != null) {
            robot.claw.open();
            sleep(500);
        }

        // 7. Back up
        driveByEncoder(-6, -6, DRIVE_SPEED, 2.0);

        telemetry.addData("Path", "Complete");
        telemetry.addData("Runtime", "%.1f seconds", runtime.seconds());
        telemetry.update();

        sleep(1000);
    }

    /**
     * Drive using encoders with timeout.
     */
    private void driveByEncoder(double leftInches, double rightInches,
                                 double speed, double timeoutSeconds) {
        if (!opModeIsActive()) return;

        robot.drivetrain.driveByEncoder(leftInches, rightInches, speed);

        ElapsedTime timer = new ElapsedTime();

        while (opModeIsActive() &&
               timer.seconds() < timeoutSeconds &&
               robot.drivetrain.isBusy()) {

            telemetry.addData("Driving", "L:%.1f R:%.1f", leftInches, rightInches);
            telemetry.addData("Encoders", "L:%d R:%d",
                robot.drivetrain.getLeftEncoderPosition(),
                robot.drivetrain.getRightEncoderPosition());
            telemetry.update();
        }

        robot.drivetrain.stop();
        sleep(100);
    }

    /**
     * Wait for arm to reach position with timeout.
     */
    private void waitForArm(double timeoutSeconds) {
        if (robot.arm == null || !opModeIsActive()) return;

        ElapsedTime timer = new ElapsedTime();

        while (opModeIsActive() &&
               timer.seconds() < timeoutSeconds &&
               robot.arm.isBusy()) {

            telemetry.addData("Arm", "Moving to position");
            telemetry.addData("Arm Position", robot.arm.getCurrentPosition());
            telemetry.update();
        }
    }
}
