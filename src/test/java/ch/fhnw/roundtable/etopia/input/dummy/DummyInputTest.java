package ch.fhnw.roundtable.etopia.input.dummy;

import ch.fhnw.roundtable.etopia.input.ButtonType;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class DummyInputTest {

    @Test
    void testZeroActivationChanceNeverFires() {
        Random random = new Random();
        DummyInput standardDummy = new DummyInput(random, ButtonType.UP, 0.0);
        DummyInput backDummy = new DummyInput(random, ButtonType.BACK, 0.0);

        for (int i = 0; i < 50; i++) {
            assertFalse(standardDummy.isPressed());
            assertFalse(backDummy.isPressed());
        }
    }

    @Test
    void testBackButtonTenPercentReductionWithDeterministicSeed() {
        // Seed 12345 guarantees that the first call to nextDouble() is ~0.147
        // and the second call is ~0.434
        Random random = new Random(12345);

        DummyInput standardDummy = new DummyInput(random, ButtonType.UP, 0.5);
        DummyInput backDummy = new DummyInput(random, ButtonType.BACK, 0.5);

        assertTrue(standardDummy.isPressed(), "Standard button should fire (0.147 < 0.5)");
        assertFalse(backDummy.isPressed(), "Back button should skip firing (0.147 is not < 0.05)");
    }
}
