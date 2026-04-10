package ch.fhnw.roundtable.etopia.views.grid.ui;

import ch.fhnw.roundtable.etopia.ETopia;
import ch.fhnw.roundtable.etopia.views.Assets;
import ch.fhnw.roundtable.etopia.views.Renderer;
import ch.fhnw.roundtable.etopia.views.UI;
import ch.fhnw.roundtable.etopia.views.grid.GridConfiguration;
import ch.fhnw.roundtable.etopia.views.grid.game.GridGame;
import ch.fhnw.roundtable.etopia.views.grid.game.Pipe;
import ch.fhnw.roundtable.etopia.views.grid.game.Pipes;

public class GridUI implements UI<GridGame> {

    private final GridConfiguration gridConfiguration;
    private final Assets<GridAsset> assets;

    public GridUI(GridConfiguration gridConfiguration, Assets<GridAsset> assets) {
        this.gridConfiguration = gridConfiguration;
        this.assets = assets;
    }

    @Override
    public void render(GridGame game, Renderer renderer) {
        renderer.batch(batch -> {

            batch.drawBackground(assets.getTexture(GridAsset.BACKGROUND));

            var pipeSize = gridConfiguration.pipeSize;
            var paddingX = (ETopia.WORLD_WIDTH - (gridConfiguration.mapWidth - 1) * pipeSize.width()) / 2f;
            var paddingY = (ETopia.WORLD_HEIGHT - (gridConfiguration.mapHeight - 1) * pipeSize.height()) / 2f;

            Pipes pipes = game.getPipes();
            for (int x = 0; x < pipes.width(); x++) {
                for (int y = 0; y < pipes.height(); y++) {
                    var pipe = pipes.get(x, y);

                    if (pipe != null) {
                        batch.drawCentered(assets.getTexture(translate(pipe)),
                                paddingX + x * pipeSize.width(), paddingY + y * pipeSize.height(),
                                pipeSize.width(), pipeSize.height(),
                                pipe.getRotation() * 90);
                    }
                }
            }

            batch.drawCentered(assets.getTexture(GridAsset.CURSOR),
                    paddingX + game.getCursor().getX() * pipeSize.width(),
                    paddingY + game.getCursor().getY() * pipeSize.height(),
                    pipeSize.width(), pipeSize.height(),
                    0);
        });
    }

    @Override
    public void dispose() {
        assets.dispose();
    }

    public GridAsset translate(Pipe pipe) {
        return switch (pipe.getType()) {
            case CROSS_IMMUTABLE -> pipe.isPowered()
                    ? GridAsset.CROSS_IMMUTABLE_POWERED : GridAsset.CROSS_IMMUTABLE_UNPOWERED;
            case STRAIGHT_IMMUTABLE -> pipe.isPowered()
                    ? GridAsset.STRAIGHT_IMMUTABLE_POWERED : GridAsset.STRAIGHT_IMMUTABLE_UNPOWERED;
            case STRAIGHT_EDITABLE -> pipe.isPowered()
                    ? GridAsset.STRAIGHT_EDITABLE_POWERED : GridAsset.STRAIGHT_EDITABLE_UNPOWERED;
            case CORNER_IMMUTABLE -> pipe.isPowered()
                    ? GridAsset.CORNER_IMMUTABLE_POWERED : GridAsset.CORNER_IMMUTABLE_UNPOWERED;
            case CORNER_EDITABLE -> pipe.isPowered()
                    ? GridAsset.CORNER_EDITABLE_POWERED : GridAsset.CORNER_EDITABLE_UNPOWERED;
            case THREE_WAY_IMMUTABLE -> pipe.isPowered()
                    ? GridAsset.THREE_WAY_IMMUTABLE_POWERED : GridAsset.THREE_WAY_IMMUTABLE_UNPOWERED;
            case THREE_WAY_EDITABLE -> pipe.isPowered()
                    ? GridAsset.THREE_WAY_EDITABLE_POWERED : GridAsset.THREE_WAY_EDITABLE_UNPOWERED;
            case PRODUCER -> GridAsset.PRODUCER;
            case CONSUMER -> pipe.isPowered()
                    ? GridAsset.CONSUMER_POWERED : GridAsset.CONSUMER_UNPOWERED;
        };
    }
}
