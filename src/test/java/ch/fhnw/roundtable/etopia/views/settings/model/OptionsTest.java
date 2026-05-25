package ch.fhnw.roundtable.etopia.views.settings.model;

import ch.fhnw.roundtable.etopia.configuration.Configuration;
import ch.fhnw.roundtable.etopia.configuration.Settings;
import ch.fhnw.roundtable.etopia.input.ButtonType;
import ch.fhnw.roundtable.etopia.input.Controls;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OptionsTest {

    private Configuration config;
    private Controls controls;

    @BeforeEach
    void setUp() {
        config = mock(Configuration.class);
        controls = mock(Controls.class);

        var settings = mock(Settings.class);

        when(config.settings()).thenReturn(settings);

        when(settings.offsetX()).thenReturn(0f);
        when(settings.bodyOffsetY()).thenReturn(0f);
        when(settings.elementOffset()).thenReturn(10f);
        when(settings.maxWidth()).thenReturn(100f);
    }

    @Test
    void shouldMoveSelectionDown() {
        var option = mock(Runnable.class);

        var options = new Options(config, List.of(
                new Option("A", null),
                new Option("B", option)
        ));

        when(controls.isButtonJustPressed(ButtonType.DOWN)).thenReturn(true);

        options.update(0.1f, controls);

        options.update(0.1f, controls);

        // second option selected now
        when(controls.isButtonJustPressed(ButtonType.SELECT)).thenReturn(true);

        options.update(0.1f, controls);

        verify(option, times(1)).run();
    }

    @Test
    void shouldMoveSelectionUpAndClamp() {
        var option = mock(Runnable.class);

        var options = new Options(config, List.of(
                new Option("A", option),
                new Option("B", option)
        ));

        when(controls.isButtonJustPressed(ButtonType.UP)).thenReturn(true);

        options.update(0.1f, controls);
        options.update(0.1f, controls);

        // should still be at 0, no crash
        assertDoesNotThrow(options::state);
    }

    @Test
    void shouldExecuteSelectedOption() {
        var runnable1 = mock(Runnable.class);
        var runnable2 = mock(Runnable.class);

        var options = new Options(config, List.of(
                new Option("A", runnable1),
                new Option("B", runnable2)
        ));

        when(controls.isButtonJustPressed(ButtonType.SELECT)).thenReturn(true);

        options.update(0.1f, controls);

        verify(runnable1).run();
        verify(runnable2, never()).run();
    }

    @Test
    void shouldGenerateStateForAllOptions() {
        var options = new Options(config, List.of(
                new Option("A", () -> {}),
                new Option("B", () -> {})
        ));

        var state = options.state();

        assertEquals(2, state.size());
        assertTrue(state.get(0).selected() || state.get(1).selected());
    }
}
