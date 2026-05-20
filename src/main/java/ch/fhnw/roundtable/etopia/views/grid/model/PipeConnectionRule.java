package ch.fhnw.roundtable.etopia.views.grid.model;

public final class PipeConnectionRule {

    private PipeConnectionRule() {

    }

    public static boolean connected(Pipe a, Direction direction, Pipe b) {
        return connects(a.getType(), direction, a.getRotation())
                && connects(b.getType(), direction.opposite(), b.getRotation());
    }

    @SuppressWarnings("PMD.CyclomaticComplexity")
    private static boolean connects(PipeType type, Direction direction, int rotation) {
        int r = rotation % 4;

        return switch (type) {
            case PRODUCER, CONSUMER, CROSS_IMMUTABLE -> true;

            case STRAIGHT_IMMUTABLE, STRAIGHT_EDITABLE -> isStraightConnected(direction, r);

            case CORNER_IMMUTABLE, CORNER_EDITABLE -> isCornerConnected(direction, r);

            case THREE_WAY_IMMUTABLE, THREE_WAY_EDITABLE -> isThreeWayConnected(direction, r);
        };
    }

    private static boolean isStraightConnected(Direction direction, int rotation) {
        return switch (direction) {
            case UP, DOWN -> rotation % 2 == 1;
            case LEFT, RIGHT -> rotation % 2 == 0;
        };
    }

    private static boolean isCornerConnected(Direction direction, int rotation) {
        return switch (direction) {
            case UP -> rotation == 2 || rotation == 3;
            case DOWN -> rotation == 0 || rotation == 1;
            case LEFT -> rotation == 0 || rotation == 3;
            case RIGHT -> rotation == 1 || rotation == 2;
        };
    }

    private static boolean isThreeWayConnected(Direction direction, int rotation) {
        return switch (direction) {
            case UP -> rotation != 0;
            case DOWN -> rotation != 2;
            case LEFT -> rotation != 1;
            case RIGHT -> rotation != 3;
        };
    }
}
