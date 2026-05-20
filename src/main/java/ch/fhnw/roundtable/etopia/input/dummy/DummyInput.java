package ch.fhnw.roundtable.etopia.input.dummy;

import ch.fhnw.roundtable.etopia.input.ButtonType;
import ch.fhnw.roundtable.etopia.input.Input;

import java.util.Random;

public class DummyInput implements Input {
    private final double activationChance;
    private final Random random;

    public DummyInput(Random random, ButtonType type, double activationChance) {
        this.random = random;
        this.activationChance = type == ButtonType.BACK
                ? activationChance / 10
                : activationChance;
    }

    @Override
    public boolean isPressed() {
        return random.nextDouble() < activationChance;
    }
}
