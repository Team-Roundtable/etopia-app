package ch.fhnw.roundtable.etopia.views.map.ui;

import ch.fhnw.roundtable.etopia.views.Asset;

public enum MapAsset implements Asset {
    PANEL("assets/information/background.png"),
    BACKGROUND("assets/map/background.png"),
    WIND("assets/map/wind.png"),
    BIOGAS("assets/map/biogas.png"),
    GEOTHERMAL("assets/map/geothermal.png"),
    SOLAR("assets/map/solar.png"),
    GRID("assets/map/grid.png"),
    INFORMATION("assets/map/information.png"),
    HUMAN("assets/map/human.png");

    private final String path;

    MapAsset(String path) {
        this.path = path;
    }

    @Override
    public String getPath() {
        return path;
    }
}
