package ch.fhnw.roundtable.etopia.views.status.model;

import ch.fhnw.roundtable.etopia.configuration.Configuration;
import ch.fhnw.roundtable.etopia.configuration.Status;
import ch.fhnw.roundtable.etopia.input.Controls;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class StatusModelTest {

    private Configuration config;

    @BeforeEach
    void setUp() {
        config = mock(Configuration.class);

        var statusConfig = mock(Status.class);
        when(config.status()).thenReturn(statusConfig);
        when(statusConfig.totalHealth()).thenReturn(5);
    }

    // =========================
    // INITIAL STATE
    // =========================

    @Test
    void shouldInitializeCorrectly() {
        var model = new StatusModel(config, 100f);

        var state = model.state();

        assertEquals(1f, state.time());
        assertEquals(0f, state.energy());
        assertEquals(1f, state.health());
    }

    // =========================
    // ENERGY BRANCHES
    // =========================

    @Test
    void shouldClampEnergyToMaxOne() {
        var model = new StatusModel(config, 100f);

        model.addEnergy(2f);

        var state = model.state();

        assertEquals(1f, state.energy());
    }

    @Test
    void shouldClampEnergyToMinZero() {
        var model = new StatusModel(config, 100f);

        model.setEnergy(-5f);

        var state = model.state();

        assertEquals(0f, state.energy());
    }

    @Test
    void shouldAccumulateEnergy() {
        var model = new StatusModel(config, 100f);

        model.addEnergy(0.4f);
        model.addEnergy(0.4f);

        var state = model.state();

        assertEquals(0.8f, state.energy(), 0.0001);
    }

    @Test
    void shouldDetectPoweredState() {
        var model = new StatusModel(config, 100f);

        model.setEnergy(1f);

        assertTrue(model.isPowered());
    }

    // =========================
    // HEALTH BRANCHES
    // =========================

    @Test
    void shouldReduceHealth() {
        var model = new StatusModel(config, 100f);

        model.removeHealth();
        model.removeHealth();

        var state = model.state();

        assertTrue(state.health() < 1f);
    }

    @Test
    void shouldDetectDeadWhenHealthZero() {
        var model = new StatusModel(config, 100f);

        for (int i = 0; i < 10; i++) {
            model.removeHealth();
        }

        assertTrue(model.isDead());
    }

    // =========================
    // TIME BRANCHES
    // =========================

    @Test
    void shouldDecreaseTimeOnUpdate() {
        var model = new StatusModel(config, 10f);

        model.update(2f, mock(Controls.class));

        var state = model.state();

        assertTrue(state.time() < 1f);
    }

    @Test
    void shouldDetectFinishedWhenTimeZero() {
        var model = new StatusModel(config, 1f);

        model.update(2f, mock(Controls.class));

        assertTrue(model.isFinished());
    }

    // =========================
    // STATE NORMALIZATION BRANCHES
    // =========================

    @Test
    void shouldClampStateValuesCorrectly() {
        var model = new StatusModel(config, 100f);

        model.setEnergy(2f);
        model.removeHealth();
        model.update(150f, mock(Controls.class));

        var state = model.state();

        assertTrue(state.energy() <= 1f);
        assertTrue(state.health() <= 1f);
        assertTrue(state.time() <= 1f);
    }
}
