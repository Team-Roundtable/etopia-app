package ch.fhnw.roundtable.etopia.configuration;

public record Biogas(
        float gameDuration,
        float shiftTimer,
        boolean animatedOnConveyor,
        boolean useAnimatedIcons
) {

    private static final float ITEM_SIDE = 128;
    private static final float CONVEYOR_OFFSET_X = 768;
    private static final float CONVEYOR_OFFSET_Y = 348;
    private static final int MAP_WIDTH = 9;
    private static final int MAP_HEIGHT = 3;

    public Biogas(ConfigurationProperties properties) {
        this(
                properties.getFloat("biogas.gameDuration").orElse(100f),
                properties.getFloat("biogas.shiftTimer").orElse(0.75f),
                properties.getBoolean("biogas.animatedOnConveyor").orElse(false),
                properties.getBoolean("biogas.useAnimatedIcons").orElse(false)
        );
    }

    public float itemSide() {
        return ITEM_SIDE;
    }

    public float conveyorOffsetX() {
        return CONVEYOR_OFFSET_X;
    }

    public float conveyorOffsetY() {
        return CONVEYOR_OFFSET_Y;
    }

    public int mapWidth() {
        return MAP_WIDTH;
    }

    public int mapHeight() {
        return MAP_HEIGHT;
    }
}
