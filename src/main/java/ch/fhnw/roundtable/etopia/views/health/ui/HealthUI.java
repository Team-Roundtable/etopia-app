package ch.fhnw.roundtable.etopia.views.health.ui;

import ch.fhnw.roundtable.etopia.views.Assets;
import ch.fhnw.roundtable.etopia.views.Renderer;
import ch.fhnw.roundtable.etopia.views.UI;
import ch.fhnw.roundtable.etopia.views.health.HealthConfiguration;
import ch.fhnw.roundtable.etopia.views.health.game.HealthGame;

public class HealthUI implements UI<HealthGame> {

    private final HealthConfiguration healthConfiguration;
    private final Assets<HealthAsset> assets;

    public HealthUI(HealthConfiguration healthConfiguration, Assets<HealthAsset> assets) {
        this.healthConfiguration = healthConfiguration;
        this.assets = assets;
    }

    @Override
    public void render(HealthGame game, Renderer renderer) {

        var heartSize = healthConfiguration.heartSize;
        var offsetX = healthConfiguration.offsetX;
        var offsetY = healthConfiguration.offsetY;

        renderer.batch(render -> {
            for (int i = 0; i < game.getCurrent(); i++) {
                render.draw(assets.getTexture(HealthAsset.HEART),
                        offsetX + heartSize.width() * i, offsetY,
                        heartSize.width(), heartSize.height());
            }
        });
    }

    @Override
    public void dispose() {
        assets.dispose();
    }
}
