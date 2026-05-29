package ch.fhnw.roundtable.etopia.views.biogas.ui;

import ch.fhnw.roundtable.etopia.rendering.Asset;

public enum BiogasAsset implements Asset {
    APPLE("assets/biogas/apple.png"),
    BANANA("assets/biogas/banana.png"),
    BOTTLE("assets/biogas/bottle.png"),
    CAN("assets/biogas/can.png"),
    COLA("assets/biogas/cola.png"),
    CUP("assets/biogas/cup.png"),
    GRAPES("assets/biogas/grapes.png"),
    GLASS("assets/biogas/glass.png"),
    BACKGROUND("assets/biogas/background.png"),
    CURSOR("assets/biogas/cursor.png"),
    POWER_ICON("assets/common/power.png"),
    CROSS_ICON("assets/common/cross.png");

    private final String path;

    BiogasAsset(String path) {
        this.path = path;
    }

    @Override
    public String getPath() {
        return path;
    }

}
