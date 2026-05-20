package ch.fhnw.roundtable.etopia.input.pi4j;

import ch.fhnw.roundtable.etopia.input.ButtonType;

public final class Pi4JButtonPins {

    private Pi4JButtonPins() {
    }

    public static int getInput(ButtonType type) {
        return switch (type) {
            case UP -> 23;
            case DOWN -> 24;
            case LEFT -> 27;
            case RIGHT -> 22;
            case SELECT -> 17;
            case BACK -> 25;
        };
    }

    public static int getLight(ButtonType type) {
        return switch (type) {
            case UP -> 0;
            case DOWN -> 1;
            case LEFT -> 2;
            case RIGHT -> 3;
            case SELECT -> 4;
            case BACK -> 5;
        };
    }
}
