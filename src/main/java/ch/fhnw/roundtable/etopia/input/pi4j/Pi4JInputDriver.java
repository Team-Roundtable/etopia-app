package ch.fhnw.roundtable.etopia.input.pi4j;

import com.pi4j.context.Context;
import com.pi4j.io.gpio.digital.DigitalInput;
import com.pi4j.io.gpio.digital.PullResistance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Pi4JInputDriver {

    private static final Logger LOGGER = LoggerFactory.getLogger(Pi4JInputDriver.class);
    private static final long DEBOUNCE_TIME_IN_MICROSECONDS = 30000;

    private final Context pi4jContext;

    public Pi4JInputDriver(Context pi4jContext) {
        this.pi4jContext = pi4jContext;
    }

    public Pi4JInput createInput(int pin) {
        var digitalInputConfig = DigitalInput.newConfigBuilder(pi4jContext)
                .id("BTN_" + pin)
                .name("BUTTON_" + pin)
                .bcm(pin)
                .pull(PullResistance.PULL_UP)
                .debounce(DEBOUNCE_TIME_IN_MICROSECONDS);

        DigitalInput digitalInput = null;

        try {
            digitalInput = pi4jContext.create(digitalInputConfig);
        } catch (Exception e) {
            LOGGER.warn("Could not initialize Pi4JInputDriver: {}", e.getMessage());
        }

        return new Pi4JInput(digitalInput);
    }
}
