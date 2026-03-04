package ch.fhnw.roundtable.etopia.input;

import com.badlogic.gdx.Gdx;
import com.pi4j.Pi4J;
import com.pi4j.context.Context;
import com.pi4j.io.gpio.digital.DigitalInput;
import com.pi4j.io.gpio.digital.PullResistance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// todo rename, debounce?
public class Input {

    private final static Logger LOGGER = LoggerFactory.getLogger(Input.class);

    // todo light up button

    private Context pi4j;
    private DigitalInput buttonUp;
    private DigitalInput buttonDown;
    private DigitalInput buttonLeft;
    private DigitalInput buttonRight;
    private DigitalInput buttonSelect;
    private DigitalInput buttonBack;

    public Input() {
        try {
            pi4j = Pi4J.newAutoContext();

            // todo magic values
            buttonUp = pi4j.create(DigitalInput.newConfigBuilder(pi4j)
                    .id("BTN_UP")
                    .name("BUTTON_UP")
                    .bcm(23)
                    .pull(PullResistance.PULL_DOWN)
                    .debounce(3000L));

            // todo repeat

            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                pi4j.shutdown();
            }));

        } catch (Exception e) {
            LOGGER.warn("Pi4J could not initialize: {}", e.getMessage());
        }
    }

    // todo review maybe justpressed, justreleased, ispressed (function with bools or enum)
    public boolean up() {
        return Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.RIGHT) || (buttonUp != null && buttonUp.isHigh());
    }

    public boolean down() {
        return false;
    }

    public boolean left() {
        return false;
    }

    public boolean right() {
        return false;
    }

    public boolean select() {
        return false;
    }

    public boolean back() {
        return false;
    }
}
