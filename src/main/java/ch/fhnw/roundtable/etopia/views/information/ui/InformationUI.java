package ch.fhnw.roundtable.etopia.views.information.ui;

import ch.fhnw.roundtable.etopia.views.Assets;
import ch.fhnw.roundtable.etopia.views.Renderer;
import ch.fhnw.roundtable.etopia.views.UI;
import ch.fhnw.roundtable.etopia.views.information.InformationConfiguration;
import ch.fhnw.roundtable.etopia.views.information.game.InformationGame;
import com.badlogic.gdx.graphics.Color;

public class InformationUI implements UI<InformationGame> {

    private final InformationConfiguration informationConfiguration;
    private final Assets<InformationAsset> assets;

    public InformationUI(InformationConfiguration informationConfiguration, Assets<InformationAsset> assets) {
        this.informationConfiguration = informationConfiguration;
        this.assets = assets;
    }

    @Override
    public void render(InformationGame game, Renderer renderer) {
        renderer.batch(render -> {
            render.drawBackground(assets.getTexture(InformationAsset.BACKGROUND));

            var maxWidth = informationConfiguration.maxWidth;
            renderer.font.setColor(Color.BLACK);

            var titleOffset = informationConfiguration.titleOffset;
            renderer.font.draw(render, game.getTitle(),
                    titleOffset.x(), titleOffset.y(),
                    maxWidth, -1, true);

            var descriptionOffset = informationConfiguration.descriptionOffset;
            renderer.font.draw(render, game.getDescription(),
                    descriptionOffset.x(), descriptionOffset.y(),
                    maxWidth, -1, true);

            if (!game.isDisabled()) {
                var noteOffset = informationConfiguration.noteOffset;
                renderer.font.draw(render, game.getNote(),
                        noteOffset.x(), noteOffset.y(),
                        maxWidth, -1, true);
            }
        });
    }

    @Override
    public void dispose() {
        assets.dispose();
    }
}
