package ch.fhnw.roundtable.etopia.views.biogas.ui;

import ch.fhnw.roundtable.etopia.views.Assets;
import ch.fhnw.roundtable.etopia.views.Renderer;
import ch.fhnw.roundtable.etopia.views.UI;
import ch.fhnw.roundtable.etopia.views.biogas.BiogasConfiguration;
import ch.fhnw.roundtable.etopia.views.biogas.game.BiogasGame;
import ch.fhnw.roundtable.etopia.views.biogas.game.Trash;

public class BiogasUI implements UI<BiogasGame> {

    private final BiogasConfiguration biogasConfiguration;
    private final Assets<BiogasAsset> assets;

    public BiogasUI(BiogasConfiguration biogasConfiguration, Assets<BiogasAsset> assets) {
        this.biogasConfiguration = biogasConfiguration;
        this.assets = assets;
    }

    @Override
    public void render(BiogasGame game, Renderer renderer) {
        renderer.batch(batch -> {
            batch.drawBackground(assets.getTexture(BiogasAsset.BACKGROUND));

            var conveyorOffsetX = biogasConfiguration.conveyorOffsetX;
            var conveyorOffsetY = biogasConfiguration.conveyorOffsetY;
            var conveyorSize = biogasConfiguration.conveyorSize;

            for (int i = 0; i < biogasConfiguration.conveyorHeight; i++) {
                batch.draw(assets.getTexture(BiogasAsset.CONVEYOR_BELT),
                        conveyorOffsetX, conveyorOffsetY + conveyorSize.height() * i,
                        conveyorSize.width(), conveyorSize.height());
            }

            Trash[][] conveyor = game.getConveyor();
            for (int x = 0; x < conveyor.length; x++) {
                for (int y = 0; y < conveyor[x].length; y++) {
                    var trash = conveyor[x][y];

                    if (trash != null) {
                        batch.draw(assets.getTexture(translate(trash)),
                                conveyorOffsetX + x * trash.getWidth(), conveyorOffsetY + y * trash.getHeight(),
                                trash.getWidth(), trash.getHeight());
                    }
                }
            }

            var cursor = game.getCursor();
            batch.draw(assets.getTexture(BiogasAsset.CURSOR),
                    conveyorOffsetX + cursor.getX() * cursor.getWidth(),
                    conveyorOffsetY + cursor.getY() * cursor.getHeight(),
                    cursor.getWidth(), cursor.getHeight());
        });
    }

    @Override
    public void dispose() {
        assets.dispose();
    }

    private BiogasAsset translate(Trash trash) {
        return trash.isBiodegradable() ? BiogasAsset.GREEN_ITEM : BiogasAsset.RED_ITEM;
    }
}
