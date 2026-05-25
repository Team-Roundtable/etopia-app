package ch.fhnw.roundtable.etopia.views.wind.model;

import ch.fhnw.roundtable.etopia.configuration.Configuration;
import ch.fhnw.roundtable.etopia.configuration.Wind;
import ch.fhnw.roundtable.etopia.input.Controls;
import ch.fhnw.roundtable.etopia.shapes.Rectangle;
import ch.fhnw.roundtable.etopia.views.wind.state.WindTurbineState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TurbineTest {

    private static final float WORLD_HEIGHT = 800f;

    private static final float TURBINE_X = 100f;
    private static final float TURBINE_WIDTH = 80f;
    private static final float TURBINE_HEIGHT = 120f;
    private static final float TURBINE_SPEED = 200f;

    private static final float FREEZE_DURATION = 2f;

    private static final float DELTA_TIME = 1f;
    private static final float HALF_DELTA_TIME = 0.5f;

    private static final float POSITIVE_INPUT = 1f;
    private static final float NEGATIVE_INPUT = -1f;
    private static final float NO_INPUT = 0f;

    private static final float EPSILON = 0.0001f;

    private static final float INITIAL_Y = WORLD_HEIGHT / 2f;
    private static final float MAX_Y =
            WORLD_HEIGHT - TURBINE_HEIGHT;

    private Configuration configuration;
    private Wind windConfiguration;
    private Controls controls;

    private Turbine turbine;

    @BeforeEach
    void setUp() {
        configuration = mock(Configuration.class);
        windConfiguration = mock(Wind.class);
        controls = mock(Controls.class);

        when(configuration.worldHeight()).thenReturn(WORLD_HEIGHT);
        when(configuration.wind()).thenReturn(windConfiguration);

        when(windConfiguration.freezeCountdown())
                .thenReturn(FREEZE_DURATION);

        when(windConfiguration.turbineWidth())
                .thenReturn(TURBINE_WIDTH);

        when(windConfiguration.turbineHeight())
                .thenReturn(TURBINE_HEIGHT);

        when(windConfiguration.turbineSpeed())
                .thenReturn(TURBINE_SPEED);

        turbine = new Turbine(configuration);
    }

    @Test
    void shouldInitializeAtMiddleOfWorld() {
        Rectangle bounds = turbine.getBounds();

        assertEquals(TURBINE_X, bounds.x(), EPSILON);
        assertEquals(INITIAL_Y, bounds.y(), EPSILON);
    }

    @Test
    void shouldMoveUpWhenInputPositive() {
        when(controls.getButtonVertical())
                .thenReturn(POSITIVE_INPUT);

        turbine.update(DELTA_TIME, controls);

        assertEquals(
                INITIAL_Y + TURBINE_SPEED,
                turbine.getBounds().y(),
                EPSILON
        );
    }

    @Test
    void shouldMoveDownWhenInputNegative() {
        when(controls.getButtonVertical())
                .thenReturn(NEGATIVE_INPUT);

        turbine.update(DELTA_TIME, controls);

        assertEquals(
                INITIAL_Y - TURBINE_SPEED,
                turbine.getBounds().y(),
                EPSILON
        );
    }

    @Test
    void shouldNotMoveWhenInputIsZero() {
        when(controls.getButtonVertical())
                .thenReturn(NO_INPUT);

        turbine.update(DELTA_TIME, controls);

        assertEquals(
                INITIAL_Y,
                turbine.getBounds().y(),
                EPSILON
        );
    }

    @Test
    void shouldScaleMovementByDeltaTime() {
        when(controls.getButtonVertical())
                .thenReturn(POSITIVE_INPUT);

        turbine.update(HALF_DELTA_TIME, controls);

        assertEquals(
                INITIAL_Y + (TURBINE_SPEED * HALF_DELTA_TIME),
                turbine.getBounds().y(),
                EPSILON
        );
    }

    @Test
    void shouldClampToTopBoundary() {
        when(controls.getButtonVertical())
                .thenReturn(NEGATIVE_INPUT);

        turbine.update(10f, controls);

        assertEquals(
                0f,
                turbine.getBounds().y(),
                EPSILON
        );
    }

    @Test
    void shouldClampToBottomBoundary() {
        when(controls.getButtonVertical())
                .thenReturn(POSITIVE_INPUT);

        turbine.update(10f, controls);

        assertEquals(
                MAX_Y,
                turbine.getBounds().y(),
                EPSILON
        );
    }

    @Test
    void shouldFreezeTurbine() {
        turbine.freeze();

        assertTrue(turbine.isFrozen());
    }

    @Test
    void shouldNotMoveWhileFrozen() {
        turbine.freeze();

        when(controls.getButtonVertical())
                .thenReturn(POSITIVE_INPUT);

        turbine.update(DELTA_TIME, controls);

        assertEquals(
                INITIAL_Y,
                turbine.getBounds().y(),
                EPSILON
        );
    }

    @Test
    void shouldSetLightNoneWhileFrozen() {
        turbine.freeze();

        turbine.update(DELTA_TIME, controls);

        verify(controls).setButtonLightNone();
    }

    @Test
    void shouldUnfreezeAfterCountdownFinishes() {
        turbine.freeze();

        turbine.update(FREEZE_DURATION, controls);

        assertFalse(turbine.isFrozen());
    }

    @Test
    void shouldSetLightVerticalWhenFreezeEnds() {
        turbine.freeze();

        turbine.update(FREEZE_DURATION, controls);

        verify(controls).setButtonLightVertical();
    }

    @Test
    void shouldReturnCorrectState() {
        WindTurbineState state = turbine.state();

        assertEquals(TURBINE_X, state.x(), EPSILON);
        assertEquals(INITIAL_Y, state.y(), EPSILON);
        assertEquals(TURBINE_WIDTH, state.width(), EPSILON);
        assertEquals(TURBINE_HEIGHT, state.height(), EPSILON);
    }

    @Test
    void shouldReturnCorrectBounds() {
        Rectangle bounds = turbine.getBounds();

        assertEquals(TURBINE_X, bounds.x(), EPSILON);
        assertEquals(INITIAL_Y, bounds.y(), EPSILON);
        assertEquals(TURBINE_WIDTH, bounds.width(), EPSILON);
        assertEquals(TURBINE_HEIGHT, bounds.height(), EPSILON);
    }
}
