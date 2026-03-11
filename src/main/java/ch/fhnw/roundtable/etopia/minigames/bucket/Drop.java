package ch.fhnw.roundtable.etopia.minigames.bucket;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/// One drop of water, falling down the screen
public class Drop {
    public float x;
    public float y;
    private final Texture texture;

    private final float FALLING_VELOCITY = 250f;
    public final float SIZE = 150f;


    public Drop(float x, float y, Texture texture) {
        this.x = x;
        this.y = y;

        this.texture = texture;
    }

    public void update(float deltaTime) {
        y -= FALLING_VELOCITY * deltaTime; // deltaTime is used to keep velocity consistent even with varying framerate.
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, x - SIZE / 2, y - SIZE / 2, SIZE, SIZE);
    }
}
