package ch.fhnw.roundtable.etopia.minigames.wind;

import ch.fhnw.roundtable.etopia.ETopia;
import ch.fhnw.roundtable.etopia.Renderer;
import ch.fhnw.roundtable.etopia.input.Input;
import ch.fhnw.roundtable.etopia.view.View;
import com.badlogic.gdx.graphics.Texture;

public class Power implements View {

    public static final float BAR_WIDTH = 512;
    public static final float BAR_HEIGHT = 64;

    public static final float INDICATOR_WIDTH = 32;
    public static final float INDICATOR_HEIGHT = 32;

    private Texture progress;
    private Texture indicator;
    private final float barX = ETopia.WORLD_WIDTH / 1.5f - BAR_WIDTH / 2.0f;
    private final float barY = ETopia.WORLD_HEIGHT - BAR_HEIGHT;
    private float indicatorX = ETopia.WORLD_WIDTH / 1.5f - BAR_WIDTH / 2.0f + INDICATOR_WIDTH / 2;
    private final float indicatorY = ETopia.WORLD_HEIGHT - BAR_HEIGHT + INDICATOR_WIDTH / 2;
    private float level;

    @Override
    public void create() {
        progress = new Texture("assets/wind/power-bar.png");
        indicator = new Texture("assets/wind/power-indicator.png");
        level = 0.0f;
    }

    @Override
    public void update(float delta, Input input) {
        indicatorX = (ETopia.WORLD_WIDTH / 1.5f - INDICATOR_WIDTH / 2) + (BAR_WIDTH / 2 * level);
    }

    @Override
    public void render(Renderer renderer) {
        renderer.batch(batch -> {
            batch.draw(progress, barX, barY, BAR_WIDTH, BAR_HEIGHT);
            batch.draw(indicator, indicatorX, indicatorY, INDICATOR_WIDTH + (BAR_WIDTH - INDICATOR_WIDTH * 2) * level, INDICATOR_HEIGHT);
        });
    }

    @Override
    public void dispose() {

    }

    public void addIndicator(float level) {
        this.level = Math.clamp(this.level + level, 0f, 1.0f);
    }

    public float getLevel() {
        return level;
    }
}
