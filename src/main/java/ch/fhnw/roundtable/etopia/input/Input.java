package ch.fhnw.roundtable.etopia.input;

public interface Input {

    boolean isUpPressed();
    boolean isDownPressed();
    boolean isLeftPressed();
    boolean isRightPressed();
    boolean isSelectPressed();
    boolean isBackPressed();

    boolean isUpJustPressed();
    boolean isDownJustPressed();
    boolean isLeftJustPressed();
    boolean isRightJustPressed();
    boolean isSelectJustPressed();
    boolean isBackJustPressed();

    boolean isUpJustReleased();
    boolean isDownJustReleased();
    boolean isLeftJustReleased();
    boolean isRightJustReleased();
    boolean isSelectJustReleased();
    boolean isBackJustReleased();

    float getHorizontalInput();
    float getVerticalInput();
}
