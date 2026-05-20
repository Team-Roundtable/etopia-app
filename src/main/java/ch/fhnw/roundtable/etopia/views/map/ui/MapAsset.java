package ch.fhnw.roundtable.etopia.views.map.ui;

import ch.fhnw.roundtable.etopia.rendering.Asset;

public enum MapAsset implements Asset {
    BACKGROUND("assets/map/background.png"),
    INFORMATION_SELECTED("assets/map/information-selected.png"),
    INFORMATION_UNSELECTED("assets/map/information-unselected.png"),
    SETTINGS_SELECTED("assets/map/settings-selected.png"),
    SETTINGS_UNSELECTED("assets/map/settings-unselected.png"),
    BIOGAS_SUCCESS_SELECTED("assets/map/biogas-success-selected.png"),
    BIOGAS_SUCCESS_UNSELECTED("assets/map/biogas-success-unselected.png"),
    BIOGAS_FAILED_SELECTED("assets/map/biogas-failed-selected.png"),
    BIOGAS_FAILED_UNSELECTED("assets/map/biogas-failed-unselected.png"),
    GEOTHERMAL_SUCCESS_SELECTED("assets/map/geothermal-success-selected.png"),
    GEOTHERMAL_SUCCESS_UNSELECTED("assets/map/geothermal-success-unselected.png"),
    GEOTHERMAL_FAILED_SELECTED("assets/map/geothermal-failed-selected.png"),
    GEOTHERMAL_FAILED_UNSELECTED("assets/map/geothermal-failed-unselected.png"),
    GRID_SUCCESS_SELECTED("assets/map/grid-success-selected.png"),
    GRID_SUCCESS_UNSELECTED("assets/map/grid-success-unselected.png"),
    GRID_FAILED_SELECTED("assets/map/grid-failed-selected.png"),
    GRID_FAILED_UNSELECTED("assets/map/grid-failed-unselected.png"),
    SOLAR_SUCCESS_SELECTED("assets/map/solar-success-selected.png"),
    SOLAR_SUCCESS_UNSELECTED("assets/map/solar-success-unselected.png"),
    SOLAR_FAILED_SELECTED("assets/map/solar-failed-selected.png"),
    SOLAR_FAILED_UNSELECTED("assets/map/solar-failed-unselected.png"),
    WIND_SUCCESS_SELECTED("assets/map/wind-success-selected.png"),
    WIND_SUCCESS_UNSELECTED("assets/map/wind-success-unselected.png"),
    WIND_FAILED_SELECTED("assets/map/wind-failed-selected.png"),
    WIND_FAILED_UNSELECTED("assets/map/wind-failed-unselected.png"),
    BOY("assets/map/boy.png"),
    GIRL("assets/map/girl.png");

    private final String path;

    MapAsset(String path) {
        this.path = path;
    }

    @Override
    public String getPath() {
        return path;
    }
}
