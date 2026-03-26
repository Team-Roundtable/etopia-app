package ch.fhnw.roundtable.etopia.views.biomass;

import ch.fhnw.roundtable.etopia.views.Asset;

public enum BiomassAsset implements Asset {
    PANEL("assets/commons/panel/background.png"),
    BACKGROUND("assets/biomass/background.png"),
    BUCKET("assets/biomass/bucket.png"),
    DROP("assets/biomass/drop.png");

    private final String path;

    BiomassAsset(String path) {
        this.path = path;
    }

    @Override
    public String getPath() {
        return path;
    }
}
