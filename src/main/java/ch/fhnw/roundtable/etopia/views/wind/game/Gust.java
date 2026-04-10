package ch.fhnw.roundtable.etopia.views.wind.game;

import ch.fhnw.roundtable.etopia.ETopia;
import ch.fhnw.roundtable.etopia.helpers.Size;
import ch.fhnw.roundtable.etopia.shapes.Rectangle;

public class Gust {

    private final Size size;
    private final float y;
    private final float speed;
    private final boolean harmful;
    private float x = ETopia.WORLD_WIDTH;

    public Gust(Size size, float y, float speed, boolean harmful) {
        this.size = size;
        this.y = y;
        this.speed = speed;
        this.harmful = harmful;
    }

    public void updateX(float delta) {
        x -= speed * delta;
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

    public boolean isHarmful() {
        return harmful;
    }
}
