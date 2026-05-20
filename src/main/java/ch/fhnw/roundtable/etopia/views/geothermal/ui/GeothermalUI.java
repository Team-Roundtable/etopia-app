package ch.fhnw.roundtable.etopia.views.geothermal.ui;

import ch.fhnw.roundtable.etopia.UI;
import ch.fhnw.roundtable.etopia.rendering.Assets;
import ch.fhnw.roundtable.etopia.rendering.Renderer;
import ch.fhnw.roundtable.etopia.views.geothermal.state.GeothermalState;

public class GeothermalUI implements UI<GeothermalState> {

    private final Assets<GeothermalAsset> assets;

    public GeothermalUI(Assets<GeothermalAsset> assets) {
        this.assets = assets;
    }

    @Override
    public void render(GeothermalState state, Renderer renderer) {
        renderer.setCameraPosition(state.cameraPositionX(), state.cameraPositionY());

        renderer.batch(batch -> {
            batch.draw(assets.getTexture(GeothermalAsset.BACKGROUND), 0, 0, state.mapWidth(), state.mapHeight());

            for (var rock : state.rocks()) {
                batch.drawCentered(assets.getTexture(GeothermalAsset.ROCK),
                        rock.x(), rock.y(),
                        rock.width(), rock.height(),
                        rock.rotation());
            }

            for (var pipe : state.pipes()) {
                batch.drawCentered(assets.getTexture(GeothermalAsset.PIPE),
                        pipe.x(), pipe.y(),
                        pipe.width(), pipe.height(),
                        pipe.rotation());
            }

            var drill = state.drill();
            batch.drawCentered(assets.getTexture(GeothermalAsset.DRILL),
                    drill.x(), drill.y(),
                    drill.width(), drill.height(),
                    drill.rotation());
        });

        renderer.resetCamera();
    }

    @Override
    public void dispose() {
        assets.dispose();
    }
}
