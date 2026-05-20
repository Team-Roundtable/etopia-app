package ch.fhnw.roundtable.etopia.time;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CountdownTest {

    private Countdown countdown;

    @BeforeEach
    void setUp() {
        countdown = new Countdown(1.0f);
    }

    @Test
    void shouldNotUpdateWhenNotStarted() {
        countdown.update(0.5f);
        assertFalse(countdown.isFinished());
        assertFalse(countdown.isStarted());
    }

    @Test
    void shouldStartCountdown() {
        countdown.start();
        assertTrue(countdown.isStarted());
    }

    @Test
    void shouldAccumulateUntilFinished() {
        countdown.start();

        countdown.update(0.4f);
        assertFalse(countdown.isFinished());

        countdown.update(0.6f);
        assertTrue(countdown.isFinished());
    }

    @Test
    void shouldRemainFinishedAfterExceedingDuration() {
        countdown.start();

        countdown.update(2.0f);
        assertTrue(countdown.isFinished());
    }

    @Test
    void shouldResetProperly() {
        countdown.start();
        countdown.update(1.0f);

        countdown.reset();

        assertFalse(countdown.isStarted());
        assertFalse(countdown.isFinished());

        countdown.update(1.0f);
        assertFalse(countdown.isFinished());
    }

    @Test
    void shouldIgnoreNegativeDelta() {
        countdown.start();

        countdown.update(-0.5f);

        assertFalse(countdown.isFinished());
        assertTrue(countdown.isStarted());
    }

    @Test
    void shouldAllowRestartAfterReset() {
        countdown.start();
        countdown.update(1.0f);
        assertTrue(countdown.isFinished());

        countdown.reset();
        countdown.start();

        countdown.update(0.5f);
        assertFalse(countdown.isFinished());

        countdown.update(0.5f);
        assertTrue(countdown.isFinished());
    }

    @Test
    void shouldReportStartedStateCorrectly() {
        assertFalse(countdown.isStarted());

        countdown.start();
        assertTrue(countdown.isStarted());

        countdown.reset();
        assertFalse(countdown.isStarted());
    }
}
