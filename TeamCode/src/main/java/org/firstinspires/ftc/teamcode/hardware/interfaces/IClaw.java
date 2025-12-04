package org.firstinspires.ftc.teamcode.hardware.interfaces;

/**
 * Interface for claw/gripper mechanism abstraction.
 * Typically servo-based with open/close positions.
 */
public interface IClaw {

    /**
     * Open the claw to release game elements.
     */
    void open();

    /**
     * Close the claw to grip game elements.
     */
    void close();

    /**
     * Set the claw to a specific position.
     * @param position Servo position (0.0 to 1.0)
     */
    void setPosition(double position);

    /**
     * Get the current claw position.
     * @return Current servo position (0.0 to 1.0)
     */
    double getPosition();

    /**
     * Check if the claw is currently open.
     * @return true if claw is in open position
     */
    boolean isOpen();

    /**
     * Check if the claw is currently closed.
     * @return true if claw is in closed position
     */
    boolean isClosed();
}
