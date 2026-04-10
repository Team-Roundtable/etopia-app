package ch.fhnw.roundtable.etopia;

import ch.fhnw.roundtable.etopia.configuration.Configuration;
import ch.fhnw.roundtable.etopia.configuration.ConfigurationProperties;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

import java.nio.file.Path;

public final class Main {

    private static final Path CONFIGURATION_PATH = Path.of("./configuration/app.properties");

    private Main() {
    }

    public static void main(String[] args) {
        var configurationProperties = new ConfigurationProperties(CONFIGURATION_PATH);

        var configuration = new Configuration(configurationProperties);

        var libGDXConfiguration = new Lwjgl3ApplicationConfiguration();
        libGDXConfiguration.setTitle("E-Topia");
        libGDXConfiguration.useVsync(true);
        libGDXConfiguration.setForegroundFPS(Lwjgl3ApplicationConfiguration.getDisplayMode().refreshRate + 1);
        // todo should start in fullscreen in the future
        libGDXConfiguration.setWindowedMode(800, 500);
        new Lwjgl3Application(new ETopia(configuration), libGDXConfiguration);
    }
}
