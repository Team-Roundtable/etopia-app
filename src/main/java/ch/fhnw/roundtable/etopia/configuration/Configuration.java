package ch.fhnw.roundtable.etopia.configuration;

public record Configuration(
        boolean dummyMode,
        boolean fullScreen,
        Languages languages,
        State state,
        Information information,
        Map map,
        Wind wind,
        Solar solar,
        Grid grid,
        Geothermal geothermal,
        Biogas biogas,
        Status status,
        Settings settings
) {

    private static final float WORLD_WIDTH = 1920;
    private static final float WORLD_HEIGHT = 1080;

    public Configuration(ConfigurationProperties properties) {
        this(
                properties.getBoolean("dummyMode").orElse(false),
                properties.getBoolean("fullScreen").orElse(true),
                new Languages(properties),
                new State(),
                new Information(properties),
                new Map(),
                new Wind(properties),
                new Solar(properties),
                new Grid(properties),
                new Geothermal(properties),
                new Biogas(properties),
                new Status(properties),
                new Settings()
        );
    }

    public String getText(String key) {
        return languages.getCurrent().get(key);
    }

    public float worldWidth() {
        return WORLD_WIDTH;
    }

    public float worldHeight() {
        return WORLD_HEIGHT;
    }
}
