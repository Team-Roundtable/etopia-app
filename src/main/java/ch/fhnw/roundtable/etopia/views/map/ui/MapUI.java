package ch.fhnw.roundtable.etopia.views.map.ui;

import ch.fhnw.roundtable.etopia.views.Assets;
import ch.fhnw.roundtable.etopia.views.Renderer;
import ch.fhnw.roundtable.etopia.views.UI;
import ch.fhnw.roundtable.etopia.views.map.game.LocationType;
import ch.fhnw.roundtable.etopia.views.map.game.MapGame;

public class MapUI implements UI<MapGame> {

    private final Assets<MapAsset> assets;

    public MapUI(Assets<MapAsset> assets) {
        this.assets = assets;
    }

    @Override
    public void render(MapGame game, Renderer renderer) {
        renderer.batch(batch -> {
            batch.drawBackground(assets.getTexture(MapAsset.BACKGROUND));

            for (var entry : game.getLocations().entrySet()) {
                var type = entry.getKey();
                var location = entry.getValue();

                batch.draw(assets.getTexture(translate(type)),
                        location.getX(), location.getY(),
                        location.getWidth(), location.getHeight());

                if (game.getHovered() == type) {
                    batch.draw(assets.getTexture(MapAsset.HUMAN),
                            location.getX() + 48, location.getY() - 32,
                            location.getWidth(), location.getHeight());
                }
            }
        });
    }

    @Override
    public void dispose() {
        assets.dispose();
    }

    private MapAsset translate(LocationType locationType) {
        return switch (locationType) {
            case BIOGAS -> MapAsset.BIOGAS;
            case WIND -> MapAsset.WIND;
            case GRID -> MapAsset.GRID;
            case GEOTHERMAL -> MapAsset.GEOTHERMAL;
            case SOLAR -> MapAsset.SOLAR;
            case INFORMATION -> MapAsset.INFORMATION;
            default -> MapAsset.PANEL;
        };
    }
}
