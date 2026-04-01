package ch.fhnw.roundtable.etopia.views.biogas;

import ch.fhnw.roundtable.etopia.input.Input;
import ch.fhnw.roundtable.etopia.views.Entity;
import ch.fhnw.roundtable.etopia.views.Renderer;
import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static ch.fhnw.roundtable.etopia.views.biogas.BiogasAsset.SCALE_MULTIPLIER;
import static com.badlogic.gdx.math.MathUtils.random;

public class ConveyorBelt extends Entity {

    public static final int Y_OFFSET = 16;

    private final Texture texture;
    private final Item[][] grid = new Item[3][11];
    private float shiftItemCooldown = 1.0f;
    private float shiftTimer = 0f;
    private List<Item> overshiftedItems;
    private Texture[] greenAssets;
    private Texture[] redAssets;

    public ConveyorBelt(Texture texture, int xCoordinate, int yCoordinate, Texture[] greenAssets, Texture[] redAssets) {
        super(xCoordinate, yCoordinate,
                texture.getWidth() * SCALE_MULTIPLIER,
                texture.getHeight() * SCALE_MULTIPLIER
        );
        this.texture = texture;
        this.greenAssets = greenAssets;
        this.redAssets = redAssets;

        for (Item[] items : grid) {
            Arrays.fill(items, null);
        }
    }

    private List<Item> shiftItems() {
        List<Item> overflowItems = new ArrayList<>();

        for (int y = 0; y < grid.length; y++) {

            if (grid[y][0] != null) {
                overflowItems.add(grid[y][0]);
            }

            for (int x = 0; x < grid[y].length - 1; x++) {

                grid[y][x] = grid[y][x + 1];
                grid[y][x + 1] = null;

                Item item = grid[y][x];
                if (item != null) {
                    var pos = Biogas.cellPositionToWorldSpace(x, y);
                    item.setPosition(pos.x);
                }
            }
        }
        return overflowItems;
    }

    public List<Item> getOvershifted() {
        return overshiftedItems;
    }

    public void deleteItem(int gridX, int gridY) {
        grid[gridY][gridX] = null;
    }

    public Item[][] getGrid() {
        return grid;
    }

    public Item getCellItem(int gridX, int gridY) {
        return grid[gridY][gridX];
    }

    public List<Item> getAllItems() {
        List<Item> itemList = new ArrayList<>();
        for (Item[] items : grid) {
            for (Item item : items) {
                if (item != null) {
                    itemList.add(item);
                }
            }
        }
        return itemList;
    }

    public void addItem(Item item, int yPosition) {
        grid[yPosition][grid[0].length - 1] = item;
    }

    public void createRandomItem() {
        // Todo: random chance to spawn items
        int randomY = random.nextInt(grid.length);
        var pos = Biogas.cellPositionToWorldSpace(10, randomY);
        boolean biodegradable = random.nextBoolean();
        var randomTexture = biodegradable ? greenAssets : redAssets;

        Item item = new Item(pos.x, pos.y, randomTexture[random(randomTexture.length - 1)], biodegradable);

        addItem(item, randomY);
    }

    @Override
    public void updateEntity(float delta, Input input) {
        shiftTimer -= delta;
        if (shiftTimer < 0) {
            shiftTimer = shiftItemCooldown;
            overshiftedItems = shiftItems();
            createRandomItem();
        }

        for (Item[] items : grid) {
            for (Item item : items) {
                if (item != null) {
                    item.update(delta, input);
                }
            }
        }
    }

    @Override
    public void renderEntity(Renderer renderer) {
        renderer.batch(batch -> {
            batch.draw(texture, x, y, width, height);
            batch.draw(texture, x, y + height + Y_OFFSET, width, height);
            batch.draw(texture, x, y + 2 * height + 2 * Y_OFFSET, width, height);
        });
    }
}
