package ch.fhnw.roundtable.etopia.views.geothermal.ui;

import ch.fhnw.roundtable.etopia.ETopia;
import ch.fhnw.roundtable.etopia.views.Assets;
import ch.fhnw.roundtable.etopia.views.Renderer;
import ch.fhnw.roundtable.etopia.views.UI;
import ch.fhnw.roundtable.etopia.views.geothermal.GeothermalConfiguration;
import ch.fhnw.roundtable.etopia.views.geothermal.game.GeothermalGame;
import ch.fhnw.roundtable.etopia.views.geothermal.game.PipeSegment;
import ch.fhnw.roundtable.etopia.views.geothermal.game.Rock;

public class GeothermalUI implements UI<GeothermalGame> {

    private final GeothermalConfiguration geothermalConfiguration;
    private final Assets<GeothermalAsset> assets;

    public GeothermalUI(GeothermalConfiguration geothermalConfiguration, Assets<GeothermalAsset> assets) {
        this.geothermalConfiguration = geothermalConfiguration;
        this.assets = assets;
    }

    @Override
    public void render(GeothermalGame game, Renderer renderer) {
        renderer.setCameraPosition(ETopia.WORLD_WIDTH / 2f, game.getDrill().getY());

        renderer.batch(batch -> {
            var map = geothermalConfiguration.mapSize;
            batch.draw(assets.getTexture(GeothermalAsset.BACKGROUND), 0, 0, map.width(), map.height());

            for (Rock rock : game.getRocks()) {
                batch.draw(assets.getTexture(GeothermalAsset.ROCK),
                        rock.getX(), rock.getY(),
                        rock.getWidth(), rock.getHeight());
            }

            for (PipeSegment pipeSegment : game.getDownPipe()) {
                batch.drawCentered(assets.getTexture(GeothermalAsset.PIPE),
                        pipeSegment.getX(), pipeSegment.getY(),
                        pipeSegment.getWidth(), pipeSegment.getHeight(),
                        pipeSegment.getRotation());
            }

            for (PipeSegment pipeSegment : game.getUpPipe()) {
                batch.drawCentered(assets.getTexture(GeothermalAsset.PIPE),
                        pipeSegment.getX(), pipeSegment.getY(),
                        pipeSegment.getWidth(), pipeSegment.getHeight(),
                        pipeSegment.getRotation());
            }

            var drill = game.getDrill();
            batch.drawCentered(assets.getTexture(GeothermalAsset.DRILL),
                    drill.getX(), drill.getY(),
                    drill.getWidth(), drill.getHeight(),
                    drill.getRotation());
        });

        renderer.resetCamera();
    }

    @Override
    public void dispose() {
        assets.dispose();
    }
}
