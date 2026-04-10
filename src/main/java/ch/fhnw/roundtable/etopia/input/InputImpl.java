package ch.fhnw.roundtable.etopia.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

// todo rename, debounce?
public class InputImpl implements Input {

    // todo light up button
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

    public void update() {
        // TODO get lightupbutton state with (buttonUp != null && buttonUp.isHigh())
        boolean newUp = (Gdx.input.isKeyPressed(Keys.UP) || Gdx.input.isKeyPressed(Keys.W));
        boolean newDown = (Gdx.input.isKeyPressed(Keys.DOWN) || Gdx.input.isKeyPressed(Keys.S));
        boolean newLeft = (Gdx.input.isKeyPressed(Keys.LEFT) || Gdx.input.isKeyPressed(Keys.A));
        boolean newRight = (Gdx.input.isKeyPressed(Keys.RIGHT) || Gdx.input.isKeyPressed(Keys.D));
        boolean newSelect = (Gdx.input.isKeyPressed(Keys.SPACE) || Gdx.input.isKeyPressed(Keys.ENTER));
        boolean newBack = (Gdx.input.isKeyPressed(Keys.BACKSPACE) || Gdx.input.isKeyPressed(Keys.ESCAPE));

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
