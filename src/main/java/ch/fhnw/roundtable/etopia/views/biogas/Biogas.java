package ch.fhnw.roundtable.etopia.views.biogas;

import ch.fhnw.roundtable.etopia.Transition;
import ch.fhnw.roundtable.etopia.View;
import ch.fhnw.roundtable.etopia.configuration.Configuration;
import ch.fhnw.roundtable.etopia.input.Controls;
import ch.fhnw.roundtable.etopia.rendering.Assets;
import ch.fhnw.roundtable.etopia.rendering.Renderer;
import ch.fhnw.roundtable.etopia.views.biogas.model.BiogasModel;
import ch.fhnw.roundtable.etopia.views.biogas.ui.BiogasAsset;
import ch.fhnw.roundtable.etopia.views.biogas.ui.BiogasUI;
import ch.fhnw.roundtable.etopia.views.information.Information;
import ch.fhnw.roundtable.etopia.views.information.model.InformationType;
import ch.fhnw.roundtable.etopia.views.map.Map;
import ch.fhnw.roundtable.etopia.views.status.model.StatusModel;
import ch.fhnw.roundtable.etopia.views.status.ui.StatusAsset;
import ch.fhnw.roundtable.etopia.views.status.ui.StatusUI;

import java.util.Random;

public class Biogas implements View {

    private final Configuration configuration;
    private final StatusModel statusModel;
    private final BiogasModel biogasModel;
    private final StatusUI statusUI;
    private final BiogasUI biogasUI;

    public Biogas(Configuration configuration) {
        this.configuration = configuration;
        statusModel = new StatusModel(configuration, configuration.biogas().gameDuration());
        biogasModel = new BiogasModel(configuration, new Random(), statusModel);
        statusUI = new StatusUI(configuration, new Assets<>(StatusAsset.class));
        biogasUI = new BiogasUI(new Assets<>(BiogasAsset.class));
    }

    @Override
    public void update(float delta, Controls controls) {
        biogasModel.update(delta, controls);
        statusModel.update(delta, controls);
    }

    @Override
    public void render(Renderer renderer) {
        biogasUI.render(biogasModel.state(), renderer);
        statusUI.render(statusModel.state(), renderer);
    }

    @Override
    public void dispose() {
        biogasUI.dispose();
        statusUI.dispose();
    }

    @Override
    public Transition transition() {
        var result = biogasModel.result();
        configuration.state().updateBiogas(result);

        return switch (result) {
            case RUNNING -> Transition.none();
            case SUCCESS -> Transition.change(
                    () -> new Information(configuration, InformationType.BIOGAS_SUCCESS,
                            () -> new Map(configuration)));
            case FAIL_TIME -> Transition.change(
                    () -> new Information(configuration, InformationType.BIOGAS_FAIL_TIME,
                            () -> new Map(configuration)));
            case FAIL_HEALTH -> Transition.change(
                    () -> new Information(configuration, InformationType.BIOGAS_FAIL_HEALTH,
                            () -> new Map(configuration)));
        };
    }
}
