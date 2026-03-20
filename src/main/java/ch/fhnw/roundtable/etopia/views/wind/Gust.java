package ch.fhnw.roundtable.etopia.views.wind;

import ch.fhnw.roundtable.etopia.helpers.AnimationHelper;
import ch.fhnw.roundtable.etopia.views.Renderer;
import ch.fhnw.roundtable.etopia.input.Input;
import ch.fhnw.roundtable.etopia.views.Entity;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class Gust extends Entity {

    private static final float SPEED = 800;

    private final AnimationHelper gust;

    public Gust(float x, float y, Texture gust) {
        super(x, y, 64, 64);
        this.gust = new AnimationHelper(gust, 64, 64, 0.1f);
    }

    @Override
    public void updateEntity(float delta, Input input) {
        gust.updateTime(delta);

        x -= delta * SPEED;
    }

    @Override
    public void renderEntity(Renderer renderer) {
        renderer.batch(batch -> {
            batch.draw(gust.getKeyFrame(true), x, y, width, height);
        });
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
}
