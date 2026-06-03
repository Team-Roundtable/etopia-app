package ch.fhnw.roundtable.etopia.views.wind.model;

import ch.fhnw.roundtable.etopia.Renderable;
import ch.fhnw.roundtable.etopia.Updateable;
import ch.fhnw.roundtable.etopia.configuration.Configuration;
import ch.fhnw.roundtable.etopia.time.Countdown;
import ch.fhnw.roundtable.etopia.input.Controls;
import ch.fhnw.roundtable.etopia.shapes.Rectangle;
import ch.fhnw.roundtable.etopia.views.wind.state.WindTurbineState;

public class Turbine implements Updateable, Renderable<WindTurbineState> {

    private final Configuration configuration;
    private final float x = 100;
    private final Countdown freezeCountdown;
    private final float width;
    private final float height;
    private float y;

    public Turbine(Configuration configuration) {
        this.configuration = configuration;
        this.y = configuration.worldHeight() / 2f;
        this.freezeCountdown = new Countdown(configuration.wind().freezeCountdown());
        this.width = configuration.wind().turbineWidth();
        this.height = configuration.wind().turbineHeight();
    }

    @Override
    public void update(float delta, Controls controls) {
        freezeCountdown.update(delta);

        if (freezeCountdown.isFinished()) {
            freezeCountdown.reset();
            controls.setButtonLightVertical();
        }

        if (freezeCountdown.isStarted()) {
            controls.setButtonLightNone();
            return;
        }

        var yDelta = controls.getButtonVertical() * (configuration.wind().turbineSpeed() * delta);

        y = Math.clamp(y + yDelta, 0, configuration.worldHeight() - height);
    }

    @Override
    public WindTurbineState state() {
        return new WindTurbineState(x, y, width, height, isFrozen());
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public void freeze() {
        freezeCountdown.start();
    }

    public boolean isFrozen() {
        return freezeCountdown.isStarted();
    }
}
