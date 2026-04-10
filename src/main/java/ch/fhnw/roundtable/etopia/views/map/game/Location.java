package ch.fhnw.roundtable.etopia.views.map.game;

import ch.fhnw.roundtable.etopia.helpers.Size;

public class Location {

    private final Size size;
    private final float x;
    private final float y;

    public Location(Size size, float x, float y) {
        this.size = size;
        this.x = x;
        this.y = y;
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
}
