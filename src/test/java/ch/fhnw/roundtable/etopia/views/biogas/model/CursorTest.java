package ch.fhnw.roundtable.etopia.views.biogas.model;

import ch.fhnw.roundtable.etopia.configuration.Configuration;
import ch.fhnw.roundtable.etopia.input.ButtonType;
import ch.fhnw.roundtable.etopia.input.Controls;
import ch.fhnw.roundtable.etopia.views.biogas.state.BiogasCursorState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CursorTest {

    private Configuration configuration;
    private Controls controls;
    private Cursor cursor;

    @BeforeEach
    void setUp() {
        configuration = mock(Configuration.class, RETURNS_DEEP_STUBS);
        controls = mock(Controls.class);

        when(configuration.biogas().mapWidth()).thenReturn(5);
        when(configuration.biogas().mapHeight()).thenReturn(5);

        cursor = new Cursor(configuration);
    }

    @Test
    void movesUpWithinBounds() {
        cursor.update(0, controls);

        when(controls.isButtonJustPressed(ButtonType.UP)).thenReturn(true);
        cursor.update(0, controls);

        assertEquals(2, cursor.localY());
    }

    @Test
    void movesDownWithinBounds() {
        when(controls.isButtonJustPressed(ButtonType.DOWN)).thenReturn(true);

        cursor.update(0, controls);

        assertEquals(0, cursor.localY()); // starts at 1, goes to 0
    }

    @Test
    void movesLeftWithinBounds() {
        when(controls.isButtonJustPressed(ButtonType.LEFT)).thenReturn(true);

        cursor.update(0, controls);

        assertEquals(0, cursor.localX());
    }

    @Test
    void movesRightWithinBounds() {
        when(controls.isButtonJustPressed(ButtonType.RIGHT)).thenReturn(true);

        cursor.update(0, controls);

        assertEquals(2, cursor.localX());
    }

    @Test
    void doesNotMoveWhenNoInput() {
        cursor.update(0, controls);

        assertEquals(1, cursor.localX());
        assertEquals(1, cursor.localY());
    }

    @Test
    void clampsAtTopBoundary() {
        when(controls.isButtonJustPressed(ButtonType.UP)).thenReturn(true);

        // move to top edge
        cursor.update(0, controls);
        cursor.update(0, controls);
        cursor.update(0, controls);
        cursor.update(0, controls);

        assertEquals(4, cursor.localY()); // max is 4 (height - 1)
    }

    @Test
    void clampsAtBottomBoundary() {
        when(controls.isButtonJustPressed(ButtonType.DOWN)).thenReturn(true);

        cursor.update(0, controls);
        cursor.update(0, controls);

        assertEquals(0, cursor.localY());
    }

    @Test
    void clampsAtLeftBoundary() {
        when(controls.isButtonJustPressed(ButtonType.LEFT)).thenReturn(true);

        cursor.update(0, controls);
        cursor.update(0, controls);

        assertEquals(0, cursor.localX());
    }

    @Test
    void clampsAtRightBoundary() {
        when(controls.isButtonJustPressed(ButtonType.RIGHT)).thenReturn(true);

        cursor.update(0, controls);
        cursor.update(0, controls);
        cursor.update(0, controls);
        cursor.update(0, controls);

        assertEquals(4, cursor.localX()); // max is 4 (width - 1)
    }

    @Test
    void stateReturnsCorrectValues() {
        when(controls.isButtonJustPressed(ButtonType.RIGHT)).thenReturn(true);
        cursor.update(0, controls);

        BiogasCursorState state = cursor.state();

        assertEquals(cursor.getX(), state.x());
        assertEquals(cursor.getY(), state.y());
    }
}
