package ch.fhnw.roundtable.etopia.views.geothermal.model;

import ch.fhnw.roundtable.etopia.configuration.Configuration;
import ch.fhnw.roundtable.etopia.configuration.Geothermal;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RockTest {

    @Test
    void shouldGenerateState() {
        var config = mock(Configuration.class);
        var geo = mock(Geothermal.class);

        when(config.worldWidth()).thenReturn(100f);
        when(config.worldHeight()).thenReturn(100f);
        when(config.geothermal()).thenReturn(geo);

        when(geo.mapHeight()).thenReturn(200f);
        when(geo.rockWidth()).thenReturn(10f);
        when(geo.rockHeight()).thenReturn(10f);

        var rock = new Rock(config, new Random(1));

        var state = rock.state();

        assertNotNull(state);
    }
}
