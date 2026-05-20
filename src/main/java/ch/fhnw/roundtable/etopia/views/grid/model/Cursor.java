package ch.fhnw.roundtable.etopia.views.grid.model;

import ch.fhnw.roundtable.etopia.Renderable;
import ch.fhnw.roundtable.etopia.Updateable;
import ch.fhnw.roundtable.etopia.configuration.Configuration;
import ch.fhnw.roundtable.etopia.input.ButtonType;
import ch.fhnw.roundtable.etopia.input.Controls;
import ch.fhnw.roundtable.etopia.views.grid.state.GridCursorState;

public class Cursor extends Tile implements Updateable, Renderable<GridCursorState> {

    private final Configuration configuration;

    public Cursor(Configuration configuration) {
        super(configuration, configuration.grid().mapWidth() / 2, configuration.grid().mapHeight() / 2);
        this.configuration = configuration;
    }

    @Override
    public void update(float delta, Controls controls) {
        if (controls.isButtonJustPressed(ButtonType.UP)) {
            y = Math.min(y + 1, configuration.grid().mapHeight() - 1);
        }
        if (controls.isButtonJustPressed(ButtonType.DOWN)) {
            y = Math.max(y - 1, 0);
        }
        if (controls.isButtonJustPressed(ButtonType.RIGHT)) {
            x = Math.min(x + 1, configuration.grid().mapWidth() - 1);
        }
        if (controls.isButtonJustPressed(ButtonType.LEFT)) {
            x = Math.max(x - 1, 0);
        }
    }

    @Override
    public GridCursorState state() {
        return new GridCursorState(
                getX(),
                getY(),
                side,
                side
        );
    }
}
