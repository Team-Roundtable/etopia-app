package ch.fhnw.roundtable.etopia.views.information.model;

import ch.fhnw.roundtable.etopia.Result;
import ch.fhnw.roundtable.etopia.configuration.Configuration;
import ch.fhnw.roundtable.etopia.configuration.Information;
import ch.fhnw.roundtable.etopia.input.ButtonType;
import ch.fhnw.roundtable.etopia.input.Controls;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class InformationModelTest {

    private Configuration config;
    private Controls controls;

    @BeforeEach
    void setUp() {
        config = mock(Configuration.class);
        controls = mock(Controls.class);
        var infoConfig = mock(Information.class);

        when(config.information()).thenReturn(infoConfig);

        when(infoConfig.disabledDuration()).thenReturn(1f);
        when(infoConfig.offsetX()).thenReturn(0f);
        when(infoConfig.titleOffsetY()).thenReturn(0f);
        when(infoConfig.descriptionOffsetY()).thenReturn(0f);
        when(infoConfig.noteOffsetY()).thenReturn(0f);
        when(infoConfig.maxWidth()).thenReturn(100f);

        when(infoConfig.videoOffsetX()).thenReturn(1f);
        when(infoConfig.videoOffsetY()).thenReturn(2f);
        when(infoConfig.videoWidth()).thenReturn(3f);
        when(infoConfig.videoHeight()).thenReturn(4f);

        when(config.getText(anyString())).thenReturn("text");
    }

    // =========================
    // INITIAL STATE
    // =========================

    @Test
    void shouldStartDisabled() {
        var model = new InformationModel(config, InformationType.INFORMATION);

        var state = model.state();

        assertTrue(state.disabled());
        assertEquals(InformationType.INFORMATION, state.type());
    }

    @Test
    void shouldReturnRunningInitially() {
        var model = new InformationModel(config, InformationType.INFORMATION);

        assertEquals(Result.RUNNING, model.result());
    }

    // =========================
    // TIMER ENABLE BRANCH
    // =========================

    @Test
    void shouldEnableAfterTimerTriggers() {
        var model = new InformationModel(config, InformationType.INFORMATION);

        model.update(10f, controls);

        // SELECT becomes available after timer triggers
        verify(controls).setButtonLight(ButtonType.SELECT, true);
    }

    // =========================
    // DISABLED STATE BRANCH
    // =========================

    @Test
    void shouldSetBackLightOnlyWhenDisabledAndEscapable() {
        var model = new InformationModel(config, InformationType.INFORMATION);

        model.update(0.1f, controls);

        verify(controls).setButtonLightNone();
        verify(controls).setButtonLight(ButtonType.BACK, true);
    }

    @Test
    void shouldNotAllowBackExitWhenNotEscapable() {
        var model = new InformationModel(config, InformationType.BIOGAS_SUCCESS);

        when(controls.isButtonJustPressed(ButtonType.BACK)).thenReturn(true);

        model.update(0.1f, controls);

        assertEquals(Result.RUNNING, model.result());
    }

    // =========================
    // EXIT BRANCH
    // =========================

    @Test
    void shouldExitOnBackWhenEscapable() {
        var model = new InformationModel(config, InformationType.INFORMATION);

        when(controls.isButtonJustPressed(ButtonType.BACK)).thenReturn(true);

        model.update(0.1f, controls);

        assertEquals(Result.FAIL_TIME, model.result());
    }

    // =========================
    // CHANGE (SELECT) BRANCH
    // =========================

    @Test
    void shouldNotChangeWhileDisabled() {
        var model = new InformationModel(config, InformationType.INFORMATION);

        when(controls.isButtonJustPressed(ButtonType.SELECT)).thenReturn(true);

        model.update(0.1f, controls);

        assertEquals(Result.RUNNING, model.result());
    }

    @Test
    void shouldChangeWhenEnabledAndSelectPressed() {
        var model = new InformationModel(config, InformationType.INFORMATION);

        // force timer to enable
        model.update(10f, controls);

        when(controls.isButtonJustPressed(ButtonType.SELECT)).thenReturn(true);

        model.update(0.1f, controls);

        assertEquals(Result.SUCCESS, model.result());
    }

    // =========================
    // STATE STRUCTURE
    // =========================

    @Test
    void shouldReturnFullState() {
        var model = new InformationModel(config, InformationType.INFORMATION);

        var state = model.state();

        assertNotNull(state.title());
        assertNotNull(state.description());
        assertNotNull(state.note());
        assertNotNull(state.video());
    }
}
