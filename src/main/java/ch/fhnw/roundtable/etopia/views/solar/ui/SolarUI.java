package ch.fhnw.roundtable.etopia.views.solar.ui;

import ch.fhnw.roundtable.etopia.views.Assets;
import ch.fhnw.roundtable.etopia.views.Renderer;
import ch.fhnw.roundtable.etopia.views.UI;
import ch.fhnw.roundtable.etopia.views.solar.game.SolarGame;

public class SolarUI implements UI<SolarGame> {

    private final Assets<SolarAsset> assets;

    public SolarUI(Assets<SolarAsset> assets) {
        this.assets = assets;
    }

    @Override
    public void render(SolarGame game, Renderer renderer) {
        renderer.batch(batch -> {

            batch.drawBackground(assets.getTexture(SolarAsset.BACKGROUND));

            var sun = game.getSun();
            batch.draw(assets.getTexture(SolarAsset.SUN),
                    sun.getX(), sun.getY(),
                    sun.getWidth(), sun.getHeight());

            var panel = game.getPanel();
            batch.drawCentered(assets.getTexture(SolarAsset.PANEL),
                    panel.getX(), panel.getY(),
                    panel.getWidth(), panel.getHeight(),
                    panel.getRotation());
        });
    }

    @Override
    public void dispose() {
        assets.dispose();
    }
}
