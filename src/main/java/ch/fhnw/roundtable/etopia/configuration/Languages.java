package ch.fhnw.roundtable.etopia.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Languages {
    private static final Logger LOGGER = LoggerFactory.getLogger(Languages.class);

    private final List<Language> available = new ArrayList<>();
    private int current = 0;

    public Languages(ConfigurationProperties properties) {
        String[] names = properties.getStrings("languages").orElse(new String[0]);

        if (names.length == 0) {
            LOGGER.error("No options defined in configuration");
        }

        for (String name : names) {
            var languageProperties = new ConfigurationProperties(Path.of("./configuration/" + name + ".properties"));
            available.add(new Language(name, languageProperties));
        }
    }

    public Language getCurrent() {
        return available.get(current);
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public List<String> getAvailable() {
        return available.stream().map(Language::getName).toList();
    }
}
