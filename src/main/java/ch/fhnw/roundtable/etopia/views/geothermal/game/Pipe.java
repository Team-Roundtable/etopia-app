package ch.fhnw.roundtable.etopia.views.geothermal.game;

import ch.fhnw.roundtable.etopia.shapes.Angles;
import ch.fhnw.roundtable.etopia.shapes.Circle;
import ch.fhnw.roundtable.etopia.shapes.Vector;
import ch.fhnw.roundtable.etopia.views.geothermal.GeothermalConfiguration;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Pipe implements Iterable<PipeSegment> {

    private final GeothermalConfiguration geothermalConfiguration;
    private final List<PipeSegment> segments = new ArrayList<>();
    private Vector previous;

    public Pipe(GeothermalConfiguration geothermalConfiguration) {
        this.geothermalConfiguration = geothermalConfiguration;
    }

    public void add(float x, float y) {
        if (previous == null) {
            previous = new Vector(x, y);
        }

        var next = new Vector(x, y);
        var midpoint = Vector.midpoint(previous, next);
        var rotation = Angles.angle(previous, next) + 90;
        previous = next;

        segments.add(new PipeSegment(geothermalConfiguration.pipeSegmentSize, midpoint.x(), midpoint.y(), rotation));
    }

    public boolean intersects(Circle circle) {
        for (PipeSegment segment : segments) {
            if (segment.intersects(circle)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public Iterator<PipeSegment> iterator() {
        return segments.iterator();
    }
}
