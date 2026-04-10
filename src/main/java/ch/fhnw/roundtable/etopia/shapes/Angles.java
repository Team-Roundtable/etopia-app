package ch.fhnw.roundtable.etopia.shapes;

public final class Angles {

    private Angles() {
    }

    public static float angle(Vector from, Vector to) {
        float dx = to.x() - from.x();
        float dy = to.y() - from.y();
        return (float) Math.toDegrees(Math.atan2(dy, dx));
    }

    public static float difference(float a, float b) {
        float diff = Math.abs(a - b) % 360;
        return diff > 180 ? 360 - diff : diff;
    }
}
