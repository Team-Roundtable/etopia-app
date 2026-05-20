package ch.fhnw.roundtable.etopia.shapes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VectorTest {

    @Test
    void testVectorProperties() {
        Vector vector = new Vector(3.5f, -2.1f);
        assertEquals(3.5f, vector.x());
        assertEquals(-2.1f, vector.y());
    }

    @Test
    void testMidpointCalculation() {
        Vector a = new Vector(0f, 0f);
        Vector b = new Vector(10f, 4f);

        Vector middle = Vector.midpoint(a, b);

        assertEquals(5.0f, middle.x(), 0.001f);
        assertEquals(2.0f, middle.y(), 0.001f);
    }

    @Test
    void testMidpointWithNegativeCoordinates() {
        Vector a = new Vector(-5f, -10f);
        Vector b = new Vector(5f, 20f);

        Vector middle = Vector.midpoint(a, b);

        assertEquals(0.0f, middle.x(), 0.001f);
        assertEquals(5.0f, middle.y(), 0.001f);
    }
}
