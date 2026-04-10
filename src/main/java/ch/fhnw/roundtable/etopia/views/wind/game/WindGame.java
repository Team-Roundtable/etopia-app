package ch.fhnw.roundtable.etopia.views.wind.game;

import ch.fhnw.roundtable.etopia.ETopia;
import ch.fhnw.roundtable.etopia.configuration.Configuration;
import ch.fhnw.roundtable.etopia.helpers.Timer;
import ch.fhnw.roundtable.etopia.input.Input;
import ch.fhnw.roundtable.etopia.shapes.Intersections;
import ch.fhnw.roundtable.etopia.views.Game;
import ch.fhnw.roundtable.etopia.views.ViewType;
import ch.fhnw.roundtable.etopia.views.clock.game.ClockGame;
import ch.fhnw.roundtable.etopia.views.energy.game.EnergyGame;
import ch.fhnw.roundtable.etopia.views.health.game.HealthGame;
import ch.fhnw.roundtable.etopia.views.wind.WindConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WindGame implements Game {

    private final Random random;
    private final WindConfiguration windConfiguration;
    private final Configuration configuration;
    private final List<Gust> gusts = new ArrayList<>();
    private final Turbine turbine;
    private final HealthGame healthGame;
    private final ClockGame clockGame;
    private final EnergyGame energyGame;

    private final Timer gustNormalTimer = new Timer(0.5f);
    private final Timer gustHarmfulTimer = new Timer(0.8f);
    private final Timer freezeTimer = new Timer(1f);

    public WindGame(Random random, WindConfiguration windConfiguration, Configuration configuration) {
        this.random = random;
        this.windConfiguration = windConfiguration;
        this.configuration = configuration;
        this.turbine = new Turbine(windConfiguration.turbineSize, ETopia.WORLD_HEIGHT / 2f,
                false, configuration.wind().turbineSpeed());
        this.healthGame = new HealthGame(3);
        this.clockGame = new ClockGame(30);
        this.energyGame = new EnergyGame();
    }

    @Override
    public void update(float delta, Input input) {
        clockGame.update(delta, input);

        updateTurbine(delta, input);
        updateGusts(delta);

        var turbineBounds = turbine.getBounds();
        var gustIterator = gusts.iterator();

        while (gustIterator.hasNext()) {
            var gust = gustIterator.next();

            if (Intersections.intersects(turbineBounds, gust.getBounds())) {
                gustIterator.remove();

                if (gust.isHarmful()) {
                    healthGame.subtract();
                    turbine.setFrozen(true);
                } else {
                    energyGame.update(0.05f);
                }
            } else if (gust.getX() + gust.getWidth() < 0) {
                gustIterator.remove();
            }
        }
    }

    // todo cleanup
    @Override
    public ViewType next() {
        if (healthGame.dead()) {
            return ViewType.WIND_FAIL_HEALTH;
        }

        if (clockGame.finished()) {
            return ViewType.WIND_FAIL_CLOCK;
        }

        if (energyGame.full()) {
            return ViewType.WIND_SUCCESS;
        }

        return null;
    }

    public List<Gust> getGusts() {
        return gusts;
    }

    public Turbine getTurbine() {
        return turbine;
    }

    public HealthGame getHealthGame() {
        return healthGame;
    }

    public ClockGame getClockGame() {
        return clockGame;
    }

    public EnergyGame getEnergyGame() {
        return energyGame;
    }

    private void updateTurbine(float delta, Input input) {
        if (turbine.isFrozen()) {
            if (freezeTimer.triggered(delta)) {
                freezeTimer.reset();
                turbine.setFrozen(false);
            }
            return;
        }

        turbine.updateY(delta, input.getVerticalInput());
    }

    private void updateGusts(float delta) {
        if (gustNormalTimer.triggered(delta)) {
            gusts.add(new Gust(windConfiguration.gustSize, random.nextInt(ETopia.WORLD_HEIGHT),
                    configuration.wind().gustSpeed(), false));
        }

        if (gustHarmfulTimer.triggered(delta)) {
            gusts.add(new Gust(windConfiguration.gustSize, random.nextInt(ETopia.WORLD_HEIGHT),
                    configuration.wind().gustSpeed(), true));
        }

        for (Gust gust : gusts) {
            gust.updateX(delta);
        }
    }
}
