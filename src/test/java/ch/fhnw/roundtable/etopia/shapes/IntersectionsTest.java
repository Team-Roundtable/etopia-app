package ch.fhnw.roundtable.etopia.shapes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IntersectionsTest {

    // --- Rectangle vs Rectangle ---

    @Test
    void testRectangleIntersects() {
        Rectangle r1 = new Rectangle(0f, 0f, 10f, 10f);
        Rectangle r2 = new Rectangle(5f, 5f, 10f, 10f); // Overlapping bottom-right
        Rectangle r3 = new Rectangle(20f, 20f, 5f, 5f);  // Completely separate

        assertTrue(Intersections.intersects(r1, r2));
        assertFalse(Intersections.intersects(r1, r3));
    }

    @Test
    void testRectangleEdgeTouchDoesNotIntersect() {
        Rectangle r1 = new Rectangle(0f, 0f, 10f, 10f);
        Rectangle r2 = new Rectangle(10f, 0f, 10f, 10f); // Shares an edge exactly

        assertFalse(Intersections.intersects(r1, r2), "Exact edge touches should be exclusive (< vs <=)");
    }

    @Test
    void testRectangleIntersectionShortCircuitConditions() {
        // Condition 1 fails: a.x() < b.x() + b.width() is FALSE
        // Meaning 'a' is entirely to the right of 'b'
        Rectangle rRight = new Rectangle(20f, 0f, 10f, 10f);
        Rectangle rLeft = new Rectangle(0f, 0f, 10f, 10f);
        assertFalse(Intersections.intersects(rRight, rLeft));

        // Condition 2 fails: a.x() + a.width() > b.x() is FALSE
        // Meaning 'a' is entirely to the left of 'b'
        assertFalse(Intersections.intersects(rLeft, rRight));

        // Condition 3 fails: a.y() < b.y() + b.height() is FALSE
        // Meaning 'a' is entirely above 'b'
        Rectangle rAbove = new Rectangle(0f, 20f, 10f, 10f);
        Rectangle rBelow = new Rectangle(0f, 0f, 10f, 10f);
        assertFalse(Intersections.intersects(rAbove, rBelow));

        // Condition 4 fails: a.y() + a.height() > b.y() is FALSE
        // Meaning 'a' is entirely below 'b'
        assertFalse(Intersections.intersects(rBelow, rAbove));
    }

    // --- Circle vs Circle ---

    @Test
    void testCircleIntersects() {
        Circle c1 = new Circle(0f, 0f, 5f);
        Circle c2 = new Circle(3f, 4f, 2f);  // Distance is 5, combined radius is 7 (Overlap)
        Circle c3 = new Circle(10f, 10f, 1f); // Completely separate

        assertTrue(Intersections.intersects(c1, c2));
        assertFalse(Intersections.intersects(c1, c3));
    }

    @Test
    void testCircleEdgeTouchIntersects() {
        Circle c1 = new Circle(0f, 0f, 3f);
        Circle c2 = new Circle(5f, 0f, 2f); // Distance is 5, combined radius is 5 (Touches)

        assertTrue(Intersections.intersects(c1, c2),
                "Circle formula uses <=, so perfect edge touches are intersections");
    }

    // --- Rectangle vs Circle ---

    @Test
    void testRectangleCircleIntersects() {
        Rectangle rect = new Rectangle(0f, 0f, 10f, 10f);

        Circle inside = new Circle(5f, 5f, 2f);
        Circle overlappingEdge = new Circle(11f, 5f, 2f);
        Circle separate = new Circle(15f, 15f, 1f);

        assertTrue(Intersections.intersects(rect, inside));
        assertTrue(Intersections.intersects(rect, overlappingEdge));
        assertFalse(Intersections.intersects(rect, separate));
    }

    @Test
    void testRectangleCircleCornerIntersection() {
        Rectangle rect = new Rectangle(0f, 0f, 4f, 4f);
        Circle nearCorner = new Circle(5f, 5f, 1.5f); // Closest point is corner (4,4). Distance is sqrt(2) ~1.414

        assertTrue(Intersections.intersects(rect, nearCorner));
    }

    @Test
    void testCircleRectangleSymmetry() {
        Rectangle rect = new Rectangle(0f, 0f, 10f, 10f);
        Circle circle = new Circle(11f, 5f, 2f);

        // Ensures both methods route correctly and yield the same result
        assertEquals(Intersections.intersects(rect, circle), Intersections.intersects(circle, rect));
    }
}
