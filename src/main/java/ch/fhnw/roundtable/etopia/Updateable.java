package ch.fhnw.roundtable.etopia;

import ch.fhnw.roundtable.etopia.input.Controls;

@FunctionalInterface
public interface Updateable {

    void update(float delta, Controls controls);
}
