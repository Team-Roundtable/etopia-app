package ch.fhnw.roundtable.etopia.minigames.biomass;

import ch.fhnw.roundtable.etopia.ETopia;
import ch.fhnw.roundtable.etopia.Renderer;
import ch.fhnw.roundtable.etopia.input.Input;
import ch.fhnw.roundtable.etopia.minigames.infopanels.GameCompletedPanel;
import ch.fhnw.roundtable.etopia.view.MiniGame;
import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/// Main class of the Bucket minigame (here for libgdx demonstration).
public class Biomass implements MiniGame {
    private List<Drop> drops;
    private Bucket bucket;
    private int score;
    private final int WIN_SCORE = 10;

    private float dropSpawnTimer = 0f;
    private final float DROP_SPAWN_COOLDOWN = 1f; // seconds

    private final Random rng = new Random();
    private Texture dropTexture;
    private Texture bucketTexture;
    private Texture backgroundTexture;

    private final GameCompletedPanel completionPanel;

    public Biomass(GameCompletedPanel completionPanel) {
        this.completionPanel = completionPanel;
    }

    @Override
    public boolean isCompleted() {
        return completionPanel.wantsToClose;
    }

    /// Is run when starting the minigame. Create stuff like the bucket, load textures, initialize game.
    @Override
    public void create() {
        drops = new ArrayList<>();
        dropTexture = new Texture("assets/biomass/drop.png");
        bucketTexture = new Texture("assets/biomass/bucket.png");
        backgroundTexture = new Texture("assets/biomass/background.png");

        bucket = new Bucket(ETopia.WORLD_WIDTH / 2f, 100, bucketTexture);
        score = 0;
    }

    /// Is run before every frame. Updates drop and bucket positions, handles player input. Delta is time since last frame
    @Override
    public void update(float delta, Input input) {
        dropSpawnTimer -= delta;
        if (dropSpawnTimer <= 0) {
            dropSpawnTimer = DROP_SPAWN_COOLDOWN;
            spawnDrop();
        }

        var iterator = drops.iterator();
        while (iterator.hasNext()) {
            var drop = iterator.next();

            drop.update(delta);
            if (drop.y < 0 - drop.SIZE) { // Drop below screen
                iterator.remove();
                continue;
            }

            if (canCollect(bucket, drop)) {
                iterator.remove();
                score += 1;
            }
        }

        if (score >= WIN_SCORE) {
            completionPanel.visible = true;
        }

        if (completionPanel.visible) {
            completionPanel.update(delta, input);
        } else {
            bucket.update(input, delta);
        }
    }

    /// Math to check whether the bucket is touching the drop
    private boolean canCollect(Bucket bucket, Drop drop) {
        return (Math.abs(bucket.x - drop.x) < (bucket.SIZE + drop.SIZE) / 2 &&
                drop.y - bucket.y < (bucket.SIZE + drop.SIZE) / 2) &&
                drop.y - bucket.y > 0;
    }

    /// spawn a new drop at the top of the screen
    private void spawnDrop() {
        final float PADDING = 50;
        var drop = new Drop(rng.nextFloat(PADDING, ETopia.WORLD_WIDTH - PADDING), ETopia.WORLD_HEIGHT, dropTexture);
        drops.add(drop);
    }

    /// Renders the scene
    @Override
    public void render(Renderer renderer) {
        renderer.batch.begin(); // required before calling stuff like renderer.batch.draw

        // Draw a texture on screen.
        renderer.batch.draw(backgroundTexture, 0, 0, ETopia.WORLD_WIDTH, ETopia.WORLD_HEIGHT);

        /// TODO add more examples of using {@link Renderer.batch}, {@link Renderer.shape}, {@link Renderer.font}

        for (var drop : drops) {
            drop.render(renderer.batch);
        }
        bucket.render(renderer.batch);

        renderer.batch.end(); // required. sends all previously called renderer.batch.draw methods to the screen at once

        if (completionPanel.visible) {
            completionPanel.render(renderer);
        }
    }

    /// Clean up all stuff at the end of game, when scene is unloaded
    @Override
    public void dispose() {
        dropTexture.dispose();
    }
}
