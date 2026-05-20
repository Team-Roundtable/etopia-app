package ch.fhnw.roundtable.etopia.views.grid.model;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PowerPropagationServiceTest {

    @Test
    void shouldShutdownRecursively() {
        var root = mock(Pipe.class);
        var child = mock(Pipe.class);

        when(root.getNeighbours()).thenReturn(Map.of(Direction.UP, child));
        when(child.getNeighbours()).thenReturn(Map.of());

        PowerPropagationService.shutdown(root);

        verify(root).setPowered(false);
        verify(child).setPowered(false);
    }

    @Test
    void shouldPowerOnlyConnectedPaths() {
        var root = mock(Pipe.class);
        var child = mock(Pipe.class);

        when(root.getNeighbours()).thenReturn(Map.of(Direction.UP, child));
        when(child.getNeighbours()).thenReturn(Map.of());

        when(root.getType()).thenReturn(PipeType.PRODUCER);
        when(child.getType()).thenReturn(PipeType.CONSUMER);

        assertDoesNotThrow(() -> PowerPropagationService.power(root));

        verify(root).setPowered(true);
    }

    @Test
    void consumerShouldReportPoweredWhenDirect() {
        var consumer = mock(Pipe.class);
        when(consumer.getType()).thenReturn(PipeType.CONSUMER);
        when(consumer.isPowered()).thenReturn(true);

        assertTrue(PowerPropagationService.isConsumerPowered(consumer));
    }

    @Test
    void consumerShouldBeFoundInDeepGraph() {
        var root = mock(Pipe.class);
        var child = mock(Pipe.class);
        var consumer = mock(Pipe.class);

        when(root.getType()).thenReturn(PipeType.PRODUCER);
        when(child.getType()).thenReturn(PipeType.PRODUCER);
        when(consumer.getType()).thenReturn(PipeType.CONSUMER);
        when(consumer.isPowered()).thenReturn(true);

        when(root.getNeighbours()).thenReturn(Map.of(Direction.UP, child));
        when(child.getNeighbours()).thenReturn(Map.of(Direction.UP, consumer));
        when(consumer.getNeighbours()).thenReturn(Map.of());

        assertTrue(PowerPropagationService.isConsumerPowered(root));
    }
}
