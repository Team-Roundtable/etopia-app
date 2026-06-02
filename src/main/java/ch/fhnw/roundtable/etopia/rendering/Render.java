package ch.fhnw.roundtable.etopia.rendering;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Render extends SpriteBatch {

    public void drawBackground(Texture texture) {
        super.draw(texture, 0, 0, Renderer.SCREEN_WIDTH, Renderer.SCREEN_HEIGHT);
    }

    public void drawCentered(Texture texture, float x, float y, float width, float height, float rotation) {
        super.draw(texture,
                x - width / 2, y - height / 2,
                width / 2, height / 2,
                width, height,
                1, 1,
                rotation,
                0, 0,
                texture.getWidth(), texture.getHeight(),
                false, false);
    }

    public void drawCentered(Texture texture, float x, float y, float width, float height, float rotation,
                             float scale) {
        super.draw(texture,
                x - width / 2, y - height / 2,
                width / 2, height / 2,
                width, height,
                scale, scale,
                rotation,
                0, 0,
                texture.getWidth(), texture.getHeight(),
                false, false);
    }
}
