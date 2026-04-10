package ch.fhnw.roundtable.etopia.views.solar;

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
import ch.fhnw.roundtable.etopia.views.solar.game.SolarGame;
import ch.fhnw.roundtable.etopia.views.solar.ui.SolarAsset;
import ch.fhnw.roundtable.etopia.views.solar.ui.SolarUI;

public class Solar implements View {

    private final SolarGame solarGame;
    private final SolarUI solarUI;
    private final ClockUI clockUI;
    private final EnergyUI energyUI;

    public Solar() {
        var solarConfiguration = new SolarConfiguration();
        var clockConfiguration = new ClockConfiguration();
        var energyConfiguration = new EnergyConfiguration();

        solarGame = new SolarGame(solarConfiguration);
        solarUI = new SolarUI(new Assets<>(SolarAsset.class));
        clockUI = new ClockUI(clockConfiguration);
        energyUI = new EnergyUI(energyConfiguration, new Assets<>(EnergyAsset.class));
    }

    @Override
    public void update(float delta, Input input) {
        solarGame.update(delta, input);
    }

    @Override
    public void render(Renderer renderer) {
        solarUI.render(solarGame, renderer);
        clockUI.render(solarGame.getClockGame(), renderer);
        energyUI.render(solarGame.getEnergyGame(), renderer);
    }

    @Override
    public void dispose() {
        solarUI.dispose();
        clockUI.dispose();
        energyUI.dispose();
    }

    @Override
    public ViewType next() {
        return solarGame.next();
    }
}
