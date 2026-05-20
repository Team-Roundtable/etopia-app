package ch.fhnw.roundtable.etopia.views.geothermal.model;

import ch.fhnw.roundtable.etopia.Renderable;
import ch.fhnw.roundtable.etopia.configuration.Configuration;
import ch.fhnw.roundtable.etopia.shapes.Circle;
import ch.fhnw.roundtable.etopia.shapes.Intersections;
import ch.fhnw.roundtable.etopia.views.geothermal.state.GeothermalPipeState;

public class Pipe implements Renderable<GeothermalPipeState> {

    private final float x;
    private final float y;
    private final float width;
    private final float height;
    private final float rotation;
    private final Circle bounds;

    public Pipe(Configuration configuration, float x, float y, float rotation) {
        this.x = x;
        this.y = y;
        this.width = configuration.geothermal().pipeWidth();
        this.height = configuration.geothermal().pipeHeight();
        this.rotation = rotation;
        this.bounds = new Circle(x, y, width / 2);
    }

    public boolean intersects(Circle circle) {
        return Intersections.intersects(bounds, circle);
    }

    @Override
    public GeothermalPipeState state() {
        return new GeothermalPipeState(
                x,
                y,
                width,
                height,
                rotation
        );
    }
}
