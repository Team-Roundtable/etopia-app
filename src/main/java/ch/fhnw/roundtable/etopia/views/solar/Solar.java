package ch.fhnw.roundtable.etopia.views.solar;

import ch.fhnw.roundtable.etopia.Transition;
import ch.fhnw.roundtable.etopia.View;
import ch.fhnw.roundtable.etopia.configuration.Configuration;
import ch.fhnw.roundtable.etopia.input.Controls;
import ch.fhnw.roundtable.etopia.rendering.Assets;
import ch.fhnw.roundtable.etopia.rendering.Renderer;
import ch.fhnw.roundtable.etopia.views.information.Information;
import ch.fhnw.roundtable.etopia.views.information.model.InformationType;
import ch.fhnw.roundtable.etopia.views.map.Map;
import ch.fhnw.roundtable.etopia.views.solar.model.SolarModel;
import ch.fhnw.roundtable.etopia.views.solar.ui.SolarAsset;
import ch.fhnw.roundtable.etopia.views.solar.ui.SolarUI;
import ch.fhnw.roundtable.etopia.views.status.model.StatusModel;
import ch.fhnw.roundtable.etopia.views.status.ui.StatusAsset;
import ch.fhnw.roundtable.etopia.views.status.ui.StatusUI;

public class Solar implements View {

    private final Configuration configuration;
    private final StatusModel statusModel;
    private final SolarModel solarModel;
    private final StatusUI statusUI;
    private final SolarUI solarUI;

    public Solar(Configuration configuration) {
        this.configuration = configuration;
        this.statusModel = new StatusModel(configuration, configuration.solar().gameDuration());
        this.solarModel = new SolarModel(configuration, statusModel);
        this.statusUI = new StatusUI(configuration, new Assets<>(StatusAsset.class));
        this.solarUI = new SolarUI(new Assets<>(SolarAsset.class));
    }

    @Override
    public void update(float delta, Controls controls) {
        solarModel.update(delta, controls);
    }

    @Override
    public void render(Renderer renderer) {
        solarUI.render(solarModel.state(), renderer);
        statusUI.render(statusModel.state(), renderer);
    }

    @Override
    public void dispose() {
        solarUI.dispose();
        statusUI.dispose();
    }

    @Override
    public Transition transition() {
        var result = solarModel.result();
        configuration.state().updateSolar(result);

        return switch (result) {
            case RUNNING, FAIL_HEALTH -> Transition.none();
            case SUCCESS -> Transition.change(
                    () -> new Information(configuration, InformationType.SOLAR_SUCCESS,
                            () -> new Map(configuration)));
            case FAIL_TIME -> Transition.change(
                    () -> new Information(configuration, InformationType.SOLAR_FAIL_TIME,
                            () -> new Map(configuration)));
        };
    }
}
