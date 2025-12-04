package org.firstinspires.ftc.teamcode.hardware.real;

import android.graphics.Color;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.SwitchableLight;

import org.firstinspires.ftc.teamcode.hardware.interfaces.IColorSensor;

/**
 * Real implementation of IColorSensor using FTC SDK ColorSensor.
 */
public class RealColorSensor implements IColorSensor {

    private ColorSensor colorSensor;
    private NormalizedColorSensor normalizedSensor;
    private float[] hsvValues = new float[3];

    public RealColorSensor(HardwareMap hardwareMap, String sensorName) {
        colorSensor = hardwareMap.get(ColorSensor.class, sensorName);

        // Try to get normalized version for better readings
        try {
            normalizedSensor = hardwareMap.get(NormalizedColorSensor.class, sensorName);
        } catch (Exception e) {
            normalizedSensor = null;
        }
    }

    @Override
    public int getRed() {
        return colorSensor.red();
    }

    @Override
    public int getGreen() {
        return colorSensor.green();
    }

    @Override
    public int getBlue() {
        return colorSensor.blue();
    }

    @Override
    public int getAlpha() {
        return colorSensor.alpha();
    }

    @Override
    public float getHue() {
        updateHsv();
        return hsvValues[0];
    }

    @Override
    public float getSaturation() {
        updateHsv();
        return hsvValues[1];
    }

    @Override
    public float getValue() {
        updateHsv();
        return hsvValues[2];
    }

    @Override
    public void enableLed(boolean enabled) {
        if (colorSensor instanceof SwitchableLight) {
            ((SwitchableLight) colorSensor).enableLight(enabled);
        }
    }

    @Override
    public DetectedColor getDetectedColor() {
        updateHsv();
        float hue = hsvValues[0];
        float saturation = hsvValues[1];
        float value = hsvValues[2];

        // Low saturation or value = likely white or unknown
        if (saturation < 0.2 && value > 0.8) {
            return DetectedColor.WHITE;
        }

        if (value < 0.1) {
            return DetectedColor.UNKNOWN;
        }

        if (saturation < 0.3) {
            return DetectedColor.UNKNOWN;
        }

        // Determine color by hue
        if (hue < 30 || hue > 330) {
            return DetectedColor.RED;
        } else if (hue >= 30 && hue < 90) {
            return DetectedColor.YELLOW;
        } else if (hue >= 90 && hue < 150) {
            return DetectedColor.GREEN;
        } else if (hue >= 200 && hue < 270) {
            return DetectedColor.BLUE;
        }

        return DetectedColor.UNKNOWN;
    }

    private void updateHsv() {
        if (normalizedSensor != null) {
            NormalizedRGBA colors = normalizedSensor.getNormalizedColors();
            Color.colorToHSV(colors.toColor(), hsvValues);
        } else {
            Color.RGBToHSV(
                colorSensor.red(),
                colorSensor.green(),
                colorSensor.blue(),
                hsvValues
            );
            // Normalize value since raw RGB isn't 0-255
            hsvValues[2] = hsvValues[2] / 255f;
        }
    }
}
