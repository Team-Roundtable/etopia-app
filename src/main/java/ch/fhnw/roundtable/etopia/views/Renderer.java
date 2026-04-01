package ch.fhnw.roundtable.etopia.views;

import ch.fhnw.roundtable.etopia.ETopia;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.function.Consumer;

public class Renderer {
    // todo rethink font rendering with convenience setup
    public final BitmapFont font;
    private final SpriteBatch batch = new SpriteBatch();
    private final ShapeRenderer shape = new ShapeRenderer();
    private final Viewport viewport = new FitViewport(ETopia.WORLD_WIDTH, ETopia.WORLD_HEIGHT);

    public Renderer() {
        var parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 64;

        var generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/fs-pixel-sans.ttf"));
        font = generator.generateFont(parameter);
        font.setUseIntegerPositions(false);
        generator.dispose();

        viewport.apply(true);
        batch.setProjectionMatrix(viewport.getCamera().combined);
        shape.setProjectionMatrix(viewport.getCamera().combined);
    }

    public void batch(Consumer<SpriteBatch> consumer) {
        batch.begin();
        consumer.accept(batch);
        batch.end();
    }

    public void shape(ShapeRenderer.ShapeType type, Consumer<ShapeRenderer> consumer) {
        shape.begin(type);
        consumer.accept(shape);
        shape.end();
    }

    // todo test
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    public void dispose() {
        batch.dispose();
        shape.dispose();
        font.dispose();
    }

    public void setCameraPosition(float x, float y) {
        viewport.getCamera().position.set(x, y, 0);
        viewport.getCamera().update();
        batch.setProjectionMatrix(viewport.getCamera().combined);
        shape.setProjectionMatrix(viewport.getCamera().combined);
    }

    public void resetCamera() {
        setCameraPosition(ETopia.WORLD_WIDTH / 2f, ETopia.WORLD_HEIGHT / 2f);
    }
}
