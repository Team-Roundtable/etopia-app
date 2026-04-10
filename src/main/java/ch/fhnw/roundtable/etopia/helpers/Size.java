package ch.fhnw.roundtable.etopia.helpers;

// todo should be moved
public record Size(float width, float height) {

    public Size {
    }

    public Size(float width, float height, float scale) {
        this(width * scale, height * scale);
    }
}
