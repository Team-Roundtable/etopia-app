package ch.fhnw.roundtable.etopia.views.wind.game;

import ch.fhnw.roundtable.etopia.ETopia;
import ch.fhnw.roundtable.etopia.helpers.Size;
import ch.fhnw.roundtable.etopia.shapes.Rectangle;

public class Turbine {

    private final Size size;
    private final float x = 100;
    private final float speed;
    private float y;
    private boolean frozen;

    public Turbine(Size size, float y, boolean frozen, float speed) {
        this.size = size;
        this.y = y;
        this.frozen = frozen;
        this.speed = speed;
    }

    public void updateY(float delta, float verticalInput) {
        var yDelta = verticalInput * (speed * delta);

        y = Math.clamp(y + yDelta, 0, ETopia.WORLD_HEIGHT);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, size.width(), size.height());
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getWidth() {
        return size.width();
    }

    public float getHeight() {
        return size.height();
    }

    public boolean isFrozen() {
        return frozen;
    }

    public void setFrozen(boolean frozen) {
        this.frozen = frozen;
    }
}
