package ch.fhnw.roundtable.etopia.configuration;

public record Grid(
        int mapWidth,
        int mapHeight,
        float gameDuration,
        float finishCountdown,
        boolean animatedPipeRotation
) {

    private static final float TILE_SIDE = 64;
    private static final float OFFSET_Y = 8;

    public Grid(ConfigurationProperties properties) {
        this(
                properties.getInt("grid.mapWidth").orElse(28),
                properties.getInt("grid.mapHeight").orElse(16),
                properties.getFloat("grid.gameDuration").orElse(60f),
                properties.getFloat("grid.finishCountdown").orElse(2f),
                properties.getBoolean("grid.animatedPipeRotation").orElse(true)
        );
    }

    public float tileSide() {
        return TILE_SIDE;
    }

    public float offsetY() {
        return OFFSET_Y;
    }
}
