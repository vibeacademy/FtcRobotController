package org.firstinspires.ftc.teamcode.hardware.mock;

import org.firstinspires.ftc.teamcode.hardware.interfaces.IClaw;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class ClawTest {

    private static final double EPSILON = 0.01;

    @Test
    public void createsMockClaw() {
        IClaw claw = new MockClaw();

        assertNotNull("Claw should not be null", claw);
    }

    @Test
    public void openSetsClawOpen() {
        MockClaw claw = new MockClaw();

        claw.close();
        assertFalse("Claw should start closed", claw.isOpen());

        claw.open();

        assertTrue("Claw should be open", claw.isOpen());
    }

    @Test
    public void closeSetsClawClosed() {
        MockClaw claw = new MockClaw();

        claw.open();
        assertTrue("Claw should start open", claw.isOpen());

        claw.close();

        assertTrue("Claw should be closed", claw.isClosed());
    }

    @Test
    public void openAndCloseUpdatePositions() {
        MockClaw claw = new MockClaw();

        assertTrue("Claw should start closed", claw.isClosed());

        claw.open();
        assertTrue("Claw should be open", claw.isOpen());

        claw.close();
        assertTrue("Claw should be closed", claw.isClosed());

        claw.open();
        assertEquals("Open position", 1.0, claw.getPosition(), EPSILON);

        claw.close();
        assertEquals("Closed position", 0.0, claw.getPosition(), EPSILON);
    }
}
