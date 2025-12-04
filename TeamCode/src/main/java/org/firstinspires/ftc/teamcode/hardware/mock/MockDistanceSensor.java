package org.firstinspires.ftc.teamcode.hardware.mock;

import org.firstinspires.ftc.teamcode.hardware.interfaces.IDistanceSensor;

/**
 * Mock implementation of IDistanceSensor for offline testing.
 * Allows manual setting of distance values.
 */
public class MockDistanceSensor implements IDistanceSensor {

    private double distanceInches = 12.0;
    private double maxRange = 120.0; // inches
    private boolean outOfRange = false;

    @Override
    public double getDistanceInches() {
        if (outOfRange) {
            return Double.MAX_VALUE;
        }
        return distanceInches;
    }

    @Override
    public double getDistanceCm() {
        if (outOfRange) {
            return Double.MAX_VALUE;
        }
        return distanceInches * 2.54;
    }

    @Override
    public boolean isObjectDetected(double thresholdInches) {
        return !outOfRange && distanceInches <= thresholdInches;
    }

    @Override
    public boolean isValidReading() {
        return !outOfRange && distanceInches <= maxRange;
    }

    // Helper methods for testing

    public void setDistanceInches(double distanceInches) {
        this.distanceInches = distanceInches;
        this.outOfRange = distanceInches > maxRange;
    }

    public void setDistanceCm(double distanceCm) {
        setDistanceInches(distanceCm / 2.54);
    }

    public void setOutOfRange(boolean outOfRange) {
        this.outOfRange = outOfRange;
    }

    public void setMaxRange(double maxRangeInches) {
        this.maxRange = maxRangeInches;
    }
}
