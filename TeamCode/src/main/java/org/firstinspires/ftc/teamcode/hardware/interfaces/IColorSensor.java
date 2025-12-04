package org.firstinspires.ftc.teamcode.hardware.interfaces;

/**
 * Interface for color sensor abstraction.
 */
public interface IColorSensor {

    /**
     * Get the red component value.
     * @return Red value (0-255 typically, but may vary by sensor)
     */
    int getRed();

    /**
     * Get the green component value.
     * @return Green value (0-255 typically)
     */
    int getGreen();

    /**
     * Get the blue component value.
     * @return Blue value (0-255 typically)
     */
    int getBlue();

    /**
     * Get the alpha (light intensity) value.
     * @return Alpha/intensity value
     */
    int getAlpha();

    /**
     * Get the hue value (0-360 degrees).
     * @return Hue in degrees
     */
    float getHue();

    /**
     * Get the saturation value (0-1).
     * @return Saturation (0.0 to 1.0)
     */
    float getSaturation();

    /**
     * Get the value/brightness (0-1).
     * @return Value/brightness (0.0 to 1.0)
     */
    float getValue();

    /**
     * Enable or disable the LED light on the sensor.
     * @param enabled true to turn on LED
     */
    void enableLed(boolean enabled);

    /**
     * Detected color enumeration.
     */
    enum DetectedColor {
        RED,
        BLUE,
        GREEN,
        YELLOW,
        WHITE,
        UNKNOWN
    }

    /**
     * Get the dominant detected color.
     * @return The detected color
     */
    DetectedColor getDetectedColor();
}
