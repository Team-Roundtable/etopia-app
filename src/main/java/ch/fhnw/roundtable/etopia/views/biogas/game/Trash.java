package ch.fhnw.roundtable.etopia.views.biogas.game;

import ch.fhnw.roundtable.etopia.helpers.Size;

public class Trash {

    private final Size size;
    private final boolean biodegradable;

    public Trash(Size size, boolean biodegradable) {
        this.size = size;
        this.biodegradable = biodegradable;
    }

    public float getWidth() {
        return size.width();
    }

    public float getHeight() {
        return size.height();
    }

    public boolean isBiodegradable() {
        return biodegradable;
    }
}
