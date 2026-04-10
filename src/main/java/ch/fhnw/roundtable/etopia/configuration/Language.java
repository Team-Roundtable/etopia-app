package ch.fhnw.roundtable.etopia.configuration;

public class Language {

    private static final String NOT_FOUND = "NOT FOUND";

    private final String name;
    private final ConfigurationProperties properties;

    public Language(String name, ConfigurationProperties properties) {
        this.name = name;
        this.properties = properties;
    }

    public String getName() {
        return name;
    }

    public String get(String key) {
        return properties.getString(key).orElse(NOT_FOUND);
    }
}
