package ch.fhnw.roundtable.etopia.views.wind.model;

import ch.fhnw.roundtable.etopia.configuration.Configuration;
import ch.fhnw.roundtable.etopia.configuration.Wind;
import ch.fhnw.roundtable.etopia.input.Controls;
import ch.fhnw.roundtable.etopia.shapes.Rectangle;
import ch.fhnw.roundtable.etopia.views.wind.state.WindGustState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GustTest {

    private static final float WORLD_WIDTH = 1000f;

    private static final float GUST_Y = 300f;
    private static final float GUST_WIDTH = 120f;
    private static final float GUST_HEIGHT = 60f;
    private static final float GUST_SPEED = 10f;

    private static final float DELTA_TIME = 1f;
    private static final float HARMFUL_SPEED_MULTIPLIER = 1.5f;

    private static final float EPSILON = 0.0001f;

    private Configuration configuration;
    private Wind windConfiguration;
    private Controls controls;

    @BeforeEach
    void setUp() {
        configuration = mock(Configuration.class);
        windConfiguration = mock(Wind.class);
        controls = mock(Controls.class);

        when(configuration.worldWidth()).thenReturn(WORLD_WIDTH);
        when(configuration.wind()).thenReturn(windConfiguration);

        when(windConfiguration.gustWidth()).thenReturn(GUST_WIDTH);
        when(windConfiguration.gustHeight()).thenReturn(GUST_HEIGHT);
        when(windConfiguration.gustSpeed()).thenReturn(GUST_SPEED);
    }

    @Test
    void shouldInitializeAtWorldWidth() {
        Gust gust = new Gust(configuration, GUST_Y, false);

        Rectangle bounds = gust.getBounds();

        assertEquals(WORLD_WIDTH, bounds.x(), EPSILON);
        assertEquals(GUST_Y, bounds.y(), EPSILON);
    }

    @Test
    void shouldMoveLeftWhenUpdated() {
        Gust gust = new Gust(configuration, GUST_Y, false);

        gust.update(DELTA_TIME, controls);

        Rectangle bounds = gust.getBounds();

        assertEquals(
                WORLD_WIDTH - GUST_SPEED,
                bounds.x(),
                EPSILON
        );
    }

    @Test
    void shouldMoveFasterWhenHarmful() {
        Gust gust = new Gust(configuration, GUST_Y, true);

        gust.update(DELTA_TIME, controls);

        Rectangle bounds = gust.getBounds();

        assertEquals(
                WORLD_WIDTH - (GUST_SPEED * HARMFUL_SPEED_MULTIPLIER),
                bounds.x(),
                EPSILON
        );
    }

    @Test
    void shouldReturnCorrectStateForHarmlessGust() {
        Gust gust = new Gust(configuration, GUST_Y, false);

        WindGustState state = gust.state();

        assertEquals(WORLD_WIDTH, state.x(), EPSILON);
        assertEquals(GUST_Y, state.y(), EPSILON);
        assertEquals(GUST_WIDTH, state.width(), EPSILON);
        assertEquals(GUST_HEIGHT, state.height(), EPSILON);
        assertFalse(state.harmful());
    }

    @Test
    void shouldReturnCorrectStateForHarmfulGust() {
        Gust gust = new Gust(configuration, GUST_Y, true);

        WindGustState state = gust.state();

        assertTrue(state.harmful());
    }

    @Test
    void shouldReturnCorrectBounds() {
        Gust gust = new Gust(configuration, GUST_Y, false);

        Rectangle bounds = gust.getBounds();

        assertEquals(WORLD_WIDTH, bounds.x(), EPSILON);
        assertEquals(GUST_Y, bounds.y(), EPSILON);
        assertEquals(GUST_WIDTH, bounds.width(), EPSILON);
        assertEquals(GUST_HEIGHT, bounds.height(), EPSILON);
    }

    @Test
    void shouldReturnTrueWhenGustIsHarmful() {
        Gust gust = new Gust(configuration, GUST_Y, true);

        assertTrue(gust.isHarmful());
    }

    @Test
    void shouldReturnFalseWhenGustIsNotHarmful() {
        Gust gust = new Gust(configuration, GUST_Y, false);

        assertFalse(gust.isHarmful());
    }

    @Test
    void shouldScaleMovementByDeltaTime() {
        Gust gust = new Gust(configuration, GUST_Y, false);

        float halfDelta = 0.5f;

        gust.update(halfDelta, controls);

        Rectangle bounds = gust.getBounds();

        assertEquals(
                WORLD_WIDTH - (GUST_SPEED * halfDelta),
                bounds.x(),
                EPSILON
        );
    }
}
