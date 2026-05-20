package ch.fhnw.roundtable.etopia.input;

import ch.fhnw.roundtable.etopia.configuration.Configuration;
import ch.fhnw.roundtable.etopia.input.dummy.DummyInput;
import ch.fhnw.roundtable.etopia.input.libgdx.LibGDXInput;
import ch.fhnw.roundtable.etopia.input.pi4j.Pi4JDriver;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Random;

public final class ControlsFactory {

    private ControlsFactory() {

    }

    public static Controls create(Configuration configuration) {
        var buttons = new EnumMap<ButtonType, Button>(ButtonType.class);

        var pi4JDriver = new Pi4JDriver();

        for (ButtonType type : ButtonType.values()) {
            var inputs = createInputs(configuration, type, pi4JDriver);
            var light = createLight(type, pi4JDriver);

            buttons.put(type, new Button(inputs, light));
        }

        return new Controls(buttons);
    }

    private static List<Input> createInputs(Configuration configuration, ButtonType type, Pi4JDriver pi4JDriver) {
        var inputs = new ArrayList<>(List.of(
                pi4JDriver.createInput(type),
                new LibGDXInput(type)));

        if (configuration.dummyMode()) {
            inputs.add(new DummyInput(new Random(), type, 0.01));
        }

        return inputs;
    }

    private static Light createLight(ButtonType type, Pi4JDriver pi4JDriver) {
        return pi4JDriver.createLight(type);
    }
}
