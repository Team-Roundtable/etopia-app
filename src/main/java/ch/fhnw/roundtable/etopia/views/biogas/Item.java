package ch.fhnw.roundtable.etopia.views.biogas;

import ch.fhnw.roundtable.etopia.input.Input;
import ch.fhnw.roundtable.etopia.views.Entity;
import ch.fhnw.roundtable.etopia.views.Renderer;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Interpolation;

import static ch.fhnw.roundtable.etopia.views.biogas.BiogasAsset.SCALE_MULTIPLIER;

public class Item extends Entity {
    private static final float INTERPOLATION_TIME = 0.25f;
    private final boolean biodegradable;
    private final Texture texture;

    private float targetX;
    private float startX;
    private float elapsed = 0f;

    public Item(float x, float y, Texture texture, boolean biodegradable) {
        super(x, y,
                texture.getWidth() * SCALE_MULTIPLIER,
                texture.getHeight() * SCALE_MULTIPLIER
        );
        startX = x;
        targetX = x;
        this.biodegradable = biodegradable;
        this.texture = texture;
    }

    public void setPosition(float x) {
        targetX = x;
    }

    public boolean isBiodegradable() {
        return biodegradable;
    }

    public void deleteItem() {
        // Todo: small deletion animation
        this.disposeEntity();
    }

    @Override
    public void updateEntity(float delta, Input input) {
        elapsed += delta;
        float progress = Math.min(elapsed / INTERPOLATION_TIME, 1f);
        x = Interpolation.swing.apply(startX, targetX, progress);

        if (progress >= 1f) {
            x = targetX;
            startX = x;
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
