package ch.fhnw.roundtable.etopia.views.grid.game;

public class Pipe {

    private final PipeType type;
    private int rotation;
    private boolean powered = false;

    public Pipe(PipeType type) {
        this(type, 0);
    }

    public Pipe(PipeType type, int rotation) {
        this.type = type;
        this.rotation = rotation;
    }

    public boolean connects(Direction direction) {
        return switch (type) {
            case PRODUCER, CONSUMER, CROSS_IMMUTABLE -> true;
            case STRAIGHT_IMMUTABLE, STRAIGHT_EDITABLE -> switch (direction) {
                case UP, DOWN -> rotation % 2 == 1;
                case LEFT, RIGHT -> rotation % 2 == 0;
            };
            case CORNER_IMMUTABLE, CORNER_EDITABLE -> switch (direction) {
                case UP -> rotation % 4 == 2 || rotation % 4 == 3;
                case DOWN -> rotation % 4 == 0 || rotation % 4 == 1;
                case LEFT -> rotation % 4 == 0 || rotation % 4 == 3;
                case RIGHT -> rotation % 4 == 1 || rotation % 4 == 2;
            };
            case THREE_WAY_IMMUTABLE, THREE_WAY_EDITABLE -> switch (direction) {
                case UP -> rotation % 4 == 1 || rotation % 4 == 2 || rotation % 4 == 3;
                case DOWN -> rotation % 4 == 0 || rotation % 4 == 1 || rotation % 4 == 3;
                case LEFT -> rotation % 4 == 0 || rotation % 4 == 2 || rotation % 4 == 3;
                case RIGHT -> rotation % 4 == 0 || rotation % 4 == 1 || rotation % 4 == 2;
            };
        };
    }

    public boolean isEditable() {
        return switch (type) {
            case PRODUCER, CONSUMER, STRAIGHT_IMMUTABLE, CROSS_IMMUTABLE,
                 CORNER_IMMUTABLE, THREE_WAY_IMMUTABLE -> false;
            case STRAIGHT_EDITABLE, CORNER_EDITABLE, THREE_WAY_EDITABLE -> true;
        };
    }

    public PipeType getType() {
        return type;
    }

    public void rotate() {
        rotation++;
    }

    public boolean isPowered() {
        return powered;
    }

    public void setPowered(boolean powered) {
        this.powered = powered;
    }

    public int getRotation() {
        return rotation;
    }
}
