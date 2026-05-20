package ch.fhnw.roundtable.etopia.views.wind;

import ch.fhnw.roundtable.etopia.Transition;
import ch.fhnw.roundtable.etopia.View;
import ch.fhnw.roundtable.etopia.configuration.Configuration;
import ch.fhnw.roundtable.etopia.input.Controls;
import ch.fhnw.roundtable.etopia.rendering.Assets;
import ch.fhnw.roundtable.etopia.rendering.Renderer;
import ch.fhnw.roundtable.etopia.views.information.Information;
import ch.fhnw.roundtable.etopia.views.information.model.InformationType;
import ch.fhnw.roundtable.etopia.views.map.Map;
import ch.fhnw.roundtable.etopia.views.status.model.StatusModel;
import ch.fhnw.roundtable.etopia.views.status.ui.StatusAsset;
import ch.fhnw.roundtable.etopia.views.status.ui.StatusUI;
import ch.fhnw.roundtable.etopia.views.wind.model.WindModel;
import ch.fhnw.roundtable.etopia.views.wind.ui.WindAsset;
import ch.fhnw.roundtable.etopia.views.wind.ui.WindUI;

import java.util.Random;

public class Wind implements View {

    private final Configuration configuration;
    private final StatusModel statusModel;
    private final WindModel windModel;
    private final StatusUI statusUI;
    private final WindUI windUI;

    public Wind(Configuration configuration) {
        this.configuration = configuration;
        this.statusModel = new StatusModel(configuration, configuration.wind().gameDuration());
        this.windModel = new WindModel(configuration, new Random(), statusModel);
        this.statusUI = new StatusUI(configuration, new Assets<>(StatusAsset.class));
        this.windUI = new WindUI(new Assets<>(WindAsset.class));
    }

    @Override
    public void update(float delta, Controls controls) {
        windModel.update(delta, controls);
        windUI.update(delta, controls);
    }

    @Override
    public void render(Renderer renderer) {
        windUI.render(windModel.state(), renderer);
        statusUI.render(statusModel.state(), renderer);
    }

    @Override
    public void dispose() {
        windUI.dispose();
        statusUI.dispose();
    }

    @Override
    public Transition transition() {
        var result = windModel.result();
        configuration.state().updateWind(result);

        return switch (result) {
            case RUNNING -> Transition.none();
            case SUCCESS -> Transition.change(
                    () -> new Information(configuration, InformationType.WIND_SUCCESS,
                            () -> new Map(configuration)));
            case FAIL_TIME -> Transition.change(
                    () -> new Information(configuration, InformationType.WIND_FAIL_TIME,
                            () -> new Map(configuration)));
            case FAIL_HEALTH -> Transition.change(
                    () -> new Information(configuration, InformationType.WIND_FAIL_HEALTH,
                            () -> new Map(configuration)));
        };
    }
}
