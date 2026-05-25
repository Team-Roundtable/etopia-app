package ch.fhnw.roundtable.etopia.input.pi4j;

import ch.fhnw.roundtable.etopia.input.Light;
import com.pi4j.io.OnOffWrite;
import com.pi4j.io.exception.IOException;

public class Pi4JLight implements Light {

    private final OnOffWrite<?> control;
    private boolean state;

    public Pi4JLight(OnOffWrite<?> control) {
        this.control = control;
    }

    @Override
    public void set(boolean newState) throws RuntimeException {
        if (control == null) {
            return;
        }

        if (newState == state) {
            return;
        }

        try {
            control.setState(newState);
            state = newState;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
