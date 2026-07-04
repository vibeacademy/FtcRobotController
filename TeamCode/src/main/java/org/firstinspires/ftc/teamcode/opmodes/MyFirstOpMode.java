package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * Lesson 03 — Your First OpMode.
 *
 * This is the exact file written in the video. Type your own version first;
 * the typos are where the learning is.
 */
// This annotation is the menu registration: it's how the driver finds your
// program on the Driver Station. No annotation = your program doesn't exist.
@TeleOp(name = "My First OpMode", group = "Lessons")
public class MyFirstOpMode extends LinearOpMode {

    @Override
    public void runOpMode() {

        // ---------- INIT ZONE ----------
        // Everything above waitForStart() happens before the match begins.
        // The robot is NOT allowed to move here — it's the rules of the sport.
        int loopCount = 0;

        telemetry.addData("Status", "Initialized — waiting for start");
        telemetry.update();

        // The dividing line between "before the match" and "the match".
        waitForStart();

        // ---------- THE LOOP ----------
        // This runs about 50 times every second until the match (or you) stops
        // it. Each pass answers one question: what should happen RIGHT NOW?
        while (opModeIsActive()) {
            loopCount++;

            // Read the gamepad. Gotcha: pushing the stick UP reads NEGATIVE.
            // (Aviation convention — everyone gets bitten by this once.)
            double leftStickY = gamepad1.left_stick_y;

            // Telemetry is the robot talking back. It's a dashboard, not a
            // scrolling log: the screen repaints on every update().
            telemetry.addData("Status", "Running");
            telemetry.addData("Loop count", loopCount);
            telemetry.addData("Left stick Y", "%.2f", leftStickY);
            telemetry.update();   // forget this line and the screen stays blank
        }

        // Code down here only runs after the loop ends (match over / stopped).
    }
}
