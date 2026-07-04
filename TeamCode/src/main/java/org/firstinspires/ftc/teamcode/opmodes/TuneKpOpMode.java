package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.autonomous.PBaseAuto;
import org.firstinspires.ftc.teamcode.hardware.RobotHardware;
import org.firstinspires.ftc.teamcode.hardware.RobotHardware.RobotConfig;

/**
 * Lesson 11 — live kP tuning.
 *
 * Controls:
 *   dpad up / down — raise / lower kP
 *   A              — run a 24-inch P-controlled drive with the current kP
 *   B              — reset encoders (drive back by hand between runs)
 *
 * What to look for:
 *   kP too LOW  → the robot crawls and stalls short (boring failure)
 *   kP too HIGH → overshoot, reverse, overshoot — oscillation (dramatic)
 *   just right  → settles fast, no overshoot
 */
@TeleOp(name = "Tune kP (Lesson 11)", group = "Lessons")
public class TuneKpOpMode extends PBaseAuto {

    @Override
    public void runOpMode() {

        RobotConfig config = RobotConfig.mecanumDrive(
                "front_left_motor", "front_right_motor",
                "back_left_motor", "back_right_motor")
            .withIMU(true);

        RobotHardware robot = RobotHardware.createReal(hardwareMap, config);
        robot.drivetrain.resetEncoders();

        double kP = 0.02;
        boolean dpadHeld = false;

        telemetry.addData("Status", "Initialized — dpad tunes, A runs");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            // Edge-detect the dpad so one press = one step.
            if (gamepad1.dpad_up && !dpadHeld) {
                kP += 0.005;
            } else if (gamepad1.dpad_down && !dpadHeld) {
                kP = Math.max(0.005, kP - 0.005);
            }
            dpadHeld = gamepad1.dpad_up || gamepad1.dpad_down;

            if (gamepad1.a) {
                driveInchesP(robot, 24.0, kP, 5.0);
            }
            if (gamepad1.b) {
                robot.drivetrain.resetEncoders();
            }

            telemetry.addData("kP", "%.3f  (dpad to adjust)", kP);
            telemetry.addData("Run", "A = drive 24in with this kP");
            telemetry.addData("Symptoms", "oscillates = too high, crawls = too low");
            telemetry.update();
        }
    }
}
