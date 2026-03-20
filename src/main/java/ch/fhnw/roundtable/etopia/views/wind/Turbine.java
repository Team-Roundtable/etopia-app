package ch.fhnw.roundtable.etopia.views.wind;

import ch.fhnw.roundtable.etopia.helpers.AnimationHelper;
import ch.fhnw.roundtable.etopia.ETopia;
import ch.fhnw.roundtable.etopia.views.Renderer;
import ch.fhnw.roundtable.etopia.input.Input;
import ch.fhnw.roundtable.etopia.views.Entity;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class Turbine extends Entity {

    public static final float SPEED = 200;

    private final AnimationHelper turbine;
    private final Texture pole;
    private final float x = 100;
    private float y = ETopia.WORLD_HEIGHT / 4.0f;

    public Turbine(Texture turbine, Texture pole) {
        super(100, ETopia.WORLD_HEIGHT / 4.0f, 64 * 3, 128 * 3);
        this.pole = pole;
        this.turbine = new AnimationHelper(turbine, 64, 128, 0.1f);
    }

    @Override
    public void updateEntity(float delta, Input input) {
        turbine.updateTime(delta);
        y = Math.clamp(y + input.getVerticalInput() * (SPEED * delta), 0, ETopia.WORLD_HEIGHT - height);
    }

    @Override
    public void renderEntity(Renderer renderer) {
        renderer.batch(batch -> {
            batch.draw(pole, x + 54, y - 864, 32, 1080);
            batch.draw(turbine.getKeyFrame(true), x, y, width, height);
        });
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
}
