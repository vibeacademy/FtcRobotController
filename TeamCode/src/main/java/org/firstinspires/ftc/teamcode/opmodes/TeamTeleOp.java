package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.hardware.RobotHardware;
import org.firstinspires.ftc.teamcode.hardware.RobotHardware.RobotConfig;

/**
 * Lesson 08 — TeamTeleOp with a structured telemetry dashboard.
 *
 * The dashboard is an instrument panel, not a diary. Three zones, always in
 * the same order, mirroring the control loop:
 *
 *   INPUTS    — what the drivers are commanding
 *   DECISIONS — what our math computed from it
 *   OUTPUTS   — what the hardware was told / reports back
 *
 * When the robot "does something weird", read the zones top to bottom.
 * The bug lives at the first zone whose numbers surprise you.
 */
@TeleOp(name = "Team TeleOp (Lesson 08)", group = "Lessons")
public class TeamTeleOp extends LinearOpMode {

    @Override
    public void runOpMode() {

        RobotConfig config = RobotConfig.mecanumDrive(
                "front_left_motor", "front_right_motor",
                "back_left_motor", "back_right_motor")
            .withArm("arm_motor")
            .withClaw("hand_servo")
            .withIMU(true);

        RobotHardware robot = RobotHardware.createReal(hardwareMap, config);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            double drive = -gamepad1.left_stick_y;
            double strafe = gamepad1.left_stick_x;
            double turn = gamepad1.right_stick_x;

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

            // ---------- THE DASHBOARD ----------
            telemetry.addLine("— INPUTS —");
            telemetry.addData("Sticks", "d %.2f  s %.2f  t %.2f", drive, strafe, turn);
            telemetry.addData("Gamepad2", "armY %.2f  A:%b B:%b",
                    -gamepad2.left_stick_y, gamepad2.a, gamepad2.b);

            telemetry.addLine("— DECISIONS —");
            telemetry.addData("Arm power (cmd)", "%.2f", armPower);

            telemetry.addLine("— OUTPUTS —");
            telemetry.addData("Encoders (ticks)", "L %d  R %d",
                    robot.drivetrain.getLeftEncoderPosition(),
                    robot.drivetrain.getRightEncoderPosition());
            if (robot.arm != null) {
                telemetry.addData("Arm pos (ticks)", robot.arm.getCurrentPosition());
            }
            if (robot.claw != null) {
                telemetry.addData("Claw", robot.claw.isOpen() ? "OPEN" : "CLOSED");
            }
            telemetry.update();
        }

        robot.drivetrain.stop();
    }
}
