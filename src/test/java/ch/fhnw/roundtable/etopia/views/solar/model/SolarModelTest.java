package ch.fhnw.roundtable.etopia.views.solar.model;

import ch.fhnw.roundtable.etopia.Result;
import ch.fhnw.roundtable.etopia.input.Controls;
import ch.fhnw.roundtable.etopia.shapes.Vector;
import ch.fhnw.roundtable.etopia.time.Timer;
import ch.fhnw.roundtable.etopia.views.status.model.StatusModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SolarModelTest {

    private static final float DELTA_TIME = 1f;
    private static final float ENERGY_GAIN = 0.05f;

    private StatusModel status;
    private Sun sun;
    private Panel panel;
    private Timer timer;
    private Controls controls;

    private SolarModel model;

    @BeforeEach
    void setUp() {
        status = mock(StatusModel.class);
        sun = mock(Sun.class);
        panel = mock(Panel.class);
        timer = mock(Timer.class);
        controls = mock(Controls.class);

        model = new SolarModel(
                status,
                sun,
                panel,
                timer
        );
    }

    @Test
    void shouldSetLightHorizontalControlOnUpdate() {
        model.update(DELTA_TIME, controls);

        verify(controls).setButtonLightHorizontal();
    }

    @Test
    void shouldUpdateAllDependencies() {
        model.update(DELTA_TIME, controls);

        verify(status).update(DELTA_TIME, controls);
        verify(sun).update(DELTA_TIME, controls);
        verify(panel).update(DELTA_TIME, controls);
    }

    @Test
    void shouldNotAddEnergyWhenTimerDoesNotTrigger() {
        when(timer.triggered(DELTA_TIME)).thenReturn(false);

        model.update(DELTA_TIME, controls);

        verify(status, never()).addEnergy(anyFloat());
    }

    @Test
    void shouldAddMaximumEnergyWhenPerfectlyAligned() {
        when(timer.triggered(DELTA_TIME)).thenReturn(true);

        Vector center = new Vector(0f, 0f);

        when(panel.getCenter()).thenReturn(center);
        when(sun.getCenter()).thenReturn(new Vector(0f, 1f));

        when(panel.getRotation()).thenReturn(0f);

        model.update(DELTA_TIME, controls);

        verify(status).addEnergy(ENERGY_GAIN);
    }

    @Test
    void shouldAddNoEnergyWhenEfficiencyIsZero() {
        when(timer.triggered(DELTA_TIME)).thenReturn(true);

        when(panel.getCenter()).thenReturn(new Vector(0f, 0f));
        when(sun.getCenter()).thenReturn(new Vector(0f, -1f));

        when(panel.getRotation()).thenReturn(0f);

        model.update(DELTA_TIME, controls);

        verify(status).addEnergy(0f);
    }

    @Test
    void shouldReturnFailTimeWhenFinished() {
        when(status.isFinished()).thenReturn(true);

        assertEquals(Result.FAIL_TIME, model.result());
    }

    @Test
    void shouldReturnSuccessWhenPowered() {
        when(status.isFinished()).thenReturn(false);
        when(status.isPowered()).thenReturn(true);

        assertEquals(Result.SUCCESS, model.result());
    }

    @Test
    void shouldReturnRunningWhenNotFinishedOrPowered() {
        when(status.isFinished()).thenReturn(false);
        when(status.isPowered()).thenReturn(false);

        assertEquals(Result.RUNNING, model.result());
    }

    @Test
    void shouldPrioritizeFailTimeOverSuccess() {
        when(status.isFinished()).thenReturn(true);
        when(status.isPowered()).thenReturn(true);

        assertEquals(Result.FAIL_TIME, model.result());
    }
}
