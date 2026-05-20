package ch.fhnw.roundtable.etopia.views.settings.ui;

import ch.fhnw.roundtable.etopia.UI;
import ch.fhnw.roundtable.etopia.rendering.Assets;
import ch.fhnw.roundtable.etopia.rendering.Renderer;
import ch.fhnw.roundtable.etopia.views.settings.state.SettingsState;
import com.badlogic.gdx.graphics.Color;

public class SettingsUI implements UI<SettingsState> {

    private final Assets<SettingsAsset> assets;

    public SettingsUI(Assets<SettingsAsset> assets) {
        this.assets = assets;
    }

    @Override
    public void render(SettingsState state, Renderer renderer) {
        renderer.batch(render -> {
            render.drawBackground(assets.getTexture(SettingsAsset.BACKGROUND));

            renderer.font.setColor(Color.WHITE);
            var title = state.title();
            renderer.font.draw(render, title.text(),
                    title.x(), title.y(),
                    title.maxWidth(), -1, true);

            for (var option : state.options()) {
                renderer.font.setColor(option.selected() ? Color.RED : Color.BLACK);
                renderer.font.draw(render, option.name(), option.x(), option.y(), option.maxWidth(), -1, true);
            }

        });
    }

    @Override
    public void dispose() {
        assets.dispose();
    }
}
