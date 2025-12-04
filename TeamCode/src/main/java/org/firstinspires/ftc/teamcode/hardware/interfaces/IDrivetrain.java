package org.firstinspires.ftc.teamcode.hardware.interfaces;

/**
 * Interface for drivetrain abstraction.
 * Supports both tank (2-wheel) and mecanum (4-wheel) drive configurations.
 */
public interface IDrivetrain {

    /**
     * Drive using arcade-style controls (forward/backward + rotation).
     * @param drive Forward/backward power (-1.0 to 1.0, positive = forward)
     * @param turn Rotation power (-1.0 to 1.0, positive = clockwise)
     */
    void arcadeDrive(double drive, double turn);

    /**
     * Drive using tank-style controls (left and right side independently).
     * @param leftPower Left side power (-1.0 to 1.0)
     * @param rightPower Right side power (-1.0 to 1.0)
     */
    void tankDrive(double leftPower, double rightPower);

    /**
     * Drive using mecanum/holonomic controls (strafe capability).
     * @param drive Forward/backward power (-1.0 to 1.0)
     * @param strafe Left/right strafe power (-1.0 to 1.0, positive = right)
     * @param turn Rotation power (-1.0 to 1.0, positive = clockwise)
     */
    void mecanumDrive(double drive, double strafe, double turn);

    /**
     * Stop all drive motors immediately.
     */
    void stop();

    /**
     * Reset all encoder positions to zero.
     */
    void resetEncoders();

    /**
     * Get the current position of the left encoder(s) in ticks.
     * For mecanum, returns average of left front and left back.
     * @return Left encoder position in ticks
     */
    int getLeftEncoderPosition();

    /**
     * Get the current position of the right encoder(s) in ticks.
     * For mecanum, returns average of right front and right back.
     * @return Right encoder position in ticks
     */
    int getRightEncoderPosition();

    /**
     * Check if any drive motors are currently running to a target position.
     * @return true if motors are busy
     */
    boolean isBusy();

    /**
     * Drive a specified distance using encoders.
     * @param leftInches Distance for left side in inches
     * @param rightInches Distance for right side in inches
     * @param speed Motor power (0.0 to 1.0)
     */
    void driveByEncoder(double leftInches, double rightInches, double speed);

    /**
     * Set the counts per inch for encoder calculations.
     * @param countsPerInch Encoder ticks per inch of travel
     */
    void setCountsPerInch(double countsPerInch);
}
