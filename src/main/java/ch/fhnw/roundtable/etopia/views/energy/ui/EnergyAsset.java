package ch.fhnw.roundtable.etopia.views.energy.ui;

import ch.fhnw.roundtable.etopia.views.Asset;

public enum EnergyAsset implements Asset {
    BAR("assets/energy/bar.png"),
    INDICATOR("assets/energy/indicator.png");

    private final String path;

    EnergyAsset(String path) {
        this.path = path;
    }

    @Override
    public String getPath() {
        return path;
    }
}
