package ch.fhnw.roundtable.etopia.views.grid;

import ch.fhnw.roundtable.etopia.input.Input;
import ch.fhnw.roundtable.etopia.input.LEDControl;
import ch.fhnw.roundtable.etopia.views.Assets;
import ch.fhnw.roundtable.etopia.views.Renderer;
import ch.fhnw.roundtable.etopia.views.View;
import ch.fhnw.roundtable.etopia.views.ViewType;
import ch.fhnw.roundtable.etopia.views.clock.ClockConfiguration;
import ch.fhnw.roundtable.etopia.views.clock.ui.ClockUI;
import ch.fhnw.roundtable.etopia.views.grid.game.GridGame;
import ch.fhnw.roundtable.etopia.views.grid.ui.GridAsset;
import ch.fhnw.roundtable.etopia.views.grid.ui.GridUI;

public class Grid implements View {

    private final GridGame gridGame;
    private final GridUI gridUI;
    private final ClockUI clockUI;

    public Grid(LEDControl ledControl) {
        ledControl.ledAllPlayableOn();

        var gridConfiguration = new GridConfiguration();
        var clockConfiguration = new ClockConfiguration();
        this.gridGame = new GridGame(gridConfiguration);
        this.gridUI = new GridUI(gridConfiguration, new Assets<>(GridAsset.class));
        this.clockUI = new ClockUI(clockConfiguration);
    }

    @Override
    public void update(float delta, Input input) {
        gridGame.update(delta, input);
    }

    @Override
    public void render(Renderer renderer) {
        gridUI.render(gridGame, renderer);
        clockUI.render(gridGame.getClockGame(), renderer);
    }

    @Override
    public void dispose() {
        gridUI.dispose();
        clockUI.dispose();
    }

    @Override
    public ViewType next() {
        return gridGame.next();
    }
}
