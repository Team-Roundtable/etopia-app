package ch.fhnw.roundtable.etopia.configuration;

public record Solar(
        float panelSpeed,
        float panelMaxRotation,
        float sunSpeed,
        float gameDuration,
        float energyInterval
) {

    private static final float SUN_WIDTH = 256;
    private static final float SUN_HEIGHT = 256;
    private static final float PANEL_WIDTH = 256;
    private static final float PANEL_HEIGHT = 256;

    public Solar(ConfigurationProperties properties) {
        this(
                properties.getFloat("solar.panelSpeed").orElse(75f),
                properties.getFloat("solar.panelMaxRotation").orElse(70f),
                properties.getFloat("solar.sunSpeed").orElse(0.7f),
                properties.getFloat("solar.gameDuration").orElse(45f),
                properties.getFloat("solar.energyInterval").orElse(0.5f)
        );
    }

    public float sunWidth() {
        return SUN_WIDTH;
    }

    public float sunHeight() {
        return SUN_HEIGHT;
    }

    public float panelWidth() {
        return PANEL_WIDTH;
    }

    public float panelHeight() {
        return PANEL_HEIGHT;
    }
}
