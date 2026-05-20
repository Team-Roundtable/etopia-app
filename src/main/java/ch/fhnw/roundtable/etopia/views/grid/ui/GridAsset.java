package ch.fhnw.roundtable.etopia.views.grid.ui;

import ch.fhnw.roundtable.etopia.rendering.Asset;

public enum GridAsset implements Asset {
    BACKGROUND("assets/grid/background.png"),
    CURSOR("assets/grid/cursor.png"),
    PRODUCER("assets/grid/producer.png"),

    CONSUMER_UNPOWERED("assets/grid/consumer-unpowered.png"),
    CONSUMER_POWERED("assets/grid/consumer-powered.png"),

    CROSS_IMMUTABLE_UNPOWERED("assets/grid/cross-immutable-unpowered.png"),
    CROSS_IMMUTABLE_POWERED("assets/grid/cross-immutable-powered.png"),

    STRAIGHT_IMMUTABLE_POWERED("assets/grid/straight-immutable-powered.png"),
    STRAIGHT_IMMUTABLE_UNPOWERED("assets/grid/straight-immutable-unpowered.png"),
    STRAIGHT_EDITABLE_POWERED("assets/grid/straight-editable-powered.png"),
    STRAIGHT_EDITABLE_UNPOWERED("assets/grid/straight-editable-unpowered.png"),

    CORNER_IMMUTABLE_POWERED("assets/grid/corner-immutable-powered.png"),
    CORNER_IMMUTABLE_UNPOWERED("assets/grid/corner-immutable-unpowered.png"),
    CORNER_EDITABLE_POWERED("assets/grid/corner-editable-powered.png"),
    CORNER_EDITABLE_UNPOWERED("assets/grid/corner-editable-unpowered.png"),

    THREE_WAY_IMMUTABLE_POWERED("assets/grid/three-immutable-powered.png"),
    THREE_WAY_IMMUTABLE_UNPOWERED("assets/grid/three-immutable-unpowered.png"),
    THREE_WAY_EDITABLE_POWERED("assets/grid/three-editable-powered.png"),
    THREE_WAY_EDITABLE_UNPOWERED("assets/grid/three-editable-unpowered.png");

    private final String path;

    GridAsset(String path) {
        this.path = path;
    }

    @Override
    public String getPath() {
        return path;
    }
}
