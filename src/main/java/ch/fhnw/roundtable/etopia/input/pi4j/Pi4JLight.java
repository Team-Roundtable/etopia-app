package ch.fhnw.roundtable.etopia.input.pi4j;

import ch.fhnw.roundtable.etopia.input.Light;
import com.pi4j.io.OnOffWrite;

public class Pi4JLight implements Light {

    private final OnOffWrite<?> control;
    private boolean state;

    public Pi4JLight(OnOffWrite<?> control) {
        this.control = control;
    }

    @Override
    public void set(boolean newState) {
        if (control == null) {
            return;
        }

        if (newState == state) {
            return;
        }

        control.setState(newState);
        state = newState;
    }
}
