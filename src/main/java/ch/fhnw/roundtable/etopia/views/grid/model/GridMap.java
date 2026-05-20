package ch.fhnw.roundtable.etopia.views.grid.model;

import ch.fhnw.roundtable.etopia.configuration.Configuration;

import java.util.List;

import static ch.fhnw.roundtable.etopia.views.grid.model.Direction.*;
import static ch.fhnw.roundtable.etopia.views.grid.model.PipeType.*;

public final class GridMap {

    private GridMap() {

    }

    public static List<Pipe> getProducers(Configuration configuration) {
        return List.of(
                fossil(configuration),
                biogas(configuration),
                wind(configuration),
                geothermal(configuration),
                solar(configuration)
        );
    }

    private static Pipe fossil(Configuration configuration) {
        var producer = new Pipe(configuration, 13, 13, PRODUCER, 0);

        var fork1 = producer.createNeighbour(UP, THREE_WAY_IMMUTABLE, 0);

        var fork2 = fork1.createNeighbour(RIGHT, STRAIGHT_IMMUTABLE, 0)
                .createNeighbour(RIGHT, CORNER_IMMUTABLE, 0)
                .createNeighbour(DOWN, STRAIGHT_IMMUTABLE, 1)
                .createNeighbour(DOWN, STRAIGHT_EDITABLE, 0)
                .createNeighbour(DOWN, STRAIGHT_IMMUTABLE, 1)
                .createNeighbour(DOWN, CORNER_IMMUTABLE, 3)
                .createNeighbour(LEFT, STRAIGHT_IMMUTABLE, 0)
                .createNeighbour(LEFT, THREE_WAY_IMMUTABLE, 0);

        fork1.createNeighbour(LEFT, STRAIGHT_IMMUTABLE, 0)
                .createNeighbour(LEFT, CORNER_IMMUTABLE, 1)
                .createNeighbour(DOWN, STRAIGHT_IMMUTABLE, 1)
                .createNeighbour(DOWN, STRAIGHT_EDITABLE, 0)
                .createNeighbour(DOWN, STRAIGHT_IMMUTABLE, 1)
                .createNeighbour(DOWN, CORNER_IMMUTABLE, 2)
                .createNeighbour(RIGHT, STRAIGHT_IMMUTABLE, 0)
                .addNeighbour(RIGHT, fork2);

        fork2.createNeighbour(DOWN, STRAIGHT_IMMUTABLE, 1)
                .createNeighbour(DOWN, STRAIGHT_IMMUTABLE, 1)
                .createNeighbour(DOWN, CONSUMER, 0);

        return producer;
    }

    private static Pipe biogas(Configuration configuration) {
        var producer = new Pipe(configuration, 5, 10, PRODUCER, 0);

        producer.createNeighbour(RIGHT, STRAIGHT_IMMUTABLE, 0)
                .createNeighbour(RIGHT, STRAIGHT_EDITABLE, 1)
                .createNeighbour(RIGHT, STRAIGHT_IMMUTABLE, 0)
                .createNeighbour(RIGHT, CORNER_EDITABLE, 1)
                .createNeighbour(DOWN, STRAIGHT_IMMUTABLE, 1)
                .createNeighbour(DOWN, CORNER_IMMUTABLE, 2)
                .createNeighbour(RIGHT, STRAIGHT_EDITABLE, 1)
                .createNeighbour(RIGHT, STRAIGHT_IMMUTABLE, 0)
                .createNeighbour(RIGHT, CONSUMER, 0);

        return producer;
    }

    private static Pipe wind(Configuration configuration) {
        var producer = new Pipe(configuration, 4, 5, PRODUCER, 0);

        producer.createNeighbour(RIGHT, STRAIGHT_EDITABLE, 1)
                .createNeighbour(RIGHT, STRAIGHT_IMMUTABLE, 0)
                .createNeighbour(RIGHT, CORNER_IMMUTABLE, 0)
                .createNeighbour(DOWN, CORNER_IMMUTABLE, 2)
                .createNeighbour(RIGHT, STRAIGHT_EDITABLE, 1)
                .createNeighbour(RIGHT, STRAIGHT_IMMUTABLE, 0)
                .createNeighbour(RIGHT, STRAIGHT_EDITABLE, 1)
                .createNeighbour(RIGHT, STRAIGHT_IMMUTABLE, 0)
                .createNeighbour(RIGHT, CORNER_IMMUTABLE, 3)
                .createNeighbour(UP, STRAIGHT_IMMUTABLE, 1)
                .createNeighbour(UP, CONSUMER, 0);

        return producer;
    }

    private static Pipe geothermal(Configuration configuration) {
        var producer = new Pipe(configuration, 25, 8, PRODUCER, 0);

        var fork1 = producer.createNeighbour(LEFT, STRAIGHT_IMMUTABLE, 0)
                .createNeighbour(LEFT, STRAIGHT_IMMUTABLE, 0)
                .createNeighbour(LEFT, THREE_WAY_IMMUTABLE, 0);

        var fork2 = fork1.createNeighbour(LEFT, STRAIGHT_IMMUTABLE, 0)
                .createNeighbour(LEFT, STRAIGHT_IMMUTABLE, 0)
                .createNeighbour(LEFT, CORNER_EDITABLE, 2)
                .createNeighbour(DOWN, STRAIGHT_IMMUTABLE, 1)
                .createNeighbour(DOWN, THREE_WAY_EDITABLE, 2);

        fork1.createNeighbour(DOWN, STRAIGHT_EDITABLE, 0)
                .createNeighbour(DOWN, CORNER_IMMUTABLE, 3)
                .createNeighbour(LEFT, STRAIGHT_IMMUTABLE, 0)
                .createNeighbour(LEFT, STRAIGHT_IMMUTABLE, 0)
                .addNeighbour(LEFT, fork2);

        fork2.createNeighbour(DOWN, STRAIGHT_IMMUTABLE, 1)
                .createNeighbour(DOWN, CORNER_IMMUTABLE, 3)
                .createNeighbour(LEFT, STRAIGHT_IMMUTABLE, 0)
                .createNeighbour(LEFT, STRAIGHT_IMMUTABLE, 0)
                .createNeighbour(LEFT, CORNER_IMMUTABLE, 2)
                .createNeighbour(UP, STRAIGHT_EDITABLE, 0)
                .createNeighbour(UP, CORNER_IMMUTABLE, 0)
                .createNeighbour(LEFT, STRAIGHT_IMMUTABLE, 0)
                .createNeighbour(LEFT, CONSUMER, 0);

        return producer;
    }

    private static Pipe solar(Configuration configuration) {
        var producer = new Pipe(configuration, 23, 13, PRODUCER, 0);

        var fork1 = producer.createNeighbour(DOWN, STRAIGHT_IMMUTABLE, 1)
                .createNeighbour(DOWN, CORNER_EDITABLE, 0)
                .createNeighbour(LEFT, STRAIGHT_IMMUTABLE, 0)
                .createNeighbour(LEFT, THREE_WAY_IMMUTABLE, 2);

        producer.createNeighbour(LEFT, STRAIGHT_IMMUTABLE, 0)
                .createNeighbour(LEFT, CORNER_EDITABLE, 2)
                .createNeighbour(DOWN, STRAIGHT_IMMUTABLE, 1)
                .addNeighbour(DOWN, fork1);

        fork1.createNeighbour(LEFT, STRAIGHT_IMMUTABLE, 0)
                .createNeighbour(LEFT, STRAIGHT_EDITABLE, 1)
                .createNeighbour(LEFT, STRAIGHT_IMMUTABLE, 0)
                .createNeighbour(LEFT, CORNER_IMMUTABLE, 1)
                .createNeighbour(DOWN, STRAIGHT_IMMUTABLE, 1)
                .createNeighbour(DOWN, STRAIGHT_EDITABLE, 0)
                .createNeighbour(DOWN, CORNER_IMMUTABLE, 3)
                .createNeighbour(LEFT, STRAIGHT_IMMUTABLE, 0)
                .createNeighbour(LEFT, STRAIGHT_IMMUTABLE, 0)
                .createNeighbour(LEFT, CONSUMER, 0);

        return producer;
    }
}
