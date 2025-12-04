package org.firstinspires.ftc.teamcode.hardware.real;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.hardware.interfaces.IArm;

/**
 * Real implementation of IArm using FTC SDK hardware.
 */
public class RealArm implements IArm {

    private DcMotor armMotor;
    private TouchSensor lowerLimitSwitch;
    private TouchSensor upperLimitSwitch;

    private int lowerLimit = 0;
    private int upperLimit = Integer.MAX_VALUE;
    private double holdPower = 0.1;

    /**
     * Constructor with just motor.
     */
    public RealArm(HardwareMap hardwareMap, String motorName) {
        armMotor = hardwareMap.get(DcMotor.class, motorName);
        armMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        armMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    /**
     * Constructor with motor and limit switches.
     */
    public RealArm(HardwareMap hardwareMap, String motorName,
                   String lowerLimitName, String upperLimitName) {
        this(hardwareMap, motorName);

        if (lowerLimitName != null) {
            lowerLimitSwitch = hardwareMap.get(TouchSensor.class, lowerLimitName);
        }
        if (upperLimitName != null) {
            upperLimitSwitch = hardwareMap.get(TouchSensor.class, upperLimitName);
        }
    }

    @Override
    public void setPower(double power) {
        power = Range.clip(power, -1.0, 1.0);

        // Check limits
        if (power > 0 && isAtUpperLimit()) {
            power = 0;
        } else if (power < 0 && isAtLowerLimit()) {
            power = 0;
        }

        armMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        armMotor.setPower(power);
    }

    @Override
    public void moveToPosition(int targetPosition, double power) {
        // Clamp target to limits
        targetPosition = Math.max(lowerLimit, Math.min(upperLimit, targetPosition));

        armMotor.setTargetPosition(targetPosition);
        armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        armMotor.setPower(Math.abs(power));
    }

    @Override
    public int getCurrentPosition() {
        return armMotor.getCurrentPosition();
    }

    @Override
    public boolean isBusy() {
        return armMotor.isBusy();
    }

    @Override
    public void stop() {
        armMotor.setPower(0);
    }

    @Override
    public void resetEncoder() {
        armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    @Override
    public void holdPosition() {
        moveToPosition(getCurrentPosition(), holdPower);
    }

    @Override
    public boolean isAtLowerLimit() {
        if (lowerLimitSwitch != null) {
            return lowerLimitSwitch.isPressed();
        }
        return getCurrentPosition() <= lowerLimit;
    }

    @Override
    public boolean isAtUpperLimit() {
        if (upperLimitSwitch != null) {
            return upperLimitSwitch.isPressed();
        }
        return getCurrentPosition() >= upperLimit;
    }

    /**
     * Set the software limits for the arm.
     */
    public void setLimits(int lower, int upper) {
        this.lowerLimit = lower;
        this.upperLimit = upper;
    }

    /**
     * Set motor direction.
     */
    public void setDirection(boolean reversed) {
        armMotor.setDirection(reversed ? DcMotorSimple.Direction.REVERSE : DcMotorSimple.Direction.FORWARD);
    }

    /**
     * Set the power used for holding position.
     */
    public void setHoldPower(double holdPower) {
        this.holdPower = holdPower;
    }
}
