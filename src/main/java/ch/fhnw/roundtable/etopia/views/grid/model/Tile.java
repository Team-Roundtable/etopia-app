package ch.fhnw.roundtable.etopia.views.grid.model;

import ch.fhnw.roundtable.etopia.configuration.Configuration;

public class Tile {

    private final float offsetY;
    protected int x;
    protected int y;
    protected float side;

    protected Tile(Configuration configuration, int x, int y) {
        this.x = x;
        this.y = y;
        this.side = configuration.grid().tileSide();
        this.offsetY = configuration.grid().offsetY();
    }

    public boolean intersects(Tile other) {
        return x == other.x && y == other.y;
    }

    protected float getX() {
        return x * side;
    }

    protected float getY() {
        return y * side - offsetY;
    }
}
