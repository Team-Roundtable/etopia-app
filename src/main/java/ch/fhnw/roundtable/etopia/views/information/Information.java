package ch.fhnw.roundtable.etopia.views.information;

import ch.fhnw.roundtable.etopia.Transition;
import ch.fhnw.roundtable.etopia.View;
import ch.fhnw.roundtable.etopia.configuration.Configuration;
import ch.fhnw.roundtable.etopia.input.Controls;
import ch.fhnw.roundtable.etopia.rendering.Assets;
import ch.fhnw.roundtable.etopia.rendering.Renderer;
import ch.fhnw.roundtable.etopia.rendering.VideoAssets;
import ch.fhnw.roundtable.etopia.views.information.model.InformationModel;
import ch.fhnw.roundtable.etopia.views.information.model.InformationType;
import ch.fhnw.roundtable.etopia.views.information.ui.InformationAsset;
import ch.fhnw.roundtable.etopia.views.information.ui.InformationUI;
import ch.fhnw.roundtable.etopia.views.information.ui.InformationVideoAsset;
import ch.fhnw.roundtable.etopia.views.map.Map;

import java.util.function.Supplier;

public class Information implements View {

    private final Configuration configuration;
    private final InformationModel informationModel;
    private final InformationUI informationUI;
    private final Supplier<View> next;

    public Information(Configuration configuration, InformationType informationType, Supplier<View> next) {
        this.configuration = configuration;
        this.informationModel = new InformationModel(configuration, informationType);
        this.informationUI = new InformationUI(
                new Assets<>(InformationAsset.class),
                new VideoAssets<>(InformationVideoAsset.class));
        this.next = next;
    }

    @Override
    public void update(float delta, Controls controls) {
        informationModel.update(delta, controls);
    }

    @Override
    public void render(Renderer renderer) {
        informationUI.render(informationModel.state(), renderer);
    }

    @Override
    public void dispose() {
        informationUI.dispose();
    }

    @Override
    public Transition transition() {
        return switch (informationModel.result()) {
            case RUNNING, FAIL_HEALTH -> Transition.none();
            case SUCCESS -> Transition.change(next);
            case FAIL_TIME -> Transition.change(() -> new Map(configuration));
        };
    }
}
