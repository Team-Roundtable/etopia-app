package ch.fhnw.roundtable.etopia.views.geothermal.model;

import ch.fhnw.roundtable.etopia.configuration.Configuration;
import ch.fhnw.roundtable.etopia.configuration.Geothermal;
import ch.fhnw.roundtable.etopia.shapes.Circle;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PipeTest {

    @Test
    void shouldCreateStateCorrectly() {
        var config = mock(Configuration.class);
        var geo = mock(Geothermal.class);

        when(config.geothermal()).thenReturn(geo);
        when(geo.pipeWidth()).thenReturn(10f);
        when(geo.pipeHeight()).thenReturn(20f);

        var pipe = new Pipe(config, 5f, 6f, 90f);

        var state = pipe.state();

        assertEquals(5f, state.x());
        assertEquals(6f, state.y());
        assertEquals(90f, state.rotation());
    }

    @Test
    void shouldDetectIntersection() {
        var config = mock(Configuration.class);
        var geo = mock(Geothermal.class);

        when(config.geothermal()).thenReturn(geo);
        when(geo.pipeWidth()).thenReturn(10f);
        when(geo.pipeHeight()).thenReturn(10f);

        var pipe = new Pipe(config, 0f, 0f, 0f);

        var circle = new Circle(0f, 0f, 1f);

        assertTrue(pipe.intersects(circle));
    }
}
