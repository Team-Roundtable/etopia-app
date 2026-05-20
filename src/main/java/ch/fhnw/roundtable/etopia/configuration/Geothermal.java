package ch.fhnw.roundtable.etopia.configuration;

public record Geothermal(
        float gameDuration,
        float drillSpeed,
        int numberOfRocks,
        float collisionCountdown,
        float bottomCountdown,
        float topCountdown
) {

    private static final float MAP_HEIGHT = 8192;
    private static final float DRILL_WIDTH = 128;
    private static final float DRILL_HEIGHT = 128;
    private static final float PIPE_WIDTH = 64;
    private static final float PIPE_HEIGHT = 64;
    private static final float ROCK_WIDTH = 256;
    private static final float ROCK_HEIGHT = 256;
    private static final float PIPE_TIMER = 0.05f;

    public Geothermal(ConfigurationProperties properties) {
        this(
                properties.getFloat("geothermal.gameDuration").orElse(120f),
                properties.getFloat("geothermal.drillSpeed").orElse(400f),
                properties.getInt("geothermal.numberOfRocks").orElse(40),
                properties.getFloat("geothermal.collisionCountdown").orElse(2f),
                properties.getFloat("geothermal.bottomCountdown").orElse(1f),
                properties.getFloat("geothermal.topCountdown").orElse(1f)
        );
    }

    public float mapHeight() {
        return MAP_HEIGHT;
    }

    public float drillWidth() {
        return DRILL_WIDTH;
    }

    public float drillHeight() {
        return DRILL_HEIGHT;
    }

    public float pipeWidth() {
        return PIPE_WIDTH;
    }

    public float pipeHeight() {
        return PIPE_HEIGHT;
    }

    public float rockWidth() {
        return ROCK_WIDTH;
    }

    public float rockHeight() {
        return ROCK_HEIGHT;
    }

    public float pipeTimer() {
        return PIPE_TIMER;
    }
}
