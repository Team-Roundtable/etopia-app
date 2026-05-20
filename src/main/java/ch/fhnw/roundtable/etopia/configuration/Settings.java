package ch.fhnw.roundtable.etopia.configuration;

public record Settings() {

    private static final float MAX_WIDTH = 832;
    private static final float OFFSET_X = 176;
    private static final float TITLE_OFFSET_Y = 1008;
    private static final float BODY_OFFSET_Y = 896;
    private static final float ELEMENT_OFFSET = 96;

    public float offsetX() {
        return OFFSET_X;
    }

    public float bodyOffsetY() {
        return BODY_OFFSET_Y;
    }

    public float maxWidth() {
        return MAX_WIDTH;
    }

    public float titleOffsetY() {
        return TITLE_OFFSET_Y;
    }

    public float elementOffset() {
        return ELEMENT_OFFSET;
    }
}
