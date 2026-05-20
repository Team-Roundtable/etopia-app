package ch.fhnw.roundtable.etopia.views.biogas.model;

import ch.fhnw.roundtable.etopia.Renderable;
import ch.fhnw.roundtable.etopia.Updateable;
import ch.fhnw.roundtable.etopia.configuration.Configuration;
import ch.fhnw.roundtable.etopia.input.ButtonType;
import ch.fhnw.roundtable.etopia.input.Controls;
import ch.fhnw.roundtable.etopia.views.biogas.state.BiogasCursorState;

public class Cursor extends Item implements Updateable, Renderable<BiogasCursorState> {

    private final Configuration configuration;

    public Cursor(Configuration configuration) {
        super(configuration, 1, 1);
        this.configuration = configuration;
    }

    @Override
    public void update(float delta, Controls controls) {
        if (controls.isButtonJustPressed(ButtonType.UP)) {
            y = Math.min(y + 1, configuration.biogas().mapHeight() - 1);
        }
        if (controls.isButtonJustPressed(ButtonType.DOWN)) {
            y = Math.max(y - 1, 0);
        }
        if (controls.isButtonJustPressed(ButtonType.LEFT)) {
            x = Math.max(x - 1, 0);
        }
        if (controls.isButtonJustPressed(ButtonType.RIGHT)) {
            x = Math.min(x + 1, configuration.biogas().mapWidth() - 1);
        }
    }

    @Override
    public BiogasCursorState state() {
        return new BiogasCursorState(
                getX(),
                getY(),
                side,
                side
        );
    }
}
