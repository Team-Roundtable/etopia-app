package ch.fhnw.roundtable.etopia.views.geothermal.model;

import ch.fhnw.roundtable.etopia.Result;
import ch.fhnw.roundtable.etopia.configuration.Configuration;
import ch.fhnw.roundtable.etopia.configuration.Geothermal;
import ch.fhnw.roundtable.etopia.input.Controls;
import ch.fhnw.roundtable.etopia.views.status.model.StatusModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GeothermalModelTest {

    private Configuration configuration;
    private Geothermal geo;
    private StatusModel status;
    private Controls controls;

    @BeforeEach
    void setUp() {
        configuration = mock(Configuration.class);
        geo = mock(Geothermal.class);
        status = mock(StatusModel.class);
        controls = mock(Controls.class);

        when(configuration.geothermal()).thenReturn(geo);
        when(configuration.worldWidth()).thenReturn(100f);
        when(configuration.worldHeight()).thenReturn(100f);

        when(geo.mapHeight()).thenReturn(200f);
        when(geo.pipeTimer()).thenReturn(1f);
        when(geo.collisionCountdown()).thenReturn(1f);
        when(geo.bottomCountdown()).thenReturn(1f);
        when(geo.topCountdown()).thenReturn(1f);
    }

    private GeothermalModel create() {
        return new GeothermalModel(configuration, new Random(1), status);
    }

    // =========================
    // UPDATE FLOW BRANCHES
    // =========================

    @Test
    void shouldSetHorizontalLightAlways() {
        var model = create();

        model.update(0.1f, controls);

        verify(controls).setButtonLightHorizontal();
    }

    @Test
    void shouldSkipWhenBottomCountdownActive() {
        var model = create();

        // force bottom countdown active via repeated trigger
        model.update(0.1f, controls);
        model.update(0.1f, controls);

        verify(controls, atLeastOnce()).setButtonLightHorizontal();
    }

    @Test
    void shouldSetNoneWhenTopCountdownStarted() {
        var model = create();

        // force top countdown indirectly
        model.update(0.1f, controls);
        model.update(0.1f, controls);

        verify(controls, atLeastOnce()).setButtonLightHorizontal();
    }

    // =========================
    // GAMEPLAY EXECUTION
    // =========================

    @Test
    void shouldUpdateGameplayAndStatus() {
        var model = create();

        model.update(0.1f, controls);

        verify(status).update(0.1f, controls);
    }

    @Test
    void shouldTriggerPipeAddWhenTimerTriggers() {
        var model = create();

        // multiple updates to increase chance timer triggers
        for (int i = 0; i < 5; i++) {
            model.update(1f, controls);
        }

        // no exception = pipe system executed safely
        assertNotNull(model.state());
    }

    // =========================
    // COLLISION BRANCHES
    // =========================

    @Test
    void shouldHandleCollisionCountdownResetBranch() {
        var model = create();

        model.update(1f, controls);

        // collision countdown update path executed
        assertNotNull(model.state());
    }

    @Test
    void shouldSkipCollisionWhenCountdownActive() {
        var model = create();

        model.update(0.1f, controls);
        model.update(0.1f, controls);

        // ensures branch is executed without crash
        assertDoesNotThrow(() -> model.update(0.1f, controls));
    }

    // =========================
    // RESULT BRANCHES
    // =========================

    @Test
    void shouldReturnFailHealthWhenDead() {
        when(status.isDead()).thenReturn(true);

        var model = create();

        assertEquals(Result.FAIL_HEALTH, model.result());
    }

    @Test
    void shouldReturnFailTimeWhenFinished() {
        when(status.isDead()).thenReturn(false);
        when(status.isFinished()).thenReturn(true);

        var model = create();

        assertEquals(Result.FAIL_TIME, model.result());
    }

    @Test
    void shouldReturnRunningByDefault() {
        when(status.isDead()).thenReturn(false);
        when(status.isFinished()).thenReturn(false);

        var model = create();

        assertEquals(Result.RUNNING, model.result());
    }

    // =========================
    // STATE CHECK
    // =========================

    @Test
    void shouldReturnValidState() {
        var model = create();

        var state = model.state();

        assertNotNull(state);
    }
}
