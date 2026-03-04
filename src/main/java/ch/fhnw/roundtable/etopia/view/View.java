package ch.fhnw.roundtable.etopia.view;

import ch.fhnw.roundtable.etopia.input.Input;
import ch.fhnw.roundtable.etopia.Renderer;

public interface View {

    void create();

    void update(float delta, Input input);

    void render(Renderer renderer);

    void dispose();
}
