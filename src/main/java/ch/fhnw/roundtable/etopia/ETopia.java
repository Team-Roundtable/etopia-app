package ch.fhnw.roundtable.etopia;

import ch.fhnw.roundtable.etopia.configuration.Configuration;
import ch.fhnw.roundtable.etopia.input.InputImpl;
import ch.fhnw.roundtable.etopia.views.Renderer;
import ch.fhnw.roundtable.etopia.views.View;
import ch.fhnw.roundtable.etopia.views.ViewType;
import ch.fhnw.roundtable.etopia.views.biogas.Biogas;
import ch.fhnw.roundtable.etopia.views.geothermal.Geothermal;
import ch.fhnw.roundtable.etopia.views.grid.Grid;
import ch.fhnw.roundtable.etopia.views.information.Information;
import ch.fhnw.roundtable.etopia.views.information.game.InformationType;
import ch.fhnw.roundtable.etopia.views.map.Map;
import ch.fhnw.roundtable.etopia.views.solar.Solar;
import ch.fhnw.roundtable.etopia.views.wind.Wind;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.ScreenUtils;

public class ETopia implements ApplicationListener {

    public static final int WORLD_WIDTH = 1920;
    public static final int WORLD_HEIGHT = 1080;

    private final Configuration configuration;
    private Renderer renderer;
    private InputImpl input;
    private ViewType currentViewType;
    private View currentView;

    public ETopia(Configuration configuration) {
        this.configuration = configuration;
    }
    // todo global game state, maybe via singleton

    @Override
    public void create() {
        renderer = new Renderer();
        input = new InputImpl();

        currentViewType = ViewType.MAP;
        currentView = new Map();
    }

    @Override
    public void resize(int width, int height) {
        renderer.resize(width, height);
    }

    @Override
    public void render() {
        float delta = Gdx.graphics.getDeltaTime();

        ScreenUtils.clear(Color.BLACK);

        input.update();

        currentView.update(delta, input);
        currentView.render(renderer);

        var next = currentView.next();
        if (next != null) {
            changeView(next);
        }
    }

    private void changeView(ViewType next) {
        currentView.dispose();

        currentViewType = next;
        currentView = switch (currentViewType) {
            case MAP -> new Map();
            case INFORMATION -> new Information(configuration, InformationType.INFORMATION);
            case SETTINGS -> new Map();

            case BIOGAS -> new Biogas();
            case BIOGAS_START -> new Information(configuration, InformationType.BIOGAS_START);
            case BIOGAS_SUCCESS -> new Information(configuration, InformationType.BIOGAS_SUCCESS);
            case BIOGAS_FAIL_HEALTH -> new Information(configuration, InformationType.BIOGAS_FAIL_HEALTH);

            case WIND -> new Wind(configuration);
            case WIND_START -> new Information(configuration, InformationType.WIND_START);
            case WIND_SUCCESS -> new Information(configuration, InformationType.WIND_SUCCESS);
            case WIND_FAIL_HEALTH -> new Information(configuration, InformationType.WIND_FAIL_HEALTH);
            case WIND_FAIL_CLOCK -> new Information(configuration, InformationType.WIND_FAIL_CLOCK);

            case GEOTHERMAL -> new Geothermal();
            case GEOTHERMAL_START -> new Information(configuration, InformationType.GEOTHERMAL_START);
            case GEOTHERMAL_SUCCESS -> new Information(configuration, InformationType.GEOTHERMAL_SUCCESS);
            case GEOTHERMAL_FAIL_HEALTH -> new Information(configuration, InformationType.GEOTHERMAL_FAIL_HEALTH);

            case SOLAR -> new Solar();
            case SOLAR_START -> new Information(configuration, InformationType.SOLAR_START);
            case SOLAR_SUCCESS -> new Information(configuration, InformationType.SOLAR_SUCCESS);
            case SOLAR_FAIL_CLOCK -> new Information(configuration, InformationType.SOLAR_FAIL_CLOCK);

            case GRID -> new Grid();
            case GRID_START -> new Information(configuration, InformationType.GRID_START);
            case GRID_SUCCESS -> new Information(configuration, InformationType.GRID_SUCCESS);
            case GRID_FAIL_CLOCK -> new Information(configuration, InformationType.GRID_FAIL_CLOCK);
        };
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        currentView.dispose();
        renderer.dispose();
    }
}
