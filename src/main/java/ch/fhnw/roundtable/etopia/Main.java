package ch.fhnw.roundtable.etopia;

import ch.fhnw.roundtable.etopia.config.Text;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    private Main() { }

    public static void main(String[] args) {
        try {
            Text.reloadLanguages();
            Text.setLanguage("german");
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            LOGGER.warn("For developers: Are you running generate-sources before before launch?");
            LOGGER.warn("(Add it to run Configuration)");
        }

        Lwjgl3ApplicationConfiguration configuration = new Lwjgl3ApplicationConfiguration();
        configuration.setTitle("E-Topia");
        configuration.useVsync(true);
        configuration.setForegroundFPS(Lwjgl3ApplicationConfiguration.getDisplayMode().refreshRate + 1);
        configuration.setWindowedMode(800, 500);
        new Lwjgl3Application(new ETopia(), configuration);
    }
}
