package ch.fhnw.roundtable.etopia;

import ch.fhnw.roundtable.etopia.input.InputImpl;
import ch.fhnw.roundtable.etopia.minigames.infopanels.GameCompletedPanel;
import ch.fhnw.roundtable.etopia.minigames.map.MainMenu;
import ch.fhnw.roundtable.etopia.minigames.biomass.Biomass;
import ch.fhnw.roundtable.etopia.minigames.wind.Wind;
import ch.fhnw.roundtable.etopia.view.Menu;
import ch.fhnw.roundtable.etopia.view.MiniGame;
import ch.fhnw.roundtable.etopia.view.View;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.List;

/// Main game class that handles all the minigames and menus and rendering.
public class ETopia implements ApplicationListener {

    private Renderer renderer;
    private InputImpl input;

    private final List<View> views = List.of(
            new MainMenu(),
            new Biomass(new GameCompletedPanel("Gut gemacht.! Du hast genug Strom gesammelt.")),
            new Wind());
    private int currentView = 0;

    public static final int WORLD_WIDTH = 1920;
    public static final int WORLD_HEIGHT = 1080;

    // todo global game state, maybe via singleton

    @Override
    public void create() {
        // todo move magic values
        renderer = new Renderer(WORLD_WIDTH, WORLD_HEIGHT);
        input = new InputImpl();

        // todo rework this shit (very very bad)
        views.get(currentView).create();
    }

    @Override
    public void resize(int width, int height) {
        renderer.resize(width, height);
    }

    @Override
    public void render() {
        float delta = Gdx.graphics.getDeltaTime();

        ScreenUtils.clear(Color.BLACK);

        // get user input
        input.update();

        // update the current view
        views.get(currentView).update(delta, input);

        // render the current view
        renderer.begin();
        views.get(currentView).render(renderer);
        renderer.end();

        var nextView = -1;

        // Check if main menu wants to load a level
        if (views.get(currentView) instanceof Menu menu && menu.changeView() != null) {
            for (int i = 0; i < views.size(); i++) {
                if (menu.changeView().isInstance(views.get(i))) {
                    nextView = i;
                    menu.clearChangeViewRequest();
                    break;
                }
            }
        }

        // Check if minigame wants to return to menu
        if (views.get(currentView) instanceof MiniGame minigame) {
            if (minigame.isCompleted()) {
                nextView = 0;
            }
        }

        // Debug load level by index by pressing a number key
        if (Gdx.input.isKeyJustPressed(Keys.NUM_0) && views.size() > 0) nextView = 0;
        if (Gdx.input.isKeyJustPressed(Keys.NUM_1) && views.size() > 1) nextView = 1;
        if (Gdx.input.isKeyJustPressed(Keys.NUM_2) && views.size() > 2) nextView = 2;
        if (Gdx.input.isKeyJustPressed(Keys.NUM_3) && views.size() > 3) nextView = 3;
        if (Gdx.input.isKeyJustPressed(Keys.NUM_4) && views.size() > 4) nextView = 4;
        if (Gdx.input.isKeyJustPressed(Keys.NUM_5) && views.size() > 5) nextView = 5;
        if (Gdx.input.isKeyJustPressed(Keys.NUM_6) && views.size() > 6) nextView = 6;
        if (Gdx.input.isKeyJustPressed(Keys.NUM_7) && views.size() > 7) nextView = 7;
        if (Gdx.input.isKeyJustPressed(Keys.NUM_8) && views.size() > 8) nextView = 8;
        if (Gdx.input.isKeyJustPressed(Keys.NUM_9) && views.size() > 9) nextView = 9;

        // Load next level
        if (nextView != -1) {
            views.get(currentView).dispose();

            views.get(nextView).create();
            currentView = nextView;
        }
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        views.get(currentView).dispose();
        renderer.dispose();
    }
}
