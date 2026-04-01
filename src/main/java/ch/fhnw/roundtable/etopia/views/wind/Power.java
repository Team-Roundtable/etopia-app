package ch.fhnw.roundtable.etopia.views.wind;

import ch.fhnw.roundtable.etopia.ETopia;
import ch.fhnw.roundtable.etopia.views.Renderer;
import ch.fhnw.roundtable.etopia.input.Input;
import ch.fhnw.roundtable.etopia.views.Entity;
import com.badlogic.gdx.graphics.Texture;

public class Power extends Entity {

    public static final float BAR_WIDTH = 512;
    public static final float BAR_HEIGHT = 64;

    public static final float INDICATOR_WIDTH = 32;
    public static final float INDICATOR_HEIGHT = 32;
    private final float indicatorY = ETopia.WORLD_HEIGHT - BAR_HEIGHT + INDICATOR_WIDTH / 2;
    private final Texture progress;
    private final Texture indicator;
    private float indicatorX = ETopia.WORLD_WIDTH / 2.0f - BAR_WIDTH / 2.0f + INDICATOR_WIDTH / 2;
    private float level = 0.0f;

    public Power(Texture progress, Texture indicator) {
        super(ETopia.WORLD_WIDTH / 2.0f - BAR_WIDTH / 2.0f, ETopia.WORLD_HEIGHT - BAR_HEIGHT, BAR_WIDTH, BAR_HEIGHT);
        this.progress = progress;
        this.indicator = indicator;
    }

    @Override
    public void updateEntity(float delta, Input input) {
        indicatorX = (ETopia.WORLD_WIDTH / 1.5f - INDICATOR_WIDTH / 2) + (BAR_WIDTH / 2 * level);
    }

    @Override
    public void renderEntity(Renderer renderer) {
        renderer.batch(batch -> {
            batch.draw(progress, x, y, BAR_WIDTH, BAR_HEIGHT);
            batch.draw(indicator,
                    indicatorX,
                    indicatorY,
                    INDICATOR_WIDTH + (BAR_WIDTH - INDICATOR_WIDTH * 2) * level,
                    INDICATOR_HEIGHT);
        });
    }

    public void addIndicator(float value) {
        this.level = Math.clamp(this.level + value, 0f, 1.0f);
    }

    public float getLevel() {
        return level;
    }
}
