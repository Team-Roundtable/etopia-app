package ch.fhnw.roundtable.etopia.views.biogas;

import ch.fhnw.roundtable.etopia.ETopia;
import ch.fhnw.roundtable.etopia.input.Input;
import ch.fhnw.roundtable.etopia.views.Renderer;
import ch.fhnw.roundtable.etopia.views.Scene;
import ch.fhnw.roundtable.etopia.views.SceneType;
import ch.fhnw.roundtable.etopia.views.commons.healthbar.HealthBar;
import ch.fhnw.roundtable.etopia.views.commons.panel.Panel;
import ch.fhnw.roundtable.etopia.views.commons.panel.PanelDetails;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import java.util.Arrays;
import java.util.List;

import static ch.fhnw.roundtable.etopia.views.biogas.BiogasAsset.CELL_SIZE;
import static ch.fhnw.roundtable.etopia.views.biogas.BiogasAsset.SCALE_MULTIPLIER;

public class Biogas extends Scene<BiogasAsset> {
    private final Panel completionPanel;

    private static final int CONVEYERBELT_YPOS = 200;
    private static final int CONVEYERBELT_XPOS = ETopia.WORLD_WIDTH - 328 * SCALE_MULTIPLIER;

    private final ConveyorBelt conveyorBelt;
    private final Cursor cursor;
    private final HealthBar healthBar;


    public Biogas() {
        super(BiogasAsset.class);

        completionPanel = new Panel(128, 128, ETopia.WORLD_WIDTH - 256,
                ETopia.WORLD_HEIGHT - 256,
                getTexture(BiogasAsset.PANEL),
                new PanelDetails(
                        "Gewonnen!",
                        "Gut gemacht.! Du hast genug Strom gesammelt.",
                        "Zurück zur Karte: Leertaste")
        );

        this.conveyorBelt = new ConveyorBelt(
                getTexture(BiogasAsset.CONVEYOR_BELT),
                CONVEYERBELT_XPOS, CONVEYERBELT_YPOS,
                getTextureArray(BiogasAsset.getGreenItems()),
                getTextureArray(BiogasAsset.getRedItems())
        );

        this.healthBar = new HealthBar((float) ETopia.WORLD_WIDTH / 2 - 100, ETopia.WORLD_HEIGHT - 100);
        healthBar.resetHealth();

        this.cursor = new Cursor(
                getTexture(BiogasAsset.CURSOR),
                0, 0,
                conveyorBelt.getGrid()[0].length,
                conveyorBelt.getGrid().length
        );
    }

    private Texture[] getTextureArray(BiogasAsset[] biogasAssets) {
        return Arrays.stream(biogasAssets)
                .map(this::getTexture)
                .toArray(Texture[]::new);
    }

    public static Vector2 cellPositionToWorldSpace(int cellX, int cellY) {
        int x = CONVEYERBELT_XPOS - (CELL_SIZE * SCALE_MULTIPLIER) + (cellX * CELL_SIZE * SCALE_MULTIPLIER) + 20;
        int y = CONVEYERBELT_YPOS + ConveyorBelt.Y_OFFSET + (cellY * ((40 * SCALE_MULTIPLIER) + ConveyorBelt.Y_OFFSET));
        return new Vector2(x, y);
    }

    private void handleOverflow(List<Item> items) {
        if (items == null) {
            return;
        }
        for (Item item : items) {
            if (!item.isBiodegradable()) {
                healthBar.removeHealth();
            }
        }
    }

    @Override
    public void updateScene(float delta, Input input) {
        // cursor
        if (input.isSelectJustPressed()) {
            Item selectedItem = conveyorBelt.getCellItem(cursor.getGridX(), cursor.getGridY());
            if (selectedItem != null) {
                if (selectedItem.isBiodegradable()) {
                    healthBar.removeHealth();
                }
                conveyorBelt.deleteItem(cursor.getGridX(), cursor.getGridY());
            }
        }

        cursor.update(delta, input);
        conveyorBelt.update(delta, input);

        //healthbar
        handleOverflow(conveyorBelt.getOvershifted());

        if (healthBar.isDead()) {
            completionPanel.update(delta, input);
        }
    }

    @Override
    public void renderScene(Renderer renderer) {
        renderer.batch(batch -> {
            batch.draw(getTexture(BiogasAsset.BACKGROUND), 0, 0,
                    ETopia.WORLD_WIDTH, ETopia.WORLD_HEIGHT);
        });

        conveyorBelt.render(renderer);

        for (Item item : conveyorBelt.getAllItems()) {
            item.render(renderer);
        }

        cursor.render(renderer);
        healthBar.render(renderer);

        if (healthBar.isDead()) {
            completionPanel.render(renderer);
        }
    }

    @Override
    public SceneType change() {
        return completionPanel.isClose() ? SceneType.MAP : null;
    }
}
