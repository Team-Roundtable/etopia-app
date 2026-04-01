package ch.fhnw.roundtable.etopia;

import ch.fhnw.roundtable.etopia.input.InputImpl;
import ch.fhnw.roundtable.etopia.views.Renderer;
import ch.fhnw.roundtable.etopia.views.Scene;
import ch.fhnw.roundtable.etopia.views.SceneType;
import ch.fhnw.roundtable.etopia.views.geothermal.Geothermal;
import ch.fhnw.roundtable.etopia.views.grid.Grid;
import ch.fhnw.roundtable.etopia.views.biogas.Biogas;
import ch.fhnw.roundtable.etopia.views.map.Map;
import ch.fhnw.roundtable.etopia.views.solar.Solar;
import ch.fhnw.roundtable.etopia.views.wind.Wind;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.ScreenUtils;

public class ETopia implements ApplicationListener {

    public static final int WORLD_WIDTH = 1920;
    public static final int WORLD_HEIGHT = 1080;
    private Renderer renderer;
    private InputImpl input;
    private SceneType currentSceneType;
    private Scene<?> currentScene;

    // todo global game state, maybe via singleton

    @Override
    public void create() {
        renderer = new Renderer();
        input = new InputImpl();

        currentSceneType = SceneType.MAP;
        currentScene = new Map();
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
        currentScene.update(delta, input);
        currentScene.render(renderer);

        var next = getNextScene();
        if (next != null) {
            currentScene.dispose();

            currentSceneType = next;
            currentScene = switch (currentSceneType) {
                case MAP -> new Map();
                case WIND -> new Wind();
                case BIOMASS -> new Biogas();
                case GRID -> new Grid();
                case SOLAR -> new Solar();
                case GEOTHERMAL -> new Geothermal();
            };
        }
    }

    private SceneType getNextScene() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            return SceneType.MAP;
        }
        return currentScene.change();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        currentScene.dispose();
        renderer.dispose();
    }
}
