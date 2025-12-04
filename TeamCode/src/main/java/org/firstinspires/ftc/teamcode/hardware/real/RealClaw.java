package org.firstinspires.ftc.teamcode.hardware.real;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.hardware.interfaces.IClaw;

/**
 * Real implementation of IClaw using FTC SDK servo.
 */
public class RealClaw implements IClaw {

    private Servo clawServo;
    private double openPosition = 1.0;
    private double closedPosition = 0.0;
    private double tolerance = 0.05;

    public RealClaw(HardwareMap hardwareMap, String servoName) {
        clawServo = hardwareMap.get(Servo.class, servoName);
    }

    public RealClaw(HardwareMap hardwareMap, String servoName,
                    double openPosition, double closedPosition) {
        this(hardwareMap, servoName);
        this.openPosition = openPosition;
        this.closedPosition = closedPosition;
    }

    @Override
    public void open() {
        clawServo.setPosition(openPosition);
    }

    @Override
    public void close() {
        clawServo.setPosition(closedPosition);
    }

    @Override
    public void setPosition(double position) {
        clawServo.setPosition(position);
    }

    @Override
    public double getPosition() {
        return clawServo.getPosition();
    }

    @Override
    public boolean isOpen() {
        return Math.abs(clawServo.getPosition() - openPosition) < tolerance;
    }

    @Override
    public boolean isClosed() {
        return Math.abs(clawServo.getPosition() - closedPosition) < tolerance;
    }

    /**
     * Set the open and closed positions.
     */
    public void setPositions(double openPosition, double closedPosition) {
        this.openPosition = openPosition;
        this.closedPosition = closedPosition;
    }

    /**
     * Set servo direction.
     */
    public void setDirection(boolean reversed) {
        clawServo.setDirection(reversed ? Servo.Direction.REVERSE : Servo.Direction.FORWARD);
    }
}
