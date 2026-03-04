package ch.fhnw.roundtable.etopia.minigames;

import ch.fhnw.roundtable.etopia.*;
import ch.fhnw.roundtable.etopia.input.Input;
import ch.fhnw.roundtable.etopia.view.Menu;
import ch.fhnw.roundtable.etopia.view.View;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class MainMenu implements Menu {

    private Texture dropTexture;
    private float x;

    @Override
    public void create() {
        dropTexture = new Texture("drop.png");
    }

    @Override
    public void update(float delta, Input input) {

        if (input.up()) {
            x++;
        }

        // language change
        // update map state

        // move cursor with lrud
        // select pressed change view
    }

    @Override
    public void render(Renderer renderer) {

        renderer.font.getData().setScale(5);

        renderer.shape.begin(ShapeRenderer.ShapeType.Filled);
        renderer.shape.setColor(Color.RED);
        renderer.shape.rect(0, 0, renderer.viewport.getWorldHeight(), renderer.viewport.getWorldHeight());
        renderer.shape.end();

        renderer.batch.begin();
        renderer.font.setColor(Color.WHITE);
        renderer.font.draw(renderer.batch, "Hello World!", x, 300);

        renderer.batch.draw(dropTexture, 200, 400, 100, 100);
        renderer.batch.end();
    }

    @Override
    public void dispose() {

    }

    @Override
    public Class<? extends View> changeView() {
        return null;
    }
}
