package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.hardware.RobotHardware;
import org.firstinspires.ftc.teamcode.hardware.RobotHardware.RobotConfig;

/**
 * Lesson 10 — Autonomous I: Park Every Time.
 *
 * A state machine, not a script of sleeps. Each state says what the robot
 * IS DOING; each transition says what makes it stop. Every motion loop has
 * three exit doors:
 *
 *   1. GOAL     — target reached
 *   2. TIMEOUT  — time expired (miss this and a stalled motor burns at
 *                 full power against a wall for the rest of autonomous)
 *   3. REFEREE  — opModeIsActive() went false (miss this and your robot
 *                 keeps moving after the ref hits stop — a rules problem)
 *
 * The standard isn't "it worked once." It's 5 runs, 5 parks.
 */
@Autonomous(name = "Park Auto (Lesson 10)", group = "Lessons")
public class ParkAuto extends LinearOpMode {

    private enum State { DRIVE_TO_ZONE, STOP, DONE }

    // Per-robot number — see lesson 09's worksheet. 100.0 = sim/mock default.
    private static final double COUNTS_PER_INCH = 100.0;

    private static final double PARK_DISTANCE_INCHES = 24.0;
    private static final double DRIVE_POWER = 0.4;
    private static final double DRIVE_TIMEOUT_SECONDS = 4.0;  // generous but real

    @Override
    public void runOpMode() {

        RobotConfig config = RobotConfig.mecanumDrive(
                "front_left_motor", "front_right_motor",
                "back_left_motor", "back_right_motor")
            .withIMU(true);

        RobotHardware robot = RobotHardware.createReal(hardwareMap, config);

        // Reset encoders in INIT — stale ticks from TeleOp are the classic
        // "my auto went insane on the second run" bug.
        robot.drivetrain.resetEncoders();
        if (robot.imu != null) {
            robot.imu.resetHeading();
        }

        telemetry.addData("Status", "Initialized — will park %.0f in ahead",
                PARK_DISTANCE_INCHES);
        telemetry.update();

        waitForStart();

        State state = State.DRIVE_TO_ZONE;
        ElapsedTime stateTimer = new ElapsedTime();

        while (opModeIsActive() && state != State.DONE) {

            switch (state) {

                case DRIVE_TO_ZONE: {
                    double traveledInches =
                            robot.drivetrain.getLeftEncoderPosition() / COUNTS_PER_INCH;

                    boolean goalReached = traveledInches >= PARK_DISTANCE_INCHES;
                    boolean timedOut = stateTimer.seconds() > DRIVE_TIMEOUT_SECONDS;

                    if (goalReached || timedOut) {
                        // Exit doors 1 and 2. (Door 3 is the while condition.)
                        state = State.STOP;
                        stateTimer.reset();
                    } else {
                        robot.drivetrain.mecanumDrive(DRIVE_POWER, 0, 0);
                    }

                    telemetry.addData("State", "DRIVE_TO_ZONE");
                    telemetry.addData("Traveled (in)", "%.1f / %.0f",
                            traveledInches, PARK_DISTANCE_INCHES);
                    telemetry.addData("State time (s)", "%.1f", stateTimer.seconds());
                    break;
                }

                case STOP: {
                    robot.drivetrain.stop();
                    state = State.DONE;
                    telemetry.addData("State", "STOP");
                    break;
                }

                default:
                    break;
            }

            telemetry.update();
        }

        // DONE state: do nothing, on purpose. Falling out of the loop without
        // an explicit stop is how robots re-command motors by accident.
        robot.drivetrain.stop();
        telemetry.addData("State", "DONE — parked");
        telemetry.update();
    }
}
