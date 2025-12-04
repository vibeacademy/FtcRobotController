package org.firstinspires.ftc.teamcode.hardware.real;

import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.hardware.interfaces.IDistanceSensor;

/**
 * Real implementation of IDistanceSensor using FTC SDK DistanceSensor.
 * Works with REV 2m Distance Sensor and similar devices.
 */
public class RealDistanceSensor implements IDistanceSensor {

    private DistanceSensor distanceSensor;
    private double maxRange = 120.0; // inches

    public RealDistanceSensor(HardwareMap hardwareMap, String sensorName) {
        distanceSensor = hardwareMap.get(DistanceSensor.class, sensorName);
    }

    @Override
    public double getDistanceInches() {
        double distance = distanceSensor.getDistance(DistanceUnit.INCH);
        if (Double.isNaN(distance) || distance > maxRange) {
            return Double.MAX_VALUE;
        }
        return distance;
    }

    @Override
    public double getDistanceCm() {
        double distance = distanceSensor.getDistance(DistanceUnit.CM);
        if (Double.isNaN(distance) || distance > maxRange * 2.54) {
            return Double.MAX_VALUE;
        }
        return distance;
    }

    @Override
    public boolean isObjectDetected(double thresholdInches) {
        double distance = getDistanceInches();
        return distance != Double.MAX_VALUE && distance <= thresholdInches;
    }

    @Override
    public boolean isValidReading() {
        double distance = getDistanceInches();
        return distance != Double.MAX_VALUE;
    }

    /**
     * Set the maximum expected range for the sensor.
     */
    public void setMaxRange(double maxRangeInches) {
        this.maxRange = maxRangeInches;
    }
}
