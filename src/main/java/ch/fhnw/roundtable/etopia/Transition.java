package ch.fhnw.roundtable.etopia;

import java.util.function.Supplier;

public sealed interface Transition {

    static Transition none() {
        return new None();
    }

    static Transition change(Supplier<View> next) {
        return new Change(next);
    }

    record None() implements Transition {
    }

    record Change(Supplier<View> next) implements Transition {
    }
}
