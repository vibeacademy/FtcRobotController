package org.firstinspires.ftc.teamcode.hardware.real;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.firstinspires.ftc.teamcode.hardware.interfaces.ITouchSensor;

/**
 * Real implementation of ITouchSensor using FTC SDK TouchSensor.
 */
public class RealTouchSensor implements ITouchSensor {

    private TouchSensor touchSensor;

    public RealTouchSensor(HardwareMap hardwareMap, String sensorName) {
        touchSensor = hardwareMap.get(TouchSensor.class, sensorName);
    }

    @Override
    public boolean isPressed() {
        return touchSensor.isPressed();
    }

    @Override
    public double getValue() {
        return touchSensor.getValue();
    }
}
