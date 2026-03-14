package ch.fhnw.roundtable.etopia.minigames.wind;

import ch.fhnw.roundtable.etopia.AnimationHelper;
import ch.fhnw.roundtable.etopia.Renderer;
import ch.fhnw.roundtable.etopia.input.Input;
import ch.fhnw.roundtable.etopia.view.View;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public class Gust implements View {

    private static final float WIDTH = 64;
    private static final float HEIGHT = 64;
    private static final float SPEED = 800;

    private AnimationHelper gust;

    private float x;
    private float y;

    @Override
    public void create() {
        gust = new AnimationHelper("assets/wind/gust.png", 64, 64, 0.1f);
    }

    @Override
    public void update(float delta, Input input) {
        gust.updateTime(delta);

        x -= delta * SPEED;
    }

    @Override
    public void render(Renderer renderer) {
        renderer.batch(batch -> {
            batch.draw(gust.getKeyFrame(true), x, y, WIDTH, HEIGHT);
        });
    }

    @Override
    public void dispose() {

    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, WIDTH, HEIGHT);
    }
}
