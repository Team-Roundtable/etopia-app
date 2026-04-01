package ch.fhnw.roundtable.etopia.views.geothermal;

import ch.fhnw.roundtable.etopia.input.Input;
import ch.fhnw.roundtable.etopia.views.Renderer;
import ch.fhnw.roundtable.etopia.views.View;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

public class PipeSegment implements View {

    static final double PIPE_LENGTH = 128;
    private static final float COLLIDER_RADIUS = 32;

    private final Sprite sprite;
    private final Vector2 center;

    public PipeSegment(Texture pipeTex, Vector2 start, Vector2 end) {

        sprite = new Sprite(pipeTex);
        center = new Vector2((start.x + end.x) / 2f, (start.y + end.y) / 2f);
        sprite.setCenter(center.x, center.y);

        double rotation = -Math.atan2(start.x - end.x, start.y - end.y);
        sprite.setRotation((float) Math.toDegrees(rotation));

        sprite.setScale(2f);
    }

    @Override
    public void update(float delta, Input input) {

    }

    @Override
    public void render(Renderer renderer) {
        renderer.batch(batch -> {
            sprite.draw(batch);
        });
    }

    @Override
    public void dispose() {

    }

    public Circle getCollider() {
        return new Circle(center.x, center.y, COLLIDER_RADIUS);
    }
}
