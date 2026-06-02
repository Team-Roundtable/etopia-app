package ch.fhnw.roundtable.etopia.views.status.ui;

import ch.fhnw.roundtable.etopia.UI;
import ch.fhnw.roundtable.etopia.configuration.Configuration;
import ch.fhnw.roundtable.etopia.rendering.Assets;
import ch.fhnw.roundtable.etopia.rendering.Render;
import ch.fhnw.roundtable.etopia.rendering.Renderer;
import ch.fhnw.roundtable.etopia.views.status.state.StatusState;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

public class StatusUI implements UI<StatusState> {

    public static final int ICON_KILL_DST = 10;

    private final Configuration configuration;
    private final Assets<StatusAsset> assets;

    private float lastTime;
    private float lastEnergy;
    private float lastHealth;

    private final List<AnimatedStatusIcon> animatedIcons = new ArrayList<>();

    public StatusUI(Configuration configuration, Assets<StatusAsset> assets) {
        this.configuration = configuration;
        this.assets = assets;
    }

    @Override
    public void render(StatusState state, Renderer renderer) {
        if (lastTime == 0 && lastEnergy == 0 && lastHealth == 0
                && (state.time() != 0 || state.energy() != 0 || state.health() != 0)) {
            lastTime = state.time();
            lastEnergy = state.energy();
            lastHealth = state.health();
        }

        renderer.batch(render -> {
            var offsetX = configuration.status().offsetX();
            var offsetY = configuration.status().offsetY();

            render.draw(assets.getTexture(StatusAsset.BACKGROUND),
                    offsetX, offsetY,
                    configuration.status().backgroundWidth(),
                    configuration.status().backgroundHeight());

            var barY = offsetY + configuration.status().barOffsetY();
            var barWidth = configuration.status().barWidth();
            var barHeight = configuration.status().barHeight();

            var timeX = offsetX + configuration.status().timeOffsetX();
            float animatedTime = lerp(lastTime, state.time(), Gdx.graphics.getDeltaTime() * 10f);
            render.draw(assets.getTexture(StatusAsset.TIME), timeX, barY, barWidth * animatedTime, barHeight);
            render.draw(assets.getTexture(StatusAsset.BAR), timeX, barY, barWidth, barHeight);

            var energyX = offsetX + configuration.status().energyOffsetX();
            float animatedEnergy = lerp(lastEnergy, state.energy(), Gdx.graphics.getDeltaTime() * 10f);
            render.draw(assets.getTexture(StatusAsset.ENERGY), energyX, barY, barWidth * animatedEnergy, barHeight);
            render.draw(assets.getTexture(StatusAsset.BAR), energyX, barY, barWidth, barHeight);

            var healthX = offsetX + configuration.status().healthOffsetX();
            float animatedHealth = lerp(lastHealth, state.health(), Gdx.graphics.getDeltaTime() * 10f);
            render.draw(assets.getTexture(StatusAsset.HEALTH), healthX, barY, barWidth * animatedHealth, barHeight);
            render.draw(assets.getTexture(StatusAsset.BAR), healthX, barY, barWidth, barHeight);

            lastTime = animatedTime;
            lastEnergy = animatedEnergy;
            lastHealth = animatedHealth;

            moveIcons();
            drawIcons(render);
        });
    }

    public Vector2 powerIconPosition() {
        var offsetX = configuration.status().offsetX();
        var offsetY = configuration.status().offsetY();
        var barY = offsetY + configuration.status().barOffsetY();
        var energyX = offsetX + configuration.status().energyOffsetX();
        return new Vector2(energyX, barY);
    }

    public Vector2 healthIconPosition() {
        var offsetX = configuration.status().offsetX();
        var offsetY = configuration.status().offsetY();
        var barY = offsetY + configuration.status().barOffsetY();
        var healthX = offsetX + configuration.status().healthOffsetX();
        return new Vector2(healthX, barY);
    }

    private void moveIcons() {
        var iterator = animatedIcons.iterator();
        while (iterator.hasNext()) {
            var icon = iterator.next();

            icon.move(Gdx.graphics.getDeltaTime());

            if (icon.distanceToTarget() < ICON_KILL_DST) {
                iterator.remove();
            }
        }
    }

    private void drawIcons(Render batch) {
        for (var icon : animatedIcons) {
            batch.draw(assets.getTexture(icon.getAsset()),
                    icon.getPosition().x,
                    icon.getPosition().y,
                    configuration.status().animatedIconsSize(),
                    configuration.status().animatedIconsSize());
        }
    }

    @Override
    public void dispose() {
        assets.dispose();
    }

    private float lerp(float a, float b, float t) {
        return (a * (1 - t)) + (b * t);
    }

    public void createAnimatedPowerIcon(Vector2 start, Interpolation interpolation, float speed) {
        animatedIcons.add(
                new AnimatedStatusIcon(start, powerIconPosition(), StatusAsset.POWER_ICON, interpolation, speed));
    }

    public void createAnimatedCrossIcon(Vector2 start, Interpolation interpolation, float speed) {
        animatedIcons.add(
                new AnimatedStatusIcon(start, healthIconPosition(), StatusAsset.CROSS_ICON, interpolation, speed));
    }
}
