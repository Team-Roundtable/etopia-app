package ch.fhnw.roundtable.etopia.config;

import ch.fhnw.roundtable.etopia.Main;
import tools.jackson.databind.ObjectMapper;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class Text {
    private final static Map<String, Map<String, String>> languages = new HashMap<>();
    private static String currentLanguage;


    public static String get(String key) {
        if (currentLanguage == null) return "nolanguage " + key;
        return Optional.ofNullable(languages.get(currentLanguage).get(key)).orElse("novalue " + key);
    }

    public static boolean addLanguage(String name, Map<String, String> text) {
        if (languages.containsKey(name)) return false;
        languages.put(name, text);
        return true;
    }

    public static boolean setLanguage(String name) {
        if (!languages.containsKey(name)) return false;
        currentLanguage = name;
        return true;
    }

    public static Set<String> getAvailableLanguages() {
        return languages.keySet();
    }

    public static void reloadLanguages() throws Exception {
        languages.clear();

        var config = loadConfig();
        for (Language language : config.languages()) {
            loadLanguage(language);
        }
    }

    private static void loadLanguage(Language language) {
        var languageFile = Path.of(getConfigDir(), language.file());
        Map<String, String> languagePack = new ObjectMapper().readValue(languageFile, HashMap.class);

        addLanguage(language.name(), languagePack);
    }

    private static Configuration loadConfig() throws Exception {
        var configFile = Path.of(getConfigDir(), "config.json");
        if (!Files.exists(configFile)) {
            throw new Exception("Couldn't find config file. Is needed for loading language packs.");
        }
        return new ObjectMapper().readValue(configFile, Configuration.class);
    }

    private static String getConfigDir() {
        String path = Main.class.getProtectionDomain()
                .getCodeSource()
                .getLocation()
                .getPath();
        File jarDir = new File(path).getParentFile();

        return jarDir.getAbsolutePath() + "/config";
    }
}
