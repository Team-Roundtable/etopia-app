package ch.fhnw.roundtable.etopia.views.information.ui;

import ch.fhnw.roundtable.etopia.UI;
import ch.fhnw.roundtable.etopia.rendering.Assets;
import ch.fhnw.roundtable.etopia.rendering.Renderer;
import ch.fhnw.roundtable.etopia.rendering.VideoAssets;
import ch.fhnw.roundtable.etopia.views.information.model.InformationType;
import ch.fhnw.roundtable.etopia.views.information.state.InformationState;
import com.badlogic.gdx.graphics.Color;

@SuppressWarnings("PMD.CyclomaticComplexity")
public class InformationUI implements UI<InformationState> {

    private final Assets<InformationAsset> assets;
    private final VideoAssets<InformationVideoAsset> videos;

    public InformationUI(Assets<InformationAsset> assets, VideoAssets<InformationVideoAsset> videos) {
        this.assets = assets;
        this.videos = videos;
    }

    @Override
    public void render(InformationState state, Renderer renderer) {

        renderer.batch(render -> {
            render.drawBackground(assets.getTexture(translateAsset(state.type())));

            renderer.font.setColor(Color.WHITE);
            var title = state.title();
            renderer.font.draw(render, title.content(),
                    title.x(), title.y(),
                    title.maxWidth(), -1, true);

            renderer.font.setColor(Color.DARK_GRAY);
            var description = state.description();
            renderer.font.draw(render, description.content(),
                    description.x(), description.y(),
                    description.maxWidth(), -1, true);

            if (!state.disabled()) {
                renderer.font.setColor(Color.BLACK);
                var note = state.note();
                renderer.font.draw(render, note.content(),
                        note.x(), note.y(),
                        note.maxWidth(), -1, true);
            }

            var video = state.video();
            var videoAsset = translateVideo(state.type());

            if (videoAsset != null) {
                var player = videos.getPlayer(videoAsset);
                if (player != null) {
                    player.update();

                    var videoTexture = player.getTexture();
                    if (videoTexture != null) {
                        render.draw(videoTexture,
                                video.x(), video.y(),
                                video.width(), video.height());
                    }
                }
            }
        });
    }

    @Override
    public void dispose() {
        assets.dispose();
        videos.dispose();
    }

    @SuppressWarnings("PMD.CyclomaticComplexity")
    private InformationAsset translateAsset(InformationType type) {
        return switch (type) {
            case INFORMATION, GRID_START, WIND_START, SOLAR_START,
                 GEOTHERMAL_START, BIOGAS_START -> InformationAsset.INFORMATION;
            case BIOGAS_SUCCESS, WIND_SUCCESS, SOLAR_SUCCESS,
                 GRID_SUCCESS, GEOTHERMAL_SUCCESS -> InformationAsset.SUCCESS;
            case BIOGAS_FAIL_HEALTH, GRID_FAIL_TIME, BIOGAS_FAIL_TIME, SOLAR_FAIL_TIME, GEOTHERMAL_FAIL_TIME,
                 GEOTHERMAL_FAIL_HEALTH, WIND_FAIL_TIME, WIND_FAIL_HEALTH -> InformationAsset.FAIL;
        };
    }

    @SuppressWarnings("PMD.CyclomaticComplexity")
    private InformationVideoAsset translateVideo(InformationType type) {
        return switch (type) {
            case INFORMATION, BIOGAS_FAIL_TIME, WIND_FAIL_TIME,
                 GEOTHERMAL_FAIL_TIME, SOLAR_FAIL_TIME, GRID_FAIL_TIME -> null;
            case BIOGAS_START -> InformationVideoAsset.BIOGAS_START;
            case BIOGAS_SUCCESS -> InformationVideoAsset.BIOGAS_SUCCESS;
            case BIOGAS_FAIL_HEALTH -> InformationVideoAsset.BIOGAS_FAIL;
            case WIND_START -> InformationVideoAsset.WIND_START;
            case WIND_SUCCESS -> InformationVideoAsset.WIND_SUCCESS;
            case WIND_FAIL_HEALTH -> InformationVideoAsset.WIND_FAIL;
            case GEOTHERMAL_START -> InformationVideoAsset.GEOTHERMAL_START;
            case GEOTHERMAL_SUCCESS -> InformationVideoAsset.GEOTHERMAL_SUCCESS;
            case GEOTHERMAL_FAIL_HEALTH -> InformationVideoAsset.GEOTHERMAL_FAIL;
            case SOLAR_START -> InformationVideoAsset.SOLAR_START;
            case SOLAR_SUCCESS -> InformationVideoAsset.SOLAR_SUCCESS;
            case GRID_START -> InformationVideoAsset.GRID_START;
            case GRID_SUCCESS -> InformationVideoAsset.GRID_SUCCESS;
        };
    }
}
