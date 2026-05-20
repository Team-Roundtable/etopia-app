package ch.fhnw.roundtable.etopia.views.map.model;

import ch.fhnw.roundtable.etopia.Renderable;
import ch.fhnw.roundtable.etopia.configuration.Configuration;
import ch.fhnw.roundtable.etopia.views.map.state.MapLocationState;

public class Location implements Renderable<MapLocationState> {

    private final Configuration configuration;
    private final Building building;
    private final float x;
    private final float y;
    private final float side;

    public Location(Configuration configuration, Building building, float x, float y) {
        this.configuration = configuration;
        this.building = building;
        this.x = x;
        this.y = y;
        this.side = building == Building.SETTINGS
                ? configuration.map().settingsSide()
                : configuration.map().buildingSide();
    }

    @Override
    public MapLocationState state() {
        return new MapLocationState(
                x,
                y,
                side,
                side,
                building,
                building.success(configuration));
    }

    public Building getBuilding() {
        return building;
    }
}
