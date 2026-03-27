package ch.fhnw.roundtable.etopia.views.commons.healthbar;

import ch.fhnw.roundtable.etopia.input.Input;
import ch.fhnw.roundtable.etopia.views.Entity;
import ch.fhnw.roundtable.etopia.views.Renderer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class HealthBar extends Entity {
    private static final int MAX_HEALTH = 3;
    private int health;

    public HealthBar(float x, float y) {
        super(x, y, 300, 100);
    }

    public void removeHealth() {
        health -= 1;
    }

    public void resetHealth() {
        health = 3;
    }

    public boolean isAlive() {
        return health > 0;
    }

    public boolean isDead() {
        return !isAlive();
    }

    @Override
    public void updateEntity(float delta, Input input) {

    }

    @Override
    public void renderEntity(Renderer renderer) {
        renderer.shape(ShapeRenderer.ShapeType.Filled, (shape) -> {
            shape.setColor(Color.RED);
            for (int i = 0; i < health; i++) {
                shape.circle(x + i * 100, y, 50);
            }
        });
    }

    public int getHealth() {
        return health;
    }
}
