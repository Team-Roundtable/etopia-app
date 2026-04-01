package ch.fhnw.roundtable.etopia.views.solar;

import ch.fhnw.roundtable.etopia.views.Asset;

public enum SolarAsset implements Asset {
    SUN("assets/solar/sun.png"),
    PANEL("assets/solar/panel.png"),
    STAND("assets/solar/panelStand.png"),
    POWER_BAR("assets/solar/power-bar.png"),
    POWER_INDICATOR("assets/solar/power-indicator.png"),
    DIRT("assets/solar/dirt.png"),
    BACKGROUND("assets/solar/simpleBackground.png");

    private final String path;

    SolarAsset(String path) {
        this.path = path;
    }

    @Override
    public String getPath() {
        return path;
    }
}
