package ch.fhnw.roundtable.etopia.configuration;

public record Map(boolean doSettingPulse) {

    private static final float BUILDING_SIDE = 448;
    private static final float SETTINGS_SIDE = 64;
    private static final float HUMAN_WIDTH = 128;
    private static final float HUMAN_HEIGHT = 128;

    public Map(ConfigurationProperties properties) {
        this(
                properties.getBoolean("map.doSettingPulse").orElse(true)
        );
    }

    public float buildingSide() {
        return BUILDING_SIDE;
    }

    public float settingsSide() {
        return SETTINGS_SIDE;
    }

    public float humanWidth() {
        return HUMAN_WIDTH;
    }

    public float humanHeight() {
        return HUMAN_HEIGHT;
    }
}
