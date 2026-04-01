package ch.fhnw.roundtable.etopia.views.geothermal;

import ch.fhnw.roundtable.etopia.input.Input;
import ch.fhnw.roundtable.etopia.views.Entity;
import ch.fhnw.roundtable.etopia.views.Renderer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;

public class Rock extends Entity {

    private final float radius;

    public Rock(float x, float y, float width, float height) {
        super(x, y, width, height);
        radius = Math.min(width, height) * 0.5f * 0.8f;
    }

    @Override
    public void updateEntity(float delta, Input input) {

    }

    @Override
    public void renderEntity(Renderer renderer) {
        renderer.shape(ShapeRenderer.ShapeType.Filled, shape -> {
            shape.setColor(Color.DARK_GRAY);
            shape.ellipse(x, y, width, height);
        });
    }

    public Circle getCollider() {
        return new Circle(x + width / 2f, y + height / 2f, radius);
    }
}
