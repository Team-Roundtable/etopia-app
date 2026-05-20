package ch.fhnw.roundtable.etopia.views.information.ui;

import ch.fhnw.roundtable.etopia.rendering.Asset;

public enum InformationAsset implements Asset {
    INFORMATION("assets/information/information.png"),
    SUCCESS("assets/information/success.png"),
    FAIL("assets/information/fail.png");

    private final String path;

    InformationAsset(String path) {
        this.path = path;
    }

    @Override
    public String getPath() {
        return path;
    }
}
