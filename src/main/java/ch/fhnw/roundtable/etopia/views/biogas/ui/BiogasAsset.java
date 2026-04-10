package ch.fhnw.roundtable.etopia.views.biogas.ui;

import ch.fhnw.roundtable.etopia.views.Asset;

public enum BiogasAsset implements Asset {
    CONVEYOR_BELT("assets/biogas/biomasse-conveyorbelt.png"),
    GREEN_ITEM("assets/biogas/biomasse-green.png"),
    RED_ITEM("assets/biogas/biomasse-red.png"),
    BACKGROUND("assets/biogas/biomasse-background.png"),
    CURSOR("assets/biogas/cursor.png");

    public static final int SCALE_MULTIPLIER = 4;
    // in Pixel
    public static final int ASSET_SIZE = 32;
    public static final int GRID_SPACING = 4;
    public static final int CELL_SIZE = ASSET_SIZE + GRID_SPACING;
    private final String path;

    BiogasAsset(String path) {
        this.path = path;
    }

    public static BiogasAsset[] getGreenItems() {
        return new BiogasAsset[]{GREEN_ITEM};
    }

    public static BiogasAsset[] getRedItems() {
        return new BiogasAsset[]{RED_ITEM};
    }

    @Override
    public String getPath() {
        return path;
    }

}
