package ch.fhnw.roundtable.etopia.views.wind.ui;

import ch.fhnw.roundtable.etopia.UI;
import ch.fhnw.roundtable.etopia.Updateable;
import ch.fhnw.roundtable.etopia.input.Controls;
import ch.fhnw.roundtable.etopia.rendering.AnimatedAsset;
import ch.fhnw.roundtable.etopia.rendering.Assets;
import ch.fhnw.roundtable.etopia.rendering.Renderer;
import ch.fhnw.roundtable.etopia.views.wind.state.WindState;
import com.badlogic.gdx.Gdx;

public class WindUI implements UI<WindState>, Updateable {

    private final Assets<WindAsset> assets;
    private final AnimatedAsset turbineAnimation;

    public WindUI(Assets<WindAsset> assets) {
        this.assets = assets;
        this.turbineAnimation = new AnimatedAsset(assets.getTexture(WindAsset.TURBINE), 4, 1/16f);
    }

    @Override
    public void update(float delta, Controls controls) {

    }

    @Override
    public void render(WindState state, Renderer renderer) {
        turbineAnimation.update(Gdx.graphics.getDeltaTime() * (state.turbine().frozen() ? 0.2f : 1f));

        renderer.batch(batch -> {
            batch.drawBackground(assets.getTexture(WindAsset.BACKGROUND));

            var turbine = state.turbine();
            batch.draw(assets.getTexture(WindAsset.POLE),
                    turbine.x() + 40, turbine.y() - 1860,
                    16, 2000);

            batch.draw(turbineAnimation.getKeyFrame(true),
                    turbine.x(), turbine.y(),
                    turbine.width(), turbine.height());

            for (var gust : state.gusts()) {
                var texture = assets.getTexture(gust.harmful() ? WindAsset.TORNADO : WindAsset.GUST);
                batch.draw(texture, gust.x(), gust.y(), gust.width(), gust.height());
            }
        });
    }

    @Override
    public void dispose() {
        assets.dispose();
    }
}
