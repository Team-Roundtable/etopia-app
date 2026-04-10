package ch.fhnw.roundtable.etopia.views.map;

import ch.fhnw.roundtable.etopia.input.Input;
import ch.fhnw.roundtable.etopia.views.Assets;
import ch.fhnw.roundtable.etopia.views.Renderer;
import ch.fhnw.roundtable.etopia.views.View;
import ch.fhnw.roundtable.etopia.views.ViewType;
import ch.fhnw.roundtable.etopia.views.map.game.MapGame;
import ch.fhnw.roundtable.etopia.views.map.ui.MapAsset;
import ch.fhnw.roundtable.etopia.views.map.ui.MapUI;

public class Map implements View {

    private final MapGame mapGame;
    private final MapUI mapUI;

    public Map() {
        var mapConfiguration = new MapConfiguration();
        this.mapGame = new MapGame(mapConfiguration);
        this.mapUI = new MapUI(new Assets<>(MapAsset.class));
    }

    @Override
    public void update(float delta, Input input) {
        mapGame.update(delta, input);
    }

    @Override
    public void render(Renderer renderer) {
        mapUI.render(mapGame, renderer);
    }

    @Override
    public void dispose() {
        mapUI.dispose();
    }

    @Override
    public ViewType next() {
        return mapGame.next();
    }
}
