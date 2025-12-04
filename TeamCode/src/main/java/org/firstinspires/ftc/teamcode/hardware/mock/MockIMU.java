package org.firstinspires.ftc.teamcode.hardware.mock;

import org.firstinspires.ftc.teamcode.hardware.interfaces.IIMU;

/**
 * Mock implementation of IIMU for offline testing.
 * Allows manual setting of orientation values for testing.
 */
public class MockIMU implements IIMU {

    private double heading = 0;
    private double pitch = 0;
    private double roll = 0;
    private double angularVelocity = 0;
    private boolean calibrated = true;
    private double headingOffset = 0;

    @Override
    public void initialize() {
        calibrated = true;
    }

    @Override
    public void resetHeading() {
        headingOffset = heading;
    }

    @Override
    public double getHeading() {
        double adjustedHeading = heading - headingOffset;
        // Normalize to -180 to 180
        while (adjustedHeading > 180) adjustedHeading -= 360;
        while (adjustedHeading < -180) adjustedHeading += 360;
        return adjustedHeading;
    }

    @Override
    public double getPitch() {
        return pitch;
    }

    @Override
    public double getRoll() {
        return roll;
    }

    @Override
    public double getAngularVelocity() {
        return angularVelocity;
    }

    @Override
    public boolean isCalibrated() {
        return calibrated;
    }

    // Helper methods for testing

    public void setHeading(double heading) {
        this.heading = heading;
    }

    public void setPitch(double pitch) {
        this.pitch = pitch;
    }

    public void setRoll(double roll) {
        this.roll = roll;
    }

    public void setAngularVelocity(double angularVelocity) {
        this.angularVelocity = angularVelocity;
    }

    public void setCalibrated(boolean calibrated) {
        this.calibrated = calibrated;
    }

    /**
     * Simulate rotation over time.
     * @param degreesPerSecond Rotation rate
     * @param seconds Duration
     */
    public void simulateRotation(double degreesPerSecond, double seconds) {
        heading += degreesPerSecond * seconds;
        angularVelocity = degreesPerSecond;
    }
}
