package ch.fhnw.roundtable.etopia.views.grid.ui;

import ch.fhnw.roundtable.etopia.UI;
import ch.fhnw.roundtable.etopia.configuration.Grid;
import ch.fhnw.roundtable.etopia.rendering.Assets;
import ch.fhnw.roundtable.etopia.rendering.Renderer;
import ch.fhnw.roundtable.etopia.views.grid.model.PipeType;
import ch.fhnw.roundtable.etopia.views.grid.state.GridPipeState;
import ch.fhnw.roundtable.etopia.views.grid.state.GridState;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GridUI implements UI<GridState> {

    private static final Map<PipeType, GridAsset> UNPOWERED = Map.of(
            PipeType.STRAIGHT_EDITABLE, GridAsset.STRAIGHT_EDITABLE_UNPOWERED,
            PipeType.CORNER_EDITABLE, GridAsset.CORNER_EDITABLE_UNPOWERED,
            PipeType.THREE_WAY_EDITABLE, GridAsset.THREE_WAY_EDITABLE_UNPOWERED,
            PipeType.STRAIGHT_IMMUTABLE, GridAsset.STRAIGHT_IMMUTABLE_UNPOWERED,
            PipeType.CROSS_IMMUTABLE, GridAsset.CROSS_IMMUTABLE_UNPOWERED,
            PipeType.CORNER_IMMUTABLE, GridAsset.CORNER_IMMUTABLE_UNPOWERED,
            PipeType.THREE_WAY_IMMUTABLE, GridAsset.THREE_WAY_IMMUTABLE_UNPOWERED,
            PipeType.CONSUMER, GridAsset.CONSUMER_UNPOWERED,
            PipeType.PRODUCER, GridAsset.PRODUCER
    );

    private static final Map<PipeType, GridAsset> POWERED = Map.of(
            PipeType.STRAIGHT_EDITABLE, GridAsset.STRAIGHT_EDITABLE_POWERED,
            PipeType.CORNER_EDITABLE, GridAsset.CORNER_EDITABLE_POWERED,
            PipeType.THREE_WAY_EDITABLE, GridAsset.THREE_WAY_EDITABLE_POWERED,
            PipeType.STRAIGHT_IMMUTABLE, GridAsset.STRAIGHT_IMMUTABLE_POWERED,
            PipeType.CROSS_IMMUTABLE, GridAsset.CROSS_IMMUTABLE_POWERED,
            PipeType.CORNER_IMMUTABLE, GridAsset.CORNER_IMMUTABLE_POWERED,
            PipeType.THREE_WAY_IMMUTABLE, GridAsset.THREE_WAY_IMMUTABLE_POWERED,
            PipeType.CONSUMER, GridAsset.CONSUMER_POWERED,
            PipeType.PRODUCER, GridAsset.PRODUCER
    );
    public static final float PIPE_ROTATION_SPEED = 40f;

    private final Grid configuration;
    private final Assets<GridAsset> assets;

    private final Map<Vector2, Float> pipeRotations = new HashMap<>();

    public GridUI(Grid configuration, Assets<GridAsset> assets) {
        this.configuration = configuration;
        this.assets = assets;
    }

    @Override
    public void render(GridState state, Renderer renderer) {
        if (configuration.animatedPipeRotation()) {
            updateAnimatedRotation(state.pipes());
        }

        renderer.batch(batch -> {
            batch.drawBackground(assets.getTexture(GridAsset.BACKGROUND));

            for (var pipe : state.pipes()) {
                float rotation = pipe.rotation();
                if (configuration.animatedPipeRotation()) {
                    rotation = pipeRotations.get(new Vector2(pipe.x(), pipe.y()));
                }

                batch.drawCentered(assets.getTexture(translate(pipe.type(), pipe.powered())),
                        pipe.x(), pipe.y(),
                        pipe.width(), pipe.height(),
                        rotation);
            }

            var cursor = state.cursor();
            batch.drawCentered(assets.getTexture(GridAsset.CURSOR),
                    cursor.x(), cursor.y(),
                    cursor.width(), cursor.height(),
                    0);
        });
    }

    private void updateAnimatedRotation(List<GridPipeState> pipes) {
        pipes.forEach(pipe ->
                pipeRotations.putIfAbsent(new Vector2(pipe.x(), pipe.y()), pipe.rotation())
        );

        for (var pipe : pipes) {
            var pos = new Vector2(pipe.x(), pipe.y());
            pipeRotations.compute(pos, (k, currentRotation) ->
                    lerp(currentRotation, pipe.rotation(), Gdx.graphics.getDeltaTime() * PIPE_ROTATION_SPEED));
        }
    }

    @Override
    public void dispose() {
        assets.dispose();
    }

    private GridAsset translate(PipeType type, boolean powered) {
        return powered
                ? POWERED.get(type)
                : UNPOWERED.get(type);
    }

    private float lerp(float a, float b, float t) {
        return (a * (1 - t)) + (b * t);
    }
}
