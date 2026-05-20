package ch.fhnw.roundtable.etopia.input.libgdx;

import ch.fhnw.roundtable.etopia.input.ButtonType;
import ch.fhnw.roundtable.etopia.input.Input;
import com.badlogic.gdx.Gdx;

public class LibGDXInput implements Input {

    private final int[] keys;

    public LibGDXInput(ButtonType type) {
        this.keys = LibGDXButtonKeys.get(type);
    }

    @Override
    public boolean isPressed() {
        for (var key : keys) {
            if (Gdx.input.isKeyPressed(key)) {
                return true;
            }
        }

        return false;
    }
}
