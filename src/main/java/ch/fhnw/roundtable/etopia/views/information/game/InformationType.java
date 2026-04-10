package ch.fhnw.roundtable.etopia.views.information.game;

import ch.fhnw.roundtable.etopia.views.ViewType;

public enum InformationType {
    INFORMATION("information", ViewType.MAP),

    BIOGAS_START("biogas.information.start", ViewType.BIOGAS),
    BIOGAS_SUCCESS("biogas.information.success", ViewType.MAP),
    BIOGAS_FAIL_HEALTH("biogas.information.failHealth", ViewType.MAP),

    WIND_START("wind.information.start", ViewType.WIND),
    WIND_SUCCESS("wind.information.success", ViewType.MAP),
    WIND_FAIL_HEALTH("wind.information.failHealth", ViewType.MAP),
    WIND_FAIL_CLOCK("wind.information.failClock", ViewType.MAP),

    GEOTHERMAL_START("geothermal.information.start", ViewType.GEOTHERMAL),
    GEOTHERMAL_SUCCESS("geothermal.information.success", ViewType.MAP),
    GEOTHERMAL_FAIL_HEALTH("geothermal.information.failHealth", ViewType.MAP),

    SOLAR_START("solar.information.start", ViewType.SOLAR),
    SOLAR_SUCCESS("solar.information.success", ViewType.MAP),
    SOLAR_FAIL_CLOCK("solar.information.failClock", ViewType.MAP),

    GRID_START("grid.information.start", ViewType.GRID),
    GRID_SUCCESS("grid.information.success", ViewType.MAP),
    GRID_FAIL_CLOCK("grid.information.failClock", ViewType.MAP);

    private final String languagePrefix;
    private final ViewType next;

    InformationType(String languagePrefix, ViewType next) {
        this.languagePrefix = languagePrefix;
        this.next = next;
    }

    public String getLanguagePrefix() {
        return languagePrefix;
    }

    public ViewType getNext() {
        return next;
    }
}
