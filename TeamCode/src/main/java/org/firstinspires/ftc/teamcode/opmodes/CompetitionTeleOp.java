package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.hardware.RobotHardware;
import org.firstinspires.ftc.teamcode.hardware.RobotHardware.RobotConfig;

/**
 * Lesson 12 — the "ship it" TeleOp.
 *
 * This is the configuration that goes to a match: the lesson 06-09 TeleOp
 * plus the slow-mode button (lesson 05's challenge, now standard equipment
 * for lining up scoring). After code freeze, this file only changes for
 * bugs that a failing test proves.
 */
@TeleOp(name = "COMPETITION TeleOp", group = "Competition")
public class CompetitionTeleOp extends LinearOpMode {

    private static final double COUNTS_PER_INCH = 100.0;  // per-robot, lesson 09
    private static final double SLOW_MODE_FACTOR = 0.35;

    @Override
    public void runOpMode() {

        RobotConfig config = RobotConfig.mecanumDrive(
                "front_left_motor", "front_right_motor",
                "back_left_motor", "back_right_motor")
            .withArm("arm_motor")
            .withClaw("hand_servo")
            .withIMU(true);

        RobotHardware robot = RobotHardware.createReal(hardwareMap, config);

        telemetry.addData("Status", "Initialized — COMPETITION");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            double drive = -gamepad1.left_stick_y;
            double strafe = gamepad1.left_stick_x;
            double turn = gamepad1.right_stick_x;

            // Slow mode: hold right bumper to line up a score gently.
            boolean slowMode = gamepad1.right_bumper;
            if (slowMode) {
                drive *= SLOW_MODE_FACTOR;
                strafe *= SLOW_MODE_FACTOR;
                turn *= SLOW_MODE_FACTOR;
            }

            robot.drivetrain.mecanumDrive(drive, strafe, turn);

            double armPower = 0.0;
            if (robot.arm != null) {
                armPower = -gamepad2.left_stick_y * 0.5;
                robot.arm.setPower(armPower);
            }
            if (robot.claw != null) {
                if (gamepad2.a) {
                    robot.claw.open();
                } else if (gamepad2.b) {
                    robot.claw.close();
                }
            }

            telemetry.addLine("— INPUTS —");
            telemetry.addData("Sticks", "d %.2f  s %.2f  t %.2f  %s",
                    drive, strafe, turn, slowMode ? "[SLOW]" : "");
            telemetry.addLine("— DECISIONS —");
            telemetry.addData("Arm power (cmd)", "%.2f", armPower);
            telemetry.addLine("— OUTPUTS —");
            telemetry.addData("Encoders (ticks)", "L %d  R %d",
                    robot.drivetrain.getLeftEncoderPosition(),
                    robot.drivetrain.getRightEncoderPosition());
            telemetry.addLine("— SENSORS —");
            telemetry.addData("Distance (in)", "L %.1f  R %.1f",
                    robot.drivetrain.getLeftEncoderPosition() / COUNTS_PER_INCH,
                    robot.drivetrain.getRightEncoderPosition() / COUNTS_PER_INCH);
            if (robot.imu != null) {
                telemetry.addData("Heading (deg)", "%.1f", robot.imu.getHeading());
            }
            telemetry.update();
        }

        robot.drivetrain.stop();
        if (robot.arm != null) {
            robot.arm.stop();
        }
    }
}
