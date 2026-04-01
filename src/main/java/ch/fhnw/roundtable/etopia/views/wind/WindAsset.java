package ch.fhnw.roundtable.etopia.views.wind;

import ch.fhnw.roundtable.etopia.views.Asset;

public enum WindAsset implements Asset {
    BACKGROUND("assets/wind/background.png"),
    POLE("assets/wind/turbine-pole.png"),
    TURBINE("assets/wind/turbine.png"),
    GUST("assets/wind/gust.png"),
    TORNADO("assets/wind/tornado.png"),
    POWER_BAR("assets/wind/power-bar.png"),
    POWER_INDICATOR("assets/wind/power-indicator.png"),
    LIFEPOINT("assets/wind/lifepoint.png");

    private final String path;

    WindAsset(String path) {
        this.path = path;
    }

    @Override
    public String getPath() {
        return path;
    }
}
