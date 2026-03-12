package ch.fhnw.roundtable.etopia.minigames.bucket;

import ch.fhnw.roundtable.etopia.ETopia;
import ch.fhnw.roundtable.etopia.input.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/// Handles the players bucket
public class Bucket {
    public float x;
    public float y;
    private final Texture texture;

    private final float SPEED = 800f;
    public final float SIZE = 200f;

    private final float MIN_X = SIZE / 2;
    private final float MAX_X = ETopia.WORLD_WIDTH - SIZE / 2;

    public Bucket(float x, float y, Texture texture) {
        this.x = x;
        this.y = y;
        this.texture = texture;
    }

    public void update(Input input, float deltaTime) {
        // TODO replace with our input system once implemented
        if (input.isLeftPressed()) x -= SPEED * deltaTime;
        if (input.isRightPressed()) x += SPEED * deltaTime;
        // Clamp to screen boundary
        if (x < MIN_X) x = MIN_X;
        if (x > MAX_X) x = MAX_X;
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, x - SIZE / 2, y - SIZE / 2, SIZE, SIZE);
    }
}
