package ch.fhnw.roundtable.etopia.views.map.model;

import ch.fhnw.roundtable.etopia.View;
import ch.fhnw.roundtable.etopia.configuration.Configuration;
import ch.fhnw.roundtable.etopia.configuration.State;
import org.junit.jupiter.api.Test;

import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class BuildingTest {


    @Test
    void shouldReturnSameBuildingWhenNoTransition() {
        assertEquals(Building.SETTINGS, Building.SETTINGS.next(Direction.UP));
    }

    @Test
    void shouldTransitionBetweenBuildings() {
        assertEquals(Building.WIND, Building.SETTINGS.next(Direction.DOWN));
    }

    @Test
    void shouldReturnFalseForSettingsSuccess() {
        var config = mock(Configuration.class);

        assertFalse(Building.SETTINGS.success(config));
    }

    @Test
    void shouldReturnSuccessFromConfigState() {
        var config = mock(Configuration.class);
        var state = mock(State.class);

        when(config.state()).thenReturn(state);
        when(state.isSolarSuccess()).thenReturn(true);

        assertTrue(Building.SOLAR.success(config));
    }

    @Test
    void shouldReturnViewSupplier() {
        var config = mock(Configuration.class);

        Supplier<View> view = Building.SOLAR.view(config);

        assertNotNull(view);
    }
}
