package ch.fhnw.roundtable.etopia.views.solar.model;

import ch.fhnw.roundtable.etopia.Renderable;
import ch.fhnw.roundtable.etopia.Updateable;
import ch.fhnw.roundtable.etopia.configuration.Configuration;
import ch.fhnw.roundtable.etopia.input.Controls;
import ch.fhnw.roundtable.etopia.shapes.Vector;
import ch.fhnw.roundtable.etopia.views.solar.state.SolarSunState;
import com.badlogic.gdx.math.MathUtils;

public class Sun implements Updateable, Renderable<SolarSunState> {

    private final Vector worldCenter;
    private final float sunOrbitRadius;
    private final float width;
    private final float height;
    private final float speed;

    private float angle = 0;

    public Sun(Configuration configuration) {
        this.worldCenter = new Vector(configuration.worldWidth() / 2f, 0);
        this.sunOrbitRadius = configuration.worldHeight();
        this.width = configuration.solar().sunWidth();
        this.height = configuration.solar().sunHeight();
        this.speed = configuration.solar().sunSpeed();
    }

    @Override
    public void update(float delta, Controls controls) {
        float currentSpeed = dayTime() >= 0 ? speed : speed * 2f;

        angle += currentSpeed * delta;
    }

    @Override
    public SolarSunState state() {
        return new SolarSunState(
                getX(),
                getY(),
                width,
                height,
                dayLight()
        );
    }

    public Vector getCenter() {
        float x = worldCenter.x() + (float) Math.cos(angle) * sunOrbitRadius;
        float y = worldCenter.y() + (float) Math.sin(angle) * sunOrbitRadius;
        return new Vector(x, y);
    }

    public double dayTime() {
        return Math.sin(angle % MathUtils.PI2);
    }

    public float dayLight() {
        return Math.clamp((float) dayTime() + 0.5f, 0.5f, 1f);
    }

    private float getX() {
        return getCenter().x() - width / 2f;
    }

    private float getY() {
        return getCenter().y() - height / 2f;
    }
}
