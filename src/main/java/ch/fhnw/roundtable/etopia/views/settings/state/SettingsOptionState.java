package ch.fhnw.roundtable.etopia.views.settings.state;

public record SettingsOptionState(
        float x,
        float y,
        float maxWidth,
        String name,
        boolean selected
) {
}
