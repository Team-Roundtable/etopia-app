package ch.fhnw.roundtable.etopia.views.geothermal.model;

import ch.fhnw.roundtable.etopia.Model;
import ch.fhnw.roundtable.etopia.Result;
import ch.fhnw.roundtable.etopia.configuration.Configuration;
import ch.fhnw.roundtable.etopia.time.Countdown;
import ch.fhnw.roundtable.etopia.time.Timer;
import ch.fhnw.roundtable.etopia.input.Controls;
import ch.fhnw.roundtable.etopia.views.geothermal.state.GeothermalState;
import ch.fhnw.roundtable.etopia.views.status.model.StatusModel;

import java.util.Random;

public class GeothermalModel implements Model<GeothermalState> {

    private final Configuration configuration;
    private final Drill drill;
    private final Pipes pipes;
    private final Rocks rocks;
    private final StatusModel status;
    private final Timer pipeTimer;
    private final Countdown collisionCountdown;
    private final Countdown bottomCountdown;
    private final Countdown topCountdown;
    private boolean justGotHurt;

    public GeothermalModel(Configuration configuration, Random random, StatusModel status) {
        this.configuration = configuration;
        this.drill = new Drill(configuration);
        this.pipes = new Pipes(configuration);
        this.status = status;
        this.rocks = new Rocks(configuration, random);
        this.pipeTimer = new Timer(configuration.geothermal().pipeTimer());
        this.collisionCountdown = new Countdown(configuration.geothermal().collisionCountdown());
        this.bottomCountdown = new Countdown(configuration.geothermal().bottomCountdown());
        this.topCountdown = new Countdown(configuration.geothermal().topCountdown());
    }

    @Override
    public void update(float delta, Controls controls) {
        controls.setButtonLightHorizontal();

        if (handleBottomCountdown(delta)) {
            return;
        }

        if (handleTopCountdown(delta, controls)) {
            return;
        }

        updateGameplay(delta, controls);
        handleCollisions(delta);

        float minY = configuration.worldHeight() / 2f;
        float maxY = configuration.geothermal().mapHeight() - minY;
        if (drill.getY() <= minY) {
            handleBottomReached();
        } else if (drill.getY() >= maxY) {
            handleTopReached();
        }
    }

    @Override
    public GeothermalState state() {
        var state = new GeothermalState(
                configuration.worldWidth() / 2f,
                drill.getY(),
                configuration.worldWidth(),
                configuration.geothermal().mapHeight(),
                rocks.getState(),
                pipes.getState(),
                drill.state(),
                justGotHurt
        );
        justGotHurt = false;
        return state;
    }

    @Override
    public Result result() {
        if (status.isDead()) {
            return Result.FAIL_HEALTH;
        }

        if (status.isFinished()) {
            return Result.FAIL_TIME;
        }

        if (topCountdown.isFinished()) {
            return Result.SUCCESS;
        }

        return Result.RUNNING;
    }

    private boolean handleBottomCountdown(float delta) {
        bottomCountdown.update(delta);

        return bottomCountdown.isStarted()
                && !bottomCountdown.isFinished();
    }

    private boolean handleTopCountdown(float delta, Controls controls) {
        topCountdown.update(delta);

        if (topCountdown.isStarted()) {
            controls.setButtonLightNone();
            return true;
        }

        return false;
    }

    private void updateGameplay(float delta, Controls controls) {
        status.update(delta, controls);
        drill.update(delta, controls);

        if (pipeTimer.triggered(delta)) {
            pipes.add(drill.getX(), drill.getY());
        }
    }

    private void handleCollisions(float delta) {
        collisionCountdown.update(delta);

        if (collisionCountdown.isFinished()) {
            collisionCountdown.reset();
        }

        if (collisionCountdown.isStarted()) {
            return;
        }

        var bounds = drill.getBounds();

        if (rocks.intersects(bounds) || pipes.intersects(bounds)) {
            damagePlayer();
        }
    }

    private void handleBottomReached() {
        drill.up();
        collisionCountdown.start();
        bottomCountdown.start();
    }

    private void handleTopReached() {
        topCountdown.start();
        status.setEnergy(1f);
    }

    private void damagePlayer() {
        status.removeHealth();
        collisionCountdown.start();
        justGotHurt = true;
    }
}
