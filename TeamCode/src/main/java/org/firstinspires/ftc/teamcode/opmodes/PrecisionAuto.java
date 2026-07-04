package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.autonomous.PBaseAuto;
import org.firstinspires.ftc.teamcode.hardware.RobotHardware;
import org.firstinspires.ftc.teamcode.hardware.RobotHardware.RobotConfig;

/**
 * Lesson 11 — ParkAuto upgraded with P-control.
 *
 * Same mission as lesson 10 (drive 24 inches and stop), but the approach
 * slows itself: power proportional to remaining error. Then it squares up
 * to heading 0 in case the drive drifted.
 */
@Autonomous(name = "Precision Auto (Lesson 11)", group = "Lessons")
public class PrecisionAuto extends PBaseAuto {

    // Tuned in the simulator with TuneKpOpMode. Re-tune when the robot
    // gains weight — these are per-robot, like counts-per-inch.
    private static final double DRIVE_KP = 0.02;
    private static final double TURN_KP = 0.015;

    @Override
    public void runOpMode() {

        RobotConfig config = RobotConfig.mecanumDrive(
                "front_left_motor", "front_right_motor",
                "back_left_motor", "back_right_motor")
            .withIMU(true);

        RobotHardware robot = RobotHardware.createReal(hardwareMap, config);
        robot.drivetrain.resetEncoders();
        if (robot.imu != null) {
            robot.imu.resetHeading();
        }

        telemetry.addData("Status", "Initialized — precision park");
        telemetry.update();

        waitForStart();

        driveInchesP(robot, 24.0, DRIVE_KP, 4.0);
        turnToHeadingP(robot, 0.0, TURN_KP, 3.0);

        telemetry.addData("Status", "DONE — parked with precision");
        telemetry.update();
    }
}
