package ch.fhnw.roundtable.etopia.views.biogas.ui;

import ch.fhnw.roundtable.etopia.UI;
import ch.fhnw.roundtable.etopia.configuration.Biogas;
import ch.fhnw.roundtable.etopia.rendering.Assets;
import ch.fhnw.roundtable.etopia.rendering.Renderer;
import ch.fhnw.roundtable.etopia.views.biogas.model.TrashType;
import ch.fhnw.roundtable.etopia.views.biogas.state.BiogasState;
import ch.fhnw.roundtable.etopia.views.biogas.state.BiogasTrashState;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class BiogasUI implements UI<BiogasState> {

    private final Assets<BiogasAsset> assets;
    private final Map<UUID, Icon> animatedItemPositions = new HashMap<>();
    private final Biogas configuration;

    public BiogasUI(Biogas configuration, Assets<BiogasAsset> assets) {
        this.assets = assets;
        this.configuration = configuration;
    }

    @Override
    public void render(BiogasState state, Renderer renderer) {
        renderer.batch(batch -> {
            batch.drawBackground(assets.getTexture(BiogasAsset.BACKGROUND));

            if (configuration.animatedOnConveyor()) {
                addAnimatedTrashes(state.trashes());
                removeAnimatedTrashes(state.deliveredTrashes());
                removeAnimatedTrashes(state.grabbedTrashes());

                updateAnimatedTrashesGoals(state);
                moveAnimatedTrashes();

                for (var trash : animatedItemPositions.values()) {
                    var trashState = trash.getState();
                    batch.draw(assets.getTexture(translate(trashState.type())),
                            trash.getAnimatedPosition().x,
                            trash.getAnimatedPosition().y,
                            trashState.width(),
                            trashState.height());
                }
            } else {
                for (var trash : state.trashes()) {
                    batch.draw(assets.getTexture(translate(trash.type())),
                            trash.x(),
                            trash.y(),
                            trash.width(),
                            trash.height());
                }
            }

            var cursor = state.cursor();
            batch.draw(assets.getTexture(BiogasAsset.CURSOR),
                    cursor.x(),
                    cursor.y(),
                    cursor.width(),
                    cursor.height());
        });
    }

    private void moveAnimatedTrashes() {
        for (var trash : animatedItemPositions.values()) {
            trash.move(Gdx.graphics.getDeltaTime());
        }
    }

    private void addAnimatedTrashes(List<BiogasTrashState> trashes) {
        for (var item : trashes) {
            animatedItemPositions.putIfAbsent(
                    item.id(),
                    new Icon(item)
            );
        }
    }

    private void updateAnimatedTrashesGoals(BiogasState state) {
        for (var item : state.trashes()) {
            animatedItemPositions.get(item.id()).setGoal(new Vector2(item.x(), item.y()));
        }
    }

    private void removeAnimatedTrashes(List<BiogasTrashState> trashes) {
        for (var item : trashes) {
            animatedItemPositions.remove(item.id());
        }
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
