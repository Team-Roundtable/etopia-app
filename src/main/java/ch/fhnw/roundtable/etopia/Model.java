package ch.fhnw.roundtable.etopia;

public interface Model<S> extends Updateable, Renderable<S> {

    Result result();
}
