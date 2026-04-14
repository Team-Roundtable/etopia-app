package ch.fhnw.roundtable.etopia.views.information;

import ch.fhnw.roundtable.etopia.configuration.Configuration;
import ch.fhnw.roundtable.etopia.input.Input;
import ch.fhnw.roundtable.etopia.input.LEDControl;
import ch.fhnw.roundtable.etopia.views.Assets;
import ch.fhnw.roundtable.etopia.views.Renderer;
import ch.fhnw.roundtable.etopia.views.View;
import ch.fhnw.roundtable.etopia.views.ViewType;
import ch.fhnw.roundtable.etopia.views.information.game.InformationGame;
import ch.fhnw.roundtable.etopia.views.information.game.InformationType;
import ch.fhnw.roundtable.etopia.views.information.ui.InformationAsset;
import ch.fhnw.roundtable.etopia.views.information.ui.InformationUI;

public class Information implements View {

    private final InformationGame informationGame;
    private final InformationUI informationUI;

    public Information(Configuration configuration, InformationType informationType, LEDControl ledControl) {
        ledControl.ledAllPlayableOff();
        ledControl.ledSelectOn();

        var informationConfiguration = new InformationConfiguration();

        informationGame = new InformationGame(configuration, informationType);
        informationUI = new InformationUI(informationConfiguration, new Assets<>(InformationAsset.class));
    }

    @Override
    public void update(float delta, Input input) {
        informationGame.update(delta, input);
    }

    @Override
    public void render(Renderer renderer) {
        informationUI.render(informationGame, renderer);
    }

    @Override
    public void dispose() {
        informationUI.dispose();
    }

    @Override
    public ViewType next() {
        return informationGame.next();
    }
}
