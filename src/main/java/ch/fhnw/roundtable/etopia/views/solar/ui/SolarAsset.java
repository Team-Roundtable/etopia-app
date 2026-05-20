package ch.fhnw.roundtable.etopia.views.solar.ui;

import ch.fhnw.roundtable.etopia.rendering.Asset;

public enum SolarAsset implements Asset {
    SUN("assets/solar/sun.png"),
    PANEL("assets/solar/panel.png"),
    STAND("assets/solar/stand.png"),
    BACKGROUND("assets/solar/background.png");

    private final String path;

    SolarAsset(String path) {
        this.path = path;
    }

    @Override
    public String getPath() {
        return path;
    }
}
