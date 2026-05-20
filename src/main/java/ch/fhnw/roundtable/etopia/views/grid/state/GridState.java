package ch.fhnw.roundtable.etopia.views.grid.state;

import java.util.List;

public record GridState(
        List<GridPipeState> pipes,
        GridCursorState cursor
) {
}
