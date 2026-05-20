package ch.fhnw.roundtable.etopia;

import ch.fhnw.roundtable.etopia.configuration.Configuration;
import ch.fhnw.roundtable.etopia.configuration.ConfigurationProperties;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;

public final class Main {

    private static final Path CONFIGURATION_PATH = Path.of("./configuration/app.properties");

    private Main() {
    }

    public static void main(String[] args) {
        Thread.setDefaultUncaughtExceptionHandler(
                (t, e) -> LoggerFactory.getLogger("CRASH").error("Uncaught exception in thread {}", t.getName(), e));

        var configurationProperties = new ConfigurationProperties(CONFIGURATION_PATH);

        var configuration = new Configuration(configurationProperties);

        var libGDXConfiguration = getLwjgl3ApplicationConfiguration();

        new Lwjgl3Application(new ETopia(configuration), libGDXConfiguration);
    }

    private static Lwjgl3ApplicationConfiguration getLwjgl3ApplicationConfiguration() {
        var configuration = new Lwjgl3ApplicationConfiguration();

        configuration.setTitle("E-Topia");

        configuration.useVsync(true);
        configuration.setDecorated(false);
        configuration.setResizable(false);

        var displayMode = Lwjgl3ApplicationConfiguration.getDisplayMode();

        configuration.setWindowedMode(displayMode.width, displayMode.height);
        configuration.setForegroundFPS(displayMode.refreshRate);

        return configuration;
    }
}
