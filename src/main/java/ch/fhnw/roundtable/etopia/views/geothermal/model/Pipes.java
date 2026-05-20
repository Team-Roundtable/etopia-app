package ch.fhnw.roundtable.etopia.views.geothermal.model;

import ch.fhnw.roundtable.etopia.configuration.Configuration;
import ch.fhnw.roundtable.etopia.shapes.Angles;
import ch.fhnw.roundtable.etopia.shapes.Circle;
import ch.fhnw.roundtable.etopia.shapes.Vector;
import ch.fhnw.roundtable.etopia.views.geothermal.state.GeothermalPipeState;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Pipes implements Iterable<Pipe> {

    private final Configuration configuration;
    private final List<Pipe> elements = new ArrayList<>();
    private Vector previous;

    public Pipes(Configuration configuration) {
        this.configuration = configuration;
    }

    public void add(float x, float y) {
        if (previous == null) {
            previous = new Vector(x, y);
        }

        var next = new Vector(x, y);
        var midpoint = Vector.midpoint(previous, next);
        var rotation = Angles.angle(previous, next) + 90;
        previous = next;

        elements.add(new Pipe(configuration, midpoint.x(), midpoint.y(), rotation));
    }

    public boolean intersects(Circle circle) {
        // the -3 is needed to not intersect with its own tail
        for (int i = 0; i < elements.size() - 3; i++) {
            if (elements.get(i).intersects(circle)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public Iterator<Pipe> iterator() {
        return elements.iterator();
    }

    public List<GeothermalPipeState> getState() {
        return elements.stream()
                .map(Pipe::state)
                .toList();
    }
}
