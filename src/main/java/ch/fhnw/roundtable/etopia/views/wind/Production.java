package ch.fhnw.roundtable.etopia.views.wind;

import ch.fhnw.roundtable.etopia.ETopia;
import ch.fhnw.roundtable.etopia.views.Renderer;
import ch.fhnw.roundtable.etopia.input.Input;
import ch.fhnw.roundtable.etopia.views.Entity;
import com.badlogic.gdx.graphics.Texture;

public class Production extends Entity {

    public static final float BAR_WIDTH = 512;
    public static final float BAR_HEIGHT = 64;

    public static final float INDICATOR_WIDTH = 16;
    public static final float INDICATOR_HEIGHT = 64;

    private final Texture bar;
    private final Texture indicator;
    // note should be its own entity
    private final float indicatorY = ETopia.WORLD_HEIGHT - BAR_HEIGHT;
    private float indicatorX = ETopia.WORLD_WIDTH / 3.0f - BAR_WIDTH / 2.0f;
    private float level = 0.0f;

    public Production(Texture bar, Texture indicator) {
        super(ETopia.WORLD_WIDTH / 3.0f - BAR_WIDTH / 2.0f, ETopia.WORLD_HEIGHT - BAR_HEIGHT, 512, 64);
        this.bar = bar;
        this.indicator = indicator;
    }

    @Override
    public void updateEntity(float delta, Input input) {
        indicatorX = (ETopia.WORLD_WIDTH / 3.0f - INDICATOR_WIDTH / 2) + (BAR_WIDTH / 2 * level);
    }

    @Override
    public void renderEntity(Renderer renderer) {
        renderer.batch(batch -> {
            batch.draw(bar, x, y, BAR_WIDTH, BAR_HEIGHT);
            batch.draw(indicator, indicatorX, indicatorY, INDICATOR_WIDTH, INDICATOR_HEIGHT);
        });
    }

    public void addIndicator(float level) {
        this.level = Math.clamp(this.level + level, -1.0f, 1.0f);
    }

    public float getLevel() {
        return level;
    }
}
