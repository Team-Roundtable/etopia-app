package ch.fhnw.roundtable.etopia.views.geothermal.model;

import ch.fhnw.roundtable.etopia.configuration.Configuration;
import ch.fhnw.roundtable.etopia.shapes.Circle;
import ch.fhnw.roundtable.etopia.views.geothermal.state.GeothermalRockState;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Rocks implements Iterable<Rock> {

    private final Random random;
    private final Configuration configuration;
    private final List<Rock> elements = new ArrayList<>();

    public Rocks(Configuration configuration, Random random) {
        this.random = random;
        this.configuration = configuration;
        generate();
    }

    public boolean intersects(Circle circle) {
        for (Rock rock : elements) {
            if (rock.intersects(circle)) {
                return true;
            }
        }

        return false;
    }

    private void generate() {
        for (int i = 0; i < configuration.geothermal().numberOfRocks(); i++) {
            elements.add(new Rock(configuration, random));
        }
    }

    @Override
    public Iterator<Rock> iterator() {
        return elements.iterator();
    }

    public List<GeothermalRockState> getState() {
        return elements.stream()
                .map(Rock::state)
                .toList();
    }
}
