package ch.fhnw.roundtable.etopia.views.map.state;

import ch.fhnw.roundtable.etopia.views.map.model.Building;

public record MapLocationState(
        float x,
        float y,
        float width,
        float height,
        Building building,
        boolean success
) {
}
