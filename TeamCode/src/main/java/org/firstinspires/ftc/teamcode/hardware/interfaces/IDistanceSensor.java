package org.firstinspires.ftc.teamcode.hardware.interfaces;

/**
 * Interface for distance sensor abstraction.
 * Supports various distance sensors (REV 2m, ultrasonic, etc.)
 */
public interface IDistanceSensor {

    /**
     * Get the distance reading in inches.
     * @return Distance in inches, or Double.MAX_VALUE if out of range
     */
    double getDistanceInches();

    /**
     * Get the distance reading in centimeters.
     * @return Distance in centimeters, or Double.MAX_VALUE if out of range
     */
    double getDistanceCm();

    /**
     * Check if an object is detected within a specified range.
     * @param thresholdInches Maximum distance in inches to consider detected
     * @return true if object is within threshold distance
     */
    boolean isObjectDetected(double thresholdInches);

    /**
     * Check if the sensor reading is valid (not out of range).
     * @return true if reading is valid
     */
    boolean isValidReading();
}
