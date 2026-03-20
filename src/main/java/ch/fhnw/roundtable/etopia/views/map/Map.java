package ch.fhnw.roundtable.etopia.views.map;

import ch.fhnw.roundtable.etopia.ETopia;
import ch.fhnw.roundtable.etopia.views.Renderer;
import ch.fhnw.roundtable.etopia.input.Input;
import ch.fhnw.roundtable.etopia.views.Scene;
import ch.fhnw.roundtable.etopia.views.SceneType;
import ch.fhnw.roundtable.etopia.views.commons.panel.Panel;
import ch.fhnw.roundtable.etopia.views.commons.panel.PanelDetails;
import com.badlogic.gdx.graphics.Texture;

public class Map extends Scene<MapAsset> {

    private final Technology wind;
    private final Technology biomass;

    private final Texture background;
    private Technology selected;
    private SceneType next;

    public Map() {
        super(MapAsset.class);

        background = getTexture(MapAsset.BACKGROUND);

        wind = new Technology(SceneType.WIND, 140, 170, 256, 256, getTexture(MapAsset.WIND),
                new Panel(400, 170, 512, 320, getTexture(MapAsset.PANEL), new PanelDetails("Wind", "Description", "Start: Leertaste")));
        biomass = new Technology(SceneType.BIOMASS, 220, 780, 256, 256, getTexture(MapAsset.BIOMASS),
                new Panel(500, 720, 512, 320, getTexture(MapAsset.PANEL), new PanelDetails("Biomasse", "something something", "Start: Leertaste")));

        selected = wind;
        selected.setSelected(true);
    }

    @Override
    public void updateScene(float delta, Input input) {
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
    public void renderScene(Renderer renderer) {
        renderer.batch(batch -> {
            batch.draw(background, 0, 0, ETopia.WORLD_WIDTH, ETopia.WORLD_HEIGHT);
            renderer.font.draw(batch, "Select [Enter/Space], Back [Backspace/Esc], Move [Arrows/WASD]", 0, 32);
        });

        wind.render(renderer);
        biomass.render(renderer);
    }

    private void updateSelected(Technology next) {
        selected.setSelected(false);
        selected = next;
        selected.setSelected(true);
    }

    @Override
    public SceneType change() {
        return next;
    }
}
