package ch.fhnw.roundtable.etopia.minigames.map.technologies;

import ch.fhnw.roundtable.etopia.Renderer;
import ch.fhnw.roundtable.etopia.input.Input;
import ch.fhnw.roundtable.etopia.view.View;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class GameInfoPanel implements View {

    private final float PANEL_WIDTH = 300;
    private final float PANEL_HEIGHT = 200;
    private final float PANEL_OUTLINE_WIDTH = 10;

    private final float x;
    private final float y;
    private final String title;
    private final String description;


    public GameInfoPanel(float x, float y, String title, String description) {
        this.x = x;
        this.y = y;
        this.title = title;
        this.description = description;
    }


    @Override
    public void create() {

    }

    @Override
    public void update(float delta, Input input) {

    }

    @Override
    public void render(Renderer renderer) {
        renderer.shape.begin(ShapeRenderer.ShapeType.Filled);

        renderer.shape.setColor(Color.BLACK);
        renderer.shape.rect(
                x - PANEL_OUTLINE_WIDTH, y - PANEL_OUTLINE_WIDTH,
                PANEL_WIDTH + 2 * PANEL_OUTLINE_WIDTH, PANEL_HEIGHT + 2 * PANEL_OUTLINE_WIDTH);

        renderer.shape.setColor(Color.WHITE);
        renderer.shape.rect(x, y, PANEL_WIDTH, PANEL_HEIGHT);

        renderer.shape.setColor(Color.GRAY);
        renderer.shape.rect(x + 10, y + 10, PANEL_WIDTH - 2*10, PANEL_HEIGHT - 2*10 - 70);

        renderer.shape.end();


        renderer.batch.begin();

        renderer.font.setColor(Color.BLACK);
        renderer.font.getData().setScale(3f);
        renderer.font.draw(renderer.batch, title, x + 20, y + PANEL_HEIGHT - 25);

        renderer.font.setColor(Color.WHITE);
        renderer.font.getData().setScale(1.5f);
        renderer.font.draw(renderer.batch, description, x + 20, y + PANEL_HEIGHT - 90);

        renderer.font.draw(renderer.batch, "Start: Leertaste", x + 20, y + PANEL_HEIGHT - 140);
        renderer.batch.end();
    }

    @Override
    public void dispose() {

    }
}
