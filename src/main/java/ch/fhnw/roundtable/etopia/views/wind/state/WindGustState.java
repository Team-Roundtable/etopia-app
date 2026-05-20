package ch.fhnw.roundtable.etopia.views.wind.state;

public record WindGustState(
        float x, float y,
        float width, float height,
        boolean harmful) {
}
