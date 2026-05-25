package ch.fhnw.roundtable.etopia.views.solar.model;

import ch.fhnw.roundtable.etopia.configuration.Configuration;
import ch.fhnw.roundtable.etopia.configuration.Solar;
import ch.fhnw.roundtable.etopia.input.Controls;
import ch.fhnw.roundtable.etopia.shapes.Vector;
import ch.fhnw.roundtable.etopia.views.solar.state.SolarSunState;
import com.badlogic.gdx.math.MathUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SunTest {

    private static final float WORLD_WIDTH = 1000f;
    private static final float WORLD_HEIGHT = 800f;

    private static final float SUN_WIDTH = 100f;
    private static final float SUN_HEIGHT = 80f;

    private static final float SUN_SPEED = 1f;

    private static final float DELTA_TIME = 1f;
    private static final float EPSILON = 0.0001f;

    private static final float WORLD_CENTER_X = WORLD_WIDTH / 2f;
    private static final float WORLD_CENTER_Y = 0f;

    private Controls controls;

    private Sun sun;

    @BeforeEach
    void setUp() {
        Configuration configuration = mock(Configuration.class);
        Solar solarConfiguration = mock(Solar.class);
        controls = mock(Controls.class);

        when(configuration.worldWidth()).thenReturn(WORLD_WIDTH);
        when(configuration.worldHeight()).thenReturn(WORLD_HEIGHT);
        when(configuration.solar()).thenReturn(solarConfiguration);

        when(solarConfiguration.sunWidth()).thenReturn(SUN_WIDTH);
        when(solarConfiguration.sunHeight()).thenReturn(SUN_HEIGHT);
        when(solarConfiguration.sunSpeed()).thenReturn(SUN_SPEED);

        sun = new Sun(configuration);
    }

    @Test
    void shouldInitializeAtRightSideOfOrbit() {
        Vector center = sun.getCenter();

        assertEquals(WORLD_CENTER_X + WORLD_HEIGHT, center.x(), EPSILON);
        assertEquals(WORLD_CENTER_Y, center.y(), EPSILON);
    }

    @Test
    void shouldMoveAlongOrbitWhenUpdatedDuringDay() {
        sun.update(DELTA_TIME, controls);

        Vector center = sun.getCenter();

        float expectedX = WORLD_CENTER_X + (float) Math.cos(SUN_SPEED) * WORLD_HEIGHT;
        float expectedY = (float) Math.sin(SUN_SPEED) * WORLD_HEIGHT;

        assertEquals(expectedX, center.x(), EPSILON);
        assertEquals(expectedY, center.y(), EPSILON);
    }

    @Test
    void shouldMoveFasterAtNight() {
        // Move sun to nighttime (sin(angle) < 0)
        sun.update(MathUtils.PI, controls);

        double before = sun.dayTime();

        sun.update(DELTA_TIME, controls);

        double after = sun.dayTime();

        assertTrue(Math.abs(after - before) > 0);
    }

    @Test
    void shouldReturnZeroDayTimeAtStart() {
        assertEquals(0f, sun.dayTime(), EPSILON);
    }

    @Test
    void shouldReturnPositiveDayTimeAtMidday() {
        sun.update(MathUtils.PI / 2f, controls);

        assertEquals(1f, sun.dayTime(), EPSILON);
    }

    @Test
    void shouldReturnNegativeDayTimeAtNight() {
        sun.update((MathUtils.PI * 1.5f), controls);

        assertEquals(-1f, sun.dayTime(), EPSILON);
    }

    @Test
    void shouldClampDayLightToMinimumValueAtNight() {
        sun.update((MathUtils.PI * 1.5f), controls);

        assertEquals(0.5f, sun.dayLight(), EPSILON);
    }

    @Test
    void shouldReturnMaximumDayLightAtMidday() {
        sun.update(MathUtils.PI / 2f, controls);

        assertEquals(1f, sun.dayLight(), EPSILON);
    }

    @Test
    void shouldReturnCorrectState() {
        SolarSunState state = sun.state();

        assertEquals(
                WORLD_CENTER_X + WORLD_HEIGHT - SUN_WIDTH / 2f,
                state.x(),
                EPSILON
        );

        assertEquals(
                -SUN_HEIGHT / 2f,
                state.y(),
                EPSILON
        );

        assertEquals(SUN_WIDTH, state.width());
        assertEquals(SUN_HEIGHT, state.height());
        assertEquals(0.5f, state.dayLight(), EPSILON);
    }
}
