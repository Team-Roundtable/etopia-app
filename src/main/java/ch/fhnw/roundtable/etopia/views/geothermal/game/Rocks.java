package ch.fhnw.roundtable.etopia.views.geothermal.game;

import ch.fhnw.roundtable.etopia.ETopia;
import ch.fhnw.roundtable.etopia.shapes.Circle;
import ch.fhnw.roundtable.etopia.views.geothermal.GeothermalConfiguration;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Rocks implements Iterable<Rock> {

    private final Random random;
    private final GeothermalConfiguration geothermalConfiguration;
    private final List<Rock> elements = new ArrayList<>();

    public Rocks(Random random, GeothermalConfiguration geothermalConfiguration) {
        this.random = random;
        this.geothermalConfiguration = geothermalConfiguration;
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
        var mapSize = geothermalConfiguration.mapSize;
        for (int i = 0; i < geothermalConfiguration.numberOfRocks; i++) {
            elements.add(new Rock(geothermalConfiguration.rockSize,
                    random.nextInt(0, (int) mapSize.width()),
                    random.nextInt(0, (int) mapSize.height() - ETopia.WORLD_HEIGHT)));
        }
    }

    @Override
    public Iterator<Rock> iterator() {
        return elements.iterator();
    }
}
