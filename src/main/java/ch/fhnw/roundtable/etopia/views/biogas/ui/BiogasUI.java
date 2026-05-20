package ch.fhnw.roundtable.etopia.views.biogas.ui;

import ch.fhnw.roundtable.etopia.UI;
import ch.fhnw.roundtable.etopia.rendering.Assets;
import ch.fhnw.roundtable.etopia.rendering.Renderer;
import ch.fhnw.roundtable.etopia.views.biogas.model.TrashType;
import ch.fhnw.roundtable.etopia.views.biogas.state.BiogasState;

public class BiogasUI implements UI<BiogasState> {

    private final Assets<BiogasAsset> assets;

    public BiogasUI(Assets<BiogasAsset> assets) {
        this.assets = assets;
    }

    @Override
    public void render(BiogasState state, Renderer renderer) {
        renderer.batch(batch -> {
            batch.drawBackground(assets.getTexture(BiogasAsset.BACKGROUND));

            for (var trash : state.trashes()) {
                batch.draw(assets.getTexture(translate(trash.type())),
                        trash.x(),
                        trash.y(),
                        trash.width(),
                        trash.height());
            }

            var cursor = state.cursor();
            batch.draw(assets.getTexture(BiogasAsset.CURSOR),
                    cursor.x(),
                    cursor.y(),
                    cursor.width(),
                    cursor.height());
        });
    }

    @Override
    public void dispose() {
        assets.dispose();
    }

    private BiogasAsset translate(TrashType type) {
        return switch (type) {
            case APPLE -> BiogasAsset.APPLE;
            case BANANA -> BiogasAsset.BANANA;
            case BOTTLE -> BiogasAsset.BOTTLE;
            case CAN -> BiogasAsset.CAN;
            case COLA -> BiogasAsset.COLA;
            case CUP -> BiogasAsset.CUP;
            case GRAPES -> BiogasAsset.GRAPES;
            case GLASS -> BiogasAsset.GLASS;
        };
    }
}
