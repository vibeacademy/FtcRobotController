package org.firstinspires.ftc.teamcode.hardware.interfaces;

/**
 * Interface for touch/limit sensor abstraction.
 */
public interface ITouchSensor {

    /**
     * Check if the sensor is currently pressed.
     * @return true if pressed
     */
    boolean isPressed();

    /**
     * Get the raw value from the sensor.
     * @return Raw sensor value (0.0 to 1.0 typically)
     */
    double getValue();
}
