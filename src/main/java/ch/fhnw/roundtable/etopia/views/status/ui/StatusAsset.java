package ch.fhnw.roundtable.etopia.views.status.ui;

import ch.fhnw.roundtable.etopia.rendering.Asset;

public enum StatusAsset implements Asset {
    BACKGROUND("assets/status/background.png"),
    BAR("assets/status/bar.png"),
    TIME("assets/status/time.png"),
    ENERGY("assets/status/energy.png"),
    HEALTH("assets/status/health.png"),
    POWER_ICON("assets/status/power.png"),
    CROSS_ICON("assets/status/cross.png");

    private final String path;

    StatusAsset(String path) {
        this.path = path;
    }

    @Override
    public String getPath() {
        return path;
    }
}
