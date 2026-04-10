package ch.fhnw.roundtable.etopia.views.grid.game;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PipeTest {

    @Test
    void straightDirection() {
        var rotation0 = new Pipe(PipeType.STRAIGHT_IMMUTABLE, 0);

        assertFalse(rotation0.connects(Direction.UP));
        assertFalse(rotation0.connects(Direction.DOWN));
        assertTrue(rotation0.connects(Direction.LEFT));
        assertTrue(rotation0.connects(Direction.RIGHT));

        var rotation1 = new Pipe(PipeType.STRAIGHT_EDITABLE, 1);

        assertTrue(rotation1.connects(Direction.UP));
        assertTrue(rotation1.connects(Direction.DOWN));
        assertFalse(rotation1.connects(Direction.LEFT));
        assertFalse(rotation1.connects(Direction.RIGHT));
    }

    @Test
    void crossDirection() {
        var rotation0 = new Pipe(PipeType.CROSS_IMMUTABLE, 0);

        assertTrue(rotation0.connects(Direction.UP));
        assertTrue(rotation0.connects(Direction.DOWN));
        assertTrue(rotation0.connects(Direction.LEFT));
        assertTrue(rotation0.connects(Direction.RIGHT));

        var rotation1 = new Pipe(PipeType.CROSS_IMMUTABLE, 1);

        assertTrue(rotation1.connects(Direction.UP));
        assertTrue(rotation1.connects(Direction.DOWN));
        assertTrue(rotation1.connects(Direction.LEFT));
        assertTrue(rotation1.connects(Direction.RIGHT));
    }

    @Test
    void cornerDirection() {
        var rotation0 = new Pipe(PipeType.CORNER_IMMUTABLE, 0);

        assertFalse(rotation0.connects(Direction.UP));
        assertTrue(rotation0.connects(Direction.DOWN));
        assertTrue(rotation0.connects(Direction.LEFT));
        assertFalse(rotation0.connects(Direction.RIGHT));

        var rotation1 = new Pipe(PipeType.CORNER_EDITABLE, 1);

        assertFalse(rotation1.connects(Direction.UP));
        assertTrue(rotation1.connects(Direction.DOWN));
        assertFalse(rotation1.connects(Direction.LEFT));
        assertTrue(rotation1.connects(Direction.RIGHT));

        var rotation2 = new Pipe(PipeType.CORNER_IMMUTABLE, 2);

        assertTrue(rotation2.connects(Direction.UP));
        assertFalse(rotation2.connects(Direction.DOWN));
        assertFalse(rotation2.connects(Direction.LEFT));
        assertTrue(rotation2.connects(Direction.RIGHT));

        var rotation3 = new Pipe(PipeType.CORNER_EDITABLE, 3);

        assertTrue(rotation3.connects(Direction.UP));
        assertFalse(rotation3.connects(Direction.DOWN));
        assertTrue(rotation3.connects(Direction.LEFT));
        assertFalse(rotation3.connects(Direction.RIGHT));
    }

    @Test
    void threeWayDirection() {
        var rotation0 = new Pipe(PipeType.THREE_WAY_IMMUTABLE, 0);

        assertFalse(rotation0.connects(Direction.UP));
        assertTrue(rotation0.connects(Direction.DOWN));
        assertTrue(rotation0.connects(Direction.LEFT));
        assertTrue(rotation0.connects(Direction.RIGHT));

        var rotation1 = new Pipe(PipeType.THREE_WAY_EDITABLE, 1);

        assertTrue(rotation1.connects(Direction.UP));
        assertTrue(rotation1.connects(Direction.DOWN));
        assertFalse(rotation1.connects(Direction.LEFT));
        assertTrue(rotation1.connects(Direction.RIGHT));

        var rotation2 = new Pipe(PipeType.THREE_WAY_IMMUTABLE, 2);

        assertTrue(rotation2.connects(Direction.UP));
        assertFalse(rotation2.connects(Direction.DOWN));
        assertTrue(rotation2.connects(Direction.LEFT));
        assertTrue(rotation2.connects(Direction.RIGHT));

        var rotation3 = new Pipe(PipeType.THREE_WAY_EDITABLE, 3);

        assertTrue(rotation3.connects(Direction.UP));
        assertTrue(rotation3.connects(Direction.DOWN));
        assertTrue(rotation3.connects(Direction.LEFT));
        assertFalse(rotation3.connects(Direction.RIGHT));
    }
}
