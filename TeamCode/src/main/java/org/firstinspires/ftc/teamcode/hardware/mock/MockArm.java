package org.firstinspires.ftc.teamcode.hardware.mock;

import org.firstinspires.ftc.teamcode.hardware.interfaces.IArm;

/**
 * Mock implementation of IArm for offline testing.
 * Simulates arm motor behavior with position tracking.
 */
public class MockArm implements IArm {

    private double power = 0;
    private int currentPosition = 0;
    private int targetPosition = 0;
    private boolean busy = false;
    private int lowerLimit = 0;
    private int upperLimit = 1000;

    @Override
    public void setPower(double power) {
        this.power = Math.max(-1.0, Math.min(1.0, power));
        simulateMovement();
    }

    @Override
    public void moveToPosition(int targetPosition, double power) {
        this.targetPosition = targetPosition;
        this.power = Math.abs(power);
        busy = true;

        // Simulate reaching target instantly for mock
        currentPosition = targetPosition;
        busy = false;
    }

    @Override
    public int getCurrentPosition() {
        return currentPosition;
    }

    @Override
    public boolean isBusy() {
        return busy;
    }

    @Override
    public void stop() {
        power = 0;
        busy = false;
    }

    @Override
    public void resetEncoder() {
        currentPosition = 0;
        targetPosition = 0;
    }

    @Override
    public void holdPosition() {
        // In mock, just maintain current position
        targetPosition = currentPosition;
    }

    @Override
    public boolean isAtLowerLimit() {
        return currentPosition <= lowerLimit;
    }

    @Override
    public boolean isAtUpperLimit() {
        return currentPosition >= upperLimit;
    }

    // Helper methods for testing

    public double getPower() {
        return power;
    }

    public void setCurrentPosition(int position) {
        this.currentPosition = position;
    }

    public void setLimits(int lower, int upper) {
        this.lowerLimit = lower;
        this.upperLimit = upper;
    }

    public int getTargetPosition() {
        return targetPosition;
    }

    private void simulateMovement() {
        // Simulate position change based on power
        int movement = (int)(power * 10);
        int newPosition = currentPosition + movement;

        // Enforce limits
        if (newPosition < lowerLimit) {
            newPosition = lowerLimit;
        } else if (newPosition > upperLimit) {
            newPosition = upperLimit;
        }

        currentPosition = newPosition;
    }
}
