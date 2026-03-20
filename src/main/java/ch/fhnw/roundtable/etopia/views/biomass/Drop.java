package ch.fhnw.roundtable.etopia.views.biomass;

import ch.fhnw.roundtable.etopia.views.Renderer;
import ch.fhnw.roundtable.etopia.input.Input;
import ch.fhnw.roundtable.etopia.views.Entity;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class Drop extends Entity {

    private final float VELOCITY = 250f;

    private final Texture texture;
    public Drop(float x, float y, Texture texture) {
        super(x, y, 150, 150);
        this.texture = texture;
    }

    @Override
    public void updateEntity(float delta, Input input) {
        y -= VELOCITY * delta;
    }

    @Override
    public void renderEntity(Renderer renderer) {
        renderer.batch(batch -> batch.draw(texture, x - width / 2, y - height / 2, width, height));
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
}
