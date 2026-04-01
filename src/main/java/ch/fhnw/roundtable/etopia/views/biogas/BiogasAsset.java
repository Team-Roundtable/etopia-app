package ch.fhnw.roundtable.etopia.views.biogas;

import ch.fhnw.roundtable.etopia.views.Asset;

public enum BiogasAsset implements Asset {
    CONVEYOR_BELT("assets/biogas/biomasse-conveyorbelt.png"),
    PANEL("assets/commons/panel/background.png"),
    GREEN_ITEM("assets/biogas/biomasse-green.png"),
    RED_ITEM("assets/biogas/biomasse-red.png"),
    BACKGROUND("assets/biogas/biomasse-background.png"),
    CURSOR("assets/biogas/cursor.png");

    private final String path;

    public static final int SCALE_MULTIPLIER = 4;
    // in Pixel
    public static final int ASSET_SIZE = 32;
    public static final int GRID_SPACING = 4;
    public static final int CELL_SIZE = ASSET_SIZE + GRID_SPACING;

    BiogasAsset(String path) {
        this.path = path;
    }

    @Override
    public String getPath() {
        return path;
    }

    public static BiogasAsset[] getGreenItems() {
        return new BiogasAsset[]{GREEN_ITEM };
    }

    public static BiogasAsset[] getRedItems() {
        return new BiogasAsset[]{RED_ITEM };
    }

}
