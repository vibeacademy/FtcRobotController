package org.firstinspires.ftc.teamcode.hardware.mock;

import org.firstinspires.ftc.teamcode.hardware.interfaces.IColorSensor;

/**
 * Mock implementation of IColorSensor for offline testing.
 * Allows manual setting of color values.
 */
public class MockColorSensor implements IColorSensor {

    private int red = 0;
    private int green = 0;
    private int blue = 0;
    private int alpha = 0;
    private boolean ledEnabled = false;

    @Override
    public int getRed() {
        return red;
    }

    @Override
    public int getGreen() {
        return green;
    }

    @Override
    public int getBlue() {
        return blue;
    }

    @Override
    public int getAlpha() {
        return alpha;
    }

    @Override
    public float getHue() {
        float[] hsv = rgbToHsv(red, green, blue);
        return hsv[0];
    }

    @Override
    public float getSaturation() {
        float[] hsv = rgbToHsv(red, green, blue);
        return hsv[1];
    }

    @Override
    public float getValue() {
        float[] hsv = rgbToHsv(red, green, blue);
        return hsv[2];
    }

    @Override
    public void enableLed(boolean enabled) {
        this.ledEnabled = enabled;
    }

    @Override
    public DetectedColor getDetectedColor() {
        // Simple color detection based on dominant channel
        int max = Math.max(red, Math.max(green, blue));

        if (max < 20) {
            return DetectedColor.UNKNOWN;
        }

        if (red == max && red > green * 1.5 && red > blue * 1.5) {
            return DetectedColor.RED;
        } else if (blue == max && blue > green * 1.5 && blue > red * 1.5) {
            return DetectedColor.BLUE;
        } else if (green == max && green > red * 1.2 && green > blue * 1.2) {
            return DetectedColor.GREEN;
        } else if (red > 100 && green > 100 && blue < 50) {
            return DetectedColor.YELLOW;
        } else if (red > 200 && green > 200 && blue > 200) {
            return DetectedColor.WHITE;
        }

        return DetectedColor.UNKNOWN;
    }

    // Helper methods for testing

    public void setRGB(int red, int green, int blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public void setRGBA(int red, int green, int blue, int alpha) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.alpha = alpha;
    }

    public void setAlpha(int alpha) {
        this.alpha = alpha;
    }

    public boolean isLedEnabled() {
        return ledEnabled;
    }

    /**
     * Set color by name for easy testing.
     */
    public void setColor(DetectedColor color) {
        switch (color) {
            case RED:
                setRGB(255, 30, 30);
                break;
            case BLUE:
                setRGB(30, 30, 255);
                break;
            case GREEN:
                setRGB(30, 255, 30);
                break;
            case YELLOW:
                setRGB(255, 255, 30);
                break;
            case WHITE:
                setRGB(255, 255, 255);
                break;
            default:
                setRGB(0, 0, 0);
                break;
        }
    }

    private float[] rgbToHsv(int r, int g, int b) {
        float rf = r / 255f;
        float gf = g / 255f;
        float bf = b / 255f;

        float max = Math.max(rf, Math.max(gf, bf));
        float min = Math.min(rf, Math.min(gf, bf));
        float delta = max - min;

        float h = 0;
        float s = max == 0 ? 0 : delta / max;
        float v = max;

        if (delta != 0) {
            if (max == rf) {
                h = 60 * (((gf - bf) / delta) % 6);
            } else if (max == gf) {
                h = 60 * (((bf - rf) / delta) + 2);
            } else {
                h = 60 * (((rf - gf) / delta) + 4);
            }
        }

        if (h < 0) h += 360;

        return new float[]{h, s, v};
    }
}
