package ch.fhnw.roundtable.etopia.views.geothermal;

import ch.fhnw.roundtable.etopia.views.Asset;

public enum GeothermalAsset implements Asset {
    PANEL("assets/commons/panel/background.png"),
    DRILL("assets/geothermal/drill.png"),
    PIPE("assets/geothermal/pipe.png");

    private final String path;

    GeothermalAsset(String path) {
        this.path = path;
    }

    @Override
    public String getPath() {
        return path;
    }
}
