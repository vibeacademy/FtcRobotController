package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.autonomous.PBaseAuto;
import org.firstinspires.ftc.teamcode.hardware.RobotHardware;
import org.firstinspires.ftc.teamcode.hardware.RobotHardware.RobotConfig;

/**
 * Lesson 12 — the "ship it" autonomous.
 *
 * Precision park (lesson 11), pinned as the competition configuration.
 * Boring and 5/5 beats fancy and 3/5 — see the lesson 10 math. Season
 * scoring moves get added as new states AFTER this baseline is reliable
 * on carpet, never instead of it.
 */
@Autonomous(name = "COMPETITION Auto", group = "Competition")
public class CompetitionAuto extends PBaseAuto {

    private static final double DRIVE_KP = 0.02;   // tuned — lesson 11 drill
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

        telemetry.addData("Status", "Initialized — COMPETITION auto");
        telemetry.update();

        waitForStart();

        driveInchesP(robot, 24.0, DRIVE_KP, 4.0);
        turnToHeadingP(robot, 0.0, TURN_KP, 3.0);

        telemetry.addData("Status", "PARKED");
        telemetry.update();
    }
}
