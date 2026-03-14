package ch.fhnw.roundtable.etopia.minigames.wind;

import ch.fhnw.roundtable.etopia.AnimationHelper;
import ch.fhnw.roundtable.etopia.ETopia;
import ch.fhnw.roundtable.etopia.Renderer;
import ch.fhnw.roundtable.etopia.input.Input;
import ch.fhnw.roundtable.etopia.view.View;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public class Turbine implements View {

    public static final float WIDTH = 64 * 3;
    public static final float HEIGHT = 128 * 3;
    public static final float SPEED = 200;

    private AnimationHelper turbine;
    private Texture pole;
    private final float x = 100;
    private float y;

    @Override
    public void create() {
        y = ETopia.WORLD_HEIGHT / 4.0f;
        turbine = new AnimationHelper("assets/wind/turbine.png", 64, 128, 0.1f);
        pole = new Texture("assets/wind/turbine-pole.png");
    }

    @Override
    public void update(float delta, Input input) {
        turbine.updateTime(delta);
        y = Math.clamp(y + input.getVerticalInput() * (SPEED * delta), 0, ETopia.WORLD_HEIGHT - HEIGHT);
    }

    @Override
    public void render(Renderer renderer) {
        renderer.batch(batch -> {
            batch.draw(pole, x + 54, y - 864, 32, 1080);
            batch.draw(turbine.getKeyFrame(true), x, y, WIDTH, HEIGHT);
        });
    }

    @Override
    public void dispose() {

    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, WIDTH, HEIGHT);
    }
}
