package ch.fhnw.roundtable.etopia.views.solar.game;

import ch.fhnw.roundtable.etopia.helpers.Timer;
import ch.fhnw.roundtable.etopia.input.Input;
import ch.fhnw.roundtable.etopia.shapes.Angles;
import ch.fhnw.roundtable.etopia.views.Game;
import ch.fhnw.roundtable.etopia.views.ViewType;
import ch.fhnw.roundtable.etopia.views.clock.game.ClockGame;
import ch.fhnw.roundtable.etopia.views.energy.game.EnergyGame;
import ch.fhnw.roundtable.etopia.views.solar.SolarConfiguration;

public class SolarGame implements Game {

    private final ClockGame clockGame;
    private final EnergyGame energyGame;

    private final Sun sun;
    private final Panel panel;
    private final Timer intervalTimer = new Timer(0.5f);

    public SolarGame(SolarConfiguration solarConfiguration) {
        this.sun = new Sun(solarConfiguration);
        this.panel = new Panel(solarConfiguration);
        this.clockGame = new ClockGame(30);
        this.energyGame = new EnergyGame();
    }

    @Override
    public void update(float delta, Input input) {
        clockGame.update(delta, input);
        sun.update(delta);
        panel.update(delta, input);

        if (intervalTimer.triggered(delta)) {
            energyGame.update(0.05f * power());
        }
    }

    private float power() {
        float target = Angles.angle(panel.getCenter(), sun.getCenter()) - 90f;
        float difference = Angles.difference(panel.getRotation(), target);

        float normalized = 1f - (difference / 180f);
        normalized = Math.max(0, normalized);

        return (float) Math.pow(normalized, 5);
    }

    @Override
    public ViewType next() {
        if (clockGame.finished()) {
            return ViewType.SOLAR_FAIL_CLOCK;
        }

        if (energyGame.full()) {
            return ViewType.SOLAR_SUCCESS;
        }

        return null;
    }

    public Sun getSun() {
        return sun;
    }

    public Panel getPanel() {
        return panel;
    }

    public ClockGame getClockGame() {
        return clockGame;
    }

    public EnergyGame getEnergyGame() {
        return energyGame;
    }
}
