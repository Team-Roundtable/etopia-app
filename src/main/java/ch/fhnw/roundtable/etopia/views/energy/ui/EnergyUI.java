package ch.fhnw.roundtable.etopia.views.energy.ui;

import ch.fhnw.roundtable.etopia.views.Assets;
import ch.fhnw.roundtable.etopia.views.Renderer;
import ch.fhnw.roundtable.etopia.views.UI;
import ch.fhnw.roundtable.etopia.views.energy.EnergyConfiguration;
import ch.fhnw.roundtable.etopia.views.energy.game.EnergyGame;

public class EnergyUI implements UI<EnergyGame> {

    private final EnergyConfiguration energyConfiguration;
    private final Assets<EnergyAsset> assets;

    public EnergyUI(EnergyConfiguration energyConfiguration, Assets<EnergyAsset> assets) {
        this.energyConfiguration = energyConfiguration;
        this.assets = assets;
    }

    @Override
    public void render(EnergyGame game, Renderer renderer) {

        var barSize = energyConfiguration.barSize;
        var indicatorSize = energyConfiguration.indicatorSize;
        var padding = energyConfiguration.padding;
        var offsetX = energyConfiguration.offsetX;
        var offsetY = energyConfiguration.offsetY;

        renderer.batch(render -> {
            render.draw(assets.getTexture(EnergyAsset.BAR),
                    offsetX, offsetY,
                    barSize.width(), barSize.height());

            render.draw(assets.getTexture(EnergyAsset.INDICATOR),
                    offsetX + padding, offsetY + padding,
                    Math.max(barSize.width() * game.getCurrent() - 2 * padding, 0), indicatorSize.height());
        });
    }

    @Override
    public void dispose() {
        assets.dispose();
    }
}
