package ch.fhnw.roundtable.etopia.configuration;

public record Configuration(Languages languages, Wind wind) {

    public Configuration(ConfigurationProperties properties) {
        this(new Languages(properties),
                new Wind(properties));
    }

    public String getText(String key) {
        return languages.getCurrent().get(key);
    }
}
