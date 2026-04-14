package ch.fhnw.roundtable.etopia.views.geothermal;

import ch.fhnw.roundtable.etopia.input.Input;
import ch.fhnw.roundtable.etopia.input.LEDControl;
import ch.fhnw.roundtable.etopia.views.Assets;
import ch.fhnw.roundtable.etopia.views.Renderer;
import ch.fhnw.roundtable.etopia.views.View;
import ch.fhnw.roundtable.etopia.views.ViewType;
import ch.fhnw.roundtable.etopia.views.geothermal.game.GeothermalGame;
import ch.fhnw.roundtable.etopia.views.geothermal.ui.GeothermalAsset;
import ch.fhnw.roundtable.etopia.views.geothermal.ui.GeothermalUI;
import ch.fhnw.roundtable.etopia.views.health.HealthConfiguration;
import ch.fhnw.roundtable.etopia.views.health.ui.HealthAsset;
import ch.fhnw.roundtable.etopia.views.health.ui.HealthUI;

import java.util.Random;

public class Geothermal implements View {

    private final GeothermalGame geothermalGame;
    private final GeothermalUI geothermalUI;
    private final HealthUI healthUI;

    public Geothermal(LEDControl ledControl) {
        ledControl.ledAllPlayableOff();
        ledControl.ledLeftOn();
        ledControl.ledRightOn();

        var geothermalConfiguration = new GeothermalConfiguration();
        var healthConfiguration = new HealthConfiguration();

        geothermalGame = new GeothermalGame(new Random(), geothermalConfiguration);
        geothermalUI = new GeothermalUI(geothermalConfiguration, new Assets<>(GeothermalAsset.class));
        healthUI = new HealthUI(healthConfiguration, new Assets<>(HealthAsset.class));
    }

    @Override
    public void update(float delta, Input input) {
        geothermalGame.update(delta, input);
    }

    @Override
    public void render(Renderer renderer) {
        geothermalUI.render(geothermalGame, renderer);
        healthUI.render(geothermalGame.getHealthGame(), renderer);
    }

    @Override
    public void dispose() {
        geothermalUI.dispose();
        healthUI.dispose();
    }

    @Override
    public ViewType next() {
        return geothermalGame.next();
    }
}
