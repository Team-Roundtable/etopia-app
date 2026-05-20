package ch.fhnw.roundtable.etopia.views.status.ui;

import ch.fhnw.roundtable.etopia.UI;
import ch.fhnw.roundtable.etopia.configuration.Configuration;
import ch.fhnw.roundtable.etopia.rendering.Assets;
import ch.fhnw.roundtable.etopia.rendering.Renderer;
import ch.fhnw.roundtable.etopia.views.status.state.StatusState;

public class StatusUI implements UI<StatusState> {

    private final Configuration configuration;
    private final Assets<StatusAsset> assets;

    public StatusUI(Configuration configuration, Assets<StatusAsset> assets) {
        this.configuration = configuration;
        this.assets = assets;
    }

    @Override
    public void render(StatusState state, Renderer renderer) {
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
            render.draw(assets.getTexture(StatusAsset.TIME), timeX, barY, barWidth * state.time(), barHeight);
            render.draw(assets.getTexture(StatusAsset.BAR), timeX, barY, barWidth, barHeight);

            var energyX = offsetX + configuration.status().energyOffsetX();
            render.draw(assets.getTexture(StatusAsset.ENERGY), energyX, barY, barWidth * state.energy(), barHeight);
            render.draw(assets.getTexture(StatusAsset.BAR), energyX, barY, barWidth, barHeight);

            var healthX = offsetX + configuration.status().healthOffsetX();
            render.draw(assets.getTexture(StatusAsset.HEALTH), healthX, barY, barWidth * state.health(), barHeight);
            render.draw(assets.getTexture(StatusAsset.BAR), healthX, barY, barWidth, barHeight);
        });
    }

    @Override
    public void dispose() {
        assets.dispose();
    }
}
