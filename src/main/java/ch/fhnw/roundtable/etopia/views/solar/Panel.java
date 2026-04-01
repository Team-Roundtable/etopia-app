package ch.fhnw.roundtable.etopia.views.solar;

import ch.fhnw.roundtable.etopia.ETopia;
import ch.fhnw.roundtable.etopia.input.Input;
import ch.fhnw.roundtable.etopia.views.Renderer;
import ch.fhnw.roundtable.etopia.views.View;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import static com.badlogic.gdx.math.MathUtils.random;

public class Panel implements View {

    private final Sprite panel;
    private final Sprite stand;
    private final Sprite dirt;
    private int dirtLayer = 0;
    private float timer = 0;

    public Panel(Texture panel, Texture stand, Texture dirt) {
        this.panel = new Sprite(panel);
        this.panel.setScale(4f);
        this.panel.setCenterX(ETopia.WORLD_WIDTH / 2f);
        this.panel.setCenterY(ETopia.WORLD_HEIGHT / 4f);

        this.stand = new Sprite(stand);
        this.stand.setScale(4f);
        this.stand.setCenterX(ETopia.WORLD_WIDTH / 2f);
        this.stand.setCenterY(ETopia.WORLD_HEIGHT / 4f);

        this.dirt = new Sprite(dirt);
        this.dirt.setScale(4f);
        this.dirt.setCenterX(ETopia.WORLD_WIDTH / 2f);
        this.dirt.setCenterY(ETopia.WORLD_HEIGHT / 4f);
    }

    public float getCenterX() {
        return panel.getX() + panel.getWidth() / 2f;
    }

    public float getCenterY() {
        return panel.getY() + panel.getHeight() / 2f;
    }

    public float getRotation() {
        return panel.getRotation();
    }

    public int getDirtLayer() {
        return dirtLayer;
    }

    @Override
    public void update(float delta, Input input) {
        float maxRotation = 70f;
        float rotationSpeed = 75f;

        dirtHandler(delta, input);

        Sprite[] movablePanelSprites = new Sprite[]{panel, dirt};

        for (Sprite sprite : movablePanelSprites) {
            panelMovementHandler(delta, input, sprite, rotationSpeed, maxRotation);
        }
    }

    private static void panelMovementHandler(float delta, Input input, Sprite sprite, float rotationSpeed, float maxRotation) {
        sprite.rotate(-rotationSpeed * delta * input.getHorizontalInput());

        if (sprite.getRotation() >= maxRotation && input.isLeftPressed()) {
            sprite.setRotation(maxRotation);
        } else if (sprite.getRotation() <= -maxRotation && input.isRightPressed()) {
            sprite.setRotation(-maxRotation);
        }
    }

    private void dirtHandler(float delta, Input input) {
        float dirtCooldown = 2f;

        timer += delta;
        if (timer >= dirtCooldown && random.nextBoolean()) {
            timer = 0;
            dirtLayer = Math.min(3, dirtLayer + 1);
        }

        if (input.isSelectJustPressed()) {
            dirtLayer = Math.max(0, dirtLayer - 1);
        }
    }

    @Override
    public void render(Renderer renderer) {
        renderer.batch(batch -> {
            stand.draw(batch);
            panel.draw(batch);
            for (int i = 0; i <= dirtLayer; i++) {
                dirt.draw(batch);
            }
        });
    }

    @Override
    public void dispose() {

    }
}
