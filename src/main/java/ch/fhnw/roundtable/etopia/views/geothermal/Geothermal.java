package ch.fhnw.roundtable.etopia.views.geothermal;

import ch.fhnw.roundtable.etopia.ETopia;
import ch.fhnw.roundtable.etopia.input.Input;
import ch.fhnw.roundtable.etopia.views.Renderer;
import ch.fhnw.roundtable.etopia.views.Scene;
import ch.fhnw.roundtable.etopia.views.SceneType;
import ch.fhnw.roundtable.etopia.views.commons.healthbar.HealthBar;
import ch.fhnw.roundtable.etopia.views.commons.panel.Panel;
import ch.fhnw.roundtable.etopia.views.commons.panel.PanelDetails;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class Geothermal extends Scene<GeothermalAsset> {
    private static final Logger LOGGER = LoggerFactory.getLogger(Geothermal.class);

    private static final int MAP_HEIGHT = 3000;
    private static final float DRILL_START_Y = MAP_HEIGHT * 0.9f;
    private static final int ROCKS = 15;

    private GameState gameState;
    private final Drill drill;
    private final List<Rock> rocks;
    private static final float HIT_COOLDOWN = 2f;
    private float hitCooldownTimer;

    private Panel completionPanel;
    private final HealthBar healthBar;

    public Geothermal() {
        super(GeothermalAsset.class);

        this.healthBar = new HealthBar(100, ETopia.WORLD_HEIGHT - 100);
        healthBar.resetHealth();

        drill = new Drill(getTexture(GeothermalAsset.DRILL), getTexture(GeothermalAsset.PIPE), DRILL_START_Y);
        rocks = new ArrayList<>();
        spawnRandomRocks(ROCKS);

        changeGameState(GameState.PREPARATION);

        hitCooldownTimer = HIT_COOLDOWN;
    }

    private void spawnRandomRocks(int num) {
        for (int i = 0; i < num; i++) {
            rocks.add(new Rock(
                    (float) Math.random() * ETopia.WORLD_WIDTH,
                    (float) Math.random() * MAP_HEIGHT * 0.75f,
                    (float) Math.random() * 300 + 100,
                    (float) Math.random() * 150 + 100));
        }
    }

    @Override
    public void updateScene(float delta, Input input) {
        if (healthBar.isDead() && completionPanel == null) {
            changeGameState(GameState.GAME_OVER);
            showLooseScreen();
        }

        drill.update(delta, input);


        rocks.forEach(r -> r.update(delta, input));

        hitCooldownTimer -= delta;
        if (hitCooldownTimer <= 0) {
            var hit = doRockCollision() | doPipeCollision();
            if (hit) {
                hitCooldownTimer = HIT_COOLDOWN;
            }
        }

        healthBar.update(delta, input);

        if (completionPanel != null) {
            completionPanel.update(delta, input);
        }

        switch (gameState) {
            case PREPARATION -> {
                changeGameState(GameState.DRILL_DOWN);
            }
            case DRILL_DOWN -> {
                if (drill.getY() <= 0) {
                    changeGameState(GameState.AT_BOTTOM);
                }
            }
            case AT_BOTTOM -> {
                changeGameState(GameState.DRILL_UP);
            }
            case DRILL_UP -> {
                if (drill.getY() >= DRILL_START_Y) {
                    changeGameState(GameState.FINISHED_DRILLING);
                }
            }
            case FINISHED_DRILLING -> {
                changeGameState(GameState.GAME_OVER);
            }
            case GAME_OVER -> {
                if (completionPanel == null) {
                    showWinScreen();
                }
            }
            default -> LOGGER.warn("Unexpected value: {}", gameState);
        }
    }

    private boolean doRockCollision() {
        boolean hit = false;

        var iterator = rocks.iterator();
        while (iterator.hasNext()) {
            var rock = iterator.next();
            if (rock.getCollider().overlaps(drill.getCollider())) {
                healthBar.removeHealth();
                iterator.remove();
                hit = true;
            }
        }

        return hit;
    }

    private boolean doPipeCollision() {
        if (drill.collidesWithPipe()) {
            healthBar.removeHealth();
            return true;
        }
        return false;
    }

    @Override
    public void renderScene(Renderer renderer) {
        float camY = Math.max(
                ETopia.WORLD_HEIGHT / 2f,
                Math.min(drill.getY(), MAP_HEIGHT - ETopia.WORLD_HEIGHT / 2f)
        );
        renderer.setCameraPosition(ETopia.WORLD_WIDTH / 2f, camY);

        renderer.shape(ShapeRenderer.ShapeType.Filled, shape -> {
            shape.setColor(Color.FOREST);
            shape.rect(0, MAP_HEIGHT, ETopia.WORLD_WIDTH, -300);
        });
        renderer.shape(ShapeRenderer.ShapeType.Filled, shape -> {
            shape.setColor(Color.BROWN);
            shape.rect(0, 0, ETopia.WORLD_WIDTH, MAP_HEIGHT - 300);
        });
        renderer.shape(ShapeRenderer.ShapeType.Filled, shape -> {
            shape.setColor(Color.ORANGE);
            shape.circle(ETopia.WORLD_WIDTH / 2f, 0, ETopia.WORLD_WIDTH / 6f);
        });

        drill.render(renderer);
        rocks.forEach(r -> r.render(renderer));

        renderer.resetCamera();
        healthBar.render(renderer);

        if (completionPanel != null) {
            completionPanel.render(renderer);
        }
    }

    @Override
    public SceneType change() {
        return gameState == GameState.GAME_OVER
                && (completionPanel != null && completionPanel.isClose()) ? SceneType.MAP : null;
    }

    private void showWinScreen() {
        showCompletionPanel(new PanelDetails(
                "Gewonnen!",
                "Gut gemacht!",
                "Zurück zur Karte: Leertaste"
        ));
    }

    private void showLooseScreen() {
        showCompletionPanel(new PanelDetails(
                "Verloren!",
                ":(",
                "Zurück zur Karte: Leertaste"
        ));
    }

    private void showCompletionPanel(PanelDetails panelDetails) {
        completionPanel = new Panel(
                128,
                128,
                ETopia.WORLD_WIDTH - 256,
                ETopia.WORLD_HEIGHT - 256,
                getTexture(GeothermalAsset.PANEL),
                panelDetails);
    }

    private void changeGameState(GameState newState) {
        this.gameState = newState;
        drill.updateGameState(gameState);
    }
}
