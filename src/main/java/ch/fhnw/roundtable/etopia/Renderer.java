package ch.fhnw.roundtable.etopia;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.function.Consumer;

public class Renderer {
    public final SpriteBatch batch;
    public final ShapeRenderer shape;
    public final Viewport viewport;

    public final FreeTypeFontGenerator generator;
    public final BitmapFont font;

    public Renderer(float width, float height) {
        this.batch = new SpriteBatch();
        this.shape = new ShapeRenderer();
        this.viewport = new FitViewport(width, height);
        this.generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/fs-pixel-sans.ttf"));


        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 64;
        this.font = generator.generateFont(parameter);
        this.font.setUseIntegerPositions(false);
    }

    public void begin() {
        viewport.apply(true);
        batch.setProjectionMatrix(viewport.getCamera().combined);
        shape.setProjectionMatrix(viewport.getCamera().combined);
    }

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
        generator.dispose();
    }
}
