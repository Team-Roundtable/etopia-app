package ch.fhnw.roundtable.etopia.views.settings.state;

import java.util.List;

public record SettingsState(
        SettingsTitleState title,
        List<SettingsOptionState> options
) {
}
