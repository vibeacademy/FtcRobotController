package org.firstinspires.ftc.teamcode.hardware.real;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AngularVelocity;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;
import org.firstinspires.ftc.teamcode.hardware.interfaces.IIMU;

/**
 * Real implementation of IIMU using FTC SDK IMU (REV Hub built-in IMU).
 */
public class RealIMU implements IIMU {

    private IMU imu;
    private double headingOffset = 0;

    public RealIMU(HardwareMap hardwareMap, String imuName) {
        imu = hardwareMap.get(IMU.class, imuName);
    }

    public RealIMU(HardwareMap hardwareMap) {
        this(hardwareMap, "imu");
    }

    @Override
    public void initialize() {
        // Default orientation assumes REV Hub is mounted flat with USB ports facing forward
        RevHubOrientationOnRobot.LogoFacingDirection logoDirection =
            RevHubOrientationOnRobot.LogoFacingDirection.UP;
        RevHubOrientationOnRobot.UsbFacingDirection usbDirection =
            RevHubOrientationOnRobot.UsbFacingDirection.FORWARD;

        RevHubOrientationOnRobot orientationOnRobot =
            new RevHubOrientationOnRobot(logoDirection, usbDirection);

        imu.initialize(new IMU.Parameters(orientationOnRobot));
    }

    /**
     * Initialize with custom orientation.
     */
    public void initialize(RevHubOrientationOnRobot.LogoFacingDirection logoDirection,
                          RevHubOrientationOnRobot.UsbFacingDirection usbDirection) {
        RevHubOrientationOnRobot orientationOnRobot =
            new RevHubOrientationOnRobot(logoDirection, usbDirection);

        imu.initialize(new IMU.Parameters(orientationOnRobot));
    }

    @Override
    public void resetHeading() {
        imu.resetYaw();
        headingOffset = 0;
    }

    @Override
    public double getHeading() {
        YawPitchRollAngles orientation = imu.getRobotYawPitchRollAngles();
        return orientation.getYaw(AngleUnit.DEGREES);
    }

    @Override
    public double getPitch() {
        YawPitchRollAngles orientation = imu.getRobotYawPitchRollAngles();
        return orientation.getPitch(AngleUnit.DEGREES);
    }

    @Override
    public double getRoll() {
        YawPitchRollAngles orientation = imu.getRobotYawPitchRollAngles();
        return orientation.getRoll(AngleUnit.DEGREES);
    }

    @Override
    public double getAngularVelocity() {
        AngularVelocity angularVelocity = imu.getRobotAngularVelocity(AngleUnit.DEGREES);
        return angularVelocity.zRotationRate;
    }

    @Override
    public boolean isCalibrated() {
        // REV Hub IMU is always calibrated after initialization
        return true;
    }
}
