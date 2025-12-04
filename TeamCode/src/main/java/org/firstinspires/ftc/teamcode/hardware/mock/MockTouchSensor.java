package org.firstinspires.ftc.teamcode.hardware.mock;

import org.firstinspires.ftc.teamcode.hardware.interfaces.ITouchSensor;

/**
 * Mock implementation of ITouchSensor for offline testing.
 */
public class MockTouchSensor implements ITouchSensor {

    private boolean pressed = false;
    private double value = 0.0;

    @Override
    public boolean isPressed() {
        return pressed;
    }

    @Override
    public double getValue() {
        return value;
    }

    // Helper methods for testing

    public void setPressed(boolean pressed) {
        this.pressed = pressed;
        this.value = pressed ? 1.0 : 0.0;
    }

    public void setValue(double value) {
        this.value = value;
        this.pressed = value > 0.5;
    }
}
