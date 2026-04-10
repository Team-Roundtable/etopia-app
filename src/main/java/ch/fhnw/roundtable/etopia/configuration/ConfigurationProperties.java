package ch.fhnw.roundtable.etopia.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.Properties;

public class ConfigurationProperties {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigurationProperties.class);

    private final Properties properties = new Properties();

    public ConfigurationProperties(Path path) {
        try {
            properties.load(new InputStreamReader(Files.newInputStream(path), StandardCharsets.UTF_8));
        } catch (IOException e) {
            LOGGER.error("Failed to read file {}", path, e);
            throw new RuntimeException("Failed to read configuration file", e);
        }
    }

    public Optional<String> getString(String key) {
        var value = properties.get(key);
        if (value == null) {
            LOGGER.warn("Property {} not found, default used", key);
            return Optional.empty();
        }

        return Optional.of(value.toString());
    }

    public Optional<String[]> getStrings(String key) {
        var value = getString(key);

        return value.map(s -> s.split(","));
    }

    public Optional<Float> getFloat(String key) {
        var value = getString(key);

        if (value.isEmpty()) {
            return Optional.empty();
        }

        try {
            return Optional.of(Float.valueOf(value.get()));
        } catch (NumberFormatException e) {
            LOGGER.warn("Invalid decimal number format for {}", key);
            return Optional.empty();
        }
    }
}
