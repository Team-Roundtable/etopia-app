package ch.fhnw.roundtable.etopia.input.libgdx;

import ch.fhnw.roundtable.etopia.input.ButtonType;
import com.badlogic.gdx.Input.Keys;

public final class LibGDXButtonKeys {

    private LibGDXButtonKeys() {
    }

    public static int[] get(ButtonType type) {
        return switch (type) {
            case UP -> new int[]{Keys.UP, Keys.W};
            case DOWN -> new int[]{Keys.DOWN, Keys.S};
            case LEFT -> new int[]{Keys.LEFT, Keys.A};
            case RIGHT -> new int[]{Keys.RIGHT, Keys.D};
            case SELECT -> new int[]{Keys.ENTER, Keys.SPACE};
            case BACK -> new int[]{Keys.ESCAPE};
        };
    }
}
