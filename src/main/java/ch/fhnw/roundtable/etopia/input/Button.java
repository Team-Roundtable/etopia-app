package ch.fhnw.roundtable.etopia.input;

import java.util.List;

public class Button {

    private final List<Input> inputs;
    private final Light light;

    private boolean pressed;
    private boolean justPressed;
    private boolean justReleased;

    public Button(List<Input> inputs, Light light) {
        this.inputs = inputs;
        this.light = light;
    }

    public boolean update() {
        var newState = inputState();

        justPressed = !pressed && newState;
        justReleased = pressed && !newState;

        boolean changed = pressed != newState;
        pressed = newState;

        return changed;
    }

    public boolean isPressed() {
        return pressed;
    }

    public boolean isJustPressed() {
        return justPressed;
    }

    public boolean isJustReleased() {
        return justReleased;
    }

    public void setLight(boolean newState) {
        light.set(newState);
    }

    private boolean inputState() {
        for (var input : inputs) {
            if (input.isPressed()) {
                return true;
            }
        }
        return false;
    }
}
