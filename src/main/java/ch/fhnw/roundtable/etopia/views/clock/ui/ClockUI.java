package ch.fhnw.roundtable.etopia.views.clock.ui;

import ch.fhnw.roundtable.etopia.views.Renderer;
import ch.fhnw.roundtable.etopia.views.UI;
import ch.fhnw.roundtable.etopia.views.clock.ClockConfiguration;
import ch.fhnw.roundtable.etopia.views.clock.game.ClockGame;

public class ClockUI implements UI<ClockGame> {

    private final ClockConfiguration clockConfiguration;

    public ClockUI(ClockConfiguration clockConfiguration) {
        this.clockConfiguration = clockConfiguration;
    }

    @Override
    public void render(ClockGame game, Renderer renderer) {
        var offsetX = clockConfiguration.offsetX;
        var offsetY = clockConfiguration.offsetY;

        renderer.batch(render -> {
            renderer.font.draw(render, String.format("%02.1f s", game.getCurrent()), offsetX, offsetY);
        });
    }

    @Override
    public void dispose() {
    }
}
