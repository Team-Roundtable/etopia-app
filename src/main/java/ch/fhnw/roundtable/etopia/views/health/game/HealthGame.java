package ch.fhnw.roundtable.etopia.views.health.game;

import ch.fhnw.roundtable.etopia.input.Input;
import ch.fhnw.roundtable.etopia.views.Game;
import ch.fhnw.roundtable.etopia.views.ViewType;

// todo game interface does not fit
public class HealthGame implements Game {

    private final int total;
    private int current;

    public HealthGame(int total) {
        this.total = total;
        this.current = total;
    }

    public int getTotal() {
        return total;
    }

    public int getCurrent() {
        return current;
    }

    public void subtract() {
        current--;
    }

    public void reset() {
        current = total;
    }

    public boolean dead() {
        return current <= 0;
    }

    @Override
    public void update(float delta, Input input) {

    }

    @Override
    public ViewType next() {
        return null;
    }
}
