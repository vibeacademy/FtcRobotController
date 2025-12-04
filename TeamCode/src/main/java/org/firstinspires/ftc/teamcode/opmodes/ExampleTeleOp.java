package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.hardware.RobotHardware;
import org.firstinspires.ftc.teamcode.hardware.RobotHardware.RobotConfig;

/**
 * Example TeleOp using the hardware abstraction layer.
 *
 * This OpMode demonstrates:
 * - Using RobotHardware for dependency injection
 * - Mecanum drive control with gamepad
 * - Arm and claw control
 *
 * Remove @Disabled to enable this OpMode.
 */
@TeleOp(name = "Example: TeleOp", group = "Examples")
@Disabled
public class ExampleTeleOp extends LinearOpMode {

    private RobotHardware robot;

    @Override
    public void runOpMode() {
        // Configure the robot hardware
        RobotConfig config = RobotConfig.mecanumDrive(
                "left_front", "right_front",
                "left_back", "right_back")
            .withArm("arm_motor")
            .withClaw("claw_servo", 0.7, 0.3)
            .withIMU(true);

        // Create the robot hardware instance
        robot = RobotHardware.createReal(hardwareMap, config);

        telemetry.addData("Status", "Initialized");
        telemetry.addData("Mode", robot.isMock() ? "MOCK" : "REAL");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            // Drivetrain control (mecanum)
            double drive = -gamepad1.left_stick_y;  // Forward/backward
            double strafe = gamepad1.left_stick_x;   // Left/right strafe
            double turn = gamepad1.right_stick_x;    // Rotation

            robot.drivetrain.mecanumDrive(drive, strafe, turn);

            // Arm control
            if (robot.arm != null) {
                double armPower = -gamepad2.left_stick_y;
                robot.arm.setPower(armPower * 0.5);  // Reduced speed for safety
            }

            // Claw control
            if (robot.claw != null) {
                if (gamepad2.a) {
                    robot.claw.open();
                } else if (gamepad2.b) {
                    robot.claw.close();
                }
            }

            // Telemetry
            telemetry.addData("Drive", "%.2f, %.2f, %.2f", drive, strafe, turn);
            telemetry.addData("Encoders", "L:%d R:%d",
                robot.drivetrain.getLeftEncoderPosition(),
                robot.drivetrain.getRightEncoderPosition());

            if (robot.imu != null) {
                telemetry.addData("Heading", "%.1f°", robot.imu.getHeading());
            }

            if (robot.arm != null) {
                telemetry.addData("Arm Position", robot.arm.getCurrentPosition());
            }

            if (robot.claw != null) {
                telemetry.addData("Claw", robot.claw.isOpen() ? "OPEN" : "CLOSED");
            }

            telemetry.update();
        }

        // Stop all motors when done
        robot.drivetrain.stop();
        if (robot.arm != null) {
            robot.arm.stop();
        }
    }
}
