package ch.fhnw.roundtable.etopia.time;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TimerTest {

    private Timer timer;

    @BeforeEach
    void setUp() {
        timer = new Timer(1.0f);
    }

    @Test
    void shouldNotTriggerBeforeDuration() {
        assertFalse(timer.triggered(0.3f));
        assertFalse(timer.triggered(0.4f));
    }

    @Test
    void shouldTriggerOnceWhenDurationReached() {
        assertFalse(timer.triggered(0.5f));
        assertTrue(timer.triggered(0.5f));
    }

    @Test
    void shouldResetTimer() {
        timer.triggered(1.0f);
        timer.reset();

        assertFalse(timer.triggered(0.9f));
    }

    @Test
    void shouldHandleOvershootCorrectly() {
        assertTrue(timer.triggered(1.2f));
        assertFalse(timer.triggered(0.5f));
    }

    @Test
    void shouldTriggerMultipleTimesOverAccumulation() {
        assertTrue(timer.triggered(2.5f)); // 2 triggers (1.0 + 1.0 + 0.5 remainder)
        assertTrue(timer.triggered(0.5f)); // completes second cycle
    }

    @Test
    void shouldNotTriggerOnNegativeDelta() {
        assertFalse(timer.triggered(-0.1f));
    }

    @Test
    void shouldAccumulateAcrossMultipleCalls() {
        assertFalse(timer.triggered(0.4f));
        assertFalse(timer.triggered(0.4f));
        assertTrue(timer.triggered(0.3f));
    }
}
