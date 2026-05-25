package ch.fhnw.roundtable.etopia.views.map.model;

import ch.fhnw.roundtable.etopia.Result;
import ch.fhnw.roundtable.etopia.View;
import ch.fhnw.roundtable.etopia.configuration.Configuration;
import ch.fhnw.roundtable.etopia.configuration.Map;
import ch.fhnw.roundtable.etopia.configuration.State;
import ch.fhnw.roundtable.etopia.input.ButtonType;
import ch.fhnw.roundtable.etopia.input.Controls;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MapModelTest {

    private Configuration config;
    private Controls controls;

    @BeforeEach
    void setUp() {
        config = mock(Configuration.class);
        controls = mock(Controls.class);

        var state = mock(State.class);
        var map = mock(Map.class);

        when(config.state()).thenReturn(state);
        when(config.map()).thenReturn(map);
        when(map.humanWidth()).thenReturn(10f);
        when(map.humanHeight()).thenReturn(20f);
    }

    // =========================
    // UPDATE / NAVIGATION
    // =========================

    @Test
    void shouldSetPlayableLightEveryUpdate() {
        var model = new MapModel(config);

        model.update(0.1f, controls);

        verify(controls).setButtonLightPlayable();
    }

    @Test
    void shouldMoveHoveredUp() {
        var model = new MapModel(config);

        when(controls.isButtonJustPressed(ButtonType.UP)).thenReturn(true);

        model.update(0.1f, controls);

        assertNotNull(model.state());
    }

    @Test
    void shouldMoveHoveredDown() {
        var model = new MapModel(config);

        when(controls.isButtonJustPressed(ButtonType.DOWN)).thenReturn(true);

        model.update(0.1f, controls);

        assertNotNull(model.state());
    }

    @Test
    void shouldMoveHoveredLeftRightAndStayValid() {
        var model = new MapModel(config);

        when(controls.isButtonJustPressed(ButtonType.LEFT)).thenReturn(true);
        when(controls.isButtonJustPressed(ButtonType.RIGHT)).thenReturn(true);

        model.update(0.1f, controls);

        assertNotNull(model.state());
    }

    @Test
    void shouldSetSelectedWhenSelectPressed() {
        var model = new MapModel(config);

        when(controls.isButtonJustPressed(ButtonType.SELECT)).thenReturn(true);

        model.update(0.1f, controls);

        assertEquals(Result.SUCCESS, model.result());
    }

    @Test
    void shouldRemainRunningWhenNotSelected() {
        var model = new MapModel(config);

        model.update(0.1f, controls);

        assertEquals(Result.RUNNING, model.result());
    }

    // =========================
    // NEXT VIEW
    // =========================

    @Test
    void shouldReturnNextViewSupplier() {
        var model = new MapModel(config);

        Supplier<View> supplier = model.next();

        assertNotNull(supplier);
    }

    // =========================
    // STATE
    // =========================

    @Test
    void shouldReturnMapStateWithLocations() {
        var model = new MapModel(config);

        var state = model.state();

        assertEquals(7, state.locations().size());
        assertNotNull(state.human());
    }
}
