package ch.fhnw.roundtable.etopia.views.wind.model;

import ch.fhnw.roundtable.etopia.Renderable;
import ch.fhnw.roundtable.etopia.Updateable;
import ch.fhnw.roundtable.etopia.configuration.Configuration;
import ch.fhnw.roundtable.etopia.input.Controls;
import ch.fhnw.roundtable.etopia.shapes.Rectangle;
import ch.fhnw.roundtable.etopia.views.wind.state.WindGustState;

public class Gust implements Updateable, Renderable<WindGustState> {

    private final Configuration configuration;
    private final float y;
    private final float width;
    private final float height;
    private final boolean harmful;
    private float x;

    public Gust(Configuration configuration, float y, boolean harmful) {
        this.configuration = configuration;
        this.x = configuration.worldWidth();
        this.y = y;
        this.harmful = harmful;
        this.width = configuration.wind().gustWidth();
        this.height = configuration.wind().gustHeight();
    }

    @Override
    public void update(float delta, Controls controls) {
        x -= configuration.wind().gustSpeed() * (harmful ? 1.5f : 1) * delta;
    }

    @Override
    public WindGustState state() {
        return new WindGustState(x, y, width, height, harmful);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public boolean isHarmful() {
        return harmful;
    }
}
