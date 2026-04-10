package ch.fhnw.roundtable.etopia.views;

import ch.fhnw.roundtable.etopia.input.Input;

public interface View {

    void update(float delta, Input input);

    void render(Renderer renderer);

    void dispose();

    ViewType next();
}
