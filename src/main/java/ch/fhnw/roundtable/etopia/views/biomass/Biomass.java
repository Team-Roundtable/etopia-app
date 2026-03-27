package ch.fhnw.roundtable.etopia.views.biomass;

import ch.fhnw.roundtable.etopia.ETopia;
import ch.fhnw.roundtable.etopia.views.Renderer;
import ch.fhnw.roundtable.etopia.input.Input;
import ch.fhnw.roundtable.etopia.views.Scene;
import ch.fhnw.roundtable.etopia.views.SceneType;
import ch.fhnw.roundtable.etopia.views.commons.healthbar.HealthBar;
import ch.fhnw.roundtable.etopia.views.commons.panel.Panel;
import ch.fhnw.roundtable.etopia.views.commons.panel.PanelDetails;
import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Biomass extends Scene<BiomassAsset> {

    private static final float DROP_SPAWN_COOLDOWN = 1f; // seconds
    private static final int WIN_SCORE = 10;

    private final Random random = new Random();
    private final Texture background;

    private final Panel completionPanel = new Panel(
            128,
            128,
            ETopia.WORLD_WIDTH - 256,
            ETopia.WORLD_HEIGHT - 256,
            getTexture(BiomassAsset.PANEL),
            new PanelDetails(
                    "Gewonnen!",
                    "Gut gemacht.! Du hast genug Strom gesammelt.",
                    "Zurück zur Karte: Leertaste"));

    private final Bucket bucket;
    private final List<Drop> drops = new ArrayList<>();
    private final HealthBar healthBar;

    private int score = 0;
    private float dropSpawnTimer = 0f;

    public Biomass() {
        super(BiomassAsset.class);
        this.healthBar = new HealthBar(100, ETopia.WORLD_HEIGHT - 100);
        healthBar.resetHealth();
        background = getTexture(BiomassAsset.BACKGROUND);
        bucket = new Bucket(ETopia.WORLD_WIDTH / 2f, 100, getTexture(BiomassAsset.BUCKET));
    }

    @Override
    public void updateScene(float delta, Input input) {
        dropSpawnTimer -= delta;
        if (dropSpawnTimer <= 0) {
            dropSpawnTimer = DROP_SPAWN_COOLDOWN;
            spawnDrop();
        }

        var bucketBounds = bucket.getBounds();

        var iterator = drops.iterator();
        while (iterator.hasNext()) {
            var drop = iterator.next();

            drop.update(delta, input);
            if (drop.getY() < 0 - drop.getWidth()) {
                iterator.remove();
                healthBar.removeHealth();
                continue;
            }

            if (bucketBounds.overlaps(drop.getBounds())) {
                iterator.remove();
                score += 1;
            }
        }

        if (score >= WIN_SCORE || healthBar.isDead()) {
            completionPanel.update(delta, input);
        } else {
            bucket.update(delta, input);
        }
    }

    private void spawnDrop() {
        final float padding = 50;

        float minX = padding;
        float maxX = ETopia.WORLD_WIDTH - padding;

        var drop = new Drop(random.nextFloat(minX, maxX), ETopia.WORLD_HEIGHT, getTexture(BiomassAsset.DROP));
        drops.add(drop);
    }

    @Override
    public void renderScene(Renderer renderer) {
        renderer.batch(batch -> batch.draw(background, 0, 0, ETopia.WORLD_WIDTH, ETopia.WORLD_HEIGHT));

        for (var drop : drops) {
            drop.render(renderer);
        }

        bucket.render(renderer);

        if (score >= WIN_SCORE || healthBar.isDead()) {
            completionPanel.render(renderer);
        }

        healthBar.render(renderer);
    }

    @Override
    public SceneType change() {
        return completionPanel.isClose() ? SceneType.MAP : null;
    }
}
