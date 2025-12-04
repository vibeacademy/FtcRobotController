package org.firstinspires.ftc.teamcode.hardware.mock;

import org.firstinspires.ftc.teamcode.hardware.interfaces.IClaw;

/**
 * Mock implementation of IClaw for offline testing.
 * Simulates servo-based claw behavior.
 */
public class MockClaw implements IClaw {

    private double position = 0.0;
    private double openPosition = 1.0;
    private double closedPosition = 0.0;
    private double tolerance = 0.05;

    @Override
    public void open() {
        position = openPosition;
    }

    @Override
    public void close() {
        position = closedPosition;
    }

    @Override
    public void setPosition(double position) {
        this.position = Math.max(0.0, Math.min(1.0, position));
    }

    @Override
    public double getPosition() {
        return position;
    }

    @Override
    public boolean isOpen() {
        return Math.abs(position - openPosition) < tolerance;
    }

    @Override
    public boolean isClosed() {
        return Math.abs(position - closedPosition) < tolerance;
    }

    // Helper methods for testing

    public void setOpenPosition(double openPosition) {
        this.openPosition = openPosition;
    }

    public void setClosedPosition(double closedPosition) {
        this.closedPosition = closedPosition;
    }

    public void setTolerance(double tolerance) {
        this.tolerance = tolerance;
    }
}
