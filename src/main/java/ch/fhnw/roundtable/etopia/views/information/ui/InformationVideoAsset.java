package ch.fhnw.roundtable.etopia.views.information.ui;

import ch.fhnw.roundtable.etopia.rendering.Asset;

public enum InformationVideoAsset implements Asset {
    BIOGAS_START("assets/information/biogas-start.webm"),
    BIOGAS_SUCCESS("assets/information/biogas-success.webm"),
    BIOGAS_FAIL("assets/information/biogas-fail.webm"),
    GEOTHERMAL_START("assets/information/geothermal-start.webm"),
    GEOTHERMAL_SUCCESS("assets/information/geothermal-success.webm"),
    GEOTHERMAL_FAIL("assets/information/geothermal-fail.webm"),
    GRID_START("assets/information/grid-start.webm"),
    GRID_SUCCESS("assets/information/grid-success.webm"),
    SOLAR_START("assets/information/solar-start.webm"),
    SOLAR_SUCCESS("assets/information/solar-success.webm"),
    WIND_START("assets/information/wind-start.webm"),
    WIND_SUCCESS("assets/information/wind-success.webm"),
    WIND_FAIL("assets/information/wind-fail.webm");

    private final String path;

    InformationVideoAsset(String path) {
        this.path = path;
    }

    @Override
    public String getPath() {
        return path;
    }
}
