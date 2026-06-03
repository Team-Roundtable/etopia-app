package ch.fhnw.roundtable.etopia.views.wind.state;

public record WindTurbineState(
        float x, float y,
        float width, float height,
        boolean frozen) {
}
