package ch.fhnw.roundtable.etopia.helpers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Timer {

    private static final Logger LOGGER = LoggerFactory.getLogger(Timer.class);

    private final float duration;
    private float current = 0;

    public Timer(float duration) {
        this.duration = duration;
    }

    public boolean triggered(float delta) {
        if (delta < 0) {
            LOGGER.error("Negative value in Timer, delta: {}", delta);
            return false;
        }

        current += delta;

        if (current >= duration) {
            current -= duration;
            return true;
        }

        return false;
    }

    public void reset() {
        current = 0;
    }

    public boolean isZero() {
        return current == 0;
    }
}
