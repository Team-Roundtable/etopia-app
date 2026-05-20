package ch.fhnw.roundtable.etopia.views.map.model;

import ch.fhnw.roundtable.etopia.View;
import ch.fhnw.roundtable.etopia.configuration.Configuration;
import ch.fhnw.roundtable.etopia.views.biogas.Biogas;
import ch.fhnw.roundtable.etopia.views.geothermal.Geothermal;
import ch.fhnw.roundtable.etopia.views.grid.Grid;
import ch.fhnw.roundtable.etopia.views.information.Information;
import ch.fhnw.roundtable.etopia.views.information.model.InformationType;
import ch.fhnw.roundtable.etopia.views.settings.Settings;
import ch.fhnw.roundtable.etopia.views.solar.Solar;
import ch.fhnw.roundtable.etopia.views.wind.Wind;

import java.util.Map;
import java.util.function.Supplier;

import static ch.fhnw.roundtable.etopia.views.map.model.Direction.*;

public enum Building {
    SETTINGS,
    BIOGAS,
    INFORMATION,
    SOLAR,
    WIND,
    GRID,
    GEOTHERMAL;

    static {
        SETTINGS.transitions = Map.of(DOWN, WIND, RIGHT, INFORMATION);
        WIND.transitions = Map.of(UP, SETTINGS, DOWN, BIOGAS, RIGHT, INFORMATION);
        INFORMATION.transitions = Map.of(UP, SETTINGS, DOWN, GRID, LEFT, WIND, RIGHT, SOLAR);
        SOLAR.transitions = Map.of(UP, SETTINGS, DOWN, GEOTHERMAL, LEFT, INFORMATION);
        BIOGAS.transitions = Map.of(UP, WIND, RIGHT, GRID);
        GRID.transitions = Map.of(UP, INFORMATION, LEFT, BIOGAS, RIGHT, GEOTHERMAL);
        GEOTHERMAL.transitions = Map.of(UP, SOLAR, LEFT, GRID);
    }

    private Map<Direction, Building> transitions;

    public Building next(Direction direction) {
        return transitions.getOrDefault(direction, this);
    }

    public Supplier<View> view(Configuration configuration) {
        return switch (this) {
            case SETTINGS -> () -> new Settings(configuration);
            case INFORMATION -> () -> new Information(configuration, InformationType.INFORMATION,
                    () -> new ch.fhnw.roundtable.etopia.views.map.Map(configuration));
            case WIND -> () -> new Information(configuration, InformationType.WIND_START,
                    () -> new Wind(configuration));
            case BIOGAS -> () -> new Information(configuration, InformationType.BIOGAS_START,
                    () -> new Biogas(configuration));
            case SOLAR -> () -> new Information(configuration, InformationType.SOLAR_START,
                    () -> new Solar(configuration));
            case GEOTHERMAL -> () -> new Information(configuration, InformationType.GEOTHERMAL_START,
                    () -> new Geothermal(configuration));
            case GRID -> () -> new Information(configuration, InformationType.GRID_START,
                    () -> new Grid(configuration));
        };
    }

    public boolean success(Configuration configuration) {
        return switch (this) {
            case SETTINGS, INFORMATION -> false;
            case BIOGAS -> configuration.state().isBiogasSuccess();
            case SOLAR -> configuration.state().isSolarSuccess();
            case WIND -> configuration.state().isWindSuccess();
            case GRID -> configuration.state().isGridSuccess();
            case GEOTHERMAL -> configuration.state().isGeothermalSuccess();
        };
    }
}
