package org.firstinspires.ftc.teamcode.hardware.mock;

import org.firstinspires.ftc.teamcode.hardware.interfaces.IArm;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class ArmTest {

    private static final int ARM_GROUND = 0;
    private static final int ARM_MID = 200;
    private static final int ARM_HIGH = 400;

    @Test
    public void createsMockArm() {
        IArm arm = new MockArm();

        assertNotNull("Arm should not be null", arm);
    }

    @Test
    public void moveToGroundPresetSetsCurrentPosition() {
        MockArm arm = new MockArm();
        arm.setCurrentPosition(200);

        arm.moveToPosition(ARM_GROUND, 0.5);

        assertEquals("Arm should be at ground", ARM_GROUND, arm.getCurrentPosition());
    }

    @Test
    public void moveToMidPresetSetsCurrentPosition() {
        MockArm arm = new MockArm();

        arm.moveToPosition(ARM_MID, 0.8);

        assertEquals("Arm should be at mid", ARM_MID, arm.getCurrentPosition());
    }

    @Test
    public void moveToHighPresetSetsCurrentPosition() {
        MockArm arm = new MockArm();

        arm.moveToPosition(ARM_HIGH, 0.8);

        assertEquals("Arm should be at high", ARM_HIGH, arm.getCurrentPosition());
    }

    @Test
    public void reportsLowerAndUpperLimits() {
        MockArm arm = new MockArm();

        arm.setCurrentPosition(0);
        assertTrue("Should be at lower limit", arm.isAtLowerLimit());
        assertFalse("Should not be at upper limit", arm.isAtUpperLimit());

        arm.setCurrentPosition(1000);
        assertFalse("Should not be at lower limit", arm.isAtLowerLimit());
        assertTrue("Should be at upper limit", arm.isAtUpperLimit());
    }
}
