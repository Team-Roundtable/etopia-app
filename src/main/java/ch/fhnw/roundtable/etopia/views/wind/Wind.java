package ch.fhnw.roundtable.etopia.views.wind;

import ch.fhnw.roundtable.etopia.configuration.Configuration;
import ch.fhnw.roundtable.etopia.input.Input;
import ch.fhnw.roundtable.etopia.views.Assets;
import ch.fhnw.roundtable.etopia.views.Renderer;
import ch.fhnw.roundtable.etopia.views.View;
import ch.fhnw.roundtable.etopia.views.ViewType;
import ch.fhnw.roundtable.etopia.views.clock.ClockConfiguration;
import ch.fhnw.roundtable.etopia.views.clock.ui.ClockUI;
import ch.fhnw.roundtable.etopia.views.energy.EnergyConfiguration;
import ch.fhnw.roundtable.etopia.views.energy.ui.EnergyAsset;
import ch.fhnw.roundtable.etopia.views.energy.ui.EnergyUI;
import ch.fhnw.roundtable.etopia.views.health.HealthConfiguration;
import ch.fhnw.roundtable.etopia.views.health.ui.HealthAsset;
import ch.fhnw.roundtable.etopia.views.health.ui.HealthUI;
import ch.fhnw.roundtable.etopia.views.wind.game.WindGame;
import ch.fhnw.roundtable.etopia.views.wind.ui.WindAsset;
import ch.fhnw.roundtable.etopia.views.wind.ui.WindUI;

import java.util.Random;

public class Wind implements View {

    private final WindGame windGame;
    private final WindUI windUI;
    private final HealthUI healthUI;
    private final ClockUI clockUI;
    private final EnergyUI energyUI;

    public Wind(Configuration configuration) {
        var windConfiguration = new WindConfiguration();
        var healthConfiguration = new HealthConfiguration();
        var clockConfiguration = new ClockConfiguration();
        var energyConfiguration = new EnergyConfiguration();

        windGame = new WindGame(new Random(), windConfiguration, configuration);
        windUI = new WindUI(new Assets<>(WindAsset.class));
        healthUI = new HealthUI(healthConfiguration, new Assets<>(HealthAsset.class));
        clockUI = new ClockUI(clockConfiguration);
        energyUI = new EnergyUI(energyConfiguration, new Assets<>(EnergyAsset.class));
    }

    @Override
    public void update(float delta, Input input) {
        windGame.update(delta, input);
    }

    @Override
    public void render(Renderer renderer) {
        windUI.render(windGame, renderer);
        healthUI.render(windGame.getHealthGame(), renderer);
        clockUI.render(windGame.getClockGame(), renderer);
        energyUI.render(windGame.getEnergyGame(), renderer);
    }

    @Override
    public void dispose() {
        windUI.dispose();
        healthUI.dispose();
        clockUI.dispose();
        energyUI.dispose();
    }

    @Override
    public ViewType next() {
        return windGame.next();
    }
}
