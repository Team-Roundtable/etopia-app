package ch.fhnw.roundtable.etopia;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.function.Consumer;

public class Renderer {
    public final SpriteBatch batch;
    public final BitmapFont font;
    public final ShapeRenderer shape;
    public final Viewport viewport;

    public Renderer(float width, float height) {
        this.batch = new SpriteBatch();
        this.font = new BitmapFont();
        this.shape = new ShapeRenderer();
        this.viewport = new FitViewport(width, height);
    }

    public void begin() {
        viewport.apply(true);
        batch.setProjectionMatrix(viewport.getCamera().combined);
        shape.setProjectionMatrix(viewport.getCamera().combined);
        font.setUseIntegerPositions(false);
    }

    // todo review
    // todo review
    public void end() {
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
}
