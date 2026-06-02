package ch.fhnw.roundtable.etopia.views.geothermal.model;

import ch.fhnw.roundtable.etopia.Renderable;
import ch.fhnw.roundtable.etopia.Updateable;
import ch.fhnw.roundtable.etopia.configuration.Configuration;
import ch.fhnw.roundtable.etopia.input.Controls;
import ch.fhnw.roundtable.etopia.shapes.Circle;
import ch.fhnw.roundtable.etopia.views.geothermal.state.GeothermalDrillState;

public class Drill implements Updateable, Renderable<GeothermalDrillState> {

    private final Configuration configuration;
    private final float width;
    private final float height;
    private final float speed;

    private float x;
    private float y;
    private boolean down = true;
    private float direction = 0;

    public Drill(Configuration configuration) {
        this.configuration = configuration;
        this.x = configuration.worldWidth() / 2f;
        this.y = configuration.geothermal().mapHeight() - configuration.worldHeight() / 2f;
        this.width = configuration.geothermal().drillWidth();
        this.height = configuration.geothermal().drillHeight();
        this.speed = configuration.geothermal().drillSpeed();
    }

    public Circle getBounds() {
        return new Circle(x, y, width / 6);
    }

    @Override
    public void update(float delta, Controls controls) {
        var horizontal = controls.getButtonHorizontal();
        direction = horizontal;

        y += speed * delta * (down ? -1 : 1);
        x = Math.clamp(x + speed * delta * direction, 0 + width / 3, configuration.worldWidth() - width / 3);
    }

    @Override
    public GeothermalDrillState state() {
        return new GeothermalDrillState(
                x,
                y,
                width,
                height,
                rotation()
        );
    }

    public void up() {
        down = false;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    private float rotation() {
        return (down ? 0f : 180f) + (down ? 45f : -45f) * direction;
    }
}
