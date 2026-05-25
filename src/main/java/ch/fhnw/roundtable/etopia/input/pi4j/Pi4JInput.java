package ch.fhnw.roundtable.etopia.input.pi4j;

import ch.fhnw.roundtable.etopia.input.Input;
import com.pi4j.io.gpio.digital.DigitalInput;

public class Pi4JInput implements Input {

    private final DigitalInput input;

    public Pi4JInput(DigitalInput input) {
        this.input = input;
    }

    @Override
    public boolean isPressed() {
        return input != null && input.isHigh();
    }
}
