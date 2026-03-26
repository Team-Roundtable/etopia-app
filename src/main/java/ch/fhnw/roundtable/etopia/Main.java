package ch.fhnw.roundtable.etopia;

import ch.fhnw.roundtable.etopia.config.Text;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

public final class Main {

    private Main() { }

    // todo timer utility updated with delta

    public static void main(String[] args) {
        try {
            Text.reloadLanguages();
            Text.setLanguage("german");
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.err.println("For developers: Are you running generate-sources before before launch?");
            System.err.println("(Add it to run Configuration)");
        }

        Lwjgl3ApplicationConfiguration configuration = new Lwjgl3ApplicationConfiguration();
        configuration.setTitle("E-Topia");
        configuration.useVsync(true);
        configuration.setForegroundFPS(Lwjgl3ApplicationConfiguration.getDisplayMode().refreshRate + 1);
        configuration.setWindowedMode(800, 500);
        new Lwjgl3Application(new ETopia(), configuration);
    }
}
