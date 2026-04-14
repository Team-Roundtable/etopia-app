package ch.fhnw.roundtable.etopia.views.wind.ui;

import ch.fhnw.roundtable.etopia.views.Assets;
import ch.fhnw.roundtable.etopia.views.Renderer;
import ch.fhnw.roundtable.etopia.views.UI;
import ch.fhnw.roundtable.etopia.views.wind.game.Gust;
import ch.fhnw.roundtable.etopia.views.wind.game.WindGame;

public class WindUI implements UI<WindGame> {

    private final Assets<WindAsset> assets;

    public WindUI(Assets<WindAsset> assets) {
        this.assets = assets;
    }

    @Override
    public void render(WindGame game, Renderer renderer) {
        renderer.batch(batch -> {
            batch.drawBackground(assets.getTexture(WindAsset.BACKGROUND));

            var turbine = game.getTurbine();
            // todo move magic values to config
            batch.draw(assets.getTexture(WindAsset.POLE),
                    turbine.getX() + 40, turbine.getY() - 1860,
                    16, 2000);
            batch.draw(assets.getTexture(WindAsset.TURBINE),
                    turbine.getX(), turbine.getY(),
                    turbine.getWidth(), turbine.getHeight());

            for (Gust gust : game.getGusts()) {
                var texture = assets.getTexture(gust.isHarmful() ? WindAsset.TORNADO : WindAsset.GUST);
                batch.draw(texture, gust.getX(), gust.getY(), gust.getWidth(), gust.getHeight());
            }
        });
    }

    @Override
    public void dispose() {
        assets.dispose();
    }
}
