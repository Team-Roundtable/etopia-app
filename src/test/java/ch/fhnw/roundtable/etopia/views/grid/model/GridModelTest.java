package ch.fhnw.roundtable.etopia.views.grid.model;

import ch.fhnw.roundtable.etopia.Result;
import ch.fhnw.roundtable.etopia.configuration.Configuration;
import ch.fhnw.roundtable.etopia.configuration.Grid;
import ch.fhnw.roundtable.etopia.input.ButtonType;
import ch.fhnw.roundtable.etopia.input.Controls;
import ch.fhnw.roundtable.etopia.views.status.model.StatusModel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GridModelTest {

    @Test
    void shouldReturnFailWhenStatusFinished() {
        var configuration = mock(Configuration.class);
        var status = mock(StatusModel.class);

        when(status.isFinished()).thenReturn(true);

        var grid = mock(Grid.class);
        when(configuration.grid()).thenReturn(grid);

        when(grid.mapWidth()).thenReturn(5);
        when(grid.mapHeight()).thenReturn(5);
        when(grid.tileSide()).thenReturn(10f);
        when(grid.offsetY()).thenReturn(0f);
        when(grid.finishCountdown()).thenReturn(1f);

        var model = new GridModel(configuration, status);

        assertEquals(Result.FAIL_TIME, model.result());
    }

    @Test
    void shouldUpdateStatusAndCursorWhenCountdownNotStarted() {
        var configuration = mock(Configuration.class);
        var status = mock(StatusModel.class);
        var controls = mock(Controls.class);

        var grid = mock(Grid.class);

        when(configuration.grid()).thenReturn(grid);
        when(grid.mapWidth()).thenReturn(5);
        when(grid.mapHeight()).thenReturn(5);
        when(grid.tileSide()).thenReturn(10f);
        when(grid.offsetY()).thenReturn(0f);
        when(grid.finishCountdown()).thenReturn(10f);

        var model = new GridModel(configuration, status);

        model.update(0.1f, controls);

        verify(status).update(0.1f, controls);
        verify(controls).setButtonLightPlayable();
    }

    @Test
    void shouldReturnRunningInitially() {
        var configuration = mock(Configuration.class);
        var status = mock(StatusModel.class);

        var grid = mock(Grid.class);

        when(configuration.grid()).thenReturn(grid);
        when(grid.mapWidth()).thenReturn(5);
        when(grid.mapHeight()).thenReturn(5);
        when(grid.tileSide()).thenReturn(10f);
        when(grid.offsetY()).thenReturn(0f);
        when(grid.finishCountdown()).thenReturn(10f);

        var model = new GridModel(configuration, status);

        assertEquals(Result.RUNNING, model.result());
    }

    @Test
    void shouldHandleSelectButtonPress() {
        var configuration = mock(Configuration.class);
        var status = mock(StatusModel.class);
        var controls = mock(Controls.class);

        var grid = mock(Grid.class);

        when(configuration.grid()).thenReturn(grid);
        when(grid.mapWidth()).thenReturn(5);
        when(grid.mapHeight()).thenReturn(5);
        when(grid.tileSide()).thenReturn(10f);
        when(grid.offsetY()).thenReturn(0f);
        when(grid.finishCountdown()).thenReturn(10f);

        when(controls.isButtonJustPressed(ButtonType.SELECT)).thenReturn(true);

        var model = new GridModel(configuration, status);

        assertDoesNotThrow(() -> model.update(0.1f, controls));

        verify(controls).isButtonJustPressed(ButtonType.SELECT);
    }

    @Test
    void shouldReturnSuccessWhenFinishCountdownFinished() {
        var configuration = mock(Configuration.class);
        var status = mock(StatusModel.class);
        var controls = mock(Controls.class);

        var grid = mock(Grid.class);

        when(configuration.grid()).thenReturn(grid);
        when(grid.mapWidth()).thenReturn(5);
        when(grid.mapHeight()).thenReturn(5);
        when(grid.tileSide()).thenReturn(10f);
        when(grid.offsetY()).thenReturn(0f);

        // instantly finished
        when(grid.finishCountdown()).thenReturn(0f);

        var model = new GridModel(configuration, status);

        model.update(0.1f, controls);

        assertEquals(Result.SUCCESS, model.result());
    }
}
