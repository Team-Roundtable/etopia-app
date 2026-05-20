package ch.fhnw.roundtable.etopia;

import ch.fhnw.roundtable.etopia.input.Controls;
import ch.fhnw.roundtable.etopia.rendering.Renderer;

public interface View {

    void update(float delta, Controls controls);

    void render(Renderer renderer);

    void dispose();

    Transition transition();
}
