package ch.fhnw.roundtable.etopia.views.biogas;

import ch.fhnw.roundtable.etopia.input.Input;
import ch.fhnw.roundtable.etopia.views.Entity;
import ch.fhnw.roundtable.etopia.views.Renderer;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Interpolation;

import static ch.fhnw.roundtable.etopia.views.biogas.BiogasAsset.SCALE_MULTIPLIER;

public class Cursor extends Entity {
    private static final float MOVE_TIME = 0.2f;
    private static final float INPUT_COOLDOWN = 0.2f;

    private final Texture texture;

    private float startX;
    private float startY;
    private int gridX = 0;
    private int gridY = 1;

    private float elapsed;
    private float inputTimer = 0f;
    private final int gridLengthY;
    private final int gridLengthX;

    public Cursor(Texture texture, int x, int y, int gridLengthX, int gridLengthY) {
        super(x, y,
                texture.getWidth() * SCALE_MULTIPLIER,
                texture.getHeight() * SCALE_MULTIPLIER
        );

        this.texture = texture;
        this.gridLengthX = gridLengthX;
        this.gridLengthY = gridLengthY;

        startX = x;
        startY = y;
    }

    private void updateGridPosition(float delta, Input input) {
        inputTimer -= delta;

        if (inputTimer <= 0) {
            inputTimer = INPUT_COOLDOWN;

            int newX = gridX + (int) input.getHorizontalInput();
            int newY = gridY + (int) input.getVerticalInput();

            gridY = Math.max(0, Math.min(newY, gridLengthY - 1));
            gridX = Math.max(1, Math.min(newX, gridLengthX - 2));
        }
    }

    public int getGridX() {
        return gridX;
    }

    public int getGridY() {
        return gridY;
    }

    @Override
    public void updateEntity(float delta, Input input) {
        updateGridPosition(delta, input);

        var pos = Biogas.cellPositionToWorldSpace(gridX, gridY);
        float targetX = pos.x;
        float targetY = pos.y;

        elapsed += delta;
        float progress = Math.min(elapsed / MOVE_TIME, 1f);

        x = Interpolation.linear.apply(startX, targetX, progress);
        y = Interpolation.linear.apply(startY, targetY, progress);

        if (progress >= 1f) {
            x = targetX;
            y = targetY;
            startX = x;
            startY = y;
            elapsed = 0;
        }
    }

    @Override
    public void renderEntity(Renderer renderer) {
        renderer.batch(batch -> {
            batch.draw(texture, x, y, width, height);
        });
    }
}
