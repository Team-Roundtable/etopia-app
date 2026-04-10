package ch.fhnw.roundtable.etopia.views.energy.game;

import ch.fhnw.roundtable.etopia.input.Input;
import ch.fhnw.roundtable.etopia.views.Game;
import ch.fhnw.roundtable.etopia.views.ViewType;

public class EnergyGame implements Game {

    private float current = 0;

    public void update(float amount) {
        current += Math.clamp(amount, 0, 1);
    }

    public boolean full() {
        return current >= 1f;
    }

    public float getCurrent() {
        return current;
    }

    @Override
    public void update(float delta, Input input) {

    }

    @Override
    public ViewType next() {
        return null;
    }
}
