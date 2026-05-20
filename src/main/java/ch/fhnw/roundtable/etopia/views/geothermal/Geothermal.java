package ch.fhnw.roundtable.etopia.views.geothermal;

import ch.fhnw.roundtable.etopia.Transition;
import ch.fhnw.roundtable.etopia.View;
import ch.fhnw.roundtable.etopia.configuration.Configuration;
import ch.fhnw.roundtable.etopia.input.Controls;
import ch.fhnw.roundtable.etopia.rendering.Assets;
import ch.fhnw.roundtable.etopia.rendering.Renderer;
import ch.fhnw.roundtable.etopia.views.geothermal.model.GeothermalModel;
import ch.fhnw.roundtable.etopia.views.geothermal.ui.GeothermalAsset;
import ch.fhnw.roundtable.etopia.views.geothermal.ui.GeothermalUI;
import ch.fhnw.roundtable.etopia.views.information.Information;
import ch.fhnw.roundtable.etopia.views.information.model.InformationType;
import ch.fhnw.roundtable.etopia.views.map.Map;
import ch.fhnw.roundtable.etopia.views.status.model.StatusModel;
import ch.fhnw.roundtable.etopia.views.status.ui.StatusAsset;
import ch.fhnw.roundtable.etopia.views.status.ui.StatusUI;

import java.util.Random;

public class Geothermal implements View {

    private final Configuration configuration;
    private final StatusModel statusModel;
    private final GeothermalModel geothermalModel;
    private final StatusUI statusUI;
    private final GeothermalUI geothermalUI;

    public Geothermal(Configuration configuration) {
        this.configuration = configuration;
        statusModel = new StatusModel(configuration, configuration.geothermal().gameDuration());
        geothermalModel = new GeothermalModel(configuration, new Random(), statusModel);

        statusUI = new StatusUI(configuration, new Assets<>(StatusAsset.class));
        geothermalUI = new GeothermalUI(new Assets<>(GeothermalAsset.class));
    }

    @Override
    public void update(float delta, Controls controls) {
        geothermalModel.update(delta, controls);
        statusModel.update(delta, controls);
    }

    @Override
    public void render(Renderer renderer) {
        geothermalUI.render(geothermalModel.state(), renderer);
        statusUI.render(statusModel.state(), renderer);
    }

    @Override
    public void dispose() {
        geothermalUI.dispose();
        statusUI.dispose();
    }

    @Override
    public Transition transition() {
        var result = geothermalModel.result();
        configuration.state().updateGeothermal(result);

        return switch (result) {
            case RUNNING -> Transition.none();
            case SUCCESS -> Transition.change(
                    () -> new Information(configuration, InformationType.GEOTHERMAL_SUCCESS,
                            () -> new Map(configuration)));
            case FAIL_TIME -> Transition.change(
                    () -> new Information(configuration, InformationType.GEOTHERMAL_FAIL_TIME,
                            () -> new Map(configuration)));
            case FAIL_HEALTH -> Transition.change(
                    () -> new Information(configuration, InformationType.GEOTHERMAL_FAIL_HEALTH,
                            () -> new Map(configuration)));
        };
    }
}
