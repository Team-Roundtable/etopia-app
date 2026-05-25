package ch.fhnw.roundtable.etopia.views.geothermal.model;

import ch.fhnw.roundtable.etopia.configuration.Configuration;
import ch.fhnw.roundtable.etopia.configuration.Geothermal;
import ch.fhnw.roundtable.etopia.shapes.Circle;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RocksTest {

    @Test
    void shouldIterateAllRocks() {
        var config = mock(Configuration.class);
        var geo = mock(Geothermal.class);

        when(config.geothermal()).thenReturn(geo);
        when(config.worldWidth()).thenReturn(100f);
        when(config.worldHeight()).thenReturn(100f);

        when(geo.mapHeight()).thenReturn(200f);
        when(geo.numberOfRocks()).thenReturn(3);
        when(geo.rockWidth()).thenReturn(10f);
        when(geo.rockHeight()).thenReturn(10f);

        var rocks = new Rocks(config, new Random(1));

        Iterator<Rock> it = rocks.iterator();

        assertTrue(it.hasNext());
        assertNotNull(it.next());
    }

    @Test
    void shouldReturnFalseWhenNoRocks() {
        var config = mock(Configuration.class);
        var geo = mock(Geothermal.class);

        when(config.geothermal()).thenReturn(geo);
        when(config.worldWidth()).thenReturn(100f);
        when(config.worldHeight()).thenReturn(100f);

        when(geo.mapHeight()).thenReturn(200f);
        when(geo.numberOfRocks()).thenReturn(0);

        var rocks = new Rocks(config, new Random(1));

        assertFalse(rocks.intersects(new Circle(0, 0, 1)));
    }
}
