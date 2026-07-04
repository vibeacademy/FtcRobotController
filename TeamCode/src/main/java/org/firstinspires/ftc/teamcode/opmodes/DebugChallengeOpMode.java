package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Lesson 08 — the debug challenge.
 *
 * THIS OPMODE HAS EXACTLY ONE PLANTED BUG. The robot misbehaves in the
 * simulator. Diagnose it using ONLY the telemetry dashboard and the four
 * questions — no reading the mixing lines until you've named the zone:
 *
 *   1. What do the INPUTS say?      (wrong → controller/driver problem)
 *   2. What do the DECISIONS say?   (wrong → your math — the code)
 *   3. Do OUTPUTS match decisions?  (wrong → config / hardware map)
 *   4. Does physics match outputs?  (wrong → build team's problem)
 *
 * The bug lives at the first zone whose numbers surprise you.
 */
@TeleOp(name = "Debug Challenge (Lesson 08)", group = "Lessons")
public class DebugChallengeOpMode extends LinearOpMode {

    @Override
    public void runOpMode() {

        DcMotor frontLeft = hardwareMap.get(DcMotor.class, "front_left_motor");
        DcMotor frontRight = hardwareMap.get(DcMotor.class, "front_right_motor");
        DcMotor backLeft = hardwareMap.get(DcMotor.class, "back_left_motor");
        DcMotor backRight = hardwareMap.get(DcMotor.class, "back_right_motor");

        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        backLeft.setDirection(DcMotor.Direction.REVERSE);

        telemetry.addData("Status", "Find the bug. Dashboard only!");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            double drive = -gamepad1.left_stick_y;
            double strafe = gamepad1.left_stick_x;
            double turn = gamepad1.right_stick_x;

            double fl = drive + strafe + turn;
            double fr = drive - strafe - turn;
            double bl = drive + strafe + turn;   // <- one wheel's recipe is wrong
            double br = drive + strafe - turn;

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

            telemetry.addLine("— INPUTS —");
            telemetry.addData("Sticks", "d %.2f  s %.2f  t %.2f", drive, strafe, turn);
            telemetry.addLine("— DECISIONS —");
            telemetry.addData("Wheels", "fl %.2f  fr %.2f  bl %.2f  br %.2f", fl, fr, bl, br);
            telemetry.addLine("— OUTPUTS —");
            telemetry.addData("Powers sent", "fl %.2f  fr %.2f  bl %.2f  br %.2f",
                    frontLeft.getPower(), frontRight.getPower(),
                    backLeft.getPower(), backRight.getPower());
            telemetry.update();
        }
    }
}
