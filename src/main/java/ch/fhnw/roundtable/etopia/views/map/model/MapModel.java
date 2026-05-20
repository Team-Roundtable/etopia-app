package ch.fhnw.roundtable.etopia.views.map.model;

import ch.fhnw.roundtable.etopia.Model;
import ch.fhnw.roundtable.etopia.Result;
import ch.fhnw.roundtable.etopia.View;
import ch.fhnw.roundtable.etopia.configuration.Configuration;
import ch.fhnw.roundtable.etopia.input.ButtonType;
import ch.fhnw.roundtable.etopia.input.Controls;
import ch.fhnw.roundtable.etopia.views.map.state.MapHumanState;
import ch.fhnw.roundtable.etopia.views.map.state.MapState;

import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

public class MapModel implements Model<MapState> {

    private final Configuration configuration;
    private final List<Location> locations;
    private final boolean humanMale = new Random().nextBoolean();
    private Building hovered = Building.INFORMATION;
    private boolean selected;

    public MapModel(Configuration configuration) {
        this.configuration = configuration;
        this.locations = List.of(
                new Location(configuration, Building.SETTINGS, 32, 984),
                new Location(configuration, Building.BIOGAS, 160, 220),
                new Location(configuration, Building.INFORMATION, 700, 650),
                new Location(configuration, Building.SOLAR, 1200, 675),
                new Location(configuration, Building.WIND, 160, 610),
                new Location(configuration, Building.GRID, 700, 300),
                new Location(configuration, Building.GEOTHERMAL, 1300, 350)
        );
    }

    @Override
    public void update(float delta, Controls controls) {
        controls.setButtonLightPlayable();

        if (controls.isButtonJustPressed(ButtonType.UP)) {
            hovered = hovered.next(Direction.UP);
        }

        if (controls.isButtonJustPressed(ButtonType.DOWN)) {
            hovered = hovered.next(Direction.DOWN);
        }

        if (controls.isButtonJustPressed(ButtonType.LEFT)) {
            hovered = hovered.next(Direction.LEFT);
        }

        if (controls.isButtonJustPressed(ButtonType.RIGHT)) {
            hovered = hovered.next(Direction.RIGHT);
        }

        selected = controls.isButtonJustPressed(ButtonType.SELECT);
    }

    @Override
    public MapState state() {
        return new MapState(
                locations.stream().map(Location::state).toList(),
                hovered,
                humanState()
        );
    }

    @Override
    public Result result() {
        if (selected) {
            return Result.SUCCESS;
        }

        return Result.RUNNING;
    }

    public Supplier<View> next() {
        return hovered.view(configuration);
    }

    private MapHumanState humanState() {
        float x;
        float y;

        if (hovered == Building.SETTINGS) {
            x = -1000;
            y = -1000;
        } else {
            var locationState = locations.stream()
                    .filter(location -> location.getBuilding() == hovered)
                    .findFirst()
                    .orElse(locations.getFirst())
                    .state();

            x = locationState.x() + locationState.width() / 2;
            y = locationState.y();
        }

        return new MapHumanState(
                x, y,
                configuration.map().humanWidth(),
                configuration.map().humanHeight(),
                humanMale);
    }
}
