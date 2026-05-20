package ch.fhnw.roundtable.etopia.views.information.model;

public enum InformationType {
    INFORMATION("information", true),
    BIOGAS_START("information.biogas.start", true),
    BIOGAS_SUCCESS("information.biogas.success", false),
    BIOGAS_FAIL_HEALTH("information.biogas.failHealth", false),
    BIOGAS_FAIL_TIME("information.biogas.failTime", false),
    WIND_START("information.wind.start", true),
    WIND_SUCCESS("information.wind.success", false),
    WIND_FAIL_HEALTH("information.wind.failHealth", false),
    WIND_FAIL_TIME("information.wind.failTime", false),
    GEOTHERMAL_START("information.geothermal.start", true),
    GEOTHERMAL_SUCCESS("information.geothermal.success", false),
    GEOTHERMAL_FAIL_HEALTH("information.geothermal.failHealth", false),
    GEOTHERMAL_FAIL_TIME("information.geothermal.failTime", false),
    SOLAR_START("information.solar.start", true),
    SOLAR_SUCCESS("information.solar.success", false),
    SOLAR_FAIL_TIME("information.solar.failTime", false),
    GRID_START("information.grid.start", true),
    GRID_SUCCESS("information.grid.success", false),
    GRID_FAIL_TIME("information.grid.failTime", false);

    private final String languagePrefix;
    private final boolean escapable;

    InformationType(String languagePrefix, boolean escapable) {
        this.languagePrefix = languagePrefix;
        this.escapable = escapable;
    }

    public String getLanguagePrefix() {
        return languagePrefix;
    }

    public boolean isEscapable() {
        return escapable;
    }
}
