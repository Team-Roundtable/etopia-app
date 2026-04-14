package ch.fhnw.roundtable.etopia.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.pi4j.context.Context;
import com.pi4j.io.gpio.digital.DigitalInput;
import com.pi4j.io.gpio.digital.PullResistance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// todo rework together with ledControl to unified input/button interface
@SuppressWarnings("PMD.NPathComplexity")
public class InputImpl implements Input {

    private static final Logger LOGGER = LoggerFactory.getLogger(InputImpl.class);
    private static final int UP_BUTTON_GPIO_PIN = 23;
    private static final int DOWN_BUTTON_GPIO_PIN = 24;
    private static final int LEFT_BUTTON_GPIO_PIN = 27;
    private static final int RIGHT_BUTTON_GPIO_PIN = 22;
    private static final int SELECT_BUTTON_GPIO_PIN = 17;
    private static final int BACK_BUTTON_GPIO_PIN = 18;
    private static final long DEBOUNCE_TIME = 3000; //microseconds

    private Context pi4j;
    private DigitalInput buttonUp;
    private DigitalInput buttonDown;
    private DigitalInput buttonLeft;
    private DigitalInput buttonRight;
    private DigitalInput buttonSelect;
    private DigitalInput buttonBack;

    private boolean upPressed = false;
    private boolean downPressed = false;
    private boolean leftPressed = false;
    private boolean rightPressed = false;
    private boolean selectPressed = false;
    private boolean backPressed = false;
    private boolean upJustPressed = false;
    private boolean downJustPressed = false;
    private boolean leftJustPressed = false;
    private boolean rightJustPressed = false;
    private boolean selectJustPressed = false;
    private boolean backJustPressed = false;
    private boolean upJustReleased = false;
    private boolean downJustReleased = false;
    private boolean leftJustReleased = false;
    private boolean rightJustReleased = false;
    private boolean selectJustReleased = false;
    private boolean backJustReleased = false;


    public InputImpl(Context context) {
        pi4j = context;
        try {
            // todo magic values
            buttonUp = pi4j.create(DigitalInput.newConfigBuilder(pi4j)
                    .id("BTN_UP")
                    .name("BUTTON_UP")
                    .bcm(UP_BUTTON_GPIO_PIN)
                    .pull(PullResistance.PULL_DOWN)
                    .debounce(DEBOUNCE_TIME));

            buttonDown = pi4j.create(DigitalInput.newConfigBuilder(pi4j)
                    .id("BTN_DOWN")
                    .name("BUTTON_DOWN")
                    .bcm(DOWN_BUTTON_GPIO_PIN)
                    .pull(PullResistance.PULL_DOWN)
                    .debounce(DEBOUNCE_TIME));

            buttonLeft = pi4j.create(DigitalInput.newConfigBuilder(pi4j)
                    .id("BTN_LEFT")
                    .name("BUTTON_LEFT")
                    .bcm(LEFT_BUTTON_GPIO_PIN)
                    .pull(PullResistance.PULL_DOWN)
                    .debounce(DEBOUNCE_TIME));

            buttonRight = pi4j.create(DigitalInput.newConfigBuilder(pi4j)
                    .id("BTN_RIGHT")
                    .name("BUTTON_RIGHT")
                    .bcm(RIGHT_BUTTON_GPIO_PIN)
                    .pull(PullResistance.PULL_DOWN)
                    .debounce(DEBOUNCE_TIME));

            buttonSelect = pi4j.create(DigitalInput.newConfigBuilder(pi4j)
                    .id("BTN_SELECT")
                    .name("BUTTON_SELECT")
                    .bcm(SELECT_BUTTON_GPIO_PIN)
                    .pull(PullResistance.PULL_DOWN)
                    .debounce(DEBOUNCE_TIME));

            buttonBack = pi4j.create(DigitalInput.newConfigBuilder(pi4j)
                    .id("BTN_BACK")
                    .name("BUTTON_BACK")
                    .bcm(BACK_BUTTON_GPIO_PIN)
                    .pull(PullResistance.PULL_DOWN)
                    .debounce(DEBOUNCE_TIME));

            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                pi4j.shutdown();
            }));

        } catch (Exception e) {
            LOGGER.warn("Pi4J could not initialize: {}", e.getMessage());
        }
    }

    public void update() {
        // TODO get lightupbutton state with (buttonUp != null && buttonUp.isHigh())
        boolean newUp = (Gdx.input.isKeyPressed(Keys.UP) || Gdx.input.isKeyPressed(Keys.W)
                || (buttonUp != null && buttonUp.isHigh()));
        boolean newDown = (Gdx.input.isKeyPressed(Keys.DOWN)
                || Gdx.input.isKeyPressed(Keys.S) || (buttonDown != null && buttonDown.isHigh()));
        boolean newLeft = (Gdx.input.isKeyPressed(Keys.LEFT)
                || Gdx.input.isKeyPressed(Keys.A) || (buttonLeft != null && buttonLeft.isHigh()));
        boolean newRight = (Gdx.input.isKeyPressed(Keys.RIGHT)
                || Gdx.input.isKeyPressed(Keys.D) || (buttonRight != null && buttonRight.isHigh()));
        boolean newSelect = (Gdx.input.isKeyPressed(Keys.SPACE)
                || Gdx.input.isKeyPressed(Keys.ENTER) || (buttonSelect != null && buttonSelect.isHigh()));
        boolean newBack = (Gdx.input.isKeyPressed(Keys.BACKSPACE)
                || Gdx.input.isKeyPressed(Keys.ESCAPE)
                || (buttonBack != null && buttonBack.isHigh()));

        updateUp(newUp);
        updateDown(newDown);
        updateLeft(newLeft);
        updateRight(newRight);
        updateSelect(newSelect);
        updateBack(newBack);
    }

    private void updateUp(boolean newUp) {
        upJustPressed = !upPressed && newUp;
        upJustReleased = upPressed && !newUp;
        upPressed = newUp;
    }

    private void updateDown(boolean newDown) {
        downJustPressed = !downPressed && newDown;
        downJustReleased = downPressed && !newDown;
        downPressed = newDown;
    }

    private void updateLeft(boolean newLeft) {
        leftJustPressed = !leftPressed && newLeft;
        leftJustReleased = leftPressed && !newLeft;
        leftPressed = newLeft;
    }

    private void updateRight(boolean newRight) {
        rightJustPressed = !rightPressed && newRight;
        rightJustReleased = rightPressed && !newRight;
        rightPressed = newRight;
    }

    private void updateSelect(boolean newSelect) {
        selectJustPressed = !selectPressed && newSelect;
        selectJustReleased = selectPressed && !newSelect;
        selectPressed = newSelect;
    }

    private void updateBack(boolean newBack) {
        backJustPressed = !backPressed && newBack;
        backJustReleased = backPressed && !newBack;
        backPressed = newBack;
    }

    @Override
    public boolean isUpPressed() {
        return upPressed;
    }

    @Override
    public boolean isDownPressed() {
        return downPressed;
    }

    @Override
    public boolean isLeftPressed() {
        return leftPressed;
    }

    @Override
    public boolean isRightPressed() {
        return rightPressed;
    }

    @Override
    public boolean isSelectPressed() {
        return selectPressed;
    }

    @Override
    public boolean isBackPressed() {
        return backPressed;
    }

    @Override
    public boolean isUpJustPressed() {
        return upJustPressed;
    }

    @Override
    public boolean isDownJustPressed() {
        return downJustPressed;
    }

    @Override
    public boolean isLeftJustPressed() {
        return leftJustPressed;
    }

    @Override
    public boolean isRightJustPressed() {
        return rightJustPressed;
    }

    @Override
    public boolean isSelectJustPressed() {
        return selectJustPressed;
    }

    @Override
    public boolean isBackJustPressed() {
        return backJustPressed;
    }

    @Override
    public boolean isUpJustReleased() {
        return upJustReleased;
    }

    @Override
    public boolean isDownJustReleased() {
        return downJustReleased;
    }

    @Override
    public boolean isLeftJustReleased() {
        return leftJustReleased;
    }

    @Override
    public boolean isRightJustReleased() {
        return rightJustReleased;
    }

    @Override
    public boolean isSelectJustReleased() {
        return selectJustReleased;
    }

    @Override
    public boolean isBackJustReleased() {
        return backJustReleased;
    }

    @Override
    public float getHorizontalInput() {
        return (leftPressed ? -1 : 0) + (rightPressed ? 1 : 0);
    }

    @Override
    public float getVerticalInput() {
        return (downPressed ? -1 : 0) + (upPressed ? 1 : 0);
    }
}
