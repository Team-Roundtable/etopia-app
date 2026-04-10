package ch.fhnw.roundtable.etopia.shapes;

public final class Intersections {

    private Intersections() {
    }

    public static boolean intersects(Rectangle a, Rectangle b) {
        return a.x() < b.x() + b.width()
                && a.x() + a.width() > b.x()
                && a.y() < b.y() + b.height()
                && a.y() + a.height() > b.y();
    }

    public static boolean intersects(Circle a, Circle b) {
        float dx = a.x() - b.x();
        float dy = a.y() - b.y();
        float r = a.radius() + b.radius();
        return dx * dx + dy * dy <= r * r;
    }

    public static boolean intersects(Rectangle r, Circle c) {
        float closestX = Math.clamp(c.x(), r.x(), r.x() + r.width());
        float closestY = Math.clamp(c.y(), r.y(), r.y() + r.height());

        float dx = c.x() - closestX;
        float dy = c.y() - closestY;

        return dx * dx + dy * dy <= c.radius() * c.radius();
    }

    public static boolean intersects(Circle c, Rectangle r) {
        return intersects(r, c);
    }
}
