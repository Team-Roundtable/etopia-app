package ch.fhnw.roundtable.etopia.views.grid.model;

import ch.fhnw.roundtable.etopia.configuration.Configuration;
import ch.fhnw.roundtable.etopia.configuration.Grid;
import ch.fhnw.roundtable.etopia.input.ButtonType;
import ch.fhnw.roundtable.etopia.input.Controls;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CursorTest {

    private Cursor cursor;
    private Controls controls;

    @BeforeEach
    void setUp() {
        var configuration = mock(Configuration.class);
        var grid = mock(Grid.class);

        when(configuration.grid()).thenReturn(grid);
        when(grid.mapWidth()).thenReturn(5);
        when(grid.mapHeight()).thenReturn(5);
        when(grid.tileSide()).thenReturn(10f);
        when(grid.offsetY()).thenReturn(0f);

        cursor = new Cursor(configuration);
        controls = mock(Controls.class);
    }

    @Test
    void shouldMoveUp() {
        when(controls.isButtonJustPressed(ButtonType.UP)).thenReturn(true);

        cursor.update(0, controls);

        assertEquals(30f, cursor.state().y());
    }

    @Test
    void shouldMoveDown() {
        when(controls.isButtonJustPressed(ButtonType.DOWN)).thenReturn(true);

        cursor.update(0, controls);

        assertEquals(10f, cursor.state().y());
    }

    @Test
    void shouldMoveLeft() {
        when(controls.isButtonJustPressed(ButtonType.LEFT)).thenReturn(true);

        cursor.update(0, controls);

        assertEquals(10f, cursor.state().x());
    }

    @Test
    void shouldMoveRight() {
        when(controls.isButtonJustPressed(ButtonType.RIGHT)).thenReturn(true);

        cursor.update(0, controls);

        assertEquals(30f, cursor.state().x());
    }

    @Test
    void shouldNotMoveOutsideUpperBoundary() {
        when(controls.isButtonJustPressed(ButtonType.UP)).thenReturn(true);

        cursor.update(0, controls);
        cursor.update(0, controls);
        cursor.update(0, controls);
        cursor.update(0, controls);
        cursor.update(0, controls);

        assertEquals(40f, cursor.state().y());
    }

    @Test
    void shouldNotMoveOutsideLowerBoundary() {
        when(controls.isButtonJustPressed(ButtonType.DOWN)).thenReturn(true);

        for (int i = 0; i < 10; i++) {
            cursor.update(0, controls);
        }

        assertEquals(0f, cursor.state().y());
    }
}
