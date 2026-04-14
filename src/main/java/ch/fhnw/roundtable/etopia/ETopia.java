package ch.fhnw.roundtable.etopia;

import ch.fhnw.roundtable.etopia.configuration.Configuration;
import ch.fhnw.roundtable.etopia.input.InputImpl;
import ch.fhnw.roundtable.etopia.input.LEDControl;
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
import com.pi4j.Pi4J;
import com.pi4j.context.Context;
import com.pi4j.plugin.gpiod.provider.gpio.digital.GpioDDigitalInputProvider;
import com.pi4j.plugin.gpiod.provider.gpio.digital.GpioDDigitalOutputProvider;
import com.pi4j.plugin.linuxfs.provider.i2c.LinuxFsI2CProvider;

public class ETopia implements ApplicationListener {

    public static final int WORLD_WIDTH = 1920;
    public static final int WORLD_HEIGHT = 1080;
    private final Configuration configuration;
    private Context pi4j;
    private Renderer renderer;
    private InputImpl input;
    private LEDControl ledControl;
    private ViewType currentViewType;
    private View currentView;

    public ETopia(Configuration configuration) {
        this.configuration = configuration;
    }
    // todo global game state, maybe via singleton

    @Override
    public void create() {
        pi4j = Pi4J.newContextBuilder()
                .add(GpioDDigitalInputProvider.newInstance())
                .add(GpioDDigitalOutputProvider.newInstance())
                .add(LinuxFsI2CProvider.newInstance())
                .build();
        renderer = new Renderer();
        input = new InputImpl(pi4j);
        ledControl = new LEDControl(pi4j);

        try {
            Thread.sleep(100);
        } catch (InterruptedException ignored) {
        }
        ledControl.ledBackOn();

        currentViewType = ViewType.MAP;
        currentView = new Map(ledControl);
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
            case MAP -> new Map(ledControl);
            case INFORMATION -> new Information(configuration, InformationType.INFORMATION, ledControl);
            case SETTINGS -> new Map(ledControl);

            case BIOGAS -> new Biogas(ledControl);
            case BIOGAS_START -> new Information(configuration, InformationType.BIOGAS_START, ledControl);
            case BIOGAS_SUCCESS -> new Information(configuration, InformationType.BIOGAS_SUCCESS, ledControl);
            case BIOGAS_FAIL_HEALTH -> new Information(configuration, InformationType.BIOGAS_FAIL_HEALTH, ledControl);

            case WIND -> new Wind(configuration, ledControl);
            case WIND_START -> new Information(configuration, InformationType.WIND_START, ledControl);
            case WIND_SUCCESS -> new Information(configuration, InformationType.WIND_SUCCESS, ledControl);
            case WIND_FAIL_HEALTH -> new Information(configuration, InformationType.WIND_FAIL_HEALTH, ledControl);
            case WIND_FAIL_CLOCK -> new Information(configuration, InformationType.WIND_FAIL_CLOCK, ledControl);

            case GEOTHERMAL -> new Geothermal(ledControl);
            case GEOTHERMAL_START -> new Information(configuration, InformationType.GEOTHERMAL_START, ledControl);
            case GEOTHERMAL_SUCCESS -> new Information(configuration, InformationType.GEOTHERMAL_SUCCESS, ledControl);
            case GEOTHERMAL_FAIL_HEALTH ->
                    new Information(configuration, InformationType.GEOTHERMAL_FAIL_HEALTH, ledControl);

            case SOLAR -> new Solar(ledControl);
            case SOLAR_START -> new Information(configuration, InformationType.SOLAR_START, ledControl);
            case SOLAR_SUCCESS -> new Information(configuration, InformationType.SOLAR_SUCCESS, ledControl);
            case SOLAR_FAIL_CLOCK -> new Information(configuration, InformationType.SOLAR_FAIL_CLOCK, ledControl);

            case GRID -> new Grid(ledControl);
            case GRID_START -> new Information(configuration, InformationType.GRID_START, ledControl);
            case GRID_SUCCESS -> new Information(configuration, InformationType.GRID_SUCCESS, ledControl);
            case GRID_FAIL_CLOCK -> new Information(configuration, InformationType.GRID_FAIL_CLOCK, ledControl);
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
