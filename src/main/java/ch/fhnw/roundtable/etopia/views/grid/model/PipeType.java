package ch.fhnw.roundtable.etopia.views.grid.model;

public enum PipeType {
    PRODUCER(false),
    CONSUMER(false),

    STRAIGHT_EDITABLE(true),
    CORNER_EDITABLE(true),
    THREE_WAY_EDITABLE(true),

    STRAIGHT_IMMUTABLE(false),
    CROSS_IMMUTABLE(false),
    CORNER_IMMUTABLE(false),
    THREE_WAY_IMMUTABLE(false);

    private final boolean editable;

    PipeType(boolean editable) {
        this.editable = editable;
    }

    public boolean isEditable() {
        return editable;
    }
}
