package ch.fhnw.roundtable.etopia.time;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Countdown {

    private static final Logger LOGGER = LoggerFactory.getLogger(Countdown.class);

    private final float duration;
    private float current = 0;
    private boolean started = false;

    public Countdown(float duration) {
        this.duration = duration;
    }

    public void start() {
        started = true;
    }

    public void reset() {
        started = false;
        current = 0;
    }

    public void update(float delta) {
        if (!started) {
            return;
        }

        if (delta < 0) {
            LOGGER.error("Negative value in Countdown, delta: {}", delta);
            return;
        }

        current += delta;
    }

    public boolean isFinished() {
        return current >= duration;
    }

    public boolean isStarted() {
        return started;
    }
}
