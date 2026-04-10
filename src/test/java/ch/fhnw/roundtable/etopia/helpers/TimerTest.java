package ch.fhnw.roundtable.etopia.helpers;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TimerTest {

    @Test
    void shouldNotTriggerBeforeDuration() {
        Timer timer = new Timer(5f);

        assertFalse(timer.triggered(1f));
        assertFalse(timer.triggered(2f));
        assertFalse(timer.triggered(1.9f));
    }

    @Test
    void shouldTriggerAtExactDuration() {
        Timer timer = new Timer(5f);

        assertFalse(timer.triggered(2f));
        assertFalse(timer.triggered(2f));
        assertTrue(timer.triggered(1f));
    }

    @Test
    void shouldCarryOverRemainder() {
        Timer timer = new Timer(5f);

        assertTrue(timer.triggered(6f));
        assertFalse(timer.triggered(3f));
        assertTrue(timer.triggered(1f));
    }

    @Test
    void shouldTriggerContinuouslyOverMultipleCalls() {
        Timer timer = new Timer(3f);

        assertFalse(timer.triggered(1f));
        assertFalse(timer.triggered(1f));
        assertTrue(timer.triggered(1f));

        assertFalse(timer.triggered(1f));
        assertFalse(timer.triggered(1f));
        assertTrue(timer.triggered(1f));
    }

    @Test
    void largeDeltaTriggersMultipleTimes() {
        Timer timer = new Timer(3f);

        assertTrue(timer.triggered(9f));
        assertTrue(timer.triggered(0f));
        assertTrue(timer.triggered(0f));
        assertFalse(timer.triggered(0f));
    }

    @Test
    void negativeValuesIgnored() {
        Timer timer = new Timer(3f);

        assertFalse(timer.triggered(2f));
        assertFalse(timer.triggered(-1f));
        assertTrue(timer.triggered(1f));
    }

    @Test
    void timerResetsTrigger() {
        Timer timer = new Timer(3f);

        assertFalse(timer.triggered(2f));
        timer.reset();
        assertFalse(timer.triggered(2f));
        assertTrue(timer.triggered(1f));
    }
}
