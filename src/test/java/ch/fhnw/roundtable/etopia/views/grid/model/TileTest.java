package ch.fhnw.roundtable.etopia.views.grid.model;

import ch.fhnw.roundtable.etopia.configuration.Configuration;
import ch.fhnw.roundtable.etopia.configuration.Grid;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TileTest {

    private Configuration configuration;

    @BeforeEach
    void setUp() {
        configuration = mock(Configuration.class);
        var grid = mock(Grid.class);

        when(configuration.grid()).thenReturn(grid);
        when(grid.tileSide()).thenReturn(10f);
        when(grid.offsetY()).thenReturn(5f);
    }

    @Test
    void shouldIntersectWhenCoordinatesMatch() {
        var a = new Tile(configuration, 1, 2);
        var b = new Tile(configuration, 1, 2);

        assertTrue(a.intersects(b));
    }

    @Test
    void shouldNotIntersectWhenCoordinatesDiffer() {
        var a = new Tile(configuration, 1, 2);
        var b = new Tile(configuration, 2, 2);

        assertFalse(a.intersects(b));
    }
}
