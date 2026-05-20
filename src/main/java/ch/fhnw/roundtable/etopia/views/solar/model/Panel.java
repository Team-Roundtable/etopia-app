package ch.fhnw.roundtable.etopia.views.solar.model;

import ch.fhnw.roundtable.etopia.Renderable;
import ch.fhnw.roundtable.etopia.Updateable;
import ch.fhnw.roundtable.etopia.configuration.Configuration;
import ch.fhnw.roundtable.etopia.input.Controls;
import ch.fhnw.roundtable.etopia.shapes.Vector;
import ch.fhnw.roundtable.etopia.views.solar.state.SolarPanelState;

public class Panel implements Updateable, Renderable<SolarPanelState> {

    private final Vector panelCenter;
    private final float width;
    private final float height;
    private final float speed;
    private final float maxRotation;

    private float rotation = 0;

    public Panel(Configuration configuration) {
        this.panelCenter = new Vector(configuration.worldWidth() / 2f, configuration.worldHeight() / 4f);
        this.width = configuration.solar().panelWidth();
        this.height = configuration.solar().panelHeight();
        this.maxRotation = configuration.solar().panelMaxRotation();
        this.speed = configuration.solar().panelSpeed();
    }

    @Override
    public void update(float delta, Controls controls) {
        rotation -= speed * delta * controls.getButtonHorizontal();
        rotation = Math.clamp(rotation, -maxRotation, maxRotation);
    }

    @Override
    public SolarPanelState state() {
        return new SolarPanelState(
                panelCenter.x(),
                panelCenter.y(),
                width,
                height,
                rotation
        );
    }

    public Vector getCenter() {
        return panelCenter;
    }

    public float getRotation() {
        return rotation;
    }
}
