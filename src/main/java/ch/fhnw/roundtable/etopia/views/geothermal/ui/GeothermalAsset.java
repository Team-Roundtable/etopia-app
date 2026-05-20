package ch.fhnw.roundtable.etopia.views.geothermal.ui;

import ch.fhnw.roundtable.etopia.rendering.Asset;

public enum GeothermalAsset implements Asset {
    BACKGROUND("assets/geothermal/background.png"),
    DRILL("assets/geothermal/drill.png"),
    ROCK("assets/geothermal/rock.png"),
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
