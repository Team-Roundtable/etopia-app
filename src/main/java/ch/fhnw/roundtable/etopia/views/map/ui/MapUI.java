package ch.fhnw.roundtable.etopia.views.map.ui;

import ch.fhnw.roundtable.etopia.UI;
import ch.fhnw.roundtable.etopia.rendering.Assets;
import ch.fhnw.roundtable.etopia.rendering.Renderer;
import ch.fhnw.roundtable.etopia.views.map.model.Building;
import ch.fhnw.roundtable.etopia.views.map.state.MapState;

public class MapUI implements UI<MapState> {

    private final Assets<MapAsset> assets;

    public MapUI(Assets<MapAsset> assets) {
        this.assets = assets;
    }

    @Override
    public void render(MapState state, Renderer renderer) {
        renderer.batch(batch -> {
            batch.drawBackground(assets.getTexture(MapAsset.BACKGROUND));

            for (var location : state.locations()) {
                var selected = state.hovered() == location.building();

                batch.draw(assets.getTexture(translate(location.building(), location.success(), selected)),
                        location.x(), location.y(),
                        location.width(), location.height());
            }

            var human = state.human();
            batch.draw(assets.getTexture(human.male() ? MapAsset.BOY : MapAsset.GIRL),
                    human.x(), human.y(),
                    human.width(), human.height());
        });
    }

    @Override
    public void dispose() {
        assets.dispose();
    }

    @SuppressWarnings("PMD.CyclomaticComplexity")
    private MapAsset translate(Building locationType, boolean success, boolean selected) {
        return switch (locationType) {
            case BIOGAS -> success
                    ? selected ? MapAsset.BIOGAS_SUCCESS_SELECTED : MapAsset.BIOGAS_SUCCESS_UNSELECTED
                    : selected ? MapAsset.BIOGAS_FAILED_SELECTED : MapAsset.BIOGAS_FAILED_UNSELECTED;
            case WIND -> success
                    ? selected ? MapAsset.WIND_SUCCESS_SELECTED : MapAsset.WIND_SUCCESS_UNSELECTED
                    : selected ? MapAsset.WIND_FAILED_SELECTED : MapAsset.WIND_FAILED_UNSELECTED;
            case GRID -> success
                    ? selected ? MapAsset.GRID_SUCCESS_SELECTED : MapAsset.GRID_SUCCESS_UNSELECTED
                    : selected ? MapAsset.GRID_FAILED_SELECTED : MapAsset.GRID_FAILED_UNSELECTED;
            case GEOTHERMAL -> success
                    ? selected ? MapAsset.GEOTHERMAL_SUCCESS_SELECTED : MapAsset.GEOTHERMAL_SUCCESS_UNSELECTED
                    : selected ? MapAsset.GEOTHERMAL_FAILED_SELECTED : MapAsset.GEOTHERMAL_FAILED_UNSELECTED;
            case SOLAR -> success
                    ? selected ? MapAsset.SOLAR_SUCCESS_SELECTED : MapAsset.SOLAR_SUCCESS_UNSELECTED
                    : selected ? MapAsset.SOLAR_FAILED_SELECTED : MapAsset.SOLAR_FAILED_UNSELECTED;
            case INFORMATION -> selected ? MapAsset.INFORMATION_SELECTED : MapAsset.INFORMATION_UNSELECTED;
            case SETTINGS -> selected ? MapAsset.SETTINGS_SELECTED : MapAsset.SETTINGS_UNSELECTED;


        };
    }
}
