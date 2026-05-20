package ch.fhnw.roundtable.etopia.views.settings.model;

import ch.fhnw.roundtable.etopia.Model;
import ch.fhnw.roundtable.etopia.Result;
import ch.fhnw.roundtable.etopia.configuration.Configuration;
import ch.fhnw.roundtable.etopia.input.ButtonType;
import ch.fhnw.roundtable.etopia.input.Controls;
import ch.fhnw.roundtable.etopia.views.settings.state.SettingsState;
import ch.fhnw.roundtable.etopia.views.settings.state.SettingsTitleState;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SettingsModel implements Model<SettingsState> {

    private final Configuration configuration;
    private final Options options;
    private boolean exit = false;

    public SettingsModel(Configuration configuration) {
        this.configuration = configuration;

        this.options = new Options(configuration, createOptions());
    }

    @Override
    public void update(float delta, Controls controls) {
        controls.setButtonLightVertical();
        controls.setButtonLight(ButtonType.SELECT, true);
        controls.setButtonLight(ButtonType.BACK, true);

        options.update(delta, controls);

        if (controls.isButtonJustPressed(ButtonType.BACK)) {
            setExit();
        }
    }

    @Override
    public SettingsState state() {
        return new SettingsState(
                titleState(),
                options.state()
        );
    }

    private List<Option> createOptions() {
        List<Option> list = new ArrayList<>();

        List<String> availableLanguages = configuration.languages().getAvailable();
        for (String language : availableLanguages) {
            list.add(new Option(capitalizeFirst(language),
                    () -> configuration.languages().setCurrent(availableLanguages.indexOf(language))));
        }

        list.add(new Option(configuration.getText("settings.exit"), this::setExit));

        return list;
    }

    @Override
    public Result result() {
        if (exit) {
            return Result.SUCCESS;
        }

        return Result.RUNNING;
    }

    private void setExit() {
        exit = true;
    }

    private SettingsTitleState titleState() {
        return new SettingsTitleState(
                configuration.settings().offsetX(),
                configuration.settings().titleOffsetY(),
                configuration.settings().maxWidth(),
                configuration.getText("settings.title"));
    }

    private String capitalizeFirst(String value) {
        return value.substring(0, 1).toUpperCase(Locale.ENGLISH) + value.substring(1);
    }
}
