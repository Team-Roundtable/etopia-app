package ch.fhnw.roundtable.etopia.views.solar.game;

import ch.fhnw.roundtable.etopia.helpers.Size;
import ch.fhnw.roundtable.etopia.shapes.Vector;
import ch.fhnw.roundtable.etopia.views.solar.SolarConfiguration;

public class Sun {

    private final Size size;
    private final float radius;
    private final float speed;
    private final Vector center;

    private float angle = 0;

    public Sun(SolarConfiguration solarConfiguration) {
        this.size = solarConfiguration.sunSize;
        this.radius = solarConfiguration.sunOrbitRadius;
        this.speed = solarConfiguration.sunSpeed;
        this.center = solarConfiguration.worldCenter;
    }

    public void update(float delta) {
        angle += speed * delta;
    }

    public Vector getCenter() {
        float x = center.x() + (float) Math.cos(angle) * radius;
        float y = center.y() + (float) Math.sin(angle) * radius;
        return new Vector(x, y);
    }

    public float getX() {
        return getCenter().x() - size.width() / 2f;
    }

    public float getY() {
        return getCenter().y() - size.height() / 2f;
    }

    public float getWidth() {
        return size.width();
    }

    public float getHeight() {
        return size.height();
    }
}
