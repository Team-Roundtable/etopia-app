package ch.fhnw.roundtable.etopia.configuration;

public record Wind(
        float turbineSpeed,
        float gustSpeed,
        float gameDuration,
        float freezeCountdown,
        float gustTimer,
        float tornadoTimer,
        float energyAdded
) {

    private static final float TURBINE_WIDTH = 128f;
    private static final float TURBINE_HEIGHT = 256f;
    private static final float GUST_WIDTH = 128f;
    private static final float GUST_HEIGHT = 64f;

    public Wind(ConfigurationProperties properties) {
        this(
                properties.getFloat("wind.turbineSpeed").orElse(400f),
                properties.getFloat("wind.gustSpeed").orElse(600f),
                properties.getFloat("wind.gameDuration").orElse(30f),
                properties.getFloat("wind.freezeCountdown").orElse(1f),
                properties.getFloat("wind.gustTimer").orElse(0.5f),
                properties.getFloat("wind.tornadoTimer").orElse(0.8f),
                properties.getFloat("wind.energyAdded").orElse(0.05f)
        );
    }

    public float turbineWidth() {
        return TURBINE_WIDTH;
    }

    public float turbineHeight() {
        return TURBINE_HEIGHT;
    }

    public float gustWidth() {
        return GUST_WIDTH;
    }

    public float gustHeight() {
        return GUST_HEIGHT;
    }
}
