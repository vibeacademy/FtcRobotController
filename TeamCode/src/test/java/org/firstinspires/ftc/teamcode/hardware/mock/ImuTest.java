package org.firstinspires.ftc.teamcode.hardware.mock;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Lesson 07 — the test written on camera.
 *
 * Hypothesis: after resetHeading(), the IMU reports 0 degrees no matter
 * where it was pointing before. (An auto that trusts a stale heading turns
 * the wrong way on its first move.)
 */
public class ImuTest {

    private static final double EPSILON = 1e-9;

    @Test
    public void resetHeadingZeroesTheHeading() {
        MockIMU imu = new MockIMU();

        // Arrange: the robot ended TeleOp pointing somewhere weird.
        imu.setHeading(87.3);

        // Act: autonomous init resets the heading.
        imu.resetHeading();

        // Assert: "which way am I facing?" now answers zero.
        assertEquals("Heading after reset", 0.0, imu.getHeading(), EPSILON);
    }
}
