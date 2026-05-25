package ch.fhnw.roundtable.etopia.views.biogas.model;

import ch.fhnw.roundtable.etopia.Result;
import ch.fhnw.roundtable.etopia.configuration.Biogas;
import ch.fhnw.roundtable.etopia.configuration.Configuration;
import ch.fhnw.roundtable.etopia.input.ButtonType;
import ch.fhnw.roundtable.etopia.input.Controls;
import ch.fhnw.roundtable.etopia.time.Timer;
import ch.fhnw.roundtable.etopia.views.biogas.state.BiogasCursorState;
import ch.fhnw.roundtable.etopia.views.biogas.state.BiogasState;
import ch.fhnw.roundtable.etopia.views.biogas.state.BiogasTrashState;
import ch.fhnw.roundtable.etopia.views.status.model.StatusModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BiogasModelTest {

    private static final float DELTA_TIME = 1f;
    private static final float ENERGY_GAIN = 0.05f;

    private static final int MAP_WIDTH = 1000;
    private static final int MAP_HEIGHT = 500;

    private Random random;
    private StatusModel status;
    private Cursor cursor;
    private Timer shiftTimer;
    private Controls controls;

    private BiogasModel model;

    @BeforeEach
    void setUp() {
        Configuration configuration = mock(Configuration.class);
        Biogas biogasConfig = mock(Biogas.class);
        random = mock(Random.class);
        status = mock(StatusModel.class);
        cursor = mock(Cursor.class);
        shiftTimer = mock(Timer.class);
        controls = mock(Controls.class);

        when(configuration.biogas()).thenReturn(biogasConfig);
        when(biogasConfig.shiftTimer()).thenReturn(1f);
        when(biogasConfig.mapWidth()).thenReturn(MAP_WIDTH);
        when(biogasConfig.mapHeight()).thenReturn(MAP_HEIGHT);

        model = new BiogasModel(configuration, random, status, cursor, shiftTimer);
    }

    // ---------------------------
    // UPDATE FLOW
    // ---------------------------

    @Test
    void shouldSetPlayableLightOnUpdate() {
        model.update(DELTA_TIME, controls);

        verify(controls).setButtonLightPlayable();
    }

    @Test
    void shouldUpdateStatus() {
        model.update(DELTA_TIME, controls);

        verify(status).update(DELTA_TIME, controls);
    }

    // ---------------------------
    // DROPPED TRASH BEHAVIOR
    // ---------------------------

    @Test
    void shouldAddEnergyWhenBiodegradableTrashDropped() {
        Trash trash = mock(Trash.class);

        when(trash.isDropped()).thenReturn(true);
        when(trash.isBiodegradable()).thenReturn(true);

        model.getTrashes().add(trash);

        model.update(DELTA_TIME, controls);

        verify(status).addEnergy(ENERGY_GAIN);
    }

    @Test
    void shouldRemoveHealthWhenNonBiodegradableTrashDropped() {
        Trash trash = mock(Trash.class);

        when(trash.isDropped()).thenReturn(true);
        when(trash.isBiodegradable()).thenReturn(false);

        model.getTrashes().add(trash);

        model.update(DELTA_TIME, controls);

        verify(status).removeHealth();
    }

    @Test
    void shouldRemoveDroppedTrash() {
        Trash trash = mock(Trash.class);

        when(trash.isDropped()).thenReturn(true);
        when(trash.isBiodegradable()).thenReturn(true);

        model.getTrashes().add(trash);

        model.update(DELTA_TIME, controls);

        assertEquals(0, model.getTrashes().size());
    }

    // ---------------------------
    // CURSOR INTERACTION
    // ---------------------------

    @Test
    void shouldRemoveTrashOnSelectIfIntersectsCursor() {
        Trash trash = mock(Trash.class);

        when(trash.intersects(cursor)).thenReturn(true);

        model.getTrashes().add(trash);

        when(controls.isButtonJustPressed(ButtonType.SELECT))
                .thenReturn(true);

        model.update(DELTA_TIME, controls);

        assertEquals(0, model.getTrashes().size());
    }

    @Test
    void shouldNotRemoveTrashWhenSelectNotPressed() {
        Trash trash = mock(Trash.class);

        when(trash.intersects(cursor)).thenReturn(true);

        model.getTrashes().add(trash);

        when(controls.isButtonJustPressed(ButtonType.SELECT))
                .thenReturn(false);

        model.update(DELTA_TIME, controls);

        assertEquals(1, model.getTrashes().size());
    }

    // ---------------------------
    // STATE MAPPING
    // ---------------------------

    @Test
    void shouldReturnCorrectState() {
        Trash trash = mock(Trash.class);
        BiogasTrashState trashState = mock(BiogasTrashState.class);
        BiogasCursorState cursorState = mock(BiogasCursorState.class);

        when(trash.state()).thenReturn(trashState);
        when(cursor.state()).thenReturn(cursorState);

        model.getTrashes().add(trash);

        BiogasState state = model.state();

        assertEquals(1, state.trashes().size());
        assertEquals(trashState, state.trashes().getFirst());
        assertEquals(cursorState, state.cursor());
    }

    // ---------------------------
    // RESULT LOGIC
    // ---------------------------

    @Test
    void shouldReturnFailHealthWhenDead() {
        when(status.isDead()).thenReturn(true);

        assertEquals(Result.FAIL_HEALTH, model.result());
    }

    @Test
    void shouldReturnFailTimeWhenFinished() {
        when(status.isDead()).thenReturn(false);
        when(status.isFinished()).thenReturn(true);

        assertEquals(Result.FAIL_TIME, model.result());
    }

    @Test
    void shouldReturnSuccessWhenPowered() {
        when(status.isPowered()).thenReturn(true);

        assertEquals(Result.SUCCESS, model.result());
    }

    @Test
    void shouldReturnRunningOtherwise() {
        assertEquals(Result.RUNNING, model.result());
    }

    @Test
    void shouldPrioritizeFailHealthOverEverything() {
        when(status.isDead()).thenReturn(true);
        when(status.isFinished()).thenReturn(true);
        when(status.isPowered()).thenReturn(true);

        assertEquals(Result.FAIL_HEALTH, model.result());
    }
}
