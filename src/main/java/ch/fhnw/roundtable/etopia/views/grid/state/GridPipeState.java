package ch.fhnw.roundtable.etopia.views.grid.state;

import ch.fhnw.roundtable.etopia.views.grid.model.PipeType;

public record GridPipeState(
        float x,
        float y,
        float width,
        float height,
        float rotation,
        PipeType type,
        boolean powered
) {
}
