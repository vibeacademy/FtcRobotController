package org.firstinspires.ftc.teamcode.hardware.mock;

import org.firstinspires.ftc.teamcode.hardware.interfaces.IDrivetrain;
import org.firstinspires.ftc.teamcode.hardware.interfaces.IIMU;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class DrivetrainTest {

    private static final double EPSILON = 0.01;

    @Test
    public void createsMockHardware() {
        IDrivetrain drivetrain = new MockDrivetrain();
        IIMU imu = new MockIMU();

        assertNotNull("Drivetrain should not be null", drivetrain);
        assertNotNull("IMU should not be null", imu);
    }

    @Test
    public void mecanumDriveForwardSetsAllWheelsPositive() {
        MockDrivetrain drivetrain = new MockDrivetrain();

        drivetrain.mecanumDrive(1.0, 0.0, 0.0);

        assertTrue("Front left should be positive", drivetrain.getFrontLeftPower() > 0);
        assertTrue("Front right should be positive", drivetrain.getFrontRightPower() > 0);
        assertTrue("Back left should be positive", drivetrain.getBackLeftPower() > 0);
        assertTrue("Back right should be positive", drivetrain.getBackRightPower() > 0);
    }

    @Test
    public void mecanumDriveStrafeRightSetsExpectedWheelDirections() {
        MockDrivetrain drivetrain = new MockDrivetrain();

        drivetrain.mecanumDrive(0.0, 1.0, 0.0);

        assertTrue("Front left should be positive", drivetrain.getFrontLeftPower() > 0);
        assertTrue("Front right should be negative", drivetrain.getFrontRightPower() < 0);
        assertTrue("Back left should be negative", drivetrain.getBackLeftPower() < 0);
        assertTrue("Back right should be positive", drivetrain.getBackRightPower() > 0);
    }

    @Test
    public void mecanumDriveTurnRightSetsExpectedWheelDirections() {
        MockDrivetrain drivetrain = new MockDrivetrain();

        drivetrain.mecanumDrive(0.0, 0.0, 1.0);

        assertTrue("Front left should be positive", drivetrain.getFrontLeftPower() > 0);
        assertTrue("Front right should be negative", drivetrain.getFrontRightPower() < 0);
        assertTrue("Back left should be positive", drivetrain.getBackLeftPower() > 0);
        assertTrue("Back right should be negative", drivetrain.getBackRightPower() < 0);
    }

    @Test
    public void mecanumDriveCombinesForwardAndStrafePower() {
        MockDrivetrain drivetrain = new MockDrivetrain();

        drivetrain.mecanumDrive(0.5, 0.5, 0.0);

        assertEquals("Front left power", 1.0, drivetrain.getFrontLeftPower(), EPSILON);
        assertEquals("Front right power", 0.0, drivetrain.getFrontRightPower(), EPSILON);
        assertEquals("Back left power", 0.0, drivetrain.getBackLeftPower(), EPSILON);
        assertEquals("Back right power", 1.0, drivetrain.getBackRightPower(), EPSILON);
    }

    @Test
    public void mecanumDriveClampsWheelPowers() {
        MockDrivetrain drivetrain = new MockDrivetrain();

        drivetrain.mecanumDrive(2.0, 0.0, 0.0);

        assertClamped(drivetrain.getFrontLeftPower());
        assertClamped(drivetrain.getFrontRightPower());
        assertClamped(drivetrain.getBackLeftPower());
        assertClamped(drivetrain.getBackRightPower());
    }

    @Test
    public void stopClearsAllWheelPowers() {
        MockDrivetrain drivetrain = new MockDrivetrain();

        drivetrain.mecanumDrive(1.0, 0.0, 0.0);
        assertTrue("Should have power before stop", drivetrain.getFrontLeftPower() > 0);

        drivetrain.stop();

        assertEquals("Front left should be 0", 0.0, drivetrain.getFrontLeftPower(), EPSILON);
        assertEquals("Front right should be 0", 0.0, drivetrain.getFrontRightPower(), EPSILON);
        assertEquals("Back left should be 0", 0.0, drivetrain.getBackLeftPower(), EPSILON);
        assertEquals("Back right should be 0", 0.0, drivetrain.getBackRightPower(), EPSILON);
    }

    @Test
    public void resetEncodersClearsEncoderPositions() {
        MockDrivetrain drivetrain = new MockDrivetrain();

        drivetrain.mecanumDrive(1.0, 0.0, 0.0);
        assertTrue("Left encoder should have moved", drivetrain.getLeftEncoderPosition() != 0);

        drivetrain.resetEncoders();

        assertEquals("Left encoder should be 0", 0, drivetrain.getLeftEncoderPosition());
        assertEquals("Right encoder should be 0", 0, drivetrain.getRightEncoderPosition());
    }

    private static void assertClamped(double power) {
        assertTrue("Power should be >= -1.0", power >= -1.0);
        assertTrue("Power should be <= 1.0", power <= 1.0);
    }
}
