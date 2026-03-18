package ch.fhnw.roundtable.etopia.minigames.panel;

import ch.fhnw.roundtable.etopia.Renderer;
import ch.fhnw.roundtable.etopia.input.Input;
import ch.fhnw.roundtable.etopia.view.View;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Panel implements View {

    private static final float PADDING = 32f;

    private final float x;
    private final float y;
    private final float width;
    private final float height;

    private String content;
    private Texture background;

    public Panel(float x, float y, float width, float height, String content) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.content = content;
    }

    @Override
    public void create() {
        background = new Texture("assets/panel/background.png");
    }

    @Override
    public void update(float delta, Input input) {

    }

    @Override
    public void render(Renderer renderer) {

        renderer.batch(batch -> {
            batch.draw(background, x, y, width, height);
            renderer.font.draw(batch, content, x + PADDING, y + height - PADDING, width - PADDING * 2, -1, true);
        });
    }

    @Override
    public void dispose() {

    }
}
