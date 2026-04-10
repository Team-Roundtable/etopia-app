package ch.fhnw.roundtable.etopia.views;

import ch.fhnw.roundtable.etopia.ETopia;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.function.Consumer;

// todo rename
public class Renderer {
    // todo rethink font rendering with convenience setup
    public final BitmapFont font;
    private final Render render = new Render();
    private final Viewport viewport = new FitViewport(ETopia.WORLD_WIDTH, ETopia.WORLD_HEIGHT);

    public Renderer() {
        var parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 64;

        var generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/fs-pixel-sans.ttf"));
        font = generator.generateFont(parameter);
        font.setUseIntegerPositions(false);
        generator.dispose();

        viewport.apply(true);
        render.setProjectionMatrix(viewport.getCamera().combined);
    }

    // todo rotate able
    // todo animation/types
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
        setCameraPosition(ETopia.WORLD_WIDTH / 2f, ETopia.WORLD_HEIGHT / 2f);
    }
}
