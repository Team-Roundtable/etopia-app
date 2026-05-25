package ch.fhnw.roundtable.etopia.views.grid.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DirectionTest {

    @Test
    void shouldReturnOppositeUp() {
        assertEquals(Direction.DOWN, Direction.UP.opposite());
    }

    @Test
    void shouldReturnOppositeDown() {
        assertEquals(Direction.UP, Direction.DOWN.opposite());
    }

    @Test
    void shouldReturnOppositeLeft() {
        assertEquals(Direction.RIGHT, Direction.LEFT.opposite());
    }

    @Test
    void shouldReturnOppositeRight() {
        assertEquals(Direction.LEFT, Direction.RIGHT.opposite());
    }
}
