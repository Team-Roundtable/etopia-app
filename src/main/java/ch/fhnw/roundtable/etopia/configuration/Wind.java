package ch.fhnw.roundtable.etopia.configuration;

public record Wind(float turbineSpeed, float gustSpeed) {

    public Wind(ConfigurationProperties properties) {
        this(properties.getFloat("wind.turbineSpeed").orElse(400f),
                properties.getFloat("wind.gustSpeed").orElse(600f));
    }
}
