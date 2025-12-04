package org.firstinspires.ftc.teamcode.hardware.mock;

import org.firstinspires.ftc.teamcode.hardware.interfaces.IDrivetrain;

/**
 * Mock implementation of IDrivetrain for offline testing.
 * Simulates motor behavior by tracking power and encoder positions.
 */
public class MockDrivetrain implements IDrivetrain {

    private double leftPower = 0;
    private double rightPower = 0;
    private int leftEncoderPosition = 0;
    private int rightEncoderPosition = 0;
    private double countsPerInch = 100.0; // Default value
    private boolean busy = false;

    // For mecanum simulation
    private double frontLeftPower = 0;
    private double frontRightPower = 0;
    private double backLeftPower = 0;
    private double backRightPower = 0;

    @Override
    public void arcadeDrive(double drive, double turn) {
        leftPower = clip(drive + turn);
        rightPower = clip(drive - turn);
        simulateMovement();
    }

    @Override
    public void tankDrive(double leftPower, double rightPower) {
        this.leftPower = clip(leftPower);
        this.rightPower = clip(rightPower);
        simulateMovement();
    }

    @Override
    public void mecanumDrive(double drive, double strafe, double turn) {
        frontLeftPower = clip(drive + strafe + turn);
        frontRightPower = clip(drive - strafe - turn);
        backLeftPower = clip(drive - strafe + turn);
        backRightPower = clip(drive + strafe - turn);

        // Average for left/right sides
        leftPower = (frontLeftPower + backLeftPower) / 2.0;
        rightPower = (frontRightPower + backRightPower) / 2.0;
        simulateMovement();
    }

    @Override
    public void stop() {
        leftPower = 0;
        rightPower = 0;
        frontLeftPower = 0;
        frontRightPower = 0;
        backLeftPower = 0;
        backRightPower = 0;
        busy = false;
    }

    @Override
    public void resetEncoders() {
        leftEncoderPosition = 0;
        rightEncoderPosition = 0;
    }

    @Override
    public int getLeftEncoderPosition() {
        return leftEncoderPosition;
    }

    @Override
    public int getRightEncoderPosition() {
        return rightEncoderPosition;
    }

    @Override
    public boolean isBusy() {
        return busy;
    }

    @Override
    public void driveByEncoder(double leftInches, double rightInches, double speed) {
        int leftTarget = leftEncoderPosition + (int)(leftInches * countsPerInch);
        int rightTarget = rightEncoderPosition + (int)(rightInches * countsPerInch);

        busy = true;

        // Simulate reaching target instantly for mock
        leftEncoderPosition = leftTarget;
        rightEncoderPosition = rightTarget;

        busy = false;
    }

    @Override
    public void setCountsPerInch(double countsPerInch) {
        this.countsPerInch = countsPerInch;
    }

    // Helper methods for testing

    public double getLeftPower() {
        return leftPower;
    }

    public double getRightPower() {
        return rightPower;
    }

    public double getFrontLeftPower() {
        return frontLeftPower;
    }

    public double getFrontRightPower() {
        return frontRightPower;
    }

    public double getBackLeftPower() {
        return backLeftPower;
    }

    public double getBackRightPower() {
        return backRightPower;
    }

    public void setEncoderPositions(int left, int right) {
        this.leftEncoderPosition = left;
        this.rightEncoderPosition = right;
    }

    private double clip(double value) {
        return Math.max(-1.0, Math.min(1.0, value));
    }

    private void simulateMovement() {
        // Simulate encoder movement based on power (simplified)
        leftEncoderPosition += (int)(leftPower * 10);
        rightEncoderPosition += (int)(rightPower * 10);
    }
}
