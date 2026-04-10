package ch.fhnw.roundtable.etopia.views.map.game;

import ch.fhnw.roundtable.etopia.input.Input;
import ch.fhnw.roundtable.etopia.views.Game;
import ch.fhnw.roundtable.etopia.views.ViewType;
import ch.fhnw.roundtable.etopia.views.map.MapConfiguration;

import java.util.EnumMap;
import java.util.Map;

public class MapGame implements Game {

    private final Map<LocationType, Location> locations;
    private LocationType hovered = LocationType.INFORMATION;
    private boolean selected;

    public MapGame(MapConfiguration mapConfiguration) {

        this.locations = new EnumMap<>(LocationType.class);
        locations.put(LocationType.SETTINGS, new Location(mapConfiguration.technologySize, 900, 900));
        locations.put(LocationType.BIOGAS, new Location(mapConfiguration.technologySize, 200, 800));
        locations.put(LocationType.INFORMATION, new Location(mapConfiguration.technologySize, 800, 700));
        locations.put(LocationType.SOLAR, new Location(mapConfiguration.technologySize, 1500, 800));
        locations.put(LocationType.WIND, new Location(mapConfiguration.technologySize, 200, 200));
        locations.put(LocationType.GRID, new Location(mapConfiguration.technologySize, 1000, 400));
        locations.put(LocationType.GEOTHERMAL, new Location(mapConfiguration.technologySize, 1500, 300));
    }

    @SuppressWarnings("PMD.ExhaustiveSwitchHasDefault")
    @Override
    public void update(float delta, Input input) {

        switch (hovered) {
            case SETTINGS -> {
                if (input.isDownJustPressed()) hovered = LocationType.INFORMATION;
                if (input.isLeftJustPressed()) hovered = LocationType.BIOGAS;
                if (input.isRightJustPressed()) hovered = LocationType.SOLAR;
            }
            case BIOGAS -> {
                if (input.isDownJustPressed()) hovered = LocationType.WIND;
                if (input.isRightJustPressed()) hovered = LocationType.INFORMATION;
            }
            case INFORMATION -> {
                if (input.isUpJustPressed()) hovered = LocationType.SETTINGS;
                if (input.isDownJustPressed()) hovered = LocationType.GRID;
                if (input.isLeftJustPressed()) hovered = LocationType.BIOGAS;
                if (input.isRightJustPressed()) hovered = LocationType.SOLAR;
            }
            case SOLAR -> {
                if (input.isDownJustPressed()) hovered = LocationType.GEOTHERMAL;
                if (input.isLeftJustPressed()) hovered = LocationType.INFORMATION;
            }
            case WIND -> {
                if (input.isUpJustPressed()) hovered = LocationType.BIOGAS;
                if (input.isRightJustPressed()) hovered = LocationType.GRID;
            }
            case GRID -> {
                if (input.isUpJustPressed()) hovered = LocationType.INFORMATION;
                if (input.isLeftJustPressed()) hovered = LocationType.WIND;
                if (input.isRightJustPressed()) hovered = LocationType.GEOTHERMAL;
            }
            case GEOTHERMAL -> {
                if (input.isUpJustPressed()) hovered = LocationType.SOLAR;
                if (input.isLeftJustPressed()) hovered = LocationType.GRID;
            }
            default -> {
            }
        }

        selected = input.isSelectJustPressed();
    }

    @Override
    public ViewType next() {
        if (selected) {
            return switch (hovered) {
                case SETTINGS -> ViewType.MAP;
                case INFORMATION -> ViewType.INFORMATION;
                case WIND -> ViewType.WIND_START;
                case BIOGAS -> ViewType.BIOGAS_START;
                case SOLAR -> ViewType.SOLAR_START;
                case GEOTHERMAL -> ViewType.GEOTHERMAL_START;
                case GRID -> ViewType.GRID_START;
            };
        } else {
            return null;
        }
    }

    public LocationType getHovered() {
        return hovered;
    }

    public Map<LocationType, Location> getLocations() {
        return locations;
    }
}
