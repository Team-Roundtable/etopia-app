package ch.fhnw.roundtable.etopia.views.biomass;

import ch.fhnw.roundtable.etopia.ETopia;
import ch.fhnw.roundtable.etopia.views.Renderer;
import ch.fhnw.roundtable.etopia.input.Input;
import ch.fhnw.roundtable.etopia.views.Entity;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class Bucket extends Entity {

    private static final float SPEED = 800f;
    private final Texture texture;

    public Bucket(float x, float y, Texture texture) {
        super(x, y, 200, 200);
        this.texture = texture;
    }

    @Override
    public void updateEntity(float delta, Input input) {
        if (input.isLeftPressed()) {
            x -= SPEED * delta;
        }

        if (input.isRightPressed()) {
            x += SPEED * delta;
        }

        x = Math.clamp(x, width / 2, ETopia.WORLD_WIDTH - width / 2);
    }

    @Override
    public void renderEntity(Renderer renderer) {
        renderer.batch(batch -> batch.draw(texture, x - width / 2, y - height / 2, width, height));
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
}
