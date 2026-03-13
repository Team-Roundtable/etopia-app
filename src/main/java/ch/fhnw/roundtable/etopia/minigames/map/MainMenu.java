package ch.fhnw.roundtable.etopia.minigames.map;

import ch.fhnw.roundtable.etopia.*;
import ch.fhnw.roundtable.etopia.helpers.Navigatable;
import ch.fhnw.roundtable.etopia.input.Input;
import ch.fhnw.roundtable.etopia.minigames.bucket.BucketGame;
import ch.fhnw.roundtable.etopia.minigames.map.technologies.GameInfoPanel;
import ch.fhnw.roundtable.etopia.minigames.map.technologies.SelectableTechnology;
import ch.fhnw.roundtable.etopia.view.Menu;
import ch.fhnw.roundtable.etopia.view.View;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;

import java.util.List;

public class MainMenu implements Menu {
    private List<SelectableTechnology> technologies;
    private Navigatable selection;


    private Class<? extends View> nextView;
    private Texture backgroundGrass;

    @Override
    public void create() {
        backgroundGrass = new Texture("map/map_mockup_grass.png");

        var wind = new SelectableTechnology(200, 900, null,
                new Texture("map/map_mockup_wind_icon.png"), new Texture("map/map_mockup_wind_icon_selected.png"),
                new GameInfoPanel(200 + 100, 900 - 200, "Windkraft", "heute nicht verfügbar"));
        var biogas = new SelectableTechnology(250, 150, BucketGame.class,
                new Texture("map/map_mockup_biogas_icon.png"), new Texture("map/map_mockup_biogas_icon_selected.png"),
                new GameInfoPanel(250 + 200, 150, "Biogas", ""));
        var thermal = new SelectableTechnology(1200, 900, null,
                new Texture("map/map_mockup_geothermal_icon.png"), new Texture("map/map_mockup_geothermal_icon_selected.png"),
                new GameInfoPanel(1200 + 200, 900 - 200, "Geothermal", "heute nicht verfügbar"));
        var solar = new SelectableTechnology(1700, 200, null,
                new Texture("map/map_mockup_solar_icon.png"), new Texture("map/map_mockup_solar_icon_selected.png"),
                new GameInfoPanel(1700 - 400, 200 + 70, "Solarkraft", "heute nicht verfügbar"));

        wind.connectTo(biogas, Navigatable.Direction.DOWN);
        wind.connectTo(thermal, Navigatable.Direction.RIGHT);
        biogas.connectTo(solar, Navigatable.Direction.RIGHT);
        thermal.connectTo(solar, Navigatable.Direction.DOWN);

        technologies = List.of(wind, biogas, thermal, solar);
        focus(technologies.getFirst());
    }

    @Override
    public void update(float delta, Input input) {

        // language change
        // update map state

        // move cursor with lrud
        if (input.isDownJustPressed() && selection.getDown() != null) {
            focus(selection.getDown());
        }
        if (input.isLeftJustPressed() && selection.getLeft() != null) {
            focus(selection.getLeft());
        }
        if (input.isUpJustPressed() && selection.getUp() != null) {
            focus(selection.getUp());
        }
        if (input.isRightJustPressed() && selection.getRight() != null) {
            focus(selection.getRight());
        }

        // select pressed change view
        if (input.isSelectJustPressed() && selection instanceof SelectableTechnology technology) {
            nextView = technology.getCorrespondingView();
        }
    }

    private void focus(Navigatable newSelection) {
        if (selection != null) selection.unselect();
        newSelection.select();
        selection = newSelection;
    }

    @Override
    public void render(Renderer renderer) {

        renderer.font.getData().setScale(5);

        renderer.batch.begin();
        renderer.batch.draw(backgroundGrass, 0, 0, ETopia.WORLD_WIDTH, ETopia.WORLD_HEIGHT);
        renderer.batch.end();

        renderer.batch.begin();
        renderer.font.setColor(Color.WHITE);
        renderer.font.draw(renderer.batch, "Press keys 0-9 anytime to load level", 200, 800);
        renderer.font.draw(renderer.batch, "Arrows / WASD to select", 200, 650);
        renderer.font.draw(renderer.batch, "SPACE to select", 200, 500);

        renderer.batch.end();

        technologies.forEach(t -> t.render(renderer));
    }

    @Override
    public void dispose() {
        technologies.forEach(t -> t.dispose());
        technologies = null;
    }

    @Override
    public Class<? extends View> changeView() {
        return nextView;
    }

    @Override
    public void clearChangeViewRequest() {
        nextView = null;
    }
}
