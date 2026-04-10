package ch.fhnw.roundtable.etopia.shapes;

public record Vector(float x, float y) {

    public static Vector midpoint(Vector a, Vector b) {
        return new Vector(
                (a.x + b.x) / 2f,
                (a.y + b.y) / 2f
        );
    }
}
