package ch.fhnw.roundtable.etopia.views.wind;

import ch.fhnw.roundtable.etopia.views.Asset;
import com.badlogic.gdx.graphics.Texture;

public enum WindAsset implements Asset {
    BACKGROUND("assets/wind/background.png"),
    POLE("assets/wind/turbine-pole.png"),
    TURBINE("assets/wind/turbine.png"),
    PRODUCTION_BAR("assets/wind/production-bar.png"),
    PRODUCTION_INDICATOR("assets/wind/production-indicator.png"),
    POWER_BAR("assets/wind/power-bar.png"),
    POWER_INDICATOR("assets/wind/power-indicator.png"),
    GUST("assets/wind/gust.png");

    private final String path;

    WindAsset(String path) {
        this.path = path;
    }

    @Override
    public String getPath() {
        return path;
    }
}
