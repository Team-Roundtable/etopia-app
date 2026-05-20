package ch.fhnw.roundtable.etopia;

import ch.fhnw.roundtable.etopia.rendering.Renderer;

public interface UI<S> {

    void render(S state, Renderer renderer);

    void dispose();
}
