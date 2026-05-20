package ch.fhnw.roundtable.etopia.views.settings.ui;

import ch.fhnw.roundtable.etopia.rendering.Asset;

public enum SettingsAsset implements Asset {
    BACKGROUND("assets/information/information.png");

    private final String path;

    SettingsAsset(String path) {
        this.path = path;
    }

    @Override
    public String getPath() {
        return path;
    }
}
