package ch.fhnw.roundtable.etopia.minigames.map;

import ch.fhnw.roundtable.etopia.*;
import ch.fhnw.roundtable.etopia.input.Input;
import ch.fhnw.roundtable.etopia.minigames.biomass.Biomass;
import ch.fhnw.roundtable.etopia.minigames.panel.Panel;
import ch.fhnw.roundtable.etopia.minigames.wind.Wind;
import ch.fhnw.roundtable.etopia.view.Menu;
import ch.fhnw.roundtable.etopia.view.View;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;
import java.util.List;

public class Map implements Menu {

    private final Technology wind = new Technology(Wind.class, 140, 170, 256, 256, "assets/map/wind.png", new Panel(400, 170, 200, 150, "Wind"));
    private final Technology biomass = new Technology(Biomass.class, 220, 780, 256, 256, "assets/map/biomass.png", new Panel(500, 780, 300, 150, "Biomasse"));

    private Texture background;
    private Technology selected;
    private Class<? extends View> next;

    @Override
    public void create() {
        background = new Texture("assets/map/background.png");
        wind.create();
        biomass.create();

        selected = wind;
        selected.setSelected(true);
        next = null;
    }

    @Override
    public void update(float delta, Input input) {

        if (input.isUpJustPressed()) {
            if (selected == wind) {
                updateSelected(biomass);
            }
        }

        if (input.isDownJustPressed()) {
            if (selected == biomass) {
                updateSelected(wind);
            }
        }
        if (input.isSelectJustPressed()) {
            next = selected.getView();
        }
    }

    @Override
    public void render(Renderer renderer) {
        renderer.batch(batch -> {
            batch.draw(background, 0, 0, ETopia.WORLD_WIDTH, ETopia.WORLD_HEIGHT);
            renderer.font.draw(batch, "Select [Enter/Space], Back [Backspace/Esc], Move [Arrows/WASD]", 0, 32);
        });

        wind.render(renderer);
        biomass.render(renderer);
    }

    @Override
    public void dispose() {

    }

    private void updateSelected(Technology next) {
        selected.setSelected(false);
        selected = next;
        selected.setSelected(true);
    }

    @Override
    public Class<? extends View> changeView() {
        return next;
    }
}
