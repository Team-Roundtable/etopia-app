package ch.fhnw.roundtable.etopia.input;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

public class Controls {

    private static final Logger LOGGER = LoggerFactory.getLogger(Controls.class);

    private final Map<ButtonType, Button> buttons;
    private final Set<ButtonType> lightErrors = EnumSet.noneOf(ButtonType.class);

    public Controls(Map<ButtonType, Button> buttons) {
        this.buttons = buttons;
    }

    public void update() {
        for (Button button : buttons.values()) {
            button.update();
        }
    }

    public boolean isButtonPressed(ButtonType type) {
        return buttons.get(type).isPressed();
    }

    public boolean isButtonJustPressed(ButtonType type) {
        return buttons.get(type).isJustPressed();
    }

    public boolean isButtonReleased(ButtonType type) {
        return buttons.get(type).isJustReleased();
    }

    public float getButtonHorizontal() {
        return (isButtonPressed(ButtonType.LEFT) ? -1 : 0) + (isButtonPressed(ButtonType.RIGHT) ? 1 : 0);
    }

    public float getButtonVertical() {
        return (isButtonPressed(ButtonType.DOWN) ? -1 : 0) + (isButtonPressed(ButtonType.UP) ? 1 : 0);
    }

    public void setButtonLight(ButtonType type, boolean newState) {
        try {
            buttons.get(type).setLight(newState);
        } catch (RuntimeException e) {
            if (lightErrors.add(type)) {
                LOGGER.error("Failed to set button light for {}: {}", type, e.getMessage(), e);
            }
        }
    }

    public void setButtonLightPlayable() {
        setButtonLight(ButtonType.UP, true);
        setButtonLight(ButtonType.DOWN, true);
        setButtonLight(ButtonType.LEFT, true);
        setButtonLight(ButtonType.RIGHT, true);
        setButtonLight(ButtonType.SELECT, true);
        setButtonLight(ButtonType.BACK, false);
    }

    public void setButtonLightNone() {
        setButtonLight(ButtonType.UP, false);
        setButtonLight(ButtonType.DOWN, false);
        setButtonLight(ButtonType.LEFT, false);
        setButtonLight(ButtonType.RIGHT, false);
        setButtonLight(ButtonType.SELECT, false);
        setButtonLight(ButtonType.BACK, false);
    }

    public void setButtonLightHorizontal() {
        setButtonLight(ButtonType.UP, false);
        setButtonLight(ButtonType.DOWN, false);
        setButtonLight(ButtonType.LEFT, true);
        setButtonLight(ButtonType.RIGHT, true);
        setButtonLight(ButtonType.SELECT, false);
        setButtonLight(ButtonType.BACK, false);
    }

    public void setButtonLightVertical() {
        setButtonLight(ButtonType.UP, true);
        setButtonLight(ButtonType.DOWN, true);
        setButtonLight(ButtonType.LEFT, false);
        setButtonLight(ButtonType.RIGHT, false);
        setButtonLight(ButtonType.SELECT, false);
        setButtonLight(ButtonType.BACK, false);
    }
}
