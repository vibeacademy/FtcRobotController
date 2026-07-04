package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Lesson 04 — Drive a Robot That Doesn't Exist.
 *
 * Two-stick tank drive, written for the virtual_robot simulator. The
 * hardware names below are the simulator's standard names — the same names
 * a real robot's configuration would use. That's the whole trick: the code
 * asks for motors by name, and whoever answers (simulator or metal), the
 * code can't tell.
 *
 * Note: this file talks to motors directly on purpose — it's the "before"
 * picture. Lesson 06 shows why competitive teams don't leave it this way.
 */
@TeleOp(name = "Sim TeleOp (Lesson 04)", group = "Lessons")
public class SimTeleOp extends LinearOpMode {

    @Override
    public void runOpMode() {

        // Ask for each motor by name. One typo here = crash on init —
        // on a real robot too. (Lesson 12 shows where these names live.)
        DcMotor frontLeft = hardwareMap.get(DcMotor.class, "front_left_motor");
        DcMotor frontRight = hardwareMap.get(DcMotor.class, "front_right_motor");
        DcMotor backLeft = hardwareMap.get(DcMotor.class, "back_left_motor");
        DcMotor backRight = hardwareMap.get(DcMotor.class, "back_right_motor");

        // Left-side motors are mounted mirrored, so reverse them —
        // otherwise "forward" spins the robot in place.
        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        backLeft.setDirection(DcMotor.Direction.REVERSE);

        telemetry.addData("Status", "Initialized — tank drive, two sticks");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            // Stick up reads negative (lesson 03), so negate to make
            // up = forward.
            double leftPower = -gamepad1.left_stick_y;
            double rightPower = -gamepad1.right_stick_y;

            frontLeft.setPower(leftPower);
            backLeft.setPower(leftPower);
            frontRight.setPower(rightPower);
            backRight.setPower(rightPower);

            telemetry.addData("Left power", "%.2f", leftPower);
            telemetry.addData("Right power", "%.2f", rightPower);
            telemetry.update();
        }
    }
}
