package ch.fhnw.roundtable.etopia.views.solar;

import ch.fhnw.roundtable.etopia.ETopia;
import ch.fhnw.roundtable.etopia.input.Input;
import ch.fhnw.roundtable.etopia.views.Renderer;
import ch.fhnw.roundtable.etopia.views.View;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import static com.badlogic.gdx.math.MathUtils.cos;
import static com.badlogic.gdx.math.MathUtils.sin;

public class Sun implements View {

    private final Sprite sun;
    private float angle = 0;
    private final float radius = 128f;
    private boolean isAtEndOfCycle = false;
    private float secondsPassed = 0;
    private float x;
    private float y;

    public Sun(Texture texture) {
        this.sun = new Sprite(texture);
        this.sun.setScale(4f);
        this.sun.setCenterX(-radius);
        this.x = sun.getX();
        this.y = sun.getY();
    }

    public float getCenterX() {
        return sun.getX() + sun.getWidth() / 2f;
    }

    public float getCenterY() {
        return sun.getY() + sun.getHeight() / 2f;
    }

    public boolean getIsCycleOver() {
        return isAtEndOfCycle;
    }

    @Override
    public void update(float delta, Input input) {

        if (!isAtEndOfCycle) {
            coordsCalculation();
        } else if (secondsPassed >= 3) {
            resetCycle();
        }

        if (sun.getX() < x - 32f) { // 32f is the difference between the calculated x value and getX() value
            sun.setCenterX(x);
            sun.setCenterY(y);
        } else {
            isAtEndOfCycle = true;
            secondsPassed += delta;
        }
    }

    private void resetCycle() {
        angle = 0;
        sun.setCenterX(-radius - 32f);
        x = -radius;
        secondsPassed = 0;
        isAtEndOfCycle = false;
    }

    private void coordsCalculation() {
        angle += 0.0075f; // Determines the speed of the sun; Lower = Slower, Higher = Faster
        x = (-(cos(angle) * (ETopia.WORLD_WIDTH + radius * 2f)) + ETopia.WORLD_WIDTH) / 2f;
        y = ((sin(angle) * (ETopia.WORLD_HEIGHT - radius * 2f)) + ETopia.WORLD_HEIGHT) / 2f;
    }

    @Override
    public void render(Renderer renderer) {
        renderer.batch(batch -> {
            sun.draw(batch);
        });
    }

    @Override
    public void dispose() {

    }
}
