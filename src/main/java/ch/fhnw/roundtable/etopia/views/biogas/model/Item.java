package ch.fhnw.roundtable.etopia.views.biogas.model;

import ch.fhnw.roundtable.etopia.configuration.Configuration;

public class Item {

    private final Configuration configuration;

    protected int x;
    protected int y;
    protected float side;

    protected Item(Configuration configuration, int x, int y) {
        this.configuration = configuration;
        this.x = x;
        this.y = y;
        this.side = configuration.biogas().itemSide();
    }

    public boolean intersects(Item other) {
        return x == other.x && y == other.y;
    }

    protected float getX() {
        return configuration.biogas().conveyorOffsetX() + x * side;
    }

    protected float getY() {
        return configuration.biogas().conveyorOffsetY() + y * side;
    }
}
