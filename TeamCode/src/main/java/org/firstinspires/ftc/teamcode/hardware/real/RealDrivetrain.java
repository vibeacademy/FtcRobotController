package org.firstinspires.ftc.teamcode.hardware.real;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.hardware.interfaces.IDrivetrain;

/**
 * Real implementation of IDrivetrain using FTC SDK hardware.
 * Supports both tank (2-motor) and mecanum (4-motor) configurations.
 */
public class RealDrivetrain implements IDrivetrain {

    private DcMotor leftFront;
    private DcMotor rightFront;
    private DcMotor leftBack;
    private DcMotor rightBack;

    private boolean isMecanum = false;
    private double countsPerInch = 100.0;

    /**
     * Constructor for tank drive (2 motors).
     */
    public RealDrivetrain(HardwareMap hardwareMap, String leftMotorName, String rightMotorName) {
        leftFront = hardwareMap.get(DcMotor.class, leftMotorName);
        rightFront = hardwareMap.get(DcMotor.class, rightMotorName);

        // Default direction setup
        leftFront.setDirection(DcMotorSimple.Direction.REVERSE);
        rightFront.setDirection(DcMotorSimple.Direction.FORWARD);

        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        isMecanum = false;
    }

    /**
     * Constructor for mecanum drive (4 motors).
     */
    public RealDrivetrain(HardwareMap hardwareMap,
                          String leftFrontName, String rightFrontName,
                          String leftBackName, String rightBackName) {
        leftFront = hardwareMap.get(DcMotor.class, leftFrontName);
        rightFront = hardwareMap.get(DcMotor.class, rightFrontName);
        leftBack = hardwareMap.get(DcMotor.class, leftBackName);
        rightBack = hardwareMap.get(DcMotor.class, rightBackName);

        // Default direction setup for mecanum
        leftFront.setDirection(DcMotorSimple.Direction.REVERSE);
        leftBack.setDirection(DcMotorSimple.Direction.REVERSE);
        rightFront.setDirection(DcMotorSimple.Direction.FORWARD);
        rightBack.setDirection(DcMotorSimple.Direction.FORWARD);

        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        isMecanum = true;
    }

    @Override
    public void arcadeDrive(double drive, double turn) {
        double leftPower = Range.clip(drive + turn, -1.0, 1.0);
        double rightPower = Range.clip(drive - turn, -1.0, 1.0);

        leftFront.setPower(leftPower);
        rightFront.setPower(rightPower);

        if (isMecanum) {
            leftBack.setPower(leftPower);
            rightBack.setPower(rightPower);
        }
    }

    @Override
    public void tankDrive(double leftPower, double rightPower) {
        leftPower = Range.clip(leftPower, -1.0, 1.0);
        rightPower = Range.clip(rightPower, -1.0, 1.0);

        leftFront.setPower(leftPower);
        rightFront.setPower(rightPower);

        if (isMecanum) {
            leftBack.setPower(leftPower);
            rightBack.setPower(rightPower);
        }
    }

    @Override
    public void mecanumDrive(double drive, double strafe, double turn) {
        if (!isMecanum) {
            // Fall back to arcade drive for non-mecanum
            arcadeDrive(drive, turn);
            return;
        }

        double frontLeftPower = Range.clip(drive + strafe + turn, -1.0, 1.0);
        double frontRightPower = Range.clip(drive - strafe - turn, -1.0, 1.0);
        double backLeftPower = Range.clip(drive - strafe + turn, -1.0, 1.0);
        double backRightPower = Range.clip(drive + strafe - turn, -1.0, 1.0);

        leftFront.setPower(frontLeftPower);
        rightFront.setPower(frontRightPower);
        leftBack.setPower(backLeftPower);
        rightBack.setPower(backRightPower);
    }

    @Override
    public void stop() {
        leftFront.setPower(0);
        rightFront.setPower(0);

        if (isMecanum) {
            leftBack.setPower(0);
            rightBack.setPower(0);
        }
    }

    @Override
    public void resetEncoders() {
        leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        if (isMecanum) {
            leftBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            rightBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        }

        leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        if (isMecanum) {
            leftBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            rightBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    }

    @Override
    public int getLeftEncoderPosition() {
        if (isMecanum) {
            return (leftFront.getCurrentPosition() + leftBack.getCurrentPosition()) / 2;
        }
        return leftFront.getCurrentPosition();
    }

    @Override
    public int getRightEncoderPosition() {
        if (isMecanum) {
            return (rightFront.getCurrentPosition() + rightBack.getCurrentPosition()) / 2;
        }
        return rightFront.getCurrentPosition();
    }

    @Override
    public boolean isBusy() {
        if (isMecanum) {
            return leftFront.isBusy() || rightFront.isBusy() ||
                   leftBack.isBusy() || rightBack.isBusy();
        }
        return leftFront.isBusy() || rightFront.isBusy();
    }

    @Override
    public void driveByEncoder(double leftInches, double rightInches, double speed) {
        int leftTarget = leftFront.getCurrentPosition() + (int)(leftInches * countsPerInch);
        int rightTarget = rightFront.getCurrentPosition() + (int)(rightInches * countsPerInch);

        leftFront.setTargetPosition(leftTarget);
        rightFront.setTargetPosition(rightTarget);

        if (isMecanum) {
            leftBack.setTargetPosition(leftTarget);
            rightBack.setTargetPosition(rightTarget);
        }

        leftFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        if (isMecanum) {
            leftBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }

        leftFront.setPower(Math.abs(speed));
        rightFront.setPower(Math.abs(speed));

        if (isMecanum) {
            leftBack.setPower(Math.abs(speed));
            rightBack.setPower(Math.abs(speed));
        }
    }

    @Override
    public void setCountsPerInch(double countsPerInch) {
        this.countsPerInch = countsPerInch;
    }

    /**
     * Set motor directions for proper drive behavior.
     */
    public void setMotorDirections(boolean reverseLeft, boolean reverseRight) {
        leftFront.setDirection(reverseLeft ? DcMotorSimple.Direction.REVERSE : DcMotorSimple.Direction.FORWARD);
        rightFront.setDirection(reverseRight ? DcMotorSimple.Direction.REVERSE : DcMotorSimple.Direction.FORWARD);

        if (isMecanum) {
            leftBack.setDirection(reverseLeft ? DcMotorSimple.Direction.REVERSE : DcMotorSimple.Direction.FORWARD);
            rightBack.setDirection(reverseRight ? DcMotorSimple.Direction.REVERSE : DcMotorSimple.Direction.FORWARD);
        }
    }
}
