package ch.fhnw.roundtable.etopia.views.map;

import ch.fhnw.roundtable.etopia.ETopia;
import ch.fhnw.roundtable.etopia.config.Text;
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
    private SceneType nextScene;

    public Map() {
        super(MapAsset.class);

        background = getTexture(MapAsset.BACKGROUND);

        PanelDetails windPanel = new PanelDetails(
                Text.get("map.infopanel.wind.title"),
                Text.get("map.infopanel.wind.description"),
                Text.get("map.infopanel.start"));
        wind = new Technology(SceneType.WIND, 140, 170, 256, 256, getTexture(MapAsset.WIND),
                new Panel(400, 170, 512, 320, getTexture(MapAsset.PANEL), windPanel));

        PanelDetails biomassPanel = new PanelDetails(
                Text.get("map.infopanel.biomass.title"),
                Text.get("map.infopanel.biomass.description"),
                Text.get("map.infopanel.start"));
        biomass = new Technology(SceneType.BIOMASS, 220, 780, 256, 256, getTexture(MapAsset.BIOMASS),
                new Panel(500, 720, 512, 320, getTexture(MapAsset.PANEL), biomassPanel));

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
            nextScene = selected.getView();
        }
    }

    @Override
    public void renderScene(Renderer renderer) {
        renderer.batch(batch -> {
            batch.draw(background, 0, 0, ETopia.WORLD_WIDTH, ETopia.WORLD_HEIGHT);
            renderer.font.draw(batch, Text.get("map.infotext"), 0, 32);
        });

        wind.render(renderer);
        biomass.render(renderer);
    }

    private void updateSelected(Technology nextTechnology) {
        selected.setSelected(false);
        selected = nextTechnology;
        selected.setSelected(true);
    }

    @Override
    public SceneType change() {
        return nextScene;
    }
}
