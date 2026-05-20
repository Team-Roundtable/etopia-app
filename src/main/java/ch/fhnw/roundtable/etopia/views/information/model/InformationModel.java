package ch.fhnw.roundtable.etopia.views.information.model;

import ch.fhnw.roundtable.etopia.Model;
import ch.fhnw.roundtable.etopia.Result;
import ch.fhnw.roundtable.etopia.configuration.Configuration;
import ch.fhnw.roundtable.etopia.time.Timer;
import ch.fhnw.roundtable.etopia.input.ButtonType;
import ch.fhnw.roundtable.etopia.input.Controls;
import ch.fhnw.roundtable.etopia.views.information.state.InformationState;
import ch.fhnw.roundtable.etopia.views.information.state.InformationTextState;
import ch.fhnw.roundtable.etopia.views.information.state.InformationVideoState;

public class InformationModel implements Model<InformationState> {

    private final Configuration configuration;
    private final InformationType type;
    private final Timer timer;
    private final InformationTextState title;
    private final InformationTextState description;
    private final InformationTextState note;

    private boolean disabled = true;
    private boolean change = false;
    private boolean exit = false;

    public InformationModel(Configuration configuration, InformationType type) {
        this.configuration = configuration;
        this.type = type;
        this.timer = new Timer(configuration.information().disabledDuration());

        this.title = new InformationTextState(
                configuration.information().offsetX(),
                configuration.information().titleOffsetY(),
                configuration.information().maxWidth(),
                configuration.getText(type.getLanguagePrefix() + ".title"));

        this.description = new InformationTextState(
                configuration.information().offsetX(),
                configuration.information().descriptionOffsetY(),
                configuration.information().maxWidth(),
                configuration.getText(type.getLanguagePrefix() + ".description"));

        this.note = new InformationTextState(
                configuration.information().offsetX(),
                configuration.information().noteOffsetY(),
                configuration.information().maxWidth(),
                configuration.getText(type.getLanguagePrefix() + ".note"));
    }

    @Override
    public void update(float delta, Controls controls) {
        if (disabled) {
            controls.setButtonLightNone();
            controls.setButtonLight(ButtonType.BACK, type.isEscapable());
        }

        if (timer.triggered(delta)) {
            disabled = false;
            controls.setButtonLight(ButtonType.SELECT, true);
        }

        if (type.isEscapable() && controls.isButtonJustPressed(ButtonType.BACK)) {
            exit = true;
        }

        if (!disabled && controls.isButtonJustPressed(ButtonType.SELECT)) {
            change = true;
        }
    }

    @Override
    public InformationState state() {
        return new InformationState(
                title,
                description,
                note,
                videoState(),
                disabled,
                type
        );
    }

    @Override
    public Result result() {
        if (exit) {
            return Result.FAIL_TIME;
        }

        if (change) {
            return Result.SUCCESS;
        }

        return Result.RUNNING;
    }

    private InformationVideoState videoState() {
        return new InformationVideoState(
                configuration.information().videoOffsetX(),
                configuration.information().videoOffsetY(),
                configuration.information().videoWidth(),
                configuration.information().videoHeight()
        );
    }
}
