package ch.fhnw.roundtable.etopia.views.wind;

import ch.fhnw.roundtable.etopia.helpers.AnimationHelper;
import ch.fhnw.roundtable.etopia.input.Input;
import ch.fhnw.roundtable.etopia.views.Entity;
import ch.fhnw.roundtable.etopia.views.Renderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.Texture;

public class Tornado extends Entity {

    private static final float SPEED = 600;

    private final AnimationHelper tornado;

    public Tornado(float x, float y, Texture tornado){
        super(x, y, 64, 64);
        this.tornado = new AnimationHelper(tornado, 64, 64, 0.1f);
    }

    @Override
    public void updateEntity(float delta, Input input) {
        tornado.updateTime(delta);
        x -= delta * SPEED;
    }

    @Override
    public void renderEntity(Renderer renderer) {
        renderer.batch(batch -> {
            batch.draw(tornado.getKeyFrame(true), x, y, width, height);
        });

    }

    public Rectangle getBounds(){
        return new Rectangle(x, y, width, height);
    }
}
