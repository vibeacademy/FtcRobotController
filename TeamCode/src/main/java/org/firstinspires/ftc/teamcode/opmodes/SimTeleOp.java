package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Lesson 05 — Mecanum Math.
 *
 * Full mecanum drive: two sticks in, four wheel powers out. Each wheel gets
 * a recipe, not a command:
 *
 *              | DRIVE | STRAFE | TURN |
 *   frontLeft  |   +   |   +    |  +   |
 *   frontRight |   +   |   -    |  -   |
 *   backLeft   |   +   |   -    |  +   |
 *   backRight  |   +   |   +    |  -   |
 *
 * The normalization step at the bottom is the safety lesson: without it,
 * combined inputs push powers past 1.0, the SDK clips each wheel
 * differently, the ratios between wheels get destroyed, and the robot
 * lurches off course.
 *
 * (Still talking to motors directly on purpose — lesson 06 fixes that.)
 */
@TeleOp(name = "Sim TeleOp (Lesson 05 - Mecanum)", group = "Lessons")
public class SimTeleOp extends LinearOpMode {

    @Override
    public void runOpMode() {

        DcMotor frontLeft = hardwareMap.get(DcMotor.class, "front_left_motor");
        DcMotor frontRight = hardwareMap.get(DcMotor.class, "front_right_motor");
        DcMotor backLeft = hardwareMap.get(DcMotor.class, "back_left_motor");
        DcMotor backRight = hardwareMap.get(DcMotor.class, "back_right_motor");

        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        backLeft.setDirection(DcMotor.Direction.REVERSE);

        telemetry.addData("Status", "Initialized — mecanum drive");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            // Three intents from two sticks.
            double drive = -gamepad1.left_stick_y;   // forward/backward
            double strafe = gamepad1.left_stick_x;   // slide left/right
            double turn = gamepad1.right_stick_x;    // rotate

            // The mixing lines — each wheel's recipe from the table above.
            double fl = drive + strafe + turn;
            double fr = drive - strafe - turn;
            double bl = drive - strafe + turn;
            double br = drive + strafe - turn;

            // Normalize: if any wheel wants more than 1.0, scale ALL four
            // down by the same factor. This preserves the ratios between
            // wheels — which is what keeps the motion straight.
            double max = Math.max(
                    Math.max(Math.abs(fl), Math.abs(fr)),
                    Math.max(Math.abs(bl), Math.abs(br)));
            if (max > 1.0) {
                fl /= max;
                fr /= max;
                bl /= max;
                br /= max;
            }

            frontLeft.setPower(fl);
            frontRight.setPower(fr);
            backLeft.setPower(bl);
            backRight.setPower(br);

            telemetry.addData("Intents", "d %.2f  s %.2f  t %.2f", drive, strafe, turn);
            telemetry.addData("Wheels", "fl %.2f  fr %.2f  bl %.2f  br %.2f", fl, fr, bl, br);
            telemetry.update();
        }
    }
}
