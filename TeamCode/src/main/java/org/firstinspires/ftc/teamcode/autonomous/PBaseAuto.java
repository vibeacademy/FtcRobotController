package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.hardware.RobotHardware;

/**
 * Lesson 11 — proportional-control helpers.
 *
 * The whole idea is one multiplication:  power = kP × error.
 * Far from the target → big power. Close → gentle. Past it → the sign
 * flips and it self-corrects. You already run this algorithm when you
 * ease a bike up to a stop sign; here we write it down.
 *
 * Tuning symptoms (memorize this):
 *   robot OSCILLATES around the target → kP too HIGH
 *   robot CRAWLS and stalls short      → kP too LOW
 *
 * Every helper keeps the three exit doors from lesson 10: goal, timeout,
 * opModeIsActive().
 */
public abstract class PBaseAuto extends LinearOpMode {

    // Per-robot number — lesson 09's worksheet. 100.0 = sim/mock default.
    protected static final double COUNTS_PER_INCH = 100.0;

    // Below this power, friction wins and the robot stalls just short of
    // the target. Floor the command so "gentle" never becomes "stuck".
    protected static final double MIN_DRIVE_POWER = 0.08;

    protected static final double DISTANCE_TOLERANCE_INCHES = 0.5;
    protected static final double HEADING_TOLERANCE_DEGREES = 2.0;

    /**
     * Drive forward (or back) a distance using P-control on encoder error.
     */
    protected void driveInchesP(RobotHardware robot, double targetInches,
                                double kP, double timeoutSeconds) {
        robot.drivetrain.resetEncoders();
        ElapsedTime timer = new ElapsedTime();

        while (opModeIsActive() && timer.seconds() < timeoutSeconds) {
            double traveled =
                    robot.drivetrain.getLeftEncoderPosition() / COUNTS_PER_INCH;
            double error = targetInches - traveled;

            if (Math.abs(error) < DISTANCE_TOLERANCE_INCHES) {
                break;  // goal door
            }

            // The one line this lesson is about.
            double power = clampWithFloor(kP * error);
            robot.drivetrain.mecanumDrive(power, 0, 0);

            telemetry.addData("driveInchesP", "err %.1f in  pwr %.2f", error, power);
            telemetry.update();
        }
        robot.drivetrain.stop();
    }

    /**
     * Turn to an absolute heading using P-control on IMU error.
     */
    protected void turnToHeadingP(RobotHardware robot, double targetDegrees,
                                  double kP, double timeoutSeconds) {
        if (robot.imu == null) {
            telemetry.addData("turnToHeadingP", "no IMU configured — skipped");
            telemetry.update();
            return;
        }
        ElapsedTime timer = new ElapsedTime();

        while (opModeIsActive() && timer.seconds() < timeoutSeconds) {
            double error = normalizeDegrees(targetDegrees - robot.imu.getHeading());

            if (Math.abs(error) < HEADING_TOLERANCE_DEGREES) {
                break;  // goal door
            }

            // Heading is positive-counterclockwise; turn command is
            // positive-clockwise — hence the minus sign.
            double power = clampWithFloor(kP * -error);
            robot.drivetrain.mecanumDrive(0, 0, power);

            telemetry.addData("turnToHeadingP", "err %.1f deg  pwr %.2f", error, power);
            telemetry.update();
        }
        robot.drivetrain.stop();
    }

    /** Clamp to [-1, 1] (lesson 05) and floor tiny magnitudes (friction). */
    private static double clampWithFloor(double power) {
        double clamped = Math.max(-1.0, Math.min(1.0, power));
        if (clamped != 0.0 && Math.abs(clamped) < MIN_DRIVE_POWER) {
            clamped = Math.copySign(MIN_DRIVE_POWER, clamped);
        }
        return clamped;
    }

    /** Wrap an angle difference into [-180, 180] — the shortest way around. */
    protected static double normalizeDegrees(double degrees) {
        while (degrees > 180) {
            degrees -= 360;
        }
        while (degrees < -180) {
            degrees += 360;
        }
        return degrees;
    }
}
