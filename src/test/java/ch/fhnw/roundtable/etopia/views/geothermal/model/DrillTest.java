package ch.fhnw.roundtable.etopia.views.geothermal.model;

import ch.fhnw.roundtable.etopia.configuration.Configuration;
import ch.fhnw.roundtable.etopia.configuration.Geothermal;
import ch.fhnw.roundtable.etopia.input.Controls;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DrillTest {


    private Configuration config;
    private Controls controls;

    @BeforeEach
    void setUp() {
        config = mock(Configuration.class);
        var geo = mock(Geothermal.class);

        when(config.worldWidth()).thenReturn(100f);
        when(config.worldHeight()).thenReturn(100f);
        when(config.geothermal()).thenReturn(geo);

        when(geo.mapHeight()).thenReturn(200f);
        when(geo.drillWidth()).thenReturn(10f);
        when(geo.drillHeight()).thenReturn(10f);
        when(geo.drillSpeed()).thenReturn(10f);

        controls = mock(Controls.class);
    }

    @Test
    void shouldMoveDownByDefault() {
        var drill = new Drill(config);

        when(controls.getButtonHorizontal()).thenReturn(0f);

        float startY = drill.getY();
        drill.update(1f, controls);

        assertTrue(drill.getY() < startY);
    }

    @Test
    void shouldMoveUpWhenDirectionChanged() {
        var drill = new Drill(config);

        when(controls.getButtonHorizontal()).thenReturn(0f);

        float startY = drill.getY();
        drill.up();
        drill.update(1f, controls);

        assertTrue(drill.getY() > startY);
    }

    @Test
    void shouldMoveUpAfterUpCommand() {
        var drill = new Drill(config);

        drill.up();

        when(controls.getButtonHorizontal()).thenReturn(0f);

        float startY = drill.getY();
        drill.update(1f, controls);

        assertTrue(drill.getY() > startY);
    }

    @Test
    void shouldClampXPosition() {
        var drill = new Drill(config);

        when(controls.getButtonHorizontal()).thenReturn(100f);

        drill.update(1f, controls);

        assertTrue(drill.getX() <= 100f);
    }

    @Test
    void shouldReturnBounds() {
        var drill = new Drill(config);

        assertNotNull(drill.getBounds());
    }
}
