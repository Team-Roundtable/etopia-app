package ch.fhnw.roundtable.etopia.views.grid.model;

public final class PipeInteractionService {

    private PipeInteractionService() {

    }

    public static boolean rotate(Pipe pipe, Cursor cursor) {
        if (pipe.getType().isEditable() && pipe.intersects(cursor)) {
            pipe.rotate();
            return true;
        }

        for (var neighbour : pipe.getNeighbours().values()) {
            if (rotate(neighbour, cursor)) {
                return true;
            }
        }

        return false;
    }
}
