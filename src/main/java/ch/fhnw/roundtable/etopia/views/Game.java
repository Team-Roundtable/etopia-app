package ch.fhnw.roundtable.etopia.views;

import ch.fhnw.roundtable.etopia.input.Input;

public interface Game {

    void update(float delta, Input input);

    ViewType next();
}
