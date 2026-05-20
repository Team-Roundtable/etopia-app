package ch.fhnw.roundtable.etopia.views.settings.model;

import ch.fhnw.roundtable.etopia.Renderable;
import ch.fhnw.roundtable.etopia.Updateable;
import ch.fhnw.roundtable.etopia.configuration.Configuration;
import ch.fhnw.roundtable.etopia.input.ButtonType;
import ch.fhnw.roundtable.etopia.input.Controls;
import ch.fhnw.roundtable.etopia.views.settings.state.SettingsOptionState;

import java.util.ArrayList;
import java.util.List;

public class Options implements Updateable, Renderable<List<SettingsOptionState>> {

    private final Configuration configuration;
    private final List<Option> elements;
    private int selected = 0;

    public Options(Configuration configuration, List<Option> elements) {
        this.configuration = configuration;
        this.elements = elements;
    }

    @Override
    public void update(float delta, Controls controls) {
        if (controls.isButtonJustPressed(ButtonType.UP)) {
            selected = Math.max(selected - 1, 0);
        }
        if (controls.isButtonJustPressed(ButtonType.DOWN)) {
            selected = Math.min(selected + 1, elements.size() - 1);
        }
        if (controls.isButtonJustPressed(ButtonType.SELECT)) {
            elements.get(selected).callback().run();
        }
    }

    @Override
    public List<SettingsOptionState> state() {
        var list = new ArrayList<SettingsOptionState>();

        for (int i = 0; i < elements.size(); i++) {
            list.add(new SettingsOptionState(
                    configuration.settings().offsetX(),
                    configuration.settings().bodyOffsetY() - i * configuration.settings().elementOffset(),
                    configuration.settings().maxWidth(),
                    elements.get(i).name(),
                    i == selected
            ));
        }

        return list;
    }
}
