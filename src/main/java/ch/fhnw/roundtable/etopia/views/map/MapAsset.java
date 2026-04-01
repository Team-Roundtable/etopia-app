package ch.fhnw.roundtable.etopia.views.map;

import ch.fhnw.roundtable.etopia.views.Asset;

public enum MapAsset implements Asset {
    PANEL("assets/commons/panel/background.png"),
    BACKGROUND("assets/map/background.png"),
    WIND("assets/map/wind.png"),
    SOLAR("assets/map/solar.png"),
    BIOMASS("assets/map/biomass.png"),
    CITY("assets/map/city.png");

    private final String path;

    MapAsset(String path) {
        this.path = path;
    }

    @Override
    public String getPath() {
        return path;
    }
}
