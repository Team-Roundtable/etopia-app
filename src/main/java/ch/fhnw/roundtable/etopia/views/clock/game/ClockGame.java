package ch.fhnw.roundtable.etopia.views.clock.game;

import ch.fhnw.roundtable.etopia.input.Input;
import ch.fhnw.roundtable.etopia.views.Game;
import ch.fhnw.roundtable.etopia.views.ViewType;

public class ClockGame implements Game {

    private float current;

    public ClockGame(float duration) {
        this.current = duration;
    }

    public boolean finished() {
        return current <= 0;
    }

    public float getCurrent() {
        return current;
    }

    @Override
    public void update(float delta, Input input) {
        current -= delta;
    }

    @Override
    public ViewType next() {
        return null;
    }
}
