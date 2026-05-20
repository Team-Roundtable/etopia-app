package ch.fhnw.roundtable.etopia;

@FunctionalInterface
public interface Renderable<S> {

    S state();
}
