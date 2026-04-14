package ch.fhnw.roundtable.etopia.input;

import com.pi4j.context.Context;
import com.pi4j.io.OnOffWrite;
import com.pi4j.io.i2c.I2C;
import com.pi4j.io.i2c.I2CConfig;
import com.pi4j.io.i2c.I2CProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// todo rework together with input into a button class
@SuppressWarnings("PMD.TooManyMethods")
public class LEDControl {
    private static final Logger LOGGER = LoggerFactory.getLogger(LEDControl.class);
    //Available pins for PCF8574 are P01 - P07
    private static final int UP_LED_EXT_PIN = 0;
    private static final int DOWN_LED_EXT_PIN = 1;
    private static final int LEFT_LED_EXT_PIN = 2;
    private static final int RIGHT_LED_EXT_PIN = 3;
    private static final int SELECT_LED_EXT_PIN = 4;
    private static final int BACK_LED_EXT_PIN = 5;

    private OnOffWrite<?> ledUp;
    private OnOffWrite<?> ledDown;
    private OnOffWrite<?> ledLeft;
    private OnOffWrite<?> ledRight;
    private OnOffWrite<?> ledSelect;
    private OnOffWrite<?> ledBack;

    public LEDControl(Context pi4j) {
        I2CProvider i2CProvider = pi4j.provider("linuxfs-i2c");
        I2CConfig i2cConfig = I2C.newConfigBuilder(pi4j)
                .id("PCF8574-Expander")
                .bus(1)
                .device(Pcf8574OutputDriver.PCF8574_ADDRESS_BASE)
                .build();

        try (I2C pcf8574 = i2CProvider.create(i2cConfig)) {
            Pcf8574OutputDriver leds = new Pcf8574OutputDriver(pcf8574);

            ledUp = leds.getOutput(UP_LED_EXT_PIN);
            ledDown = leds.getOutput(DOWN_LED_EXT_PIN);
            ledLeft = leds.getOutput(LEFT_LED_EXT_PIN);
            ledRight = leds.getOutput(RIGHT_LED_EXT_PIN);
            ledSelect = leds.getOutput(SELECT_LED_EXT_PIN);
            ledBack = leds.getOutput(BACK_LED_EXT_PIN);
        } catch (Exception e) {
            LOGGER.warn("i2C could not initialize: {}", e.getMessage());
        }
    }

    public void ledUpOn() {
        if (ledUp == null) {
            return;
        }
        ledUp.on();
    }

    public void ledUpOff() {
        if (ledUp == null) {
            return;
        }
        ledUp.off();
    }

    public void ledDownOn() {
        if (ledDown == null) {
            return;
        }
        ledDown.on();
    }

    public void ledDownOff() {
        if (ledDown == null) {
            return;
        }
        ledDown.off();
    }

    public void ledLeftOn() {
        if (ledLeft == null) {
            return;
        }
        ledLeft.on();
    }

    public void ledLeftOff() {
        if (ledLeft == null) {
            return;
        }
        ledLeft.off();
    }

    public void ledRightOn() {
        if (ledRight == null) {
            return;
        }
        ledRight.on();
    }

    public void ledRightOff() {
        if (ledRight == null) {
            return;
        }
        ledRight.off();
    }

    public void ledSelectOn() {
        if (ledSelect == null) {
            return;
        }
        ledSelect.on();
    }

    public void ledSelectOff() {
        if (ledSelect == null) {
            return;
        }
        ledSelect.off();
    }

    public void ledBackOn() {
        if (ledBack == null) {
            return;
        }
        ledBack.on();
    }

    public void ledBackOff() {
        if (ledBack == null) {
            return;
        }
        ledBack.off();
    }

    // Changes every LED except "reset button"
    // todo: better naming for methods
    public void ledAllPlayableOn() {
        if (ledUp == null) {
            return;
        }
        ledUpOn();
        ledDownOn();
        ledLeftOn();
        ledRightOn();
        ledSelectOn();
    }

    public void ledAllPlayableOff() {
        if (ledUp == null) {
            return;
        }
        ledUpOff();
        ledDownOff();
        ledLeftOff();
        ledRightOff();
        ledSelectOff();
    }
}
