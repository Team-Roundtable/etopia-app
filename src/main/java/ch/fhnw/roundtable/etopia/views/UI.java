package ch.fhnw.roundtable.etopia.views;

public interface UI<G extends Game> {

    void render(G game, Renderer renderer);

    void dispose();
}
