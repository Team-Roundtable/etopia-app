package ch.fhnw.roundtable.etopia.views.geothermal.game;

import ch.fhnw.roundtable.etopia.ETopia;
import ch.fhnw.roundtable.etopia.helpers.Size;
import ch.fhnw.roundtable.etopia.input.Input;
import ch.fhnw.roundtable.etopia.shapes.Circle;
import ch.fhnw.roundtable.etopia.views.geothermal.GeothermalConfiguration;

public class Drill {

    private final Size size;
    private final float speed;
    private float x;
    private float y;
    private float rotation = 0;

    public Drill(GeothermalConfiguration geothermalConfiguration) {
        this.size = geothermalConfiguration.drillSize;
        this.speed = geothermalConfiguration.drillSpeed;
        this.x = ETopia.WORLD_WIDTH / 2f;
        this.y = geothermalConfiguration.mapSize.height() - ETopia.WORLD_HEIGHT / 2f;
    }

    public Circle getBounds() {
        // todo wrong calculation
        return new Circle(x, y, size.width() / 6);
    }

    public void update(float delta, Input input, boolean down) {
        y += speed * delta * (down ? -1 : 1);
        x = Math.clamp(x + speed * delta * input.getHorizontalInput(), 0, ETopia.WORLD_WIDTH);

        rotation = down ? 0 : 180;
    }

    public float getWidth() {
        return size.width();
    }

    public float getHeight() {
        return size.height();
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getRotation() {
        return rotation;
    }
}
