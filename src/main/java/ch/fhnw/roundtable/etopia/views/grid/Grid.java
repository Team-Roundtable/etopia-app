package ch.fhnw.roundtable.etopia.views.grid;

import ch.fhnw.roundtable.etopia.Transition;
import ch.fhnw.roundtable.etopia.View;
import ch.fhnw.roundtable.etopia.configuration.Configuration;
import ch.fhnw.roundtable.etopia.input.Controls;
import ch.fhnw.roundtable.etopia.rendering.Assets;
import ch.fhnw.roundtable.etopia.rendering.Renderer;
import ch.fhnw.roundtable.etopia.views.grid.model.GridModel;
import ch.fhnw.roundtable.etopia.views.grid.ui.GridAsset;
import ch.fhnw.roundtable.etopia.views.grid.ui.GridUI;
import ch.fhnw.roundtable.etopia.views.information.Information;
import ch.fhnw.roundtable.etopia.views.information.model.InformationType;
import ch.fhnw.roundtable.etopia.views.map.Map;
import ch.fhnw.roundtable.etopia.views.status.model.StatusModel;
import ch.fhnw.roundtable.etopia.views.status.ui.StatusAsset;
import ch.fhnw.roundtable.etopia.views.status.ui.StatusUI;

public class Grid implements View {

    private final Configuration configuration;
    private final StatusModel statusModel;
    private final GridModel gridModel;
    private final StatusUI statusUI;
    private final GridUI gridUI;

    public Grid(Configuration configuration) {
        this.configuration = configuration;
        this.statusModel = new StatusModel(configuration, configuration.grid().gameDuration());
        this.gridModel = new GridModel(configuration, statusModel);
        this.statusUI = new StatusUI(configuration, new Assets<>(StatusAsset.class));
        this.gridUI = new GridUI(configuration.grid(), new Assets<>(GridAsset.class));
    }

    @Override
    public void update(float delta, Controls controls) {
        gridModel.update(delta, controls);
    }

    @Override
    public void render(Renderer renderer) {
        gridUI.render(gridModel.state(), renderer);
        statusUI.render(statusModel.state(), renderer);
    }

    @Override
    public void dispose() {
        gridUI.dispose();
        statusUI.dispose();
    }

    @Override
    public Transition transition() {
        var result = gridModel.result();
        configuration.state().updateGrid(result);

        return switch (result) {
            case RUNNING, FAIL_HEALTH -> Transition.none();
            case SUCCESS -> Transition.change(
                    () -> new Information(configuration, InformationType.GRID_SUCCESS,
                            () -> new Map(configuration)));
            case FAIL_TIME -> Transition.change(
                    () -> new Information(configuration, InformationType.GRID_FAIL_TIME,
                            () -> new Map(configuration)));
        };
    }
}
