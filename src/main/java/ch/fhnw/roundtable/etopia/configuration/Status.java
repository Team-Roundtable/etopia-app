package ch.fhnw.roundtable.etopia.configuration;

public record Status(
        int totalHealth
) {

    private static final float BACKGROUND_WIDTH = 1152;
    private static final float BACKGROUND_HEIGHT = 80;
    private static final float OFFSET_X = 384;
    private static final float OFFSET_Y = 8;
    private static final float BAR_WIDTH = 288;
    private static final float BAR_HEIGHT = 40;
    private static final float BAR_OFFSET_Y = 20;
    private static final float TIME_OFFSET_X = 96;
    private static final float ENERGY_OFFSET_X = 464;
    private static final float HEALTH_OFFSET_X = 832;

    public Status(ConfigurationProperties properties) {
        this(
                properties.getInt("status.totalHealth").orElse(5)
        );
    }

    public float backgroundWidth() {
        return BACKGROUND_WIDTH;
    }

    public float backgroundHeight() {
        return BACKGROUND_HEIGHT;
    }

    public float offsetX() {
        return OFFSET_X;
    }

    public float offsetY() {
        return OFFSET_Y;
    }

    public float barWidth() {
        return BAR_WIDTH;
    }

    public float barHeight() {
        return BAR_HEIGHT;
    }

    public float barOffsetY() {
        return BAR_OFFSET_Y;
    }

    public float timeOffsetX() {
        return TIME_OFFSET_X;
    }

    public float energyOffsetX() {
        return ENERGY_OFFSET_X;
    }

    public float healthOffsetX() {
        return HEALTH_OFFSET_X;
    }


}
