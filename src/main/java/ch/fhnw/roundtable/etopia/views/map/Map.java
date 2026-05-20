package ch.fhnw.roundtable.etopia.views.map;

import ch.fhnw.roundtable.etopia.Transition;
import ch.fhnw.roundtable.etopia.View;
import ch.fhnw.roundtable.etopia.configuration.Configuration;
import ch.fhnw.roundtable.etopia.input.Controls;
import ch.fhnw.roundtable.etopia.rendering.Assets;
import ch.fhnw.roundtable.etopia.rendering.Renderer;
import ch.fhnw.roundtable.etopia.views.map.model.MapModel;
import ch.fhnw.roundtable.etopia.views.map.ui.MapAsset;
import ch.fhnw.roundtable.etopia.views.map.ui.MapUI;

public class Map implements View {

    private final MapModel mapModel;
    private final MapUI mapUI;

    public Map(Configuration configuration) {
        this.mapModel = new MapModel(configuration);
        this.mapUI = new MapUI(new Assets<>(MapAsset.class));
    }

    @Override
    public void update(float delta, Controls controls) {
        mapModel.update(delta, controls);
    }

    @Override
    public void render(Renderer renderer) {
        mapUI.render(mapModel.state(), renderer);
    }

    @Override
    public void dispose() {
        mapUI.dispose();
    }

    @Override
    public Transition transition() {
        var result = mapModel.result();

        return switch (result) {
            case RUNNING, FAIL_TIME, FAIL_HEALTH -> Transition.none();
            case SUCCESS -> Transition.change(mapModel.next());
        };
    }
}
