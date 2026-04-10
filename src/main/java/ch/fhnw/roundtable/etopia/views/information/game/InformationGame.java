package ch.fhnw.roundtable.etopia.views.information.game;

import ch.fhnw.roundtable.etopia.configuration.Configuration;
import ch.fhnw.roundtable.etopia.helpers.Timer;
import ch.fhnw.roundtable.etopia.input.Input;
import ch.fhnw.roundtable.etopia.views.Game;
import ch.fhnw.roundtable.etopia.views.ViewType;

public class InformationGame implements Game {

    private final InformationType type;
    private final Timer timer;
    private final String title;
    private final String description;
    private final String note;
    private boolean disabled = true;
    private boolean change = false;

    public InformationGame(Configuration configuration, InformationType type) {
        this.type = type;
        this.timer = new Timer(2f);
        this.title = configuration.getText(type.getLanguagePrefix() + ".title");
        this.description = configuration.getText(type.getLanguagePrefix() + ".description");
        this.note = configuration.getText(type.getLanguagePrefix() + ".note");
    }

    @Override
    public void update(float delta, Input input) {
        if (timer.triggered(delta)) {
            disabled = false;
        }

        if (!disabled && input.isSelectJustPressed()) {
            change = true;
        }
    }

    @Override
    public ViewType next() {
        return change ? type.getNext() : null;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getNote() {
        return note;
    }

    public boolean isDisabled() {
        return disabled;
    }
}
