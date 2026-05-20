package ch.fhnw.roundtable.etopia.configuration;

public record Information(
        float disabledDuration
) {

    private static final float OFFSET_X = 176;
    private static final float MAX_WIDTH = 832;
    private static final float TITLE_OFFSET_Y = 1008;
    private static final float DESCRIPTION_OFFSET_Y = 896;
    private static final float NOTE_OFFSET_Y = 192;
    private static final float VIDEO_OFFSET_X = 1040;
    private static final float VIDEO_OFFSET_Y = 32;
    private static final float VIDEO_WIDTH = 800;
    private static final float VIDEO_HEIGHT = 540;

    public Information(ConfigurationProperties properties) {
        this(
                properties.getFloat("information.disabledDuration").orElse(3f)
        );
    }

    public float offsetX() {
        return OFFSET_X;
    }

    public float maxWidth() {
        return MAX_WIDTH;
    }

    public float titleOffsetY() {
        return TITLE_OFFSET_Y;
    }

    public float descriptionOffsetY() {
        return DESCRIPTION_OFFSET_Y;
    }

    public float noteOffsetY() {
        return NOTE_OFFSET_Y;
    }

    public float videoOffsetX() {
        return VIDEO_OFFSET_X;
    }

    public float videoOffsetY() {
        return VIDEO_OFFSET_Y;
    }

    public float videoWidth() {
        return VIDEO_WIDTH;
    }

    public float videoHeight() {
        return VIDEO_HEIGHT;
    }
}
