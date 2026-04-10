package ch.fhnw.roundtable.etopia.views.information.ui;

import ch.fhnw.roundtable.etopia.views.Asset;

public enum InformationAsset implements Asset {
    BACKGROUND("assets/information/background.png");

    private final String path;

    InformationAsset(String path) {
        this.path = path;
    }

    @Override
    public String getPath() {
        return path;
    }
}
