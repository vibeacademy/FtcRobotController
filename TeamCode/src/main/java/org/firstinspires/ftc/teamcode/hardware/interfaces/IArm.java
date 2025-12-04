package org.firstinspires.ftc.teamcode.hardware.interfaces;

/**
 * Interface for arm/lift mechanism abstraction.
 * Supports both continuous rotation (motor-based) and positional (servo-based) arms.
 */
public interface IArm {

    /**
     * Set the arm motor power for manual control.
     * @param power Motor power (-1.0 to 1.0, positive = up)
     */
    void setPower(double power);

    /**
     * Move the arm to a specific encoder position.
     * @param targetPosition Target encoder position in ticks
     * @param power Motor power (0.0 to 1.0)
     */
    void moveToPosition(int targetPosition, double power);

    /**
     * Get the current arm encoder position.
     * @return Current position in encoder ticks
     */
    int getCurrentPosition();

    /**
     * Check if the arm is currently moving to a target position.
     * @return true if arm motor is busy
     */
    boolean isBusy();

    /**
     * Stop the arm motor.
     */
    void stop();

    /**
     * Reset the arm encoder to zero.
     */
    void resetEncoder();

    /**
     * Hold the current position using motor power.
     * Useful for arms that need to resist gravity.
     */
    void holdPosition();

    /**
     * Check if the arm has reached its lower limit.
     * @return true if at lower limit
     */
    boolean isAtLowerLimit();

    /**
     * Check if the arm has reached its upper limit.
     * @return true if at upper limit
     */
    boolean isAtUpperLimit();
}
