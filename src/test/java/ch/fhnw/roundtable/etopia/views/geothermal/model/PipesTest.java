package ch.fhnw.roundtable.etopia.views.geothermal.model;

import ch.fhnw.roundtable.etopia.configuration.Configuration;
import ch.fhnw.roundtable.etopia.configuration.Geothermal;
import ch.fhnw.roundtable.etopia.shapes.Circle;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PipesTest {

    @Test
    void shouldReturnFalseWhenLessThanThreeElements() {
        var config = mock(Configuration.class);
        when(config.geothermal()).thenReturn(mock(Geothermal.class));

        var pipes = new Pipes(config);

        pipes.add(0, 0);
        pipes.add(1, 1);
        pipes.add(2, 2);

        assertFalse(pipes.intersects(new Circle(0, 0, 1)));
    }

    @Test
    void shouldReturnTrueWhenPipeBeforeTailIntersects() {
        var config = mock(Configuration.class);
        var geo = mock(Geothermal.class);

        when(config.geothermal()).thenReturn(geo);
        when(geo.pipeWidth()).thenReturn(10f);
        when(geo.pipeHeight()).thenReturn(10f);

        var pipes = new Pipes(config);

        pipes.add(0, 0);
        pipes.add(10, 10);
        pipes.add(20, 20);
        pipes.add(30, 30);
        pipes.add(40, 40);
        pipes.add(50, 50);
        pipes.add(60, 60);

        assertTrue(pipes.intersects(new Circle(0, 0, 100)));
    }

    @Test
    void shouldIterateAllPipes() {
        var config = mock(Configuration.class);
        when(config.geothermal()).thenReturn(mock(Geothermal.class));

        var pipes = new Pipes(config);

        pipes.add(0, 0);
        pipes.add(1, 1);
        pipes.add(2, 2);

        Iterator<Pipe> it = pipes.iterator();

        assertTrue(it.hasNext());
        assertNotNull(it.next());
    }
}
