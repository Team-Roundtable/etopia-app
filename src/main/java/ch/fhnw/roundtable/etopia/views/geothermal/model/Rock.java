package ch.fhnw.roundtable.etopia.views.geothermal.model;

import ch.fhnw.roundtable.etopia.Renderable;
import ch.fhnw.roundtable.etopia.configuration.Configuration;
import ch.fhnw.roundtable.etopia.shapes.Circle;
import ch.fhnw.roundtable.etopia.shapes.Intersections;
import ch.fhnw.roundtable.etopia.views.geothermal.state.GeothermalRockState;

import java.util.Random;

public class Rock implements Renderable<GeothermalRockState> {

    private final float x;
    private final float y;
    private final float width;
    private final float height;
    private final Circle bounds;
    private final float rotation;

    public Rock(Configuration configuration, Random random) {
        this.x = random.nextFloat(configuration.worldWidth());
        this.y = random.nextFloat(configuration.geothermal().mapHeight() - configuration.worldHeight());
        this.width = configuration.geothermal().rockWidth();
        this.height = configuration.geothermal().rockHeight();
        this.rotation = random.nextFloat(360);
        this.bounds = new Circle(x, y, width / 2);
    }

    public boolean intersects(Circle circle) {
        return Intersections.intersects(bounds, circle);
    }

    @Override
    public GeothermalRockState state() {
        return new GeothermalRockState(
                x,
                y,
                width,
                height,
                rotation
        );
    }
}
