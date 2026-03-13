package ch.fhnw.roundtable.etopia.minigames.infopanels;

import ch.fhnw.roundtable.etopia.ETopia;
import ch.fhnw.roundtable.etopia.Renderer;
import ch.fhnw.roundtable.etopia.input.Input;
import ch.fhnw.roundtable.etopia.view.View;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class GameCompletedPanel implements View {
    private final String happyEndingText;
    public boolean visible;
    public boolean wantsToClose;

    public GameCompletedPanel(String happyEndingText) {
        this.happyEndingText = happyEndingText;
        this.visible = false;
    }


    @Override
    public void create() {

    }

    @Override
    public void update(float delta, Input input) {
        if (input.isSelectJustPressed()) {
            wantsToClose = true;
        }
    }

    @Override
    public void render(Renderer renderer) {
        renderer.shape.begin(ShapeRenderer.ShapeType.Filled);

        // text saying 'Gut gemacht!'
        renderer.shape.setColor(Color.WHITE);
        renderer.shape.rect(ETopia.WORLD_WIDTH / 3f, ETopia.WORLD_HEIGHT - 250, ETopia.WORLD_WIDTH / 3f, 200);

        renderer.shape.setColor(Color.WHITE);
        renderer.shape.rect(200, ETopia.WORLD_HEIGHT / 5f, ETopia.WORLD_WIDTH - 2 * 200, ETopia.WORLD_HEIGHT * (2.7f/5));

        renderer.shape.end();


        renderer.batch.begin();

        renderer.font.getData().setScale(6);
        renderer.font.setColor(Color.BLACK);
        renderer.font.draw(renderer.batch, "Gut gemacht!", ETopia.WORLD_WIDTH / 3f + 50, ETopia.WORLD_HEIGHT - 100);

        renderer.font.getData().setScale(2.5f);
        renderer.font.draw(renderer.batch, happyEndingText, 200 + 50, ETopia.WORLD_HEIGHT * 3.5f/5);
        renderer.font.draw(renderer.batch, "Zurück zur Karte: Leertaste", 200 + 50, ETopia.WORLD_HEIGHT * 3.5f/5 - 400);

        renderer.batch.end();
    }

    @Override
    public void dispose() {

    }
}
