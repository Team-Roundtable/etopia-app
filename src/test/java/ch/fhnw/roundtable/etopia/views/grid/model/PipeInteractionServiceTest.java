package ch.fhnw.roundtable.etopia.views.grid.model;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class PipeInteractionServiceTest {

    @Test
    void shouldRotateWhenEditableAndIntersecting() {
        var pipe = mock(Pipe.class);
        var cursor = mock(Cursor.class);

        var type = mock(PipeType.class);
        when(type.isEditable()).thenReturn(true);

        when(pipe.getType()).thenReturn(type);
        when(pipe.intersects(cursor)).thenReturn(true);

        assertTrue(PipeInteractionService.rotate(pipe, cursor));
        verify(pipe).rotate();
    }

    @Test
    void shouldNotRotateWhenNotEditable() {
        var pipe = mock(Pipe.class);
        var cursor = mock(Cursor.class);

        var type = mock(PipeType.class);
        when(type.isEditable()).thenReturn(false);

        when(pipe.getType()).thenReturn(type);
        when(pipe.intersects(cursor)).thenReturn(true);

        assertFalse(PipeInteractionService.rotate(pipe, cursor));
        verify(pipe, never()).rotate();
    }

    @Test
    void shouldNotRotateWhenNotIntersecting() {
        var pipe = mock(Pipe.class);
        var cursor = mock(Cursor.class);

        var type = mock(PipeType.class);
        when(type.isEditable()).thenReturn(true);

        when(pipe.getType()).thenReturn(type);
        when(pipe.intersects(cursor)).thenReturn(false);

        assertFalse(PipeInteractionService.rotate(pipe, cursor));
        verify(pipe, never()).rotate();
    }

    @Test
    void shouldSearchNeighboursWhenNotIntersecting() {
        var pipe = mock(Pipe.class);
        var neighbour = mock(Pipe.class);
        var cursor = mock(Cursor.class);

        var type = mock(PipeType.class);
        when(type.isEditable()).thenReturn(false);

        when(pipe.getType()).thenReturn(type);
        when(pipe.intersects(cursor)).thenReturn(false);
        when(pipe.getNeighbours()).thenReturn(Map.of(Direction.UP, neighbour));

        var nType = mock(PipeType.class);
        when(nType.isEditable()).thenReturn(true);

        when(neighbour.getType()).thenReturn(nType);
        when(neighbour.intersects(cursor)).thenReturn(true);

        assertTrue(PipeInteractionService.rotate(pipe, cursor));
        verify(neighbour).rotate();
    }

    @Test
    void shouldReturnFalseWhenNothingMatches() {
        var pipe = mock(Pipe.class);
        var cursor = mock(Cursor.class);

        var type = mock(PipeType.class);
        when(type.isEditable()).thenReturn(false);

        when(pipe.getType()).thenReturn(type);
        when(pipe.intersects(cursor)).thenReturn(false);
        when(pipe.getNeighbours()).thenReturn(Map.of());

        assertFalse(PipeInteractionService.rotate(pipe, cursor));
    }
}
