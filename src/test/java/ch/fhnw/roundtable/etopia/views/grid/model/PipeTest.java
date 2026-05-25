package ch.fhnw.roundtable.etopia.views.grid.model;

import ch.fhnw.roundtable.etopia.configuration.Configuration;
import ch.fhnw.roundtable.etopia.configuration.Grid;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PipeTest {

    private Configuration configuration;

    @BeforeEach
    void setUp() {
        configuration = mock(Configuration.class);

        var grid = mock(Grid.class);

        when(configuration.grid()).thenReturn(grid);
        when(grid.tileSide()).thenReturn(10f);
        when(grid.offsetY()).thenReturn(0f);
    }

    @Test
    void shouldReturnType() {
        var pipe = new Pipe(configuration, 1, 2, PipeType.PRODUCER, 0);

        assertEquals(PipeType.PRODUCER, pipe.getType());
    }

    @Test
    void shouldRotate() {
        var pipe = new Pipe(configuration, 0, 0, PipeType.PRODUCER, 0);

        pipe.rotate();

        assertEquals(1, pipe.getRotation());
    }

    @Test
    void shouldSetPowered() {
        var pipe = new Pipe(configuration, 0, 0, PipeType.PRODUCER, 0);

        pipe.setPowered(true);

        assertTrue(pipe.isPowered());
    }

    @Test
    void shouldCreateNeighbour() {
        var pipe = new Pipe(configuration, 1, 1, PipeType.PRODUCER, 0);

        var neighbour = pipe.createNeighbour(Direction.RIGHT, PipeType.CONSUMER, 2);

        assertEquals(neighbour, pipe.getNeighbours().get(Direction.RIGHT));
        assertEquals(PipeType.CONSUMER, neighbour.getType());
        assertEquals(2, neighbour.getRotation());
    }

    @Test
    void shouldAddNeighbour() {
        var pipe = new Pipe(configuration, 0, 0, PipeType.PRODUCER, 0);
        var neighbour = new Pipe(configuration, 1, 0, PipeType.CONSUMER, 0);

        pipe.addNeighbour(Direction.RIGHT, neighbour);

        assertEquals(neighbour, pipe.getNeighbours().get(Direction.RIGHT));
    }

    @Test
    void shouldReturnStateIncludingNeighbours() {
        var pipe = new Pipe(configuration, 0, 0, PipeType.PRODUCER, 0);

        pipe.createNeighbour(Direction.RIGHT, PipeType.CONSUMER, 0);

        assertEquals(2, pipe.state().size());
    }
}
