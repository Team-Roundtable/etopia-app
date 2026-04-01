package ch.fhnw.roundtable.etopia.config;

import ch.fhnw.roundtable.etopia.Main;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Properties;
import java.util.stream.Collectors;

public final class ConfigLoader {

    public static final String APP_PROPERTIES = "app.properties";

    private ConfigLoader() { }

    public static Configuration loadConfig() {
        var propertiesFile = Path.of(getConfigDir(), APP_PROPERTIES).toFile();
        Properties prop = new Properties();
        try {
            prop.load(new FileReader(propertiesFile));
            return parseConfig(prop);
        } catch (IOException e) {
            throw new RuntimeException("Couldn't load 'app.properties'. Is needed for loading language packs.");
        }
    }

    public static HashMap<String, String> loadTextPack(Language language) {
        var languageFile = Path.of(getConfigDir(), language.file()).toFile();

        Properties prop = new Properties();
        try {
            prop.load(new FileReader(languageFile));
        } catch (IOException e) {
            throw new RuntimeException("Failed to load language '" + language.name() + "': " + e.getMessage());
        }
        return streamConvert(prop);
    }

    private static Configuration parseConfig(Properties prop) {
        var languages = Arrays.stream(prop.getProperty("languages").split(","))
                .map(l -> new Language(l, getLanguageFileName(l))).toList();
        return new Configuration(languages);
    }

    private static String getLanguageFileName(String l) {
        return l + ".properties";
    }

    private static String getConfigDir() {
        String path = Main.class.getProtectionDomain()
                .getCodeSource()
                .getLocation()
                .getPath();
        File jarDir = new File(path).getParentFile();

        return jarDir.getAbsolutePath() + "/config";
    }

    // From: https://www.baeldung.com/java-convert-properties-to-hashmap#2-stream-and-collectors-api
    // Turns Properties into a HashMap
    static HashMap<String, String> streamConvert(Properties prop) {
        return prop.entrySet().stream().collect(
                Collectors.toMap(
                        e -> String.valueOf(e.getKey()),
                        e -> String.valueOf(e.getValue()),
                        (prev, next) -> next, HashMap::new
                ));
    }
}
