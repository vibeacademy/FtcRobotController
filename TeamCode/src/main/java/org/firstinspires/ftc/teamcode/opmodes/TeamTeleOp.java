package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.hardware.RobotHardware;
import org.firstinspires.ftc.teamcode.hardware.RobotHardware.RobotConfig;

/**
 * Lesson 06 — The Hardware Abstraction Layer ("after").
 *
 * Same robot behavior as SimTeleOp (the "before" file, kept side by side on
 * purpose — diff them). The differences:
 *
 * - The OpMode never touches a motor. It talks to robot.drivetrain — an
 *   interface (a socket shape). Real motors, simulator motors, or mock
 *   motors can answer; this file can't tell and doesn't care.
 * - The mecanum mixing math from lesson 05 now lives INSIDE the drivetrain
 *   implementation. OpModes express intent; subsystems own math.
 * - Hardware names live in ONE place: the config. Fix a typo once.
 * - Optional hardware gets a null check — the arm exists on Tuesdays, when
 *   the build team hasn't borrowed the motor.
 */
@TeleOp(name = "Team TeleOp (Lesson 06)", group = "Lessons")
public class TeamTeleOp extends LinearOpMode {

    @Override
    public void runOpMode() {

        // The config is the single source of truth for what the robot has
        // and what everything is called.
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

            // One line. The mixing table and the normalize step are inside.
            robot.drivetrain.mecanumDrive(drive, strafe, turn);

            // Optional hardware: null check, always.
            if (robot.arm != null) {
                robot.arm.setPower(-gamepad2.left_stick_y * 0.5);
            }
            if (robot.claw != null) {
                if (gamepad2.a) {
                    robot.claw.open();
                } else if (gamepad2.b) {
                    robot.claw.close();
                }
            }

            telemetry.addData("Intents", "d %.2f  s %.2f  t %.2f", drive, strafe, turn);
            telemetry.update();
        }

        robot.drivetrain.stop();
    }
}
