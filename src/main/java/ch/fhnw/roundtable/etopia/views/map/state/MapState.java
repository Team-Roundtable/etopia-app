package ch.fhnw.roundtable.etopia.views.map.state;

import ch.fhnw.roundtable.etopia.views.map.model.Building;

import java.util.List;

public record MapState(
        List<MapLocationState> locations,
        Building hovered,
        MapHumanState human
) {
}
