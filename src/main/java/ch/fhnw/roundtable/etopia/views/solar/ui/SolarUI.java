package ch.fhnw.roundtable.etopia.views.solar.ui;

import ch.fhnw.roundtable.etopia.UI;
import ch.fhnw.roundtable.etopia.rendering.Assets;
import ch.fhnw.roundtable.etopia.rendering.Renderer;
import ch.fhnw.roundtable.etopia.views.solar.state.SolarState;
import com.badlogic.gdx.graphics.Color;

public class SolarUI implements UI<SolarState> {

    private final Assets<SolarAsset> assets;

    public SolarUI(Assets<SolarAsset> assets) {
        this.assets = assets;
    }

    @Override
    public void render(SolarState state, Renderer renderer) {
        renderer.batch(batch -> {

            var sun = state.sun();
            batch.setColor(sun.dayLight(), sun.dayLight(), sun.dayLight(), 1f);

            batch.drawBackground(assets.getTexture(SolarAsset.BACKGROUND));

            batch.draw(assets.getTexture(SolarAsset.SUN),
                    sun.x(), sun.y(),
                    sun.width(), sun.height());

            var panel = state.panel();
            batch.drawCentered(assets.getTexture(SolarAsset.STAND),
                    panel.x(), panel.y(),
                    panel.width(), panel.height(),
                    0);

            renderer.font.setColor(Color.WHITE);
            renderer.font.draw(batch, String.format("%02.0f%%", state.efficiency()),
                    panel.x() - panel.width() / 2,
                    panel.y() - panel.height() / 2);

            batch.drawCentered(assets.getTexture(SolarAsset.PANEL),
                    panel.x(), panel.y(),
                    panel.width(), panel.height(),
                    panel.rotation());

            batch.setColor(1f, 1f, 1f, 1f);
        });
    }

    @Override
    public void dispose() {
        assets.dispose();
    }
}
