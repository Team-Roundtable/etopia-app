package ch.fhnw.roundtable.etopia.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.pi4j.Pi4J;
import com.pi4j.context.Context;
import com.pi4j.io.gpio.digital.DigitalInput;
import com.pi4j.io.gpio.digital.PullResistance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// todo rename, debounce?
public class InputImpl implements Input {

    private final static Logger LOGGER = LoggerFactory.getLogger(InputImpl.class);

    // todo light up button
    boolean isUpPressed = false;
    boolean isDownPressed = false;
    boolean isLeftPressed = false;
    boolean isRightPressed = false;
    boolean isSelectPressed = false;
    boolean isBackPressed = false;
    boolean isUpJustPressed = false;
    boolean isDownJustPressed = false;
    boolean isLeftJustPressed = false;
    boolean isRightJustPressed = false;
    boolean isSelectJustPressed = false;
    boolean isBackJustPressed = false;
    boolean isUpJustReleased = false;
    boolean isDownJustReleased = false;
    boolean isLeftJustReleased = false;
    boolean isRightJustReleased = false;
    boolean isSelectJustReleased = false;
    boolean isBackJustReleased = false;
    private Context pi4j;
    private DigitalInput buttonUp;
    private DigitalInput buttonDown;
    private DigitalInput buttonLeft;
    private DigitalInput buttonRight;
    private DigitalInput buttonSelect;
    private DigitalInput buttonBack;


    public InputImpl() {
        try {
            pi4j = Pi4J.newAutoContext();

            // todo magic values
            buttonUp = pi4j.create(DigitalInput.newConfigBuilder(pi4j)
                    .id("BTN_UP")
                    .name("BUTTON_UP")
                    .address(23)
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

    public void update() {
        // TODO get lightupbutton state with (buttonUp != null && buttonUp.isHigh())
        boolean newUp = (Gdx.input.isKeyPressed(Keys.UP) || Gdx.input.isKeyPressed(Keys.W));
        boolean newDown = (Gdx.input.isKeyPressed(Keys.DOWN) || Gdx.input.isKeyPressed(Keys.S));
        boolean newLeft = (Gdx.input.isKeyPressed(Keys.LEFT) || Gdx.input.isKeyPressed(Keys.A));
        boolean newRight = (Gdx.input.isKeyPressed(Keys.RIGHT) || Gdx.input.isKeyPressed(Keys.D));
        boolean newSelect = (Gdx.input.isKeyPressed(Keys.SPACE) || Gdx.input.isKeyPressed(Keys.ENTER));
        boolean newBack = (Gdx.input.isKeyPressed(Keys.BACKSPACE) || Gdx.input.isKeyPressed(Keys.ESCAPE));

        isUpJustPressed = !isUpPressed && newUp;
        isDownJustPressed = !isDownPressed && newDown;
        isLeftJustPressed = !isLeftPressed && newLeft;
        isRightJustPressed = !isRightPressed && newRight;
        isSelectJustPressed = !isSelectPressed && newSelect;
        isBackJustPressed = !isBackPressed && newBack;

        isUpJustReleased = isUpPressed && !newUp;
        isDownJustReleased = isDownPressed && !newDown;
        isLeftJustReleased = isLeftPressed && !newLeft;
        isRightJustReleased = isRightPressed && !newRight;
        isSelectJustReleased = isSelectPressed && !newSelect;
        isBackJustReleased = isBackPressed && !newBack;

        isUpPressed = newUp;
        isDownPressed = newDown;
        isLeftPressed = newLeft;
        isRightPressed = newRight;
        isSelectPressed = newSelect;
        isBackPressed = newBack;
    }

    public boolean isUpPressed() {
        return isUpPressed;
    }

    public boolean isDownPressed() {
        return isDownPressed;
    }

    public boolean isLeftPressed() {
        return isLeftPressed;
    }

    public boolean isRightPressed() {
        return isRightPressed;
    }

    public boolean isSelectPressed() {
        return isSelectPressed;
    }

    public boolean isBackPressed() {
        return isBackPressed;
    }

    @Override
    public boolean isUpJustPressed() {
        return isUpJustPressed;
    }

    @Override
    public boolean isDownJustPressed() {
        return isDownJustPressed;
    }

    @Override
    public boolean isLeftJustPressed() {
        return isLeftJustPressed;
    }

    @Override
    public boolean isRightJustPressed() {
        return isRightJustPressed;
    }

    @Override
    public boolean isSelectJustPressed() {
        return isSelectJustPressed;
    }

    @Override
    public boolean isBackJustPressed() {
        return isBackJustPressed;
    }

    @Override
    public boolean isUpJustReleased() {
        return isUpJustReleased;
    }

    @Override
    public boolean isDownJustReleased() {
        return isDownJustReleased;
    }

    @Override
    public boolean isLeftJustReleased() {
        return isLeftJustReleased;
    }

    @Override
    public boolean isRightJustReleased() {
        return isRightJustReleased;
    }

    @Override
    public boolean isSelectJustReleased() {
        return isSelectJustReleased;
    }

    @Override
    public boolean isBackJustReleased() {
        return isBackJustReleased;
    }

    @Override
    public float getHorizontalInput() {
        return (isLeftPressed ? -1 : 0) + (isRightPressed ? 1 : 0);
    }

    @Override
    public float getVerticalInput() {
        return (isDownPressed ? -1 : 0) + (isUpPressed ? 1 : 0);
    }
}
