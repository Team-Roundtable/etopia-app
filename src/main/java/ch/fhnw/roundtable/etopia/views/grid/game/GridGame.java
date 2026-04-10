package ch.fhnw.roundtable.etopia.views.grid.game;

import ch.fhnw.roundtable.etopia.input.Input;
import ch.fhnw.roundtable.etopia.shapes.Vector;
import ch.fhnw.roundtable.etopia.views.Game;
import ch.fhnw.roundtable.etopia.views.ViewType;
import ch.fhnw.roundtable.etopia.views.clock.game.ClockGame;
import ch.fhnw.roundtable.etopia.views.grid.GridConfiguration;

import java.util.ArrayList;

public class GridGame implements Game {

    private final GridConfiguration gridConfiguration;
    private final Cursor cursor;
    private final Pipes pipes;
    private final ClockGame clockGame;

    public GridGame(GridConfiguration gridConfiguration) {
        this.gridConfiguration = gridConfiguration;
        this.cursor = new Cursor(gridConfiguration, 10, 10);
        this.pipes = new Pipes(gridConfiguration);
        this.clockGame = new ClockGame(30);
    }

    @Override
    public void update(float delta, Input input) {
        clockGame.update(delta, input);
        cursor.update(input);

        if (input.isSelectJustPressed()) {
            var selected = pipes.get(cursor.getX(), cursor.getY());

            if (selected != null && selected.isEditable()) {
                selected.rotate();
            }
        }

        var producers = new ArrayList<Vector>();
        for (int x = 0; x < pipes.width(); x++) {
            for (int y = 0; y < pipes.height(); y++) {
                var pipe = pipes.get(x, y);

                if (pipe != null) {
                    pipe.setPowered(false);

                    if (pipe.getType() == PipeType.PRODUCER) {
                        producers.add(new Vector(x, y));
                    }
                }
            }
        }

        for (var producer : producers) {
            propagatePower((int) producer.x(), (int) producer.y());
        }
    }

    @Override
    public ViewType next() {
        if (clockGame.finished()) {
            return ViewType.GRID_FAIL_CLOCK;
        }

        if (pipes.consumersPowered()) {
            return ViewType.GRID_SUCCESS;
        }

        return null;
    }

    public Cursor getCursor() {
        return cursor;
    }

    public Pipes getPipes() {
        return pipes;
    }

    public ClockGame getClockGame() {
        return clockGame;
    }

    private void propagatePower(int x, int y) {
        var pipe = pipes.get(x, y);

        if (pipe == null || pipe.isPowered()) {
            return;
        }

        pipe.setPowered(true);

        propagatePowerDirection(pipe, Direction.UP, x, y + 1);
        propagatePowerDirection(pipe, Direction.DOWN, x, y - 1);
        propagatePowerDirection(pipe, Direction.LEFT, x - 1, y);
        propagatePowerDirection(pipe, Direction.RIGHT, x + 1, y);
    }

    private void propagatePowerDirection(Pipe origin, Direction direction, int x, int y) {
        if (!origin.connects(direction)) {
            return;
        }

        if (x < 0 || x >= gridConfiguration.mapWidth
                || y < 0 || y >= gridConfiguration.mapHeight) {
            return;
        }

        var neighbour = pipes.get(x, y);

        if (neighbour != null && neighbour.connects(direction.opposite())) {
            propagatePower(x, y);
        }
    }
}
