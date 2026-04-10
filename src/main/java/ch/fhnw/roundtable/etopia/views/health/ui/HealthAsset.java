package ch.fhnw.roundtable.etopia.views.health.ui;

import ch.fhnw.roundtable.etopia.views.Asset;

public enum HealthAsset implements Asset {
    HEART("assets/health/heart.png");

    private final String path;

    HealthAsset(String path) {
        this.path = path;
    }

    @Override
    public String getPath() {
        return path;
    }
}
