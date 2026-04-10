package ch.fhnw.roundtable.etopia.views.solar.game;

import ch.fhnw.roundtable.etopia.helpers.Size;
import ch.fhnw.roundtable.etopia.input.Input;
import ch.fhnw.roundtable.etopia.shapes.Vector;
import ch.fhnw.roundtable.etopia.views.solar.SolarConfiguration;

public class Panel {

    private final Size size;
    private final float speed;
    private final float maxRotation;
    private final Vector center;

    private float rotation = 0;

    public Panel(SolarConfiguration solarConfiguration) {
        this.size = solarConfiguration.panelSize;
        this.speed = solarConfiguration.panelSpeed;
        this.maxRotation = solarConfiguration.panelMaxRotation;
        this.center = solarConfiguration.panelCenter;
    }

    public void update(float delta, Input input) {
        rotation -= speed * delta * input.getHorizontalInput();
        rotation = Math.clamp(rotation, -maxRotation, maxRotation);
    }

    public Vector getCenter() {
        return center;
    }

    public float getRotation() {
        return rotation;
    }

    public float getX() {
        return center.x();
    }

    public float getY() {
        return center.y();
    }

    public float getWidth() {
        return size.width();
    }

    public float getHeight() {
        return size.height();
    }
}
