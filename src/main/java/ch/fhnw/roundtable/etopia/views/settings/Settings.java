package ch.fhnw.roundtable.etopia.views.settings;

import ch.fhnw.roundtable.etopia.Transition;
import ch.fhnw.roundtable.etopia.View;
import ch.fhnw.roundtable.etopia.configuration.Configuration;
import ch.fhnw.roundtable.etopia.input.Controls;
import ch.fhnw.roundtable.etopia.rendering.Assets;
import ch.fhnw.roundtable.etopia.rendering.Renderer;
import ch.fhnw.roundtable.etopia.views.map.Map;
import ch.fhnw.roundtable.etopia.views.settings.model.SettingsModel;
import ch.fhnw.roundtable.etopia.views.settings.ui.SettingsAsset;
import ch.fhnw.roundtable.etopia.views.settings.ui.SettingsUI;

public class Settings implements View {

    private final Configuration configuration;
    private final SettingsModel settingsModel;
    private final SettingsUI settingsUI;

    public Settings(Configuration configuration) {
        this.configuration = configuration;
        settingsModel = new SettingsModel(configuration);
        settingsUI = new SettingsUI(new Assets<>(SettingsAsset.class));
    }

    @Override
    public void update(float delta, Controls controls) {
        settingsModel.update(delta, controls);
    }

    @Override
    public void render(Renderer renderer) {
        settingsUI.render(settingsModel.state(), renderer);
    }

    @Override
    public void dispose() {
        settingsUI.dispose();
    }

    @Override
    public Transition transition() {
        return switch (settingsModel.result()) {
            case RUNNING, FAIL_TIME, FAIL_HEALTH -> Transition.none();
            case SUCCESS -> Transition.change(() -> new Map(configuration));
        };
    }
}
