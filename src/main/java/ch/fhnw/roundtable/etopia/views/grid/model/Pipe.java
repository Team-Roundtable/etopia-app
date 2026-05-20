package ch.fhnw.roundtable.etopia.views.grid.model;

import ch.fhnw.roundtable.etopia.Renderable;
import ch.fhnw.roundtable.etopia.configuration.Configuration;
import ch.fhnw.roundtable.etopia.views.grid.state.GridPipeState;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class Pipe extends Tile implements Renderable<List<GridPipeState>> {

    private final Configuration configuration;
    private final PipeType type;
    private final Map<Direction, Pipe> neighbours = new EnumMap<>(Direction.class);

    private int rotation;
    private boolean powered = false;

    public Pipe(Configuration configuration, int x, int y, PipeType type, int rotation) {
        super(configuration, x, y);
        this.configuration = configuration;
        this.type = type;
        this.rotation = rotation;
    }

    @Override
    public List<GridPipeState> state() {
        var list = new ArrayList<GridPipeState>();

        list.add(new GridPipeState(
                getX(),
                getY(),
                side,
                side,
                rotation * 90,
                type,
                powered
        ));

        for (var neighbour : neighbours.values()) {
            list.addAll(neighbour.state());
        }

        return list;
    }

    public Pipe createNeighbour(Direction direction, PipeType neighbourType, int neighbourRotation) {
        int neighbourX = x + direction.dx;
        int neighbourY = y + direction.dy;

        var neighbour = new Pipe(configuration, neighbourX, neighbourY, neighbourType, neighbourRotation);
        neighbours.put(direction, neighbour);

        return neighbour;
    }

    public void addNeighbour(Direction direction, Pipe neighbour) {
        neighbours.put(direction, neighbour);
    }

    public PipeType getType() {
        return type;
    }

    public int getRotation() {
        return rotation;
    }

    public Map<Direction, Pipe> getNeighbours() {
        return neighbours;
    }

    public boolean isPowered() {
        return powered;
    }

    public void setPowered(boolean powered) {
        this.powered = powered;
    }

    public void rotate() {
        rotation++;
    }
}
