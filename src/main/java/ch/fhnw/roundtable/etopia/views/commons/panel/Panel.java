package ch.fhnw.roundtable.etopia.views.commons.panel;

import ch.fhnw.roundtable.etopia.views.Renderer;
import ch.fhnw.roundtable.etopia.input.Input;
import ch.fhnw.roundtable.etopia.views.Entity;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;

public class Panel extends Entity {

    private static final float PADDING = 32f;
    private final Texture background;
    private final PanelDetails details;
    private boolean close = false;

    public Panel(float x, float y, float width, float height, Texture background, PanelDetails details) {
        super(x, y, width, height);
        this.background = background;
        this.details = details;
    }

    @Override
    public void updateEntity(float delta, Input input) {
        if (input.isSelectJustPressed()) {
            close = true;
        }
    }

    @Override
    public void renderEntity(Renderer renderer) {

        renderer.batch(batch -> {
            batch.draw(background, x, y, width, height);
            renderer.font.setColor(Color.BLACK);
            renderer.font.draw(batch, details.toString(), x + PADDING, y + height - PADDING, width - PADDING * 2, -1, true);
        });
    }

    public boolean isClose() {
        return close;
    }
}
