package ch.fhnw.roundtable.etopia.views.grid.game;

import ch.fhnw.roundtable.etopia.input.Input;
import ch.fhnw.roundtable.etopia.views.grid.GridConfiguration;

public class Cursor {

    private final GridConfiguration gridConfiguration;
    private int x;
    private int y;

    public Cursor(GridConfiguration gridConfiguration, int x, int y) {
        this.gridConfiguration = gridConfiguration;
        this.x = x;
        this.y = y;
    }

    public void update(Input input) {
        if (input.isUpJustPressed()) {
            y = Math.min(y + 1, gridConfiguration.mapHeight - 1);
        }
        if (input.isDownJustPressed()) {
            y = Math.max(y - 1, 0);
        }
        if (input.isRightJustPressed()) {
            x = Math.min(x + 1, gridConfiguration.mapWidth - 1);
        }
        if (input.isLeftJustPressed()) {
            x = Math.max(x - 1, 0);
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
