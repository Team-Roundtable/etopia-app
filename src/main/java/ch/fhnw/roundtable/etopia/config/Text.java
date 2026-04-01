package ch.fhnw.roundtable.etopia.config;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public final class Text {
    private static final Map<String, Map<String, String>> LANGUAGES = new HashMap<>();
    public static final String NOLANGUAGE_PREFIX = "nolanguage";
    public static final String NOVALUE_PREFIX = "novalue";
    private static String currentLanguage;

    private Text() {
    }

    public static String get(String key) {
        if (currentLanguage == null) return NOLANGUAGE_PREFIX + " " + key;
        return Optional.ofNullable(LANGUAGES.get(currentLanguage).get(key)).orElse(NOVALUE_PREFIX + " " + key);
    }

    public static boolean addLanguage(String name, Map<String, String> text) {
        if (LANGUAGES.containsKey(name)) return false;
        LANGUAGES.put(name, text);
        return true;
    }

    public static boolean setLanguage(String name) {
        if (!LANGUAGES.containsKey(name)) return false;
        currentLanguage = name;
        return true;
    }

    public static Set<String> getAvailableLanguages() {
        return LANGUAGES.keySet();
    }

    public static void reloadLanguages() {
        LANGUAGES.clear();

        var config = ConfigLoader.loadConfig();
        for (Language language : config.languages()) {
            loadLanguage(language);
        }
    }

    private static void loadLanguage(Language language) {
        HashMap<String, String> textPack = ConfigLoader.loadTextPack(language);
        addLanguage(language.name(), textPack);
    }

}
