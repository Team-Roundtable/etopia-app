package ch.fhnw.roundtable.etopia.views.map.model;

import ch.fhnw.roundtable.etopia.configuration.Configuration;
import ch.fhnw.roundtable.etopia.configuration.Map;
import ch.fhnw.roundtable.etopia.configuration.State;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class LocationTest {

    @Test
    void shouldReturnCorrectState() {
        var config = mock(Configuration.class);
        var map = mock(Map.class);
        var state = mock(State.class);

        when(config.map()).thenReturn(map);
        when(config.state()).thenReturn(state);

        when(map.settingsSide()).thenReturn(10f);
        when(map.buildingSide()).thenReturn(20f);

        when(state.isSolarSuccess()).thenReturn(true);

        var location = new Location(config, Building.SOLAR, 5f, 6f);

        var s = location.state();

        assertEquals(5f, s.x());
        assertEquals(6f, s.y());
        assertEquals(Building.SOLAR, s.building());
        assertTrue(location.getBuilding() == Building.SOLAR);
    }

    @Test
    void shouldUseSettingsSideForSettingsBuilding() {
        var config = mock(Configuration.class);
        var map = mock(Map.class);

        when(config.map()).thenReturn(map);
        when(config.state()).thenReturn(mock(State.class));

        when(map.settingsSide()).thenReturn(99f);
        when(map.buildingSide()).thenReturn(10f);

        var location = new Location(config, Building.SETTINGS, 0, 0);

        assertEquals(99f, location.state().width());
    }

    @Test
    void shouldUseBuildingSideForNormalBuilding() {
        var config = mock(Configuration.class);
        var map = mock(Map.class);

        when(config.map()).thenReturn(map);
        when(config.state()).thenReturn(mock(State.class));

        when(map.settingsSide()).thenReturn(99f);
        when(map.buildingSide()).thenReturn(10f);

        var location = new Location(config, Building.SOLAR, 0, 0);

        assertEquals(10f, location.state().width());
    }
}
