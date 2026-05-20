package ch.fhnw.roundtable.etopia.shapes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AnglesTest {

    @Test
    void testAngleDirections() {
        Vector origin = new Vector(0f, 0f);

        // East / Right side -> 0 degrees
        assertEquals(0f, Angles.angle(origin, new Vector(10f, 0f)), 0.001f);

        // North / Up side -> 90 degrees
        assertEquals(90f, Angles.angle(origin, new Vector(0f, 10f)), 0.001f);

        // West / Left side -> 180 degrees
        assertEquals(180f, Angles.angle(origin, new Vector(-10f, 0f)), 0.001f);

        // South / Down side -> -90 degrees
        assertEquals(-90f, Angles.angle(origin, new Vector(0f, -10f)), 0.001f);
    }

    @Test
    void testAngleDifferenceSimpleCases() {
        assertEquals(30f, Angles.difference(0f, 30f), 0.001f);
        assertEquals(30f, Angles.difference(30f, 0f), 0.001f);
        assertEquals(0f, Angles.difference(45f, 45f), 0.001f);
    }

    @Test
    void testAngleDifferenceCrossingWrapBoundary() {
        // The shortest route across 0/360 degrees threshold
        // e.g., from 350 to 10 degrees is a 20-degree gap step
        assertEquals(20f, Angles.difference(350f, 10f), 0.001f);
        assertEquals(20f, Angles.difference(10f, 350f), 0.001f);

        // Exact opposite sides of the circle spectrum
        assertEquals(180f, Angles.difference(0f, 180f), 0.001f);
    }

    @Test
    void testAngleDifferenceHandlesLargeModuloInputs() {
        // 730%360 is 10. 370%360 is 10. Difference should be 0.
        assertEquals(0f, Angles.difference(730f, 370f), 0.001f);
    }
}
