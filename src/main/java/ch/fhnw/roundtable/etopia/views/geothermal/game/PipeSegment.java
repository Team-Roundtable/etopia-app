package ch.fhnw.roundtable.etopia.views.geothermal.game;

import ch.fhnw.roundtable.etopia.helpers.Size;
import ch.fhnw.roundtable.etopia.shapes.Circle;
import ch.fhnw.roundtable.etopia.shapes.Intersections;

public class PipeSegment {

    private final Size size;
    private final float x;
    private final float y;
    private final float rotation;
    private final Circle bounds;

    public PipeSegment(Size size, float x, float y, float rotation) {
        this.size = size;
        this.x = x;
        this.y = y;
        this.rotation = rotation;
        this.bounds = new Circle(x, y, size.width() / 2);
    }

    public boolean intersects(Circle circle) {
        return Intersections.intersects(bounds, circle);
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
