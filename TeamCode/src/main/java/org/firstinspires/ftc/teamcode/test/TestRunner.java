package org.firstinspires.ftc.teamcode.test;

import org.firstinspires.ftc.teamcode.hardware.interfaces.*;
import org.firstinspires.ftc.teamcode.hardware.mock.*;

/**
 * Standalone test runner for SimpleTeleOp logic.
 * Uses mock hardware directly to avoid FTC SDK dependencies.
 *
 * Run from command line:
 *   cd TeamCode/src/main/java
 *   javac -d /tmp/ftc-test org/firstinspires/ftc/teamcode/hardware/interfaces/*.java \
 *         org/firstinspires/ftc/teamcode/hardware/mock/*.java \
 *         org/firstinspires/ftc/teamcode/test/TestRunner.java
 *   java -cp /tmp/ftc-test org.firstinspires.ftc.teamcode.test.TestRunner
 */
public class TestRunner {

    // Arm position presets (matching SimpleTeleOp)
    public static final int ARM_GROUND = 0;
    public static final int ARM_MID = 200;
    public static final int ARM_HIGH = 400;

    private static int testsRun = 0;
    private static int testsPassed = 0;
    private static int testsFailed = 0;

    public static void main(String[] args) {
        System.out.println("===========================================");
        System.out.println("  SimpleTeleOp Unit Tests");
        System.out.println("===========================================\n");

        // Run all tests
        testMockHardwareCreation();
        testMecanumDriveForward();
        testMecanumDriveStrafe();
        testMecanumDriveTurn();
        testMecanumDriveCombined();
        testMecanumDriveStop();
        testArmPresetGround();
        testArmPresetMid();
        testArmPresetHigh();
        testArmLimits();
        testClawOpen();
        testClawClose();
        testClawToggle();
        testEncoderReset();

        // Summary
        System.out.println("\n===========================================");
        System.out.println("  Test Summary");
        System.out.println("===========================================");
        System.out.println("Tests Run:    " + testsRun);
        System.out.println("Tests Passed: " + testsPassed);
        System.out.println("Tests Failed: " + testsFailed);
        System.out.println("===========================================");

        // Exit with appropriate code
        System.exit(testsFailed > 0 ? 1 : 0);
    }

    // =========================================
    // Test: Mock Hardware Creation
    // =========================================
    private static void testMockHardwareCreation() {
        testsRun++;
        try {
            MockDrivetrain drivetrain = new MockDrivetrain();
            MockArm arm = new MockArm();
            MockClaw claw = new MockClaw();
            MockIMU imu = new MockIMU();

            assertNotNull("Drivetrain should not be null", drivetrain);
            assertNotNull("Arm should not be null", arm);
            assertNotNull("Claw should not be null", claw);
            assertNotNull("IMU should not be null", imu);

            pass("testMockHardwareCreation");
        } catch (Exception e) {
            fail("testMockHardwareCreation", e.getMessage());
        }
    }

    // =========================================
    // Test: Mecanum Drive Forward
    // =========================================
    private static void testMecanumDriveForward() {
        testsRun++;
        try {
            MockDrivetrain drivetrain = new MockDrivetrain();

            // Drive forward: drive=1.0, strafe=0, turn=0
            drivetrain.mecanumDrive(1.0, 0.0, 0.0);

            // All wheels should have positive power for forward motion
            assertTrue("Front left should be positive", drivetrain.getFrontLeftPower() > 0);
            assertTrue("Front right should be positive", drivetrain.getFrontRightPower() > 0);
            assertTrue("Back left should be positive", drivetrain.getBackLeftPower() > 0);
            assertTrue("Back right should be positive", drivetrain.getBackRightPower() > 0);

            pass("testMecanumDriveForward");
        } catch (Exception e) {
            fail("testMecanumDriveForward", e.getMessage());
        }
    }

    // =========================================
    // Test: Mecanum Drive Strafe Right
    // =========================================
    private static void testMecanumDriveStrafe() {
        testsRun++;
        try {
            MockDrivetrain drivetrain = new MockDrivetrain();

            // Strafe right: drive=0, strafe=1.0, turn=0
            drivetrain.mecanumDrive(0.0, 1.0, 0.0);

            // For strafe right: FL+, FR-, BL-, BR+
            assertTrue("Front left should be positive", drivetrain.getFrontLeftPower() > 0);
            assertTrue("Front right should be negative", drivetrain.getFrontRightPower() < 0);
            assertTrue("Back left should be negative", drivetrain.getBackLeftPower() < 0);
            assertTrue("Back right should be positive", drivetrain.getBackRightPower() > 0);

            pass("testMecanumDriveStrafe");
        } catch (Exception e) {
            fail("testMecanumDriveStrafe", e.getMessage());
        }
    }

    // =========================================
    // Test: Mecanum Drive Turn
    // =========================================
    private static void testMecanumDriveTurn() {
        testsRun++;
        try {
            MockDrivetrain drivetrain = new MockDrivetrain();

            // Turn right: drive=0, strafe=0, turn=1.0
            drivetrain.mecanumDrive(0.0, 0.0, 1.0);

            // For turn right: FL+, FR-, BL+, BR-
            assertTrue("Front left should be positive", drivetrain.getFrontLeftPower() > 0);
            assertTrue("Front right should be negative", drivetrain.getFrontRightPower() < 0);
            assertTrue("Back left should be positive", drivetrain.getBackLeftPower() > 0);
            assertTrue("Back right should be negative", drivetrain.getBackRightPower() < 0);

            pass("testMecanumDriveTurn");
        } catch (Exception e) {
            fail("testMecanumDriveTurn", e.getMessage());
        }
    }

    // =========================================
    // Test: Mecanum Drive Combined
    // =========================================
    private static void testMecanumDriveCombined() {
        testsRun++;
        try {
            MockDrivetrain drivetrain = new MockDrivetrain();

            // Drive forward while strafing right
            drivetrain.mecanumDrive(0.5, 0.5, 0.0);

            // FL should be highest (drive + strafe)
            // FR should be zero (drive - strafe)
            // BL should be zero (drive - strafe)
            // BR should be highest (drive + strafe)
            assertEquals("Front left power", 1.0, drivetrain.getFrontLeftPower(), 0.01);
            assertEquals("Front right power", 0.0, drivetrain.getFrontRightPower(), 0.01);
            assertEquals("Back left power", 0.0, drivetrain.getBackLeftPower(), 0.01);
            assertEquals("Back right power", 1.0, drivetrain.getBackRightPower(), 0.01);

            pass("testMecanumDriveCombined");
        } catch (Exception e) {
            fail("testMecanumDriveCombined", e.getMessage());
        }
    }

    // =========================================
    // Test: Mecanum Drive Stop
    // =========================================
    private static void testMecanumDriveStop() {
        testsRun++;
        try {
            MockDrivetrain drivetrain = new MockDrivetrain();

            // Drive forward first
            drivetrain.mecanumDrive(1.0, 0.0, 0.0);
            assertTrue("Should have power before stop", drivetrain.getFrontLeftPower() > 0);

            // Stop
            drivetrain.stop();

            assertEquals("Front left should be 0", 0.0, drivetrain.getFrontLeftPower(), 0.01);
            assertEquals("Front right should be 0", 0.0, drivetrain.getFrontRightPower(), 0.01);
            assertEquals("Back left should be 0", 0.0, drivetrain.getBackLeftPower(), 0.01);
            assertEquals("Back right should be 0", 0.0, drivetrain.getBackRightPower(), 0.01);

            pass("testMecanumDriveStop");
        } catch (Exception e) {
            fail("testMecanumDriveStop", e.getMessage());
        }
    }

    // =========================================
    // Test: Arm Preset Ground
    // =========================================
    private static void testArmPresetGround() {
        testsRun++;
        try {
            MockArm arm = new MockArm();

            // Start at a different position
            arm.setCurrentPosition(200);

            // Move to ground
            arm.moveToPosition(ARM_GROUND, 0.5);

            assertEquals("Arm should be at ground", ARM_GROUND, arm.getCurrentPosition());

            pass("testArmPresetGround");
        } catch (Exception e) {
            fail("testArmPresetGround", e.getMessage());
        }
    }

    // =========================================
    // Test: Arm Preset Mid
    // =========================================
    private static void testArmPresetMid() {
        testsRun++;
        try {
            MockArm arm = new MockArm();

            arm.moveToPosition(ARM_MID, 0.8);

            assertEquals("Arm should be at mid", ARM_MID, arm.getCurrentPosition());

            pass("testArmPresetMid");
        } catch (Exception e) {
            fail("testArmPresetMid", e.getMessage());
        }
    }

    // =========================================
    // Test: Arm Preset High
    // =========================================
    private static void testArmPresetHigh() {
        testsRun++;
        try {
            MockArm arm = new MockArm();

            arm.moveToPosition(ARM_HIGH, 0.8);

            assertEquals("Arm should be at high", ARM_HIGH, arm.getCurrentPosition());

            pass("testArmPresetHigh");
        } catch (Exception e) {
            fail("testArmPresetHigh", e.getMessage());
        }
    }

    // =========================================
    // Test: Arm Limits
    // =========================================
    private static void testArmLimits() {
        testsRun++;
        try {
            MockArm arm = new MockArm();

            // At lower limit
            arm.setCurrentPosition(0);
            assertTrue("Should be at lower limit", arm.isAtLowerLimit());
            assertFalse("Should not be at upper limit", arm.isAtUpperLimit());

            // At upper limit
            arm.setCurrentPosition(1000);
            assertFalse("Should not be at lower limit", arm.isAtLowerLimit());
            assertTrue("Should be at upper limit", arm.isAtUpperLimit());

            pass("testArmLimits");
        } catch (Exception e) {
            fail("testArmLimits", e.getMessage());
        }
    }

    // =========================================
    // Test: Claw Open
    // =========================================
    private static void testClawOpen() {
        testsRun++;
        try {
            MockClaw claw = new MockClaw();

            // Start closed
            claw.close();
            assertFalse("Claw should start closed", claw.isOpen());

            // Open it
            claw.open();
            assertTrue("Claw should be open", claw.isOpen());

            pass("testClawOpen");
        } catch (Exception e) {
            fail("testClawOpen", e.getMessage());
        }
    }

    // =========================================
    // Test: Claw Close
    // =========================================
    private static void testClawClose() {
        testsRun++;
        try {
            MockClaw claw = new MockClaw();

            // Start open
            claw.open();
            assertTrue("Claw should start open", claw.isOpen());

            // Close it
            claw.close();
            assertTrue("Claw should be closed", claw.isClosed());

            pass("testClawClose");
        } catch (Exception e) {
            fail("testClawClose", e.getMessage());
        }
    }

    // =========================================
    // Test: Claw Toggle
    // =========================================
    private static void testClawToggle() {
        testsRun++;
        try {
            MockClaw claw = new MockClaw();

            // Initial state (closed by default at position 0)
            assertTrue("Claw should start closed", claw.isClosed());

            // Toggle open
            claw.open();
            assertTrue("Claw should be open", claw.isOpen());

            // Toggle closed
            claw.close();
            assertTrue("Claw should be closed", claw.isClosed());

            // Verify positions
            claw.open();
            assertEquals("Open position", 1.0, claw.getPosition(), 0.01);

            claw.close();
            assertEquals("Closed position", 0.0, claw.getPosition(), 0.01);

            pass("testClawToggle");
        } catch (Exception e) {
            fail("testClawToggle", e.getMessage());
        }
    }

    // =========================================
    // Test: Encoder Reset
    // =========================================
    private static void testEncoderReset() {
        testsRun++;
        try {
            MockDrivetrain drivetrain = new MockDrivetrain();

            // Drive to change encoder values
            drivetrain.mecanumDrive(1.0, 0.0, 0.0);
            assertTrue("Left encoder should have moved", drivetrain.getLeftEncoderPosition() != 0);

            // Reset encoders
            drivetrain.resetEncoders();

            assertEquals("Left encoder should be 0", 0, drivetrain.getLeftEncoderPosition());
            assertEquals("Right encoder should be 0", 0, drivetrain.getRightEncoderPosition());

            pass("testEncoderReset");
        } catch (Exception e) {
            fail("testEncoderReset", e.getMessage());
        }
    }

    // =========================================
    // Assertion Helpers
    // =========================================

    private static void assertTrue(String message, boolean condition) {
        if (!condition) {
            throw new AssertionError(message + " - expected true but was false");
        }
    }

    private static void assertFalse(String message, boolean condition) {
        if (condition) {
            throw new AssertionError(message + " - expected false but was true");
        }
    }

    private static void assertNotNull(String message, Object obj) {
        if (obj == null) {
            throw new AssertionError(message + " - expected non-null but was null");
        }
    }

    private static void assertEquals(String message, int expected, int actual) {
        if (expected != actual) {
            throw new AssertionError(message + " - expected " + expected + " but was " + actual);
        }
    }

    private static void assertEquals(String message, double expected, double actual, double delta) {
        if (Math.abs(expected - actual) > delta) {
            throw new AssertionError(message + " - expected " + expected + " but was " + actual);
        }
    }

    private static void pass(String testName) {
        testsPassed++;
        System.out.println("[PASS] " + testName);
    }

    private static void fail(String testName, String reason) {
        testsFailed++;
        System.out.println("[FAIL] " + testName + ": " + reason);
    }
}
