package ch.fhnw.roundtable.etopia.views.solar.model;

import ch.fhnw.roundtable.etopia.configuration.Configuration;
import ch.fhnw.roundtable.etopia.configuration.Solar;
import ch.fhnw.roundtable.etopia.input.Controls;
import ch.fhnw.roundtable.etopia.shapes.Vector;
import ch.fhnw.roundtable.etopia.views.solar.state.SolarPanelState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PanelTest {

    private static final float WORLD_WIDTH = 1000f;
    private static final float WORLD_HEIGHT = 800f;

    private static final float PANEL_WIDTH = 200f;
    private static final float PANEL_HEIGHT = 50f;

    private static final float PANEL_SPEED = 10f;
    private static final float MAX_ROTATION = 45f;

    private static final float DELTA_TIME = 1f;
    private static final float HALF_DELTA_TIME = 0.5f;

    private static final float POSITIVE_INPUT = 1f;
    private static final float NEGATIVE_INPUT = -1f;
    private static final float NO_INPUT = 0f;

    private static final float INITIAL_ROTATION = 0f;

    private Controls controls;

    private Panel panel;

    @BeforeEach
    void setUp() {
        Configuration configuration = mock(Configuration.class);
        Solar solarConfiguration = mock(Solar.class);
        controls = mock(Controls.class);

        when(configuration.worldWidth()).thenReturn(WORLD_WIDTH);
        when(configuration.worldHeight()).thenReturn(WORLD_HEIGHT);
        when(configuration.solar()).thenReturn(solarConfiguration);

        when(solarConfiguration.panelWidth()).thenReturn(PANEL_WIDTH);
        when(solarConfiguration.panelHeight()).thenReturn(PANEL_HEIGHT);
        when(solarConfiguration.panelMaxRotation()).thenReturn(MAX_ROTATION);
        when(solarConfiguration.panelSpeed()).thenReturn(PANEL_SPEED);

        panel = new Panel(configuration);
    }

    @Test
    void shouldInitializeCenterFromWorldDimensions() {
        Vector center = panel.getCenter();

        assertEquals(WORLD_WIDTH / 2f, center.x());
        assertEquals(WORLD_HEIGHT / 4f, center.y());
    }

    @Test
    void shouldInitializeWithZeroRotation() {
        assertEquals(INITIAL_ROTATION, panel.getRotation());
    }

    @Test
    void shouldRotateLeftWhenHorizontalControlPositive() {
        when(controls.getButtonHorizontal()).thenReturn(POSITIVE_INPUT);

        panel.update(DELTA_TIME, controls);

        assertEquals(-PANEL_SPEED, panel.getRotation());
    }

    @Test
    void shouldRotateRightWhenHorizontalControlNegative() {
        when(controls.getButtonHorizontal()).thenReturn(NEGATIVE_INPUT);

        panel.update(DELTA_TIME, controls);

        assertEquals(PANEL_SPEED, panel.getRotation());
    }

    @Test
    void shouldNotRotateWhenHorizontalControlIsZero() {
        when(controls.getButtonHorizontal()).thenReturn(NO_INPUT);

        panel.update(DELTA_TIME, controls);

        assertEquals(INITIAL_ROTATION, panel.getRotation());
    }

    @Test
    void shouldScaleRotationByDeltaTime() {
        when(controls.getButtonHorizontal()).thenReturn(POSITIVE_INPUT);

        panel.update(HALF_DELTA_TIME, controls);

        assertEquals(-(PANEL_SPEED * HALF_DELTA_TIME), panel.getRotation());
    }

    @Test
    void shouldClampRotationToPositiveMaxRotation() {
        when(controls.getButtonHorizontal()).thenReturn(NEGATIVE_INPUT);

        panel.update(10f, controls);

        assertEquals(MAX_ROTATION, panel.getRotation());
    }

    @Test
    void shouldClampRotationToNegativeMaxRotation() {
        when(controls.getButtonHorizontal()).thenReturn(POSITIVE_INPUT);

        panel.update(10f, controls);

        assertEquals(-MAX_ROTATION, panel.getRotation());
    }

    @Test
    void shouldReturnCorrectState() {
        when(controls.getButtonHorizontal()).thenReturn(NEGATIVE_INPUT);

        panel.update(DELTA_TIME, controls);

        SolarPanelState state = panel.state();

        assertEquals(WORLD_WIDTH / 2f, state.x());
        assertEquals(WORLD_HEIGHT / 4f, state.y());
        assertEquals(PANEL_WIDTH, state.width());
        assertEquals(PANEL_HEIGHT, state.height());
        assertEquals(PANEL_SPEED, state.rotation());
    }

    @Test
    void shouldAccumulateRotationAcrossUpdates() {
        when(controls.getButtonHorizontal()).thenReturn(NEGATIVE_INPUT);

        panel.update(DELTA_TIME, controls);
        panel.update(DELTA_TIME, controls);

        assertEquals(PANEL_SPEED * 2, panel.getRotation());
    }
}
