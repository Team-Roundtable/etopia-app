package ch.fhnw.roundtable.etopia.rendering;

import ch.fhnw.roundtable.etopia.configuration.Configuration;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.function.Consumer;

public class Renderer {

    public static final int SCREEN_WIDTH = 1920;
    public static final int SCREEN_HEIGHT = 1080;

    private static final String FONT = "fonts/pixellari.ttf";
    private static final int FONT_SIZE = 48;

    public final BitmapFont font;
    private final Render render = new Render();
    private final Viewport viewport = new FitViewport(SCREEN_WIDTH, SCREEN_HEIGHT);

    public Renderer(Configuration configuration) {
        var parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = FONT_SIZE;

        var generator = new FreeTypeFontGenerator(Gdx.files.internal(FONT));
        font = generator.generateFont(parameter);
        font.setUseIntegerPositions(false);
        generator.dispose();

        viewport.apply(true);
        render.setProjectionMatrix(viewport.getCamera().combined);

        if (configuration.fullScreen()) {
            Gdx.input.setCursorCatched(true);
        }
    }

    public void batch(Consumer<Render> consumer) {
        render.begin();
        consumer.accept(render);
        render.end();
    }

    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    public void dispose() {
        render.dispose();
        font.dispose();
    }

    public void setCameraPosition(float x, float y) {
        viewport.getCamera().position.set(x, y, 0);
        viewport.getCamera().update();
        render.setProjectionMatrix(viewport.getCamera().combined);
    }

    public void resetCamera() {
        setCameraPosition(SCREEN_WIDTH / 2f, SCREEN_HEIGHT / 2f);
    }
}
