package org.firstinspires.ftc.teamcode.hardware.interfaces;

/**
 * Interface for IMU (Inertial Measurement Unit) abstraction.
 * Provides heading, pitch, and roll information.
 */
public interface IIMU {

    /**
     * Initialize the IMU. Call this before using other methods.
     */
    void initialize();

    /**
     * Reset the heading to zero.
     */
    void resetHeading();

    /**
     * Get the current heading (yaw) in degrees.
     * @return Heading in degrees (-180 to 180, positive = counterclockwise)
     */
    double getHeading();

    /**
     * Get the current pitch in degrees.
     * @return Pitch in degrees (-90 to 90)
     */
    double getPitch();

    /**
     * Get the current roll in degrees.
     * @return Roll in degrees (-180 to 180)
     */
    double getRoll();

    /**
     * Get the angular velocity around the Z axis (yaw rate).
     * @return Angular velocity in degrees per second
     */
    double getAngularVelocity();

    /**
     * Check if the IMU is calibrated and ready.
     * @return true if IMU is calibrated
     */
    boolean isCalibrated();
}
