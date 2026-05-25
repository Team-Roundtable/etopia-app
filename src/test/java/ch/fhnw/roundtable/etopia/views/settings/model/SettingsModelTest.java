package ch.fhnw.roundtable.etopia.views.settings.model;

import ch.fhnw.roundtable.etopia.Result;
import ch.fhnw.roundtable.etopia.configuration.Configuration;
import ch.fhnw.roundtable.etopia.configuration.Languages;
import ch.fhnw.roundtable.etopia.configuration.Settings;
import ch.fhnw.roundtable.etopia.input.ButtonType;
import ch.fhnw.roundtable.etopia.input.Controls;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class SettingsModelTest {

    private Configuration config;
    private Controls controls;

    @BeforeEach
    void setUp() {
        config = mock(Configuration.class);
        controls = mock(Controls.class);

        var lang = mock(Languages.class);
        var settings = mock(Settings.class);

        when(config.languages()).thenReturn(lang);
        when(config.settings()).thenReturn(settings);

        when(lang.getAvailable()).thenReturn(List.of("en", "de"));
        when(config.getText(anyString())).thenReturn("text");

        when(settings.offsetX()).thenReturn(0f);
        when(settings.titleOffsetY()).thenReturn(0f);
        when(settings.maxWidth()).thenReturn(100f);
        when(settings.bodyOffsetY()).thenReturn(0f);
        when(settings.elementOffset()).thenReturn(10f);
    }

    @Test
    void shouldInitializeWithoutExit() {
        var model = new SettingsModel(config);

        assertEquals(Result.RUNNING, model.result());
    }

    @Test
    void shouldExitWhenBackPressed() {
        var model = new SettingsModel(config);

        when(controls.isButtonJustPressed(ButtonType.BACK)).thenReturn(true);

        model.update(0.1f, controls);

        assertEquals(Result.SUCCESS, model.result());
    }

    @Test
    void shouldNotExitWhenBackNotPressed() {
        var model = new SettingsModel(config);

        model.update(0.1f, controls);

        assertEquals(Result.RUNNING, model.result());
    }

    @Test
    void shouldCallOptionCallbackOnSelectThroughOptions() {
        var lang = mock(Languages.class);
        when(config.languages()).thenReturn(lang);
        when(lang.getAvailable()).thenReturn(List.of("en"));

        var model = new SettingsModel(config);

        when(controls.isButtonJustPressed(ButtonType.SELECT)).thenReturn(true);

        model.update(0.1f, controls);

        verify(lang).setCurrent(0);
    }
}
