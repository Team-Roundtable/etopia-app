package ch.fhnw.roundtable.etopia.views.biogas.game;

import ch.fhnw.roundtable.etopia.helpers.Size;
import ch.fhnw.roundtable.etopia.input.Input;
import ch.fhnw.roundtable.etopia.views.biogas.BiogasConfiguration;

public class Cursor {

    private final BiogasConfiguration biogasConfiguration;
    private final Size size;
    private int x;
    private int y;

    public Cursor(BiogasConfiguration biogasConfiguration) {
        this.biogasConfiguration = biogasConfiguration;
        this.size = biogasConfiguration.trashSize;
    }

    public void update(Input input) {

        if (input.isUpJustPressed()) {
            y = Math.min(y + 1, biogasConfiguration.conveyorHeight - 1);
        }
        if (input.isDownJustPressed()) {
            y = Math.max(y - 1, 0);
        }
        if (input.isRightJustPressed()) {
            x = Math.min(x + 1, biogasConfiguration.conveyorWidth - 1);
        }
        if (input.isLeftJustPressed()) {
            x = Math.max(x - 1, 0);
        }
    }

    public float getWidth() {
        return size.width();
    }

    public float getHeight() {
        return size.height();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

}
